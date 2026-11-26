package com.jpmsoft.videoaccess.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmsoft.videoaccess.port.VideoPorts.VideoGateway;
import com.jpmsoft.videoaccess.service.ptz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class PtzConfiguration {
    @Bean public PtzLeaseManager ptzLeaseManager(VideoGateway gateway, ObjectMapper json, Environment environment) {
        boolean enabled = environment.getProperty("video.ptz.single-instance-enabled", Boolean.class, false);
        int instances = environment.getProperty("video.ptz.instance-count", Integer.class, 1);
        if (enabled && instances != 1) {
            throw new IllegalStateException("In-memory PTZ coordination requires one instance; implement shared ownership before scaling");
        }
        return new PtzLeaseManager(gateway, new LoggingControlAudit(json), enabled);
    }
}
