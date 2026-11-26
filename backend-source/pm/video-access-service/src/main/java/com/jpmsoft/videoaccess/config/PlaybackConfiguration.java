package com.jpmsoft.videoaccess.config;
import com.jpmsoft.videoaccess.port.VideoPorts.VideoGateway;
import com.jpmsoft.videoaccess.service.playback.PlaybackSessions;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
@Configuration
public class PlaybackConfiguration {
    @Bean public PlaybackSessions playbackSessions(VideoGateway gateway, Environment environment) {
        if (environment.getProperty("video.playback.instance-count", Integer.class, 1) != 1) throw new IllegalStateException("Playback ownership requires one business instance");
        return new PlaybackSessions(gateway);
    }
}
