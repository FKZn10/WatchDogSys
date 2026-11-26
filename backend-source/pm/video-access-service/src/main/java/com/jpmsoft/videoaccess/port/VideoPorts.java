package com.jpmsoft.videoaccess.port;

import com.jpmsoft.videoaccess.dto.VideoRequests.*;
import com.jpmsoft.videoaccess.model.VideoModels.*;
import java.util.List;
import java.util.Optional;

/** Ports separate business decisions from company auth/catalog and WVP HTTP details. */
public final class VideoPorts {
    private VideoPorts() { }

    public interface ProjectAccess {
        void requireIdentity();
        void requireProject(String pId);
        /** Platform-owned hook. Default is disconnected; explicit module mode delegates auth to the containing platform. */
        default com.jpmsoft.videoaccess.service.ptz.ControlActor requireControl(Camera camera) {
            throw new com.jpmsoft.videoaccess.api.ApiException(503, "CONTROL_ACCESS_NOT_CONFIGURED", "摄像头控制权限适配尚未接入");
        }
    }

    public interface CameraCatalog {
        CameraPage query(String pId, int page, int size);
        Optional<Camera> findById(String sscId);
    }

    public interface VideoGateway {
        StreamInfo preview(Camera camera, Preview request);
        void ptz(Camera camera, Ptz request);
        List<RecordingSegment> recordings(Camera camera, TimeRange request);
        StreamInfo startPlayback(Camera camera, TimeRange request);
        default void pausePlayback(String stream) { throw playbackUnavailable(); }
        default void resumePlayback(String stream) { throw playbackUnavailable(); }
        default void stopPlayback(Camera camera, String stream) { throw playbackUnavailable(); }
        default void cancelPlaybackStart(Camera camera, TimeRange range) { throw playbackUnavailable(); }
        default com.jpmsoft.videoaccess.api.ApiException playbackUnavailable() {
            return new com.jpmsoft.videoaccess.api.ApiException(503, "VIDEO_PLATFORM_NOT_CONFIGURED", "回放适配尚未配置");
        }
    }
}
