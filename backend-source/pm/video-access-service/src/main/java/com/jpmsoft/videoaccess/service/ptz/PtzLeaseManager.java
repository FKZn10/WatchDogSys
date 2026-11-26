package com.jpmsoft.videoaccess.service.ptz;

import com.jpmsoft.videoaccess.api.ApiException;
import com.jpmsoft.videoaccess.dto.VideoRequests.*;
import com.jpmsoft.videoaccess.model.VideoModels.Camera;
import com.jpmsoft.videoaccess.port.VideoPorts.VideoGateway;
import javax.annotation.PreDestroy;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.LongSupplier;

/** Single-process coordinator; service registration is independent of the one-instance limit. */
public final class PtzLeaseManager implements AutoCloseable {
    public static final long TTL_MS = 4000, MAX_HOLD_MS = 60000;
    private final VideoGateway gateway;
    private final ControlAudit audit;
    private final boolean enabled;
    private final LongSupplier nanoTime;
    private final ScheduledThreadPoolExecutor timers;
    private final Map<String, Slot> slots = new HashMap<>();
    private volatile boolean closed;

    public PtzLeaseManager(VideoGateway gateway, ControlAudit audit, boolean enabled) {
        this(gateway, audit, enabled, System::nanoTime);
    }
    PtzLeaseManager(VideoGateway gateway, ControlAudit audit, boolean enabled, LongSupplier nanoTime) {
        this.gateway = gateway; this.audit = audit; this.enabled = enabled; this.nanoTime = nanoTime;
        timers = new ScheduledThreadPoolExecutor(4, task -> {
            Thread thread = new Thread(task, "ptz-expiry"); thread.setDaemon(true); return thread;
        });
        timers.setRemoveOnCancelPolicy(true);
    }
    public static final class LeaseView {
        public final String leaseId, status = "LEASE_GRANTED";
        public final long renewAfterMs = 1000, remainingMs;
        LeaseView(Lease lease, long now) {
            leaseId = lease.id; remainingMs = Math.max(0, TimeUnit.NANOSECONDS.toMillis(lease.deadline - now));
        }
    }
    public static final class CommandView {
        public final String status = "ACCEPTED";
        public final String meaning = "服务端已受理，不代表设备已执行或云台已转动";
    }
    private static final class Slot { Lease lease; }
    private static final class Lease {
        final String id = UUID.randomUUID().toString();
        final ControlActor actor; final Camera camera; final long hardDeadline;
        long deadline; boolean moving, quarantined; ScheduledFuture<?> expiry;
        Lease(ControlActor actor, Camera camera, long now) {
            this.actor = actor; this.camera = camera;
            deadline = now + TimeUnit.MILLISECONDS.toNanos(TTL_MS);
            hardDeadline = now + TimeUnit.MILLISECONDS.toNanos(MAX_HOLD_MS);
        }
    }
    private synchronized Slot slot(Camera camera) {
        if (!enabled || closed) throw error(503, "PTZ_LEASE_DISABLED", "云台租约未启用或服务正在关闭");
        if (camera == null || !camera.isBound()) throw error(409, "CAMERA_UNBOUND", "摄像头尚未绑定");
        // Alias camera records and users/tenants must share one physical-channel lock.
        String key = camera.deviceId.length() + ":" + camera.deviceId + camera.channelId;
        Slot slot = slots.get(key);
        if (slot == null) {
            if (slots.size() >= 512) throw error(503, "PTZ_CAPACITY", "本地控制通道上限已达到");
            slot = new Slot(); slots.put(key, slot);
        }
        return slot;
    }
    public LeaseView acquire(ControlActor actor, Camera camera) {
        if (actor == null) throw error(403, "CONTROL_FORBIDDEN", "缺少可信控制身份");
        if (camera == null || !camera.isBound()) throw error(409, "CAMERA_UNBOUND", "摄像头尚未绑定");
        if (!Boolean.TRUE.equals(camera.ptzSupported)) {
            throw error(409, "PTZ_CAPABILITY_UNKNOWN_OR_UNSUPPORTED", "通道云台能力未确认或不支持");
        }
        Slot slot = slot(camera);
        synchronized (slot) {
            requireOpen();
            expireIfDue(slot, slot.lease);
            if (slot.lease != null) {
                safeAudit(actor, camera, "ACQUIRE", "BUSY");
                throw error(409, "PTZ_BUSY", "通道已有控制者或停止状态待确认");
            }
            Lease lease = new Lease(actor, camera, nanoTime.getAsLong());
            audit.record(actor, camera, "ACQUIRE", "GRANTED");
            slot.lease = lease; schedule(slot, lease);
            return new LeaseView(lease, nanoTime.getAsLong());
        }
    }
    public LeaseView renew(ControlActor actor, Camera camera, String id) {
        Slot slot = slot(camera);
        synchronized (slot) {
            requireOpen();
            Lease lease = owned(slot, actor, camera, id);
            expireIfDue(slot, lease);
            if (slot.lease != lease || lease.quarantined) throw expired();
            lease.deadline = Math.min(nanoTime.getAsLong() + TimeUnit.MILLISECONDS.toNanos(TTL_MS), lease.hardDeadline);
            schedule(slot, lease); return new LeaseView(lease, nanoTime.getAsLong());
        }
    }
    public CommandView command(ControlActor actor, Camera camera, Ptz request) {
        Slot slot = slot(camera);
        synchronized (slot) {
            requireOpen();
            Lease lease = owned(slot, actor, camera, request.getLeaseId());
            if (request.getAction() == PtzAction.STOP) {
                if (!stop(slot, lease, "EXPLICIT_STOP", false)) throw stopFailed();
                return new CommandView();
            }
            expireIfDue(slot, lease);
            if (slot.lease != lease || lease.quarantined) throw expired();
            if (request.getAction() != PtzAction.START || request.getCommand() == null || request.getSpeed() == null
                    || request.getSpeed().scale() > 3 || request.getSpeed().precision() > 4
                    || request.getSpeed().signum() < 0 || request.getSpeed().compareTo(java.math.BigDecimal.ONE) > 0) {
                throw error(400, "INVALID_ARGUMENT", "云台动作与速度不合法");
            }
            String action = "START_" + request.getCommand().name() + "_SPEED_" + request.getSpeed().toPlainString();
            audit.record(actor, lease.camera, action, "REQUESTED");
            lease.moving = true; // An HTTP timeout may still mean the device received START.
            try {
                gateway.ptz(lease.camera, request);
                safeAudit(actor, lease.camera, action, "DISPATCH_ACCEPTED");
            } catch (RuntimeException failure) {
                safeAudit(actor, lease.camera, action, "DISPATCH_UNCERTAIN");
                stop(slot, lease, "START_FAILURE_STOP", true);
                throw error(502, "PTZ_DISPATCH_UNCERTAIN", "云台指令结果不确定，已尝试停止");
            }
            expireIfDue(slot, lease);
            if (slot.lease != lease || lease.quarantined) throw expired();
            // Do not renew a lease implicitly with a command; renew is an explicit caller heartbeat.
            return new CommandView();
        }
    }
    public CommandView release(ControlActor actor, Camera camera, String id) {
        Slot slot = slot(camera);
        synchronized (slot) {
            requireOpen();
            Lease lease = owned(slot, actor, camera, id);
            if (!stop(slot, lease, "RELEASE_STOP", true)) throw stopFailed();
            return new CommandView();
        }
    }
    private Lease owned(Slot slot, ControlActor actor, Camera camera, String id) {
        Lease lease = slot.lease;
        if (lease == null || id == null || !lease.id.equals(id) || !lease.actor.equals(actor)
                || !lease.camera.sscId.equals(camera.sscId) || !lease.camera.pId.equals(camera.pId)) {
            throw error(403, "PTZ_LEASE_FORBIDDEN", "控制租约无效或不属于当前用户/摄像头");
        }
        return lease;
    }
    private void schedule(Slot slot, Lease lease) {
        if (lease.expiry != null) lease.expiry.cancel(false);
        lease.expiry = timers.schedule(() -> {
            synchronized (slot) { expireIfDue(slot, lease); }
        }, Math.max(0, lease.deadline - nanoTime.getAsLong()), TimeUnit.NANOSECONDS);
    }
    // Same function is exercised by deterministic deadline/race tests and real scheduler callbacks.
    void expireIfDue(Slot slot, Lease expected) {
        if (expected != null && !expected.quarantined && slot.lease == expected && nanoTime.getAsLong() >= expected.deadline) {
            stop(slot, expected, "WATCHDOG_STOP", true);
        }
    }
    private boolean stop(Slot slot, Lease lease, String reason, boolean release) {
        boolean accepted = !lease.moving;
        if (lease.moving) {
            Ptz command = new Ptz(); command.setSscId(lease.camera.sscId); command.setAction(PtzAction.STOP);
            // Keep this physical channel locked through all attempts; no stale STOP after a new owner's START.
            for (int i = 1; i <= 3; i++) {
                try {
                    gateway.ptz(lease.camera, command); accepted = true;
                    safeAudit(lease.actor, lease.camera, reason + "_" + i, "DISPATCH_ACCEPTED");
                } catch (RuntimeException failure) {
                    safeAudit(lease.actor, lease.camera, reason + "_" + i, "DISPATCH_FAILED");
                }
            }
        }
        if (accepted) {
            lease.moving = false; lease.quarantined = false;
            if (release) { if (lease.expiry != null) lease.expiry.cancel(false); slot.lease = null; }
        } else {
            lease.quarantined = true; // Do not hand the channel to another user after all STOP attempts fail.
            if (lease.expiry != null) lease.expiry.cancel(false);
        }
        safeAudit(lease.actor, lease.camera, reason, accepted ? "COMPLETED_DISPATCH" : "QUARANTINED");
        return accepted;
    }
    private void safeAudit(ControlActor actor, Camera camera, String action, String outcome) {
        try { audit.record(actor, camera, action, outcome); }
        catch (RuntimeException ignored) { /* An audit outage must never suppress the best-effort STOP. */ }
    }
    private static ApiException error(int status, String code, String message) { return new ApiException(status, code, message); }
    private void requireOpen() { if (closed) throw error(503, "PTZ_LEASE_DISABLED", "服务正在关闭"); }
    private static ApiException expired() { return error(409, "PTZ_LEASE_EXPIRED", "控制租约已到期或停止状态待确认"); }
    private static ApiException stopFailed() { return error(502, "PTZ_STOP_UNCERTAIN", "停止请求均失败，通道已禁止交给其他控制者"); }
    @PreDestroy @Override public void close() {
        closed = true;
        List<Slot> snapshot;
        synchronized (this) { snapshot = new ArrayList<>(slots.values()); }
        for (Slot slot : snapshot) synchronized (slot) {
            if (slot.lease != null) stop(slot, slot.lease, "SHUTDOWN_STOP", true);
        }
        timers.shutdownNow();
    }
}
