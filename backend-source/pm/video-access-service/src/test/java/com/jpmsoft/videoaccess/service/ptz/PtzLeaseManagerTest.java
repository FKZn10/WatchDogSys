package com.jpmsoft.videoaccess.service.ptz;

import com.jpmsoft.videoaccess.api.ApiException;
import com.jpmsoft.videoaccess.dto.VideoRequests.*;
import com.jpmsoft.videoaccess.model.VideoModels.Camera;
import com.jpmsoft.videoaccess.port.VideoPorts.VideoGateway;
import org.junit.jupiter.api.*;
import java.math.BigDecimal;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class PtzLeaseManagerTest {
    final ControlActor alice = new ControlActor("tenant", "alice"), bob = new ControlActor("tenant", "bob");
    final Camera camera = new Camera("camera", "project", "test", "local", "device", "channel", true);
    VideoGateway gateway; ControlAudit audit; AtomicLong clock; PtzLeaseManager manager;
    @BeforeEach void setup() {
        gateway = mock(VideoGateway.class); audit = mock(ControlAudit.class); clock = new AtomicLong();
        manager = new PtzLeaseManager(gateway, audit, true, clock::get);
    }
    @AfterEach void cleanup() { manager.close(); }
    Ptz command(String lease, PtzAction action) {
        Ptz request = new Ptz(); request.setSscId(camera.sscId); request.setLeaseId(lease); request.setAction(action);
        request.setCommand(PtzCommand.UP); request.setSpeed(new BigDecimal("0.5")); return request;
    }
    void rejected(Runnable call, String code) {
        assertThatThrownBy(call::run).isInstanceOfSatisfying(ApiException.class, e -> assertThat(e.getCode()).isEqualTo(code));
    }
    @Test void disabledManagerNeverDispatches() {
        try (PtzLeaseManager disabled = new PtzLeaseManager(gateway, audit, false)) {
            rejected(() -> disabled.acquire(alice, camera), "PTZ_LEASE_DISABLED"); verifyNoInteractions(gateway);
        }
    }
    @Test void unknownAndFixedCameraCannotAcquire() {
        for (Boolean capability : new Boolean[]{null, false}) rejected(() -> manager.acquire(alice,
                new Camera("camera", "project", "test", "local", "device", "channel", capability)), "PTZ_CAPABILITY_UNKNOWN_OR_UNSUPPORTED");
        verifyNoInteractions(gateway);
    }
    @Test void samePhysicalChannelIsExclusiveAcrossAliasesAndTenants() {
        manager.acquire(alice, camera);
        Camera alias = new Camera("alias", "other-project", "test", "local", "device", "channel", true);
        rejected(() -> manager.acquire(new ControlActor("other-tenant", "bob"), alias), "PTZ_BUSY");
        verifyNoInteractions(gateway);
    }
    @Test void anotherUserTenantOrCameraCannotUseLease() {
        String id = manager.acquire(alice, camera).leaseId;
        rejected(() -> manager.command(bob, camera, command(id, PtzAction.START)), "PTZ_LEASE_FORBIDDEN");
        rejected(() -> manager.renew(new ControlActor("other", "alice"), camera, id), "PTZ_LEASE_FORBIDDEN");
        rejected(() -> manager.release(alice, camera, "guessed"), "PTZ_LEASE_FORBIDDEN");
        Camera alias = new Camera("alias", "project", "test", "local", "device", "channel", true);
        rejected(() -> manager.release(alice, alias, id), "PTZ_LEASE_FORBIDDEN");
        verifyNoInteractions(gateway);
    }
    @Test void renewalExtendsDeadlineAndExpiredOwnerCannotStopNewOwner() {
        String first = manager.acquire(alice, camera).leaseId;
        manager.command(alice, camera, command(first, PtzAction.START));
        clock.set(TimeUnit.SECONDS.toNanos(3)); manager.renew(alice, camera, first);
        clock.set(TimeUnit.SECONDS.toNanos(6)); rejected(() -> manager.acquire(bob, camera), "PTZ_BUSY");
        clock.set(TimeUnit.SECONDS.toNanos(8)); String second = manager.acquire(bob, camera).leaseId;
        assertThat(second).isNotEqualTo(first);
        rejected(() -> manager.command(alice, camera, command(first, PtzAction.STOP)), "PTZ_LEASE_FORBIDDEN");
        verify(gateway, times(3)).ptz(eq(camera), argThat(r -> r.getAction() == PtzAction.STOP));
    }
    @Test void startDoesNotImplicitlyRenewLease() {
        String id = manager.acquire(alice, camera).leaseId;
        clock.set(TimeUnit.SECONDS.toNanos(3)); manager.command(alice, camera, command(id, PtzAction.START));
        clock.set(TimeUnit.SECONDS.toNanos(5)); manager.acquire(bob, camera);
        verify(gateway, times(3)).ptz(eq(camera), argThat(r -> r.getAction() == PtzAction.STOP));
    }
    @Test void renewalCannotExceedHardSixtySecondLimit() {
        String id = manager.acquire(alice, camera).leaseId;
        manager.command(alice, camera, command(id, PtzAction.START));
        for (int second = 3; second < 60; second += 3) { clock.set(TimeUnit.SECONDS.toNanos(second)); manager.renew(alice, camera, id); }
        clock.set(TimeUnit.SECONDS.toNanos(60)); rejected(() -> manager.renew(alice, camera, id), "PTZ_LEASE_EXPIRED");
        verify(gateway, times(3)).ptz(eq(camera), argThat(r -> r.getAction() == PtzAction.STOP));
    }
    @Test void allFailedStopsQuarantineChannelAndCanBeRetriedByOwner() {
        String id = manager.acquire(alice, camera).leaseId;
        manager.command(alice, camera, command(id, PtzAction.START));
        doThrow(new IllegalStateException("synthetic transport failure")).when(gateway).ptz(eq(camera), argThat(r -> r.getAction() == PtzAction.STOP));
        rejected(() -> manager.release(alice, camera, id), "PTZ_STOP_UNCERTAIN");
        rejected(() -> manager.acquire(bob, camera), "PTZ_BUSY");
        rejected(() -> manager.command(alice, camera, command(id, PtzAction.START)), "PTZ_LEASE_EXPIRED");
        doNothing().when(gateway).ptz(eq(camera), argThat(r -> r.getAction() == PtzAction.STOP));
        manager.release(alice, camera, id); manager.acquire(bob, camera);
    }
    @Test void uncertainStartAlwaysAttemptsThreeStops() {
        String id = manager.acquire(alice, camera).leaseId;
        doThrow(new IllegalStateException("synthetic timeout")).when(gateway).ptz(eq(camera), argThat(r -> r.getAction() == PtzAction.START));
        rejected(() -> manager.command(alice, camera, command(id, PtzAction.START)), "PTZ_DISPATCH_UNCERTAIN");
        verify(gateway, times(3)).ptz(eq(camera), argThat(r -> r.getAction() == PtzAction.STOP));
        manager.acquire(bob, camera);
    }
    @Test void failedAuditPreventsStartButDoesNotPreventStop() {
        String id = manager.acquire(alice, camera).leaseId;
        doThrow(new IllegalStateException("audit unavailable")).when(audit).record(any(), any(), startsWith("START"), any());
        assertThatThrownBy(() -> manager.command(alice, camera, command(id, PtzAction.START))).isInstanceOf(IllegalStateException.class);
        verifyNoInteractions(gateway);
        reset(audit); manager.command(alice, camera, command(id, PtzAction.START));
        doThrow(new IllegalStateException("audit unavailable")).when(audit).record(any(), any(), any(), any());
        manager.release(alice, camera, id);
        verify(gateway, times(3)).ptz(eq(camera), argThat(r -> r.getAction() == PtzAction.STOP));
    }
    @Test void newOwnerCannotStartBeforeAllOldStopAttemptsFinish() throws Exception {
        String first = manager.acquire(alice, camera).leaseId;
        manager.command(alice, camera, command(first, PtzAction.START));
        CountDownLatch entered = new CountDownLatch(1), unblock = new CountDownLatch(1);
        AtomicInteger stops = new AtomicInteger();
        doAnswer(call -> { stops.incrementAndGet(); entered.countDown(); if (!unblock.await(3, TimeUnit.SECONDS)) throw new IllegalStateException(); return null; })
                .when(gateway).ptz(eq(camera), argThat(r -> r.getAction() == PtzAction.STOP));
        ExecutorService pool = Executors.newFixedThreadPool(2);
        try {
            Future<?> releasing = pool.submit(() -> manager.release(alice, camera, first));
            assertThat(entered.await(2, TimeUnit.SECONDS)).isTrue();
            Future<?> acquiring = pool.submit(() -> manager.acquire(bob, camera));
            assertThatThrownBy(() -> acquiring.get(100, TimeUnit.MILLISECONDS)).isInstanceOf(TimeoutException.class);
            unblock.countDown(); releasing.get(3, TimeUnit.SECONDS); acquiring.get(3, TimeUnit.SECONDS);
            assertThat(stops.get()).isEqualTo(3);
        } finally { unblock.countDown(); pool.shutdownNow(); }
    }
    @Test void realTimerStopsWhenCallerDisappears() throws Exception {
        CountDownLatch stops = new CountDownLatch(3);
        doAnswer(call -> { if (((Ptz)call.getArgument(1)).getAction() == PtzAction.STOP) stops.countDown(); return null; }).when(gateway).ptz(any(), any());
        try (PtzLeaseManager realTime = new PtzLeaseManager(gateway, audit, true)) {
            String id = realTime.acquire(alice, camera).leaseId;
            realTime.command(alice, camera, command(id, PtzAction.START));
            assertThat(stops.await(7, TimeUnit.SECONDS)).isTrue();
        }
    }
    @Test void shutdownStopsAndRejectsFutureControl() {
        String id = manager.acquire(alice, camera).leaseId;
        manager.command(alice, camera, command(id, PtzAction.START)); manager.close();
        verify(gateway, times(3)).ptz(eq(camera), argThat(r -> r.getAction() == PtzAction.STOP));
        rejected(() -> manager.acquire(bob, camera), "PTZ_LEASE_DISABLED");
    }
    @Test void slowStartThatOutlivesLeaseIsStoppedBeforeReturning() {
        String id = manager.acquire(alice, camera).leaseId;
        doAnswer(call -> { clock.set(TimeUnit.SECONDS.toNanos(5)); return null; })
                .when(gateway).ptz(eq(camera), argThat(r -> r.getAction() == PtzAction.START));
        rejected(() -> manager.command(alice, camera, command(id, PtzAction.START)), "PTZ_LEASE_EXPIRED");
        verify(gateway, times(3)).ptz(eq(camera), argThat(r -> r.getAction() == PtzAction.STOP));
    }
}
