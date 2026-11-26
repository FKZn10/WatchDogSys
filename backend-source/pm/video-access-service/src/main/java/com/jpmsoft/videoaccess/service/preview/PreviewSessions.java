package com.jpmsoft.videoaccess.service.preview;

import com.jpmsoft.videoaccess.api.ApiException;
import com.jpmsoft.videoaccess.dto.VideoRequests.Preview;
import com.jpmsoft.videoaccess.dto.VideoRequests.StreamType;
import com.jpmsoft.videoaccess.model.VideoModels.Camera;
import com.jpmsoft.videoaccess.model.VideoModels.StreamInfo;
import com.jpmsoft.videoaccess.port.VideoPorts.VideoGateway;
import javax.annotation.PreDestroy;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.LongSupplier;

/** Local viewer tickets, not media authorization. WVP/ZLM alone close streams on zero actual readers. */
public final class PreviewSessions implements AutoCloseable {
    public static final long TTL_MS = 30000, RENEW_MS = 5000;
    private static final int MAX_SOURCES = 512, MAX_PER_SOURCE = 128, MAX_VIEWERS = 4096;
    private final VideoGateway gateway;
    private final LongSupplier nanoTime;
    private final Map<String, Source> sources = new HashMap<>();
    private final ConcurrentMap<String, Lease> leases = new ConcurrentHashMap<>();
    private final Semaphore capacity = new Semaphore(MAX_VIEWERS);
    private final ScheduledExecutorService timer;
    private volatile boolean closed;

