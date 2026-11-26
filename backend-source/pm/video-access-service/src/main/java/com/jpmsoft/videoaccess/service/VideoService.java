package com.jpmsoft.videoaccess.service;

import com.jpmsoft.videoaccess.api.ApiException;
import com.jpmsoft.videoaccess.dto.VideoRequests.*;
import com.jpmsoft.videoaccess.model.VideoModels.*;
import com.jpmsoft.videoaccess.port.VideoPorts.*;
import org.springframework.stereotype.Service;
import com.jpmsoft.videoaccess.service.preview.PreviewSessions;
import java.util.List;

@Service
public class VideoService {
    private final ProjectAccess access;
    private final CameraCatalog catalog;
    private final VideoGateway gateway;
    private final com.jpmsoft.videoaccess.service.ptz.PtzLeaseManager ptz;
    private final PreviewSessions previews;
    private final com.jpmsoft.videoaccess.service.playback.PlaybackSessions playbacks;

    public VideoService(ProjectAccess access, CameraCatalog catalog, VideoGateway gateway, com.jpmsoft.videoaccess.service.ptz.PtzLeaseManager ptz, PreviewSessions previews, com.jpmsoft.videoaccess.service.playback.PlaybackSessions playbacks) {
        this.access = access; this.catalog = catalog; this.gateway = gateway;
        this.ptz = ptz;
        this.previews = previews;
        this.playbacks = playbacks;
    }

    private Camera authorizedBoundCamera(String sscId) {
        access.requireIdentity();
        Camera camera = catalog.findById(sscId)
                .orElseThrow(() -> new ApiException(404, "CAMERA_NOT_FOUND", "摄像头不存在或尚未接入资料"));
        access.requireProject(camera.pId);
        if (!camera.isBound()) {
            throw new ApiException(409, "CAMERA_UNBOUND", "摄像头尚未绑定国标设备和通道");
        }
        return camera;
    }

    public PreviewSessions.ViewingStream preview(Preview request) { return previews.open(authorizedBoundCamera(request.getSscId()), request); }
    public PreviewSessions.LeaseView renewPreview(PreviewSession request) {
        return previews.renew(authorizedBoundCamera(request.getSscId()), request.getSessionId());
    }
    public PreviewSessions.ReleaseView releasePreview(PreviewSession request) {
        return previews.release(authorizedBoundCamera(request.getSscId()), request.getSessionId());
    }
    public com.jpmsoft.videoaccess.service.ptz.PtzLeaseManager.CommandView ptz(Ptz request) {
        Camera camera = authorizedBoundCamera(request.getSscId());
        return ptz.command(access.requireControl(camera), camera, request);
    }
    public com.jpmsoft.videoaccess.service.ptz.PtzLeaseManager.LeaseView acquirePtz(CameraRequest request) {
        Camera camera = authorizedBoundCamera(request.getSscId());
        return ptz.acquire(access.requireControl(camera), camera);
    }
    public com.jpmsoft.videoaccess.service.ptz.PtzLeaseManager.LeaseView renewPtz(PtzLease request) {
        Camera camera = authorizedBoundCamera(request.getSscId());
        return ptz.renew(access.requireControl(camera), camera, request.getLeaseId());
    }
    public com.jpmsoft.videoaccess.service.ptz.PtzLeaseManager.CommandView releasePtz(PtzLease request) {
        Camera camera = authorizedBoundCamera(request.getSscId());
        return ptz.release(access.requireControl(camera), camera, request.getLeaseId());
    }
    public List<RecordingSegment> recordings(TimeRange request) {
        Camera camera = authorizedBoundCamera(request.getSscId());
        com.jpmsoft.videoaccess.service.playback.PlaybackSessions.validateRange(request);
        return gateway.recordings(camera, request);
    }
    public com.jpmsoft.videoaccess.service.playback.PlaybackSessions.View startPlayback(TimeRange request) {
        return playbacks.start(authorizedBoundCamera(request.getSscId()), request);
    }
    public com.jpmsoft.videoaccess.service.playback.PlaybackSessions.View controlPlayback(PlaybackControl request) {
        return playbacks.control(authorizedBoundCamera(request.getSscId()), request);
    }
    public com.jpmsoft.videoaccess.service.playback.PlaybackSessions.View renewPlayback(PlaybackSession request) {
        return playbacks.renew(authorizedBoundCamera(request.getSscId()), request.getPlaybackId());
    }
}
