package com.jpmsoft.videoaccess.config;

import com.jpmsoft.videoaccess.port.VideoPorts.VideoGateway;
import com.jpmsoft.videoaccess.service.preview.PreviewSessions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class PreviewConfiguration {
    @Bean public PreviewSessions previewSessions(VideoGateway gateway, Environment environment) {
        if (environment.getProperty("video.preview.instance-count", Integer.class, 1) != 1) {
            throw new IllegalStateException("Viewer tickets are local memory; use one business instance until shared storage or routing is implemented");
        }
        return new PreviewSessions(gateway);
    }
}
