package com.jpmsoft.videoaccess.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmsoft.videoaccess.adapter.wvp.WvpHttpClient;
import com.jpmsoft.videoaccess.adapter.wvp.WvpVideoGateway;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;

@Configuration
@EnableConfigurationProperties(WvpProperties.class)
@ConditionalOnProperty(prefix = "video.wvp", name = "enabled", havingValue = "true")
public class WvpAdapterConfiguration {
    @Bean public WvpHttpClient wvpHttpClient(WvpProperties settings, VideoProperties video,
                                             ObjectMapper json, Environment environment) {
        if (video.isDemoEnabled() || environment.acceptsProfiles(Profiles.of("local"))) {
            throw new IllegalStateException("A real WVP adapter cannot run with the local demo");
        }
        return new WvpHttpClient(settings, json);
    }
    @Bean public WvpVideoGateway wvpVideoGateway(WvpHttpClient client, WvpProperties settings) {
        return new WvpVideoGateway(client, settings);
    }
}
