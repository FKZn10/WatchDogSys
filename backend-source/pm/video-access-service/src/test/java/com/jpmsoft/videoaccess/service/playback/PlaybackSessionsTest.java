package com.jpmsoft.videoaccess.service.playback;
import com.jpmsoft.videoaccess.api.ApiException;
import com.jpmsoft.videoaccess.dto.VideoRequests.*;
import com.jpmsoft.videoaccess.model.VideoModels.*;
import com.jpmsoft.videoaccess.port.VideoPorts.VideoGateway;
import org.junit.jupiter.api.*;
import java.time.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlaybackSessionsTest {
    VideoGateway gateway = mock(VideoGateway.class);
    AtomicLong now = new AtomicLong(); PlaybackSessions sessions;
    Camera camera = camera("a", "channel");
    OffsetDateTime start = OffsetDateTime.parse("2026-11-04T08:00:00+08:00");
    private static Camera camera(String id, String channel) { return new Camera(id,"p","n","l","device",channel); }
    TimeRange range(OffsetDateTime from, OffsetDateTime to) { TimeRange r = new TimeRange(); r.setSscId("a"); r.setStartTime(from); r.setEndTime(to); return r; }
    TimeRange range() { return range(start,start.plusSeconds(120)); }
    PlaybackControl command(String id, PlaybackAction action, int revision) { PlaybackControl r=new PlaybackControl();r.setSscId("a");r.setPlaybackId(id);r.setAction(action);r.setRevision(revision);return r; }
    @BeforeEach void setup() {
        sessions=new PlaybackSessions(gateway,now::get);
        when(gateway.recordings(any(),any())).thenReturn(Collections.singletonList(new RecordingSegment("clip",start,start.plusSeconds(120))));
        when(gateway.startPlayback(any(),any())).thenAnswer(call -> {
            TimeRange r=call.getArgument(1); return new StreamInfo("vendor-"+r.getStartTime().toEpochSecond(),"HLS","http://127.0.0.1/hls.m3u8",null);
        });
    }
    @AfterEach void close() { sessions.close(); }
    @Test void opaqueHandleCannotBeUsedOnAnotherCameraAndAliasCannotStartOverIt() {
        PlaybackSessions.View view=sessions.start(camera,range()); assertThat(view.playbackId).doesNotStartWith("vendor-");
        assertThatThrownBy(()->sessions.renew(camera("alias","channel"),view.playbackId)).isInstanceOfSatisfying(ApiException.class,e->assertThat(e.getStatus()).isEqualTo(403));
        assertThatThrownBy(()->sessions.start(camera("alias","channel"),range())).isInstanceOfSatisfying(ApiException.class,e->assertThat(e.getCode()).isEqualTo("PLAYBACK_BUSY"));
        verify(gateway,times(1)).startPlayback(any(),any());
    }
    @Test void pauseAndResumeAreIdempotentAndUseOnlyOwnedUpstream() {
        PlaybackSessions.View view=sessions.start(camera,range());
        PlaybackControl pause=command(view.playbackId,PlaybackAction.PAUSE,1);
        assertThat(sessions.control(camera,pause).state).isEqualTo("PAUSED_REQUESTED"); sessions.control(camera,pause);
        sessions.control(camera,command(view.playbackId,PlaybackAction.RESUME,1));
        verify(gateway,times(1)).pausePlayback("vendor-"+start.toEpochSecond()); verify(gateway,times(1)).resumePlayback("vendor-"+start.toEpochSecond());
    }
    @Test void seekStopsOnlyOwnedSourceThenStartsTargetRangeAndRejectsStaleCommands() {
        PlaybackSessions.View before=sessions.start(camera,range());
        PlaybackControl seek=command(before.playbackId,PlaybackAction.SEEK,1);seek.setPositionTime(start.plusSeconds(80));
        PlaybackSessions.View after=sessions.control(camera,seek);
        assertThat(after.playbackId).isEqualTo(before.playbackId);assertThat(after.revision).isEqualTo(2);assertThat(after.mediaStartTime).isEqualTo(start.plusSeconds(80));
        org.mockito.InOrder order=inOrder(gateway);order.verify(gateway).recordings(any(),any());order.verify(gateway).startPlayback(any(),any());
        order.verify(gateway).stopPlayback(camera,"vendor-"+start.toEpochSecond());
        order.verify(gateway).startPlayback(eq(camera),argThat(r->r.getStartTime().equals(start.plusSeconds(80))&&r.getEndTime().equals(start.plusSeconds(120))));
        assertThatThrownBy(()->sessions.control(camera,command(before.playbackId,PlaybackAction.PAUSE,1))).isInstanceOfSatisfying(ApiException.class,e->assertThat(e.getCode()).isEqualTo("PLAYBACK_REVISION_MISMATCH"));
    }
    @Test void seekOutsideClipOrAtEndDoesNotStopCurrentPlayback() {
        PlaybackSessions.View view=sessions.start(camera,range());
        for(OffsetDateTime time:Arrays.asList(start.minusSeconds(1),start.plusSeconds(120),start.plusSeconds(1).plusNanos(1))) {
            PlaybackControl seek=command(view.playbackId,PlaybackAction.SEEK,1);seek.setPositionTime(time);
            assertThatThrownBy(()->sessions.control(camera,seek)).isInstanceOf(ApiException.class);
        }
        verify(gateway,never()).stopPlayback(any(),any());
    }
    @Test void emptyAndGappedRecordingsNeverStartAStream() {
        when(gateway.recordings(any(),any())).thenReturn(Collections.emptyList());
        assertThatThrownBy(()->sessions.start(camera,range())).isInstanceOfSatisfying(ApiException.class,e->assertThat(e.getCode()).isEqualTo("NO_RECORDINGS"));
        when(gateway.recordings(any(),any())).thenReturn(Arrays.asList(new RecordingSegment("a",start,start.plusSeconds(30)),new RecordingSegment("b",start.plusSeconds(60),start.plusSeconds(120))));
        assertThatThrownBy(()->sessions.start(camera,range())).isInstanceOfSatisfying(ApiException.class,e->assertThat(e.getCode()).isEqualTo("RECORDING_RANGE_UNAVAILABLE"));
        verify(gateway,never()).startPlayback(any(),any());
    }
    @Test void expiredPausedPlaybackIsStoppedAndCannotBeRevived() {
        PlaybackSessions.View view=sessions.start(camera,range());sessions.control(camera,command(view.playbackId,PlaybackAction.PAUSE,1));
        now.addAndGet(TimeUnit.MILLISECONDS.toNanos(30001));
        assertThatThrownBy(()->sessions.renew(camera,view.playbackId)).isInstanceOfSatisfying(ApiException.class,e->assertThat(e.getStatus()).isEqualTo(410));
        verify(gateway).stopPlayback(camera,"vendor-"+start.toEpochSecond());
        assertThatThrownBy(()->sessions.renew(camera,view.playbackId)).isInstanceOf(ApiException.class);
        sessions.start(camera,range());
    }
    @Test void failedStopQuarantinesChannelUntilCleanupSucceeds() {
        PlaybackSessions.View view=sessions.start(camera,range());doThrow(new ApiException(504,"timeout","timeout")).when(gateway).stopPlayback(any(),any());
        assertThatThrownBy(()->sessions.control(camera,command(view.playbackId,PlaybackAction.STOP,1))).isInstanceOf(ApiException.class);
        assertThatThrownBy(()->sessions.start(camera,range())).isInstanceOfSatisfying(ApiException.class,e->assertThat(e.getCode()).isEqualTo("PLAYBACK_BUSY"));
        doNothing().when(gateway).stopPlayback(any(),any());sessions.control(camera,command(view.playbackId,PlaybackAction.STOP,1));sessions.start(camera,range());
    }
    @Test void uncertainStartIsCancelledByExactCameraAndRange() {
        doThrow(new ApiException(504,"timeout","timeout")).when(gateway).startPlayback(any(),any());
        TimeRange range=range();assertThatThrownBy(()->sessions.start(camera,range)).isInstanceOf(ApiException.class);
        verify(gateway).cancelPlaybackStart(camera,range);
    }
    @Test void differentPhysicalChannelHasItsOwnPlayback() {
        PlaybackSessions.View a=sessions.start(camera,range()),b=sessions.start(camera("b","other-channel"),range());
        sessions.control(camera,command(a.playbackId,PlaybackAction.STOP,1));assertThat(sessions.renew(camera("b","other-channel"),b.playbackId).state).isEqualTo("PLAYING_REQUESTED");
    }
    @Test void invalidOrExcessiveTimeRangeNeverCallsVendor() {
        assertThatThrownBy(()->sessions.start(camera,range(start,start.plusHours(25)))).isInstanceOf(ApiException.class);
        assertThatThrownBy(()->sessions.start(camera,range(start.plusNanos(1),start.plusSeconds(60)))).isInstanceOf(ApiException.class);
        verifyNoInteractions(gateway);
    }
    @Test void shutdownDuringRecordQueryCannotCreateAnOrphanPlayback() throws Exception {
        CountDownLatch entered=new CountDownLatch(1),proceed=new CountDownLatch(1);
        doAnswer(call->{entered.countDown();proceed.await(3,TimeUnit.SECONDS);return Collections.singletonList(new RecordingSegment("clip",start,start.plusSeconds(120)));})
                .when(gateway).recordings(any(),any());
        ExecutorService pool=Executors.newSingleThreadExecutor();
        try {
            Future<?> opening=pool.submit(()->sessions.start(camera,range()));
            assertThat(entered.await(1,TimeUnit.SECONDS)).isTrue();sessions.close();proceed.countDown();
            assertThatThrownBy(()->opening.get(3,TimeUnit.SECONDS)).isInstanceOf(ExecutionException.class).hasCauseInstanceOf(ApiException.class);
            verify(gateway,never()).startPlayback(any(),any());
        } finally {proceed.countDown();pool.shutdownNow();}
    }
}
