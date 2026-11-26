package com.jpmsoft.videoaccess.model;

import java.time.OffsetDateTime;
import java.util.List;

public final class VideoModels {
    private VideoModels() { }

    /** Internal data; never serialize this directly as a public camera response. */
    public static final class Camera {
        public final String sscId, pId, name, location, deviceId, channelId;
        public final Boolean ptzSupported;
        public Camera(String sscId, String pId, String name, String location, String deviceId, String channelId) {
            this(sscId, pId, name, location, deviceId, channelId, null);
        }
        public Camera(String sscId, String pId, String name, String location, String deviceId, String channelId, Boolean ptzSupported) {
            this.sscId = sscId; this.pId = pId; this.name = name; this.location = location;
            this.deviceId = deviceId; this.channelId = channelId;
            this.ptzSupported = ptzSupported;
        }
        public boolean isBound() { return present(deviceId) && present(channelId); }
        private boolean present(String value) { return value != null && !value.trim().isEmpty(); }
    }

    public static final class CameraPage {
        public final List<Camera> cameras;
        public final int total;
        public CameraPage(List<Camera> cameras, int total) { this.cameras = cameras; this.total = total; }
    }

    /** Whitelist fields for the Vue adapter; unknown is never converted into online. */
    public static final class CameraView {
        public final String id, projectId, name, location;
        public final Boolean ptzSupported;
        public final String status = "unknown";
        public CameraView(Camera camera) {
            id = camera.sscId; projectId = camera.pId; name = camera.name; location = camera.location;
            ptzSupported = camera.ptzSupported;
        }
    }

    public static final class StreamInfo {
        public final String sessionId, protocol, url;
        public final OffsetDateTime expiresAt;
        public StreamInfo(String sessionId, String protocol, String url, OffsetDateTime expiresAt) {
            this.sessionId = sessionId; this.protocol = protocol; this.url = url; this.expiresAt = expiresAt;
        }
    }

    public static final class RecordingSegment {
        public final String recordId;
        public final OffsetDateTime startTime, endTime;
        public RecordingSegment(String recordId, OffsetDateTime startTime, OffsetDateTime endTime) {
            this.recordId = recordId; this.startTime = startTime; this.endTime = endTime;
        }
    }
}
