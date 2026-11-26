package com.jpmsoft.videoaccess.api;

import com.jpmsoft.videoaccess.config.VideoProperties;
import com.jpmsoft.videoaccess.dto.VideoRequests.*;
import com.jpmsoft.videoaccess.model.VideoModels.*;
import com.jpmsoft.videoaccess.service.VideoService;
import com.jpmsoft.videoaccess.service.CameraQueryService;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/video/v1")
public class VideoController {
    private final VideoService service;
    private final CameraQueryService directory;
    private final VideoProperties properties;
    public VideoController(VideoService service, CameraQueryService directory, VideoProperties properties) {
        this.service = service; this.directory = directory; this.properties = properties;
    }

    @PostMapping("/cameras/query")
    public ApiResponse<List<CameraView>> cameras(@Valid @RequestBody CameraQuery request) {
        CameraPage page = directory.query(request);
        return ApiResponse.ok(page.cameras.stream().map(CameraView::new).collect(Collectors.toList()), page.total, properties.getMode());
    }
    @PostMapping("/preview")
    public ApiResponse<com.jpmsoft.videoaccess.service.preview.PreviewSessions.ViewingStream> preview(@Valid @RequestBody Preview request) {
        return ApiResponse.ok(service.preview(request), 0, properties.getMode());
    }
    @PostMapping("/preview/renew")
    public ApiResponse<com.jpmsoft.videoaccess.service.preview.PreviewSessions.LeaseView> renewPreview(@Valid @RequestBody PreviewSession request) {
        return ApiResponse.ok(service.renewPreview(request), 0, properties.getMode());
    }
    @PostMapping("/preview/release")
    public ApiResponse<com.jpmsoft.videoaccess.service.preview.PreviewSessions.ReleaseView> releasePreview(@Valid @RequestBody PreviewSession request) {
        return ApiResponse.ok(service.releasePreview(request), 0, properties.getMode());
    }
    @PostMapping("/ptz")
    public ApiResponse<com.jpmsoft.videoaccess.service.ptz.PtzLeaseManager.CommandView> ptz(@Valid @RequestBody Ptz request) {
        return ApiResponse.accepted(service.ptz(request), properties.getMode());
    }
    @PostMapping("/ptz/lease/acquire")
    public ApiResponse<com.jpmsoft.videoaccess.service.ptz.PtzLeaseManager.LeaseView> acquirePtz(@Valid @RequestBody CameraRequest request) {
        return ApiResponse.ok(service.acquirePtz(request), 0, properties.getMode());
    }
    @PostMapping("/ptz/lease/renew")
    public ApiResponse<com.jpmsoft.videoaccess.service.ptz.PtzLeaseManager.LeaseView> renewPtz(@Valid @RequestBody PtzLease request) {
        return ApiResponse.ok(service.renewPtz(request), 0, properties.getMode());
    }
    @PostMapping("/ptz/lease/release")
    public ApiResponse<com.jpmsoft.videoaccess.service.ptz.PtzLeaseManager.CommandView> releasePtz(@Valid @RequestBody PtzLease request) {
        return ApiResponse.accepted(service.releasePtz(request), properties.getMode());
    }
    @PostMapping("/recordings/query")
    public ApiResponse<List<RecordingSegment>> recordings(@Valid @RequestBody TimeRange request) {
        List<RecordingSegment> segments = service.recordings(request);
        return ApiResponse.ok(segments, segments.size(), properties.getMode());
    }
    @PostMapping("/playback/start")
    public ApiResponse<com.jpmsoft.videoaccess.service.playback.PlaybackSessions.View> playback(@Valid @RequestBody TimeRange request) {
        return ApiResponse.ok(service.startPlayback(request), 0, properties.getMode());
    }
    @PostMapping("/playback/control")
    public ApiResponse<com.jpmsoft.videoaccess.service.playback.PlaybackSessions.View> control(@Valid @RequestBody PlaybackControl request) {
        return ApiResponse.accepted(service.controlPlayback(request), properties.getMode());
    }
    @PostMapping("/playback/renew")
    public ApiResponse<com.jpmsoft.videoaccess.service.playback.PlaybackSessions.View> renewPlayback(@Valid @RequestBody PlaybackSession request) {
        return ApiResponse.ok(service.renewPlayback(request), 0, properties.getMode());
    }
}