    public PreviewSessions(VideoGateway gateway) { this(gateway, System::nanoTime); }
    PreviewSessions(VideoGateway gateway, LongSupplier nanoTime) {
        this.gateway = gateway; this.nanoTime = nanoTime;
        timer = Executors.newSingleThreadScheduledExecutor(task -> {
            Thread thread = new Thread(task, "preview-expiry"); thread.setDaemon(true); return thread;
        });
        timer.scheduleWithFixedDelay(this::expire, 1, 1, TimeUnit.SECONDS);
    }
    private static final class Source {
        final Map<StreamType, CompletableFuture<StreamInfo>> opening = new HashMap<>();
        final AtomicInteger viewers = new AtomicInteger();
    }
    private static final class Lease {
        final String id = UUID.randomUUID().toString();
        final Camera camera; final Source source;
        long deadline;
        Lease(Camera camera, Source source, long now) {
            this.camera = camera; this.source = source; deadline = now + TimeUnit.MILLISECONDS.toNanos(TTL_MS);
        }
    }
    public static class LeaseView {
        public final String sessionId;
        public final long remainingMs, renewAfterMs = RENEW_MS;
        public final OffsetDateTime expiresAt;
        LeaseView(Lease lease, long now) {
            sessionId = lease.id;
            remainingMs = Math.max(0, TimeUnit.NANOSECONDS.toMillis(lease.deadline - now));
            expiresAt = OffsetDateTime.now(java.time.ZoneOffset.UTC).plusNanos(TimeUnit.MILLISECONDS.toNanos(remainingMs));
        }
    }
    public static final class ViewingStream extends LeaseView {
        public final String protocol, url;
        public final String streamSelection = "CHANNEL_DEFAULT";
        // A requested SDP attribute is not proof of a physical camera's actual encoder profile.
        public final String actualStreamType = "UNKNOWN";
        ViewingStream(Lease lease, StreamInfo upstream, long now) {
            super(lease, now); protocol = upstream.protocol; url = upstream.url;
        }
    }
    public static final class ReleaseView {
        public final String status = "RELEASED";
        public final String meaning = "仅释放本次观看；共享流由媒体服务在无人观看后关闭";
    }
    private synchronized Source source(Camera camera) {
        requireOpen();
        if (camera == null || !camera.isBound()) throw error(409, "CAMERA_UNBOUND", "摄像头尚未绑定");
        String key = camera.deviceId.length() + ":" + camera.deviceId + camera.channelId;
        Source source = sources.get(key);
        if (source == null) {
            if (sources.size() >= MAX_SOURCES) throw busy();
            source = new Source(); sources.put(key, source);
        }
        return source;
    }
    public ViewingStream open(Camera camera, Preview request) {
        Source source = source(camera);
        if (!capacity.tryAcquire()) throw busy();
        if (source.viewers.incrementAndGet() > MAX_PER_SOURCE) {
            source.viewers.decrementAndGet(); capacity.release(); throw busy();
        }
        boolean transferred = false;
        try {
            requireOpen();
            StreamInfo upstream = negotiate(source, camera, request);
            synchronized (this) {
                requireOpen();
                Lease lease = new Lease(camera, source, nanoTime.getAsLong());
                leases.put(lease.id, lease); transferred = true;
                return new ViewingStream(lease, upstream, nanoTime.getAsLong());
            }
        } finally {
            if (!transferred) { source.viewers.decrementAndGet(); capacity.release(); }
        }
    }
    private StreamInfo negotiate(Source source, Camera camera, Preview request) {
        if (request == null || request.getStreamType() == null) throw error(400, "INVALID_ARGUMENT", "缺少播放档位");
        // Only overlap in-flight requests. Later opens must ask WVP for a current URL.
        // Unsupported/different profiles must never join a successful DEFAULT request.
        StreamType key = request.getStreamType() == StreamType.MAIN ? StreamType.DEFAULT : request.getStreamType();
        CompletableFuture<StreamInfo> flight;
        boolean leader;
        synchronized (source) {
            requireOpen(); flight = source.opening.get(key); leader = flight == null;
            if (leader) { flight = new CompletableFuture<>(); source.opening.put(key, flight); }
        }
        if (leader) {
            try {
                StreamInfo upstream = gateway.preview(camera, request);
                if (upstream == null || upstream.url == null || upstream.protocol == null) {
                    throw error(502, "INVALID_MEDIA_RESPONSE", "视频平台未返回有效播放信息");
                }
                flight.complete(upstream);
            } catch (RuntimeException | Error failed) { flight.completeExceptionally(failed); }
            finally { synchronized (source) { source.opening.remove(key, flight); } }
        }
        try {
            // Upstream HTTP has its own bounded timeout; followers do not queue another negotiation.
            return flight.get(20, TimeUnit.SECONDS);
        } catch (InterruptedException interrupted) {
            Thread.currentThread().interrupt(); throw error(503, "PREVIEW_BUSY", "播放申请已中断，请重试");
        } catch (TimeoutException timeout) {
            // Do not cancel a shared negotiation because just one viewer stopped waiting.
            throw error(504, "PREVIEW_TIMEOUT", "等待播放信息超时，请重试");
        } catch (ExecutionException failed) {
            Throwable cause = failed.getCause();
            if (cause instanceof RuntimeException) throw (RuntimeException) cause;
            if (cause instanceof Error) throw (Error) cause;
            throw error(502, "INVALID_MEDIA_RESPONSE", "播放申请未完成，请重试");
        }
    }
    public LeaseView renew(Camera camera, String sessionId) {
        requireOpen();
        Lease lease = leases.get(sessionId);
        if (lease == null) throw expired();
        synchronized (lease) {
            requireOwner(lease, camera);
            if (leases.get(sessionId) != lease || nanoTime.getAsLong() >= lease.deadline) {
                remove(lease); throw expired();
            }
            requireOpen();
            lease.deadline = nanoTime.getAsLong() + TimeUnit.MILLISECONDS.toNanos(TTL_MS);
            return new LeaseView(lease, nanoTime.getAsLong());
        }
    }
    public ReleaseView release(Camera camera, String sessionId) {
        requireOpen();
        Lease lease = leases.get(sessionId);
        if (lease != null) synchronized (lease) { requireOwner(lease, camera); remove(lease); }
        // Unknown/expired/repeated releases are idempotent and cannot affect any other ticket.
        // Deliberately no gateway stop call: even another process or an external WVP reader must remain playing.
        return new ReleaseView();
    }
    private void requireOwner(Lease lease, Camera camera) {
        Camera original = lease.camera;
        if (!Objects.equals(original.sscId, camera.sscId) || !Objects.equals(original.pId, camera.pId)
                || !Objects.equals(original.deviceId, camera.deviceId) || !Objects.equals(original.channelId, camera.channelId)) {
            throw error(403, "PREVIEW_SESSION_MISMATCH", "观看会话与当前摄像头不匹配");
        }
    }
    void expire() {
        for (Lease lease : leases.values()) synchronized (lease) {
            if (nanoTime.getAsLong() >= lease.deadline) remove(lease);
        }
    }
    private void remove(Lease lease) {
        if (leases.remove(lease.id, lease)) { lease.source.viewers.decrementAndGet(); capacity.release(); }
    }
    private void requireOpen() { if (closed) throw error(503, "PREVIEW_CLOSED", "观看会话服务正在关闭"); }
    private static ApiException expired() { return error(410, "PREVIEW_SESSION_EXPIRED", "观看会话已结束，请重新播放"); }
    private static ApiException busy() { return error(503, "PREVIEW_CAPACITY", "观看会话已达到本机上限，请稍后重试"); }
    private static ApiException error(int status, String code, String message) { return new ApiException(status, code, message); }
    int activeCount() { return leases.size(); }
    @Override @PreDestroy public void close() {
        List<Source> pending;
        synchronized (this) { closed = true; pending = new ArrayList<>(sources.values()); }
        for (Source source : pending) synchronized (source) {
            for (CompletableFuture<StreamInfo> flight : source.opening.values()) {
                flight.completeExceptionally(error(503, "PREVIEW_CLOSED", "观看会话服务正在关闭"));
            }
        }
        timer.shutdownNow();
        for (Lease lease : leases.values()) synchronized (lease) { remove(lease); }
    }
}
