package com.jpmsoft.videoaccess.service.ptz;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmsoft.videoaccess.model.VideoModels.Camera;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

/** Structured operational log. Production retention/durable audit storage remains a deployment task. */
public final class LoggingControlAudit implements ControlAudit {
    private static final Logger LOG = LoggerFactory.getLogger("VIDEO_CONTROL_AUDIT");
    private final ObjectMapper json;
    public LoggingControlAudit(ObjectMapper json) { this.json = json; }
    public void record(ControlActor actor, Camera camera, String action, String outcome) {
        if (!LOG.isInfoEnabled()) throw new IllegalStateException("Control audit INFO logger must be enabled");
        Map<String, Object> event = new LinkedHashMap<>();
        event.put("at", Instant.now().toString()); event.put("tenantId", actor.tenantId);
        event.put("userId", actor.userId); event.put("projectId", camera.pId); event.put("cameraId", camera.sscId);
        event.put("action", action); event.put("outcome", outcome);
        try { LOG.info("{}", json.writeValueAsString(event)); }
        catch (JsonProcessingException error) { throw new IllegalStateException("Control audit serialization failed"); }
    }
}
