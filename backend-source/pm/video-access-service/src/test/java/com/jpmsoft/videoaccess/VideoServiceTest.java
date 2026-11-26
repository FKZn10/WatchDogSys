package com.jpmsoft.videoaccess;

import com.jpmsoft.videoaccess.api.ApiException;
import com.jpmsoft.videoaccess.config.AdapterConfiguration;
import com.jpmsoft.videoaccess.dto.VideoRequests.*;
import com.jpmsoft.videoaccess.model.VideoModels.Camera;
import com.jpmsoft.videoaccess.port.VideoPorts.*;
import com.jpmsoft.videoaccess.service.VideoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class VideoServiceTest {
    ProjectAccess access;
    CameraCatalog catalog;
    VideoGateway gateway;
    VideoService service;
    com.jpmsoft.videoaccess.service.preview.PreviewSessions previews;
    com.jpmsoft.videoaccess.service.playback.PlaybackSessions playbacks;
    @BeforeEach void setup() {
        access = mock(ProjectAccess.class); catalog = mock(CameraCatalog.class); gateway = mock(VideoGateway.class);
        previews = new com.jpmsoft.videoaccess.service.preview.PreviewSessions(gateway);
        playbacks = new com.jpmsoft.videoaccess.service.playback.PlaybackSessions(gateway);
        service = new VideoService(access, catalog, gateway, new com.jpmsoft.videoaccess.service.ptz.PtzLeaseManager(gateway, (a,c,x,o) -> {}, false), previews, playbacks);
    }
    @AfterEach void cleanup() { previews.close(); playbacks.close(); }
    private Preview preview() { Preview request = new Preview(); request.setSscId("camera"); return request; }

    @Test void verifiesIdentityBeforeReadingCameraData() {
        doThrow(new ApiException(503, "IDENTITY_NOT_CONFIGURED", "unavailable")).when(access).requireIdentity();
        assertThatThrownBy(() -> service.preview(preview())).isInstanceOf(ApiException.class);
        verifyNoInteractions(catalog, gateway);
    }
    @Test void rejectsCrossProjectCameraBeforeCallingMedia() {
        when(catalog.findById("camera")).thenReturn(Optional.of(new Camera("camera", "other-project", "test", "test", "device", "channel")));
        doThrow(new ApiException(403, "PROJECT_FORBIDDEN", "forbidden")).when(access).requireProject("other-project");
        assertThatThrownBy(() -> service.preview(preview())).isInstanceOfSatisfying(ApiException.class,
                e -> org.assertj.core.api.Assertions.assertThat(e.getStatus()).isEqualTo(403));
        verifyNoInteractions(gateway);
    }
    @Test void unboundCameraIsNotConvertedToAnInventedDeviceId() {
        when(catalog.findById("camera")).thenReturn(Optional.of(new Camera("camera", "project", "test", "test", null, null)));
        assertThatThrownBy(() -> service.preview(preview())).isInstanceOfSatisfying(ApiException.class,
                e -> org.assertj.core.api.Assertions.assertThat(e.getCode()).isEqualTo("CAMERA_UNBOUND"));
        verifyNoInteractions(gateway);
    }
    @Test void boundCameraStillReportsMissingPlatformAdapter() {
        when(catalog.findById("camera")).thenReturn(Optional.of(new Camera("camera", "project", "test", "test", "device", "channel")));
        VideoGateway disconnected = new AdapterConfiguration().videoGateway();
        try (com.jpmsoft.videoaccess.service.preview.PreviewSessions disabledPreviews = new com.jpmsoft.videoaccess.service.preview.PreviewSessions(disconnected)) {
            VideoService disabled = new VideoService(access, catalog, disconnected, new com.jpmsoft.videoaccess.service.ptz.PtzLeaseManager(gateway, (a,c,x,o) -> {}, false), disabledPreviews, playbacks);
            assertThatThrownBy(() -> disabled.preview(preview())).isInstanceOfSatisfying(ApiException.class,
                    e -> org.assertj.core.api.Assertions.assertThat(e.getCode()).isEqualTo("VIDEO_PLATFORM_NOT_CONFIGURED"));
        }
    }
    @Test void neverControlsAnUnverifiedPlaybackSession() {
        when(catalog.findById("camera")).thenReturn(Optional.of(new Camera("camera", "project", "test", "test", "device", "channel")));
        PlaybackControl request = new PlaybackControl(); request.setSscId("camera");
        request.setPlaybackId("someone-elses-session"); request.setAction(PlaybackAction.STOP);
        assertThatThrownBy(() -> service.controlPlayback(request)).isInstanceOfSatisfying(ApiException.class,
                e -> org.assertj.core.api.Assertions.assertThat(e.getCode()).isEqualTo("PLAYBACK_SESSION_EXPIRED"));
        verifyNoInteractions(gateway);
    }
    @Test void projectPermissionAloneCannotGrantPtzControl() {
        Camera camera = new Camera("camera", "project", "test", "local", "device", "channel", true);
        when(catalog.findById("camera")).thenReturn(Optional.of(camera));
        doThrow(new ApiException(403, "CONTROL_FORBIDDEN", "forbidden")).when(access).requireControl(camera);
        CameraRequest request = new CameraRequest(); request.setSscId("camera");
        assertThatThrownBy(() -> service.acquirePtz(request)).isInstanceOfSatisfying(ApiException.class,
                e -> org.assertj.core.api.Assertions.assertThat(e.getCode()).isEqualTo("CONTROL_FORBIDDEN"));
        verifyNoInteractions(gateway);
    }
    @Test void unimplementedControlPortDefaultsToDeny() {
        ProjectAccess viewOnly = new ProjectAccess() {
            public void requireIdentity() { }
            public void requireProject(String projectId) { }
        };
        assertThatThrownBy(() -> viewOnly.requireControl(new Camera("camera", "p", "n", "l", "d", "c", true)))
                .isInstanceOfSatisfying(ApiException.class,
                        e -> org.assertj.core.api.Assertions.assertThat(e.getCode()).isEqualTo("CONTROL_ACCESS_NOT_CONFIGURED"));
    }
}
