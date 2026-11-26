package com.jpmsoft.videoaccess.service.playback;

import com.jpmsoft.videoaccess.api.ApiException;
import com.jpmsoft.videoaccess.dto.VideoRequests.*;
import com.jpmsoft.videoaccess.model.VideoModels.*;
import com.jpmsoft.videoaccess.port.VideoPorts.VideoGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.PreDestroy;
import java.time.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.LongSupplier;

/** One managed playback per physical channel: pinned WVP uses non-unique range-derived stream IDs. */
public final class PlaybackSessions implements AutoCloseable {
    public static final long TTL_MS = 30000, RENEW_MS = 5000;
    private static final Logger log = LoggerFactory.getLogger(PlaybackSessions.class);
    private final VideoGateway gateway;
    private final LongSupplier nanoTime;
    private final Map<String, Slot> slots = new HashMap<>();
    private final ConcurrentMap<String, Lease> leases = new ConcurrentHashMap<>();
    private final ScheduledThreadPoolExecutor timer;
    private volatile boolean closed;
    private static final class Slot { final ReentrantLock lock = new ReentrantLock(); Lease lease; }
    private static final class Lease {
        final String id = UUID.randomUUID().toString(); final Camera camera; final Slot slot;
        final OffsetDateTime start, end; OffsetDateTime mediaStart;
        StreamInfo upstream; TimeRange pendingRange; long deadline; int revision = 1;
        String state = "STARTING"; ScheduledFuture<?> expiry;
        Lease(Camera camera, Slot slot, TimeRange range) {
            this.camera = camera; this.slot = slot; start = range.getStartTime(); end = range.getEndTime(); mediaStart = start;
        }
    }
    public static final class View {
        public final String playbackId, state, protocol, url, status = "ACCEPTED";
        public final String meaning = "服务端已受理；实际位置/暂停以播放器画面和设备为准";
        public final OffsetDateTime startTime, endTime, mediaStartTime, expiresAt;
        public final long remainingMs, renewAfterMs = RENEW_MS;
        public final int revision;
        View(Lease lease, long now) {
            playbackId = lease.id; state = lease.state; revision = lease.revision;
            startTime = lease.start; endTime = lease.end; mediaStartTime = lease.mediaStart;
            remainingMs = Math.max(0, TimeUnit.NANOSECONDS.toMillis(lease.deadline - now));
            expiresAt = OffsetDateTime.now(ZoneOffset.UTC).plusNanos(TimeUnit.MILLISECONDS.toNanos(remainingMs));
            protocol = lease.upstream == null ? null : lease.upstream.protocol;
            url = lease.upstream == null ? null : lease.upstream.url;
        }
    }
    public PlaybackSessions(VideoGateway gateway) { this(gateway, System::nanoTime); }
    PlaybackSessions(VideoGateway gateway, LongSupplier nanoTime) {
        this.gateway = gateway; this.nanoTime = nanoTime;
        timer = new ScheduledThreadPoolExecutor(4, task -> { Thread t = new Thread(task, "playback-expiry"); t.setDaemon(true); return t; });
        timer.setRemoveOnCancelPolicy(true);
    }
    public static void validateRange(TimeRange range) {
        if (range == null || range.getStartTime() == null || range.getEndTime() == null
                || !range.getEndTime().isAfter(range.getStartTime()) || range.getStartTime().getNano() != 0 || range.getEndTime().getNano() != 0
                || Duration.between(range.getStartTime(), range.getEndTime()).getSeconds() > 86400) {
            throw error(400, "INVALID_TIME_RANGE", "请选择精确到秒且不超过 24 小时的有效录像范围");
        }
    }
    private synchronized Slot slot(Camera camera) {
        requireOpen();
        if (camera == null || !camera.isBound()) throw error(409, "CAMERA_UNBOUND", "摄像头尚未绑定");
        String key = camera.deviceId.length() + ":" + camera.deviceId + camera.channelId;
        Slot slot = slots.get(key);
        if (slot == null) {
            if (slots.size() >= 512) throw error(503, "PLAYBACK_CAPACITY", "回放通道达到本机上限");
            slot = new Slot(); slots.put(key, slot);
        }
        return slot;
    }
    private void lock(Slot slot) {
        try { if (!slot.lock.tryLock(2, TimeUnit.SECONDS)) throw error(503, "PLAYBACK_BUSY", "回放操作正在处理，请稍后重试"); }
        catch (InterruptedException e) { Thread.currentThread().interrupt(); throw error(503, "PLAYBACK_BUSY", "回放操作已中断"); }
    }
    public View start(Camera camera, TimeRange range) {
        validateRange(range); Slot slot = slot(camera); lock(slot);
        try {
            requireOpen();
            if (slot.lease != null) throw error(409, "PLAYBACK_BUSY", "该通道已有回放或清理尚未完成，请结束后重试");
            List<RecordingSegment> segments = gateway.recordings(camera, range);
            if (segments.isEmpty()) throw error(404, "NO_RECORDINGS", "所选范围没有录像");
            if (segments.stream().noneMatch(segment -> !segment.startTime.isAfter(range.getStartTime()) && !segment.endTime.isBefore(range.getEndTime()))) {
                throw error(409, "RECORDING_RANGE_UNAVAILABLE", "所选范围跨越录像空洞，请选择单个有效片段");
            }
            Lease lease = new Lease(camera, slot, range);
            synchronized (this) { requireOpen(); slot.lease = lease; leases.put(lease.id, lease); }
            try { startSource(lease, range); requireOpen(); }
            catch (RuntimeException failed) { cleanup(lease); throw failed; }
            refresh(lease); return new View(lease, nanoTime.getAsLong());
        } finally { slot.lock.unlock(); }
    }
    private void startSource(Lease lease, TimeRange range) {
        lease.pendingRange = range;
        StreamInfo stream = gateway.startPlayback(lease.camera, range);
        if (stream == null || stream.sessionId == null || stream.url == null) throw error(502, "INVALID_MEDIA_RESPONSE", "回放平台未返回有效信息");
        lease.upstream = stream; lease.pendingRange = null; lease.mediaStart = range.getStartTime(); lease.state = "PLAYING_REQUESTED";
    }
    public View renew(Camera camera, String id) {
        Lease lease = owned(camera, id); lock(lease.slot);
        try { verifyActive(lease); refresh(lease); return new View(lease, nanoTime.getAsLong()); }
        finally { lease.slot.lock.unlock(); }
    }
    public View control(Camera camera, PlaybackControl request) {
        Lease lease = owned(camera, request.getPlaybackId()); lock(lease.slot);
        try {
            if (request.getAction() == PlaybackAction.STOP) {
                if (!cleanup(lease)) throw error(502, "PLAYBACK_CLEANUP_PENDING", "回放停止尚未确认，将继续清理");
                return new View(lease, nanoTime.getAsLong());
            }
            verifyActive(lease);
            if (request.getRevision() == null || request.getRevision() != lease.revision) throw error(409, "PLAYBACK_REVISION_MISMATCH", "回放位置已变化，请重新获取当前会话");
            try {
                if (request.getAction() == PlaybackAction.PAUSE && !"PAUSED_REQUESTED".equals(lease.state)) {
                    gateway.pausePlayback(lease.upstream.sessionId); lease.state = "PAUSED_REQUESTED";
                } else if (request.getAction() == PlaybackAction.RESUME && !"PLAYING_REQUESTED".equals(lease.state)) {
                    gateway.resumePlayback(lease.upstream.sessionId); lease.state = "PLAYING_REQUESTED";
                } else if (request.getAction() == PlaybackAction.SEEK) {
                    OffsetDateTime position = request.getPositionTime();
                    if (position == null || position.getNano() != 0 || position.isBefore(lease.start) || !position.isBefore(lease.end)) {
                        throw error(400, "PLAYBACK_SEEK_OUT_OF_RANGE", "定位时间必须在当前录像片段内，且早于结束时间");
                    }
                    // A fresh bounded INVITE and URL flush old HLS buffers, including backward seeks.
                    // This intentionally does not expose pinned WVP's INFO seek as a successful browser seek.
                    lease.revision++;
                    gateway.stopPlayback(lease.camera, lease.upstream.sessionId); lease.upstream = null;
                    TimeRange next = new TimeRange(); next.setSscId(camera.sscId); next.setStartTime(position); next.setEndTime(lease.end);
                    startSource(lease, next);
                } else if (request.getAction() == null) throw error(400, "INVALID_ARGUMENT", "缺少回放动作");
            } catch (ApiException failure) {
                if (failure.getStatus() < 500) throw failure;
                cleanup(lease); throw failure;
            } catch (RuntimeException failure) { cleanup(lease); throw failure; }
            refresh(lease); return new View(lease, nanoTime.getAsLong());
        } finally { lease.slot.lock.unlock(); }
    }
    private Lease owned(Camera camera, String id) {
        requireOpen(); Lease lease = id == null ? null : leases.get(id);
        if (lease == null) throw expired();
        Camera original = lease.camera;
        if (!Objects.equals(original.sscId, camera.sscId) || !Objects.equals(original.pId, camera.pId)
                || !Objects.equals(original.deviceId, camera.deviceId) || !Objects.equals(original.channelId, camera.channelId)) {
            throw error(403, "PLAYBACK_SESSION_MISMATCH", "回放会话与摄像头绑定不匹配");
        }
        return lease;
    }
    private void verifyActive(Lease lease) {
        requireOpen();
        if (lease.slot.lease != lease || "CLEANUP_PENDING".equals(lease.state) || nanoTime.getAsLong() >= lease.deadline) {
            if (lease.slot.lease == lease) cleanup(lease);
            throw expired();
        }
    }
    private void refresh(Lease lease) {
        lease.deadline = nanoTime.getAsLong() + TimeUnit.MILLISECONDS.toNanos(TTL_MS);
        schedule(lease, TTL_MS);
    }
    private void schedule(Lease lease, long delay) {
        if (lease.expiry != null) lease.expiry.cancel(false);
        if (closed) return;
        lease.expiry = timer.schedule(() -> {
            lease.slot.lock.lock();
            try {
                if (lease.slot.lease != lease) return;
                long left = TimeUnit.NANOSECONDS.toMillis(lease.deadline - nanoTime.getAsLong());
                if (left > 0 && !"CLEANUP_PENDING".equals(lease.state)) schedule(lease, left + 1);
                else cleanup(lease);
            } finally { lease.slot.lock.unlock(); }
        }, delay, TimeUnit.MILLISECONDS);
    }
    private boolean cleanup(Lease lease) {
        if (lease.slot.lease != lease) return true;
        lease.state = "CLEANUP_PENDING";
        try {
            if (lease.upstream != null) gateway.stopPlayback(lease.camera, lease.upstream.sessionId);
            else if (lease.pendingRange != null) gateway.cancelPlaybackStart(lease.camera, lease.pendingRange);
            lease.upstream = null; lease.pendingRange = null; lease.state = "STOPPED";
            if (lease.expiry != null) lease.expiry.cancel(false);
            leases.remove(lease.id, lease); lease.slot.lease = null; return true;
        } catch (RuntimeException failure) {
            // Keep the physical-channel slot quarantined; do not start another playback over an uncertain source.
            log.warn("Playback cleanup pending ({})", failure.getClass().getSimpleName()); schedule(lease, 3000); return false;
        }
    }
    private void requireOpen() { if (closed) throw error(503, "PLAYBACK_CLOSED", "回放服务正在关闭"); }
    private static ApiException expired() { return error(410, "PLAYBACK_SESSION_EXPIRED", "回放会话已结束，请重新播放"); }
    private static ApiException error(int code, String key, String message) { return new ApiException(code, key, message); }
    @Override @PreDestroy public void close() {
        synchronized (this) { closed = true; }
        for (Lease lease : leases.values()) { lease.slot.lock.lock(); try { cleanup(lease); } finally { lease.slot.lock.unlock(); } }
        timer.shutdownNow();
    }
}
