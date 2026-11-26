package com.jpmsoft.videoaccess.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

/** Each nested class is an HTTP request DTO. These are not database entities. */
public final class VideoRequests {
    private VideoRequests() { }

    /** DEFAULT uses the server-maintained channel profile. MAIN is a legacy alias, not a physical-main guarantee. */
    public enum StreamType { DEFAULT, MAIN, SUB }
    public enum PtzAction { START, STOP }
    public enum PtzCommand { UP, DOWN, LEFT, RIGHT, UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT, ZOOM_IN, ZOOM_OUT }
    public enum PlaybackAction { PAUSE, RESUME, SEEK, STOP }

    public static class CameraQuery {
        @NotBlank @Size(max = 64)
        private String pId;
        @Min(1)
        private int page = 1;
        @Min(1) @Max(100)
        private int size = 20;
        public String getpId() { return pId; }
        public void setpId(String pId) { this.pId = pId; }
        public int getPage() { return page; }
        public void setPage(int page) { this.page = page; }
        public int getSize() { return size; }
        public void setSize(int size) { this.size = size; }
    }

    public static class CameraRequest {
        @NotBlank @Size(max = 64)
        private String sscId;
        public String getSscId() { return sscId; }
        public void setSscId(String sscId) { this.sscId = sscId; }
    }

    public static class Preview extends CameraRequest {
        @NotNull
        private StreamType streamType = StreamType.DEFAULT;
        public StreamType getStreamType() { return streamType; }
        public void setStreamType(StreamType streamType) { this.streamType = streamType; }
    }

    public static class PreviewSession extends CameraRequest {
        @NotBlank @Pattern(regexp = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}")
        private String sessionId;
        public String getSessionId() { return sessionId; }
        public void setSessionId(String sessionId) { this.sessionId = sessionId; }
    }

    public static class Ptz extends CameraRequest {
        @Size(max = 64)
        private String leaseId;
        public String getLeaseId() { return leaseId; }
        public void setLeaseId(String leaseId) { this.leaseId = leaseId; }
        @NotNull
        private PtzAction action;
        private PtzCommand command;
        @DecimalMin("0.0") @DecimalMax("1.0") @Digits(integer = 1, fraction = 3)
        private BigDecimal speed;
        public PtzAction getAction() { return action; }
        public void setAction(PtzAction action) { this.action = action; }
        public PtzCommand getCommand() { return command; }
        public void setCommand(PtzCommand command) { this.command = command; }
        public BigDecimal getSpeed() { return speed; }
        public void setSpeed(BigDecimal speed) { this.speed = speed; }
        @JsonIgnore
        @AssertTrue(message = "START 必须提供 command 和 0–1 的 speed")
        public boolean isStartParametersValid() {
            return action != PtzAction.START || (command != null && speed != null);
        }
    }

    public static class PtzLease extends CameraRequest {
        @NotBlank @Size(max = 64)
        private String leaseId;
        public String getLeaseId() { return leaseId; }
        public void setLeaseId(String leaseId) { this.leaseId = leaseId; }
    }

    public static class TimeRange extends CameraRequest {
        @NotNull
        private OffsetDateTime startTime;
        @NotNull
        private OffsetDateTime endTime;
        public OffsetDateTime getStartTime() { return startTime; }
        public void setStartTime(OffsetDateTime startTime) { this.startTime = startTime; }
        public OffsetDateTime getEndTime() { return endTime; }
        public void setEndTime(OffsetDateTime endTime) { this.endTime = endTime; }
        @JsonIgnore
        @AssertTrue(message = "endTime 必须晚于 startTime")
        public boolean isTimeRangeValid() {
            return startTime == null || endTime == null || endTime.isAfter(startTime);
        }
    }

    public static class PlaybackSession extends CameraRequest {
        @NotBlank @Pattern(regexp = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}")
        private String playbackId;
        public String getPlaybackId() { return playbackId; }
        public void setPlaybackId(String playbackId) { this.playbackId = playbackId; }
    }
    public static class PlaybackControl extends PlaybackSession {
        @NotNull @Min(1)
        private Integer revision;
        public Integer getRevision() { return revision; }
        public void setRevision(Integer revision) { this.revision = revision; }
        @NotNull
        private PlaybackAction action;
        private OffsetDateTime positionTime;
        public PlaybackAction getAction() { return action; }
        public void setAction(PlaybackAction action) { this.action = action; }
        public OffsetDateTime getPositionTime() { return positionTime; }
        public void setPositionTime(OffsetDateTime positionTime) { this.positionTime = positionTime; }
        @JsonIgnore
        @AssertTrue(message = "SEEK 必须提供 positionTime")
        public boolean isSeekPositionValid() {
            return action != PlaybackAction.SEEK || positionTime != null;
        }
    }
}
