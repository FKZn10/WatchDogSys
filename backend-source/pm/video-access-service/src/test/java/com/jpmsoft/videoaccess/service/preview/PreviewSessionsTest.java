package com.jpmsoft.videoaccess.service.preview;

import com.jpmsoft.videoaccess.api.ApiException;
import com.jpmsoft.videoaccess.dto.VideoRequests.*;
import com.jpmsoft.videoaccess.model.VideoModels.*;
import com.jpmsoft.videoaccess.port.VideoPorts.VideoGateway;
import org.junit.jupiter.api.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class PreviewSessionsTest {
    VideoGateway gateway = mock(VideoGateway.class);
    AtomicLong now = new AtomicLong();
    PreviewSessions sessions;
    Camera camera = camera("a", "channel");
    private static Camera camera(String id, String channel) { return new Camera(id, "p", "n", "l", "device", channel); }
    private static Preview request() { Preview r = new Preview(); r.setSscId("a"); r.setStreamType(StreamType.MAIN); return r; }
    @BeforeEach void setup() {
        sessions = new PreviewSessions(gateway, now::get);
        when(gateway.preview(any(), any())).thenReturn(new StreamInfo("vendor-stream", "HLS", "http://127.0.0.1/test/hls.m3u8", null));
    }
    @AfterEach void close() { sessions.close(); }
    private void advance(long ms) { now.addAndGet(TimeUnit.MILLISECONDS.toNanos(ms)); }
    @Test void independentOpaqueTicketsReleaseOnlyOneViewerAndNeverDispatchMediaStop() {
        PreviewSessions.ViewingStream a = sessions.open(camera, request()), b = sessions.open(camera, request());
        assertThat(a.sessionId).isNotEqualTo(b.sessionId).isNotEqualTo("vendor-stream");
        assertThat(a.renewAfterMs).isEqualTo(5000); assertThat(a.remainingMs).isEqualTo(30000);
        assertThat(a.url).isEqualTo(b.url);
        sessions.release(camera, a.sessionId); sessions.release(camera, a.sessionId);
        assertThat(sessions.activeCount()).isEqualTo(1);
        assertThat(sessions.renew(camera, b.sessionId).remainingMs).isEqualTo(30000);
        verify(gateway, times(2)).preview(eq(camera), any(Preview.class));
        verifyNoMoreInteractions(gateway);
    }
    @Test void expiredViewerCannotRenewAndDoesNotExpireAnotherViewer() {
        String a = sessions.open(camera, request()).sessionId;
        advance(10000); String b = sessions.open(camera, request()).sessionId;
        advance(20001); sessions.expire();
        assertThat(sessions.activeCount()).isEqualTo(1);
        assertThatThrownBy(() -> sessions.renew(camera, a)).isInstanceOfSatisfying(ApiException.class, e -> assertThat(e.getStatus()).isEqualTo(410));
        sessions.renew(camera, b); advance(20000); sessions.expire();
        assertThat(sessions.activeCount()).isEqualTo(1);
        advance(10001); sessions.expire(); assertThat(sessions.activeCount()).isZero();
    }
    @Test void wrongCameraOrChangedBindingCannotReleaseSomeoneElsesTicket() {
        String id = sessions.open(camera, request()).sessionId;
        for (Camera mismatch : new Camera[] { camera("alias", "channel"), camera("a", "changed-channel"),
                new Camera("a", "other-project", "n", "l", "device", "channel") }) {
            assertThatThrownBy(() -> sessions.release(mismatch, id)).isInstanceOfSatisfying(ApiException.class, e -> assertThat(e.getStatus()).isEqualTo(403));
            assertThatThrownBy(() -> sessions.renew(mismatch, id)).isInstanceOf(ApiException.class);
        }
        assertThat(sessions.activeCount()).isEqualTo(1);
    }
    @Test void retriesAskWvpForCurrentStreamInsteadOfReusingAnExpiredMediaUrl() {
        sessions.open(camera, request());
        when(gateway.preview(any(), any())).thenReturn(new StreamInfo("replacement", "HLS", "http://127.0.0.1/new/hls.m3u8", null));
        assertThat(sessions.open(camera, request()).url).contains("/new/");
        assertThat(sessions.activeCount()).isEqualTo(2);
    }
    @Test void failedOrUnsupportedOpenDoesNotConsumeCapacityOrAffectExistingViewer() {
        String id = sessions.open(camera, request()).sessionId;
        when(gateway.preview(any(), any())).thenThrow(new ApiException(501, "WVP_STREAM_TYPE_UNSUPPORTED", "unsupported"));
        for (int i = 0; i < 130; i++) assertThatThrownBy(() -> sessions.open(camera, request())).isInstanceOf(ApiException.class);
        assertThat(sessions.activeCount()).isEqualTo(1); sessions.renew(camera, id);
    }
    @Test void perChannelBoundIncludesAliasesAndExpiredSlotsCanBeReused() {
        for (int i = 0; i < 128; i++) sessions.open(camera("alias-" + i, "channel"), request());
        assertThatThrownBy(() -> sessions.open(camera, request())).isInstanceOfSatisfying(ApiException.class, e -> assertThat(e.getCode()).isEqualTo("PREVIEW_CAPACITY"));
        advance(30001); sessions.expire();
        sessions.open(camera, request()); assertThat(sessions.activeCount()).isEqualTo(1);
    }
    @Test void simultaneousAliasOpensShareNegotiationAndBothRetainTheirOwnTicket() throws Exception {
        CountDownLatch entered = new CountDownLatch(1), proceed = new CountDownLatch(1);
        AtomicInteger inFlight = new AtomicInteger(), peak = new AtomicInteger();
        when(gateway.preview(any(), any())).thenAnswer(call -> {
            int count = inFlight.incrementAndGet(); peak.accumulateAndGet(count, Math::max); entered.countDown();
            try { assertThat(proceed.await(2, TimeUnit.SECONDS)).isTrue(); }
            finally { inFlight.decrementAndGet(); }
            return new StreamInfo("shared", "HLS", "http://127.0.0.1/hls.m3u8", null);
        });
        ExecutorService pool = Executors.newFixedThreadPool(2);
        try {
            Future<PreviewSessions.ViewingStream> a = pool.submit(() -> sessions.open(camera, request()));
            assertThat(entered.await(1, TimeUnit.SECONDS)).isTrue();
            Future<PreviewSessions.ViewingStream> b = pool.submit(() -> sessions.open(camera("alias", "channel"), request()));
            proceed.countDown();
            assertThat(a.get(3, TimeUnit.SECONDS).sessionId).isNotEqualTo(b.get(3, TimeUnit.SECONDS).sessionId);
            assertThat(peak.get()).isEqualTo(1); assertThat(sessions.activeCount()).isEqualTo(2);
        } finally { proceed.countDown(); pool.shutdownNow(); }
    }
    private static void waiting(List<Thread> threads, int count) throws InterruptedException {
        long deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(3);
        while (System.nanoTime() < deadline) {
            if (threads.size() == count && threads.stream().allMatch(t -> t.getState() == Thread.State.TIMED_WAITING)) return;
            Thread.sleep(10);
        }
        fail("All callers must enter the blocked negotiation before the fixture completes");
    }
    @Test void fifteenColdViewersWaitBeyondTwoSecondsShareOneNegotiationAndHaveIndependentTickets() throws Exception {
        CountDownLatch entered = new CountDownLatch(1), proceed = new CountDownLatch(1);
        List<Thread> threads = new CopyOnWriteArrayList<>();
        ExecutorService pool = Executors.newFixedThreadPool(15, task -> { Thread t = new Thread(task); threads.add(t); return t; });
        when(gateway.preview(any(), any())).thenAnswer(call -> {
            entered.countDown(); assertThat(proceed.await(8, TimeUnit.SECONDS)).isTrue();
            return new StreamInfo("shared", "HLS", "http://127.0.0.1/shared/hls.m3u8", null);
        });
        try {
            List<Future<PreviewSessions.ViewingStream>> opens = new ArrayList<>();
            for (int i = 0; i < 15; i++) {
                final int index = i;
                opens.add(pool.submit(() -> {
                    Preview r = request(); if (index % 2 == 0) r.setStreamType(StreamType.DEFAULT);
                    return sessions.open(camera("alias-" + index, "channel"), r);
                }));
            }
            assertThat(entered.await(2, TimeUnit.SECONDS)).isTrue(); waiting(threads, 15);
            Thread.sleep(2300); assertThat(opens).allMatch(f -> !f.isDone()); proceed.countDown();
            Set<String> tickets = new HashSet<>();
            for (int i = 0; i < opens.size(); i++) {
                PreviewSessions.ViewingStream stream = opens.get(i).get(3, TimeUnit.SECONDS);
                assertThat(stream.url).contains("/shared/"); tickets.add(stream.sessionId);
                sessions.release(camera("alias-" + i, "channel"), stream.sessionId);
            }
            assertThat(tickets).hasSize(15); assertThat(sessions.activeCount()).isZero();
            verify(gateway, times(1)).preview(any(), any()); verifyNoMoreInteractions(gateway);
        } finally { proceed.countDown(); pool.shutdownNow(); }
    }
    @Test void sharedFailureWakesAllWaitersAndNextOpenRetriesWithoutLeakingCapacity() throws Exception {
        CountDownLatch proceed = new CountDownLatch(1);
        List<Thread> threads = new CopyOnWriteArrayList<>();
        ExecutorService pool = Executors.newFixedThreadPool(15, task -> { Thread t = new Thread(task); threads.add(t); return t; });
        when(gateway.preview(any(), any())).thenAnswer(call -> { proceed.await(8, TimeUnit.SECONDS); throw new ApiException(504, "WVP_TIMEOUT", "fixture"); });
        try {
            List<Future<?>> opens = new ArrayList<>();
            for (int i = 0; i < 15; i++) opens.add(pool.submit(() -> sessions.open(camera, request())));
            waiting(threads, 15); proceed.countDown();
            for (Future<?> open : opens) assertThatThrownBy(() -> open.get(3, TimeUnit.SECONDS))
                    .isInstanceOf(ExecutionException.class).hasCauseInstanceOf(ApiException.class)
                    .satisfies(e -> assertThat(((ApiException)e.getCause()).getCode()).isEqualTo("WVP_TIMEOUT"));
            assertThat(sessions.activeCount()).isZero(); verify(gateway, times(1)).preview(any(), any());
            doReturn(new StreamInfo("new", "HLS", "http://127.0.0.1/new/hls.m3u8", null)).when(gateway).preview(any(), any());
            for (int i = 0; i < 128; i++) sessions.open(camera, request());
            assertThat(sessions.activeCount()).isEqualTo(128);
        } finally { proceed.countDown(); pool.shutdownNow(); }
    }
    @Test void differentPhysicalChannelsNegotiateConcurrently() throws Exception {
        CountDownLatch entered = new CountDownLatch(2), proceed = new CountDownLatch(1);
        ExecutorService pool = Executors.newFixedThreadPool(2);
        when(gateway.preview(any(), any())).thenAnswer(call -> { entered.countDown(); proceed.await(4, TimeUnit.SECONDS); return new StreamInfo("s", "HLS", "u", null); });
        try {
            Future<?> a = pool.submit(() -> sessions.open(camera, request()));
            Future<?> b = pool.submit(() -> sessions.open(camera("b", "other"), request()));
            assertThat(entered.await(2, TimeUnit.SECONDS)).isTrue(); proceed.countDown(); a.get(2, TimeUnit.SECONDS); b.get(2, TimeUnit.SECONDS);
            verify(gateway, times(2)).preview(any(), any());
        } finally { proceed.countDown(); pool.shutdownNow(); }
    }
    @Test void unsupportedSubCannotBorrowAnInflightDefaultResult() throws Exception {
        CountDownLatch entered = new CountDownLatch(1), proceed = new CountDownLatch(1);
        ExecutorService pool = Executors.newSingleThreadExecutor();
        when(gateway.preview(any(), any())).thenAnswer(call -> {
            Preview r = call.getArgument(1);
            if (r.getStreamType() == StreamType.SUB) throw new ApiException(501, "WVP_STREAM_TYPE_UNSUPPORTED", "fixture");
            entered.countDown(); proceed.await(4, TimeUnit.SECONDS); return new StreamInfo("s", "HLS", "u", null);
        });
        try {
            Future<?> a = pool.submit(() -> sessions.open(camera, request())); assertThat(entered.await(2, TimeUnit.SECONDS)).isTrue();
            Preview sub = request(); sub.setStreamType(StreamType.SUB);
            assertThatThrownBy(() -> sessions.open(camera, sub)).isInstanceOfSatisfying(ApiException.class, e -> assertThat(e.getStatus()).isEqualTo(501));
            proceed.countDown(); a.get(2, TimeUnit.SECONDS); assertThat(sessions.activeCount()).isEqualTo(1);
        } finally { proceed.countDown(); pool.shutdownNow(); }
    }
    @Test void interruptedFollowerDoesNotCancelTheOtherViewersNegotiation() throws Exception {
        CountDownLatch entered = new CountDownLatch(1), proceed = new CountDownLatch(1), exited = new CountDownLatch(1);
        List<Thread> threads = new CopyOnWriteArrayList<>();
        AtomicReference<String> result = new AtomicReference<>(); AtomicBoolean interrupted = new AtomicBoolean();
        ExecutorService pool = Executors.newFixedThreadPool(2, task -> { Thread t = new Thread(task); threads.add(t); return t; });
        when(gateway.preview(any(), any())).thenAnswer(call -> { entered.countDown(); proceed.await(5, TimeUnit.SECONDS); return new StreamInfo("s", "HLS", "u", null); });
        try {
            Future<PreviewSessions.ViewingStream> leader = pool.submit(() -> sessions.open(camera, request()));
            assertThat(entered.await(2, TimeUnit.SECONDS)).isTrue();
            pool.submit(() -> {
                try { sessions.open(camera, request()); }
                catch (ApiException e) { result.set(e.getCode()); interrupted.set(Thread.currentThread().isInterrupted()); }
                finally { exited.countDown(); }
            });
            waiting(threads, 2); threads.get(1).interrupt(); assertThat(exited.await(2, TimeUnit.SECONDS)).isTrue();
            assertThat(result.get()).isEqualTo("PREVIEW_BUSY"); assertThat(interrupted.get()).isTrue(); assertThat(leader.isDone()).isFalse();
            proceed.countDown(); sessions.release(camera, leader.get(2, TimeUnit.SECONDS).sessionId);
            assertThat(sessions.activeCount()).isZero(); verify(gateway, times(1)).preview(any(), any());
        } finally { proceed.countDown(); pool.shutdownNow(); }
    }
    @Test void shutdownImmediatelyWakesFollowersWithoutWaitingForUpstream() throws Exception {
        CountDownLatch entered = new CountDownLatch(1), proceed = new CountDownLatch(1);
        List<Thread> threads = new CopyOnWriteArrayList<>();
        ExecutorService pool = Executors.newFixedThreadPool(2, task -> { Thread t = new Thread(task); threads.add(t); return t; });
        when(gateway.preview(any(), any())).thenAnswer(call -> { entered.countDown(); proceed.await(5, TimeUnit.SECONDS); return new StreamInfo("s", "HLS", "u", null); });
        try {
            Future<?> leader = pool.submit(() -> sessions.open(camera, request())); assertThat(entered.await(2, TimeUnit.SECONDS)).isTrue();
            Future<?> follower = pool.submit(() -> sessions.open(camera, request())); waiting(threads, 2); sessions.close();
            assertThatThrownBy(() -> follower.get(1, TimeUnit.SECONDS)).hasCauseInstanceOf(ApiException.class);
            assertThat(leader.isDone()).isFalse(); proceed.countDown();
            assertThatThrownBy(() -> leader.get(2, TimeUnit.SECONDS)).hasCauseInstanceOf(ApiException.class);
            assertThat(sessions.activeCount()).isZero();
        } finally { proceed.countDown(); pool.shutdownNow(); }
    }
    @Test void shutdownRacingWithOpenCannotCreateAnOrphanTicket() throws Exception {
        CountDownLatch entered = new CountDownLatch(1), proceed = new CountDownLatch(1);
        when(gateway.preview(any(), any())).thenAnswer(call -> { entered.countDown(); proceed.await(2, TimeUnit.SECONDS); return new StreamInfo("s", "HLS", "u", null); });
        ExecutorService pool = Executors.newSingleThreadExecutor();
        try {
            Future<?> opening = pool.submit(() -> sessions.open(camera, request()));
            assertThat(entered.await(1, TimeUnit.SECONDS)).isTrue(); sessions.close(); proceed.countDown();
            assertThatThrownBy(() -> opening.get(3, TimeUnit.SECONDS)).isInstanceOf(ExecutionException.class).hasCauseInstanceOf(ApiException.class);
            assertThat(sessions.activeCount()).isZero();
        } finally { proceed.countDown(); pool.shutdownNow(); }
    }
}
