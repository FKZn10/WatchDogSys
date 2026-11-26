package com.jpmsoft.videoaccess;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmsoft.videoaccess.adapter.wvp.WvpVideoGateway;
import com.jpmsoft.videoaccess.config.*;
import com.jpmsoft.videoaccess.port.VideoPorts.VideoGateway;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import static org.assertj.core.api.Assertions.*;

class WvpConfigurationTest {
    private final ApplicationContextRunner context = new ApplicationContextRunner()
            .withUserConfiguration(AdapterConfiguration.class, WvpAdapterConfiguration.class)
            .withBean(ObjectMapper.class, ObjectMapper::new);
    private ApplicationContextRunner enabled() {
        return context.withPropertyValues("video.wvp.enabled=true", "video.wvp.base-url=http://127.0.0.1:18089/wvp",
                "video.wvp.api-key=synthetic-wvp-key", "video.wvp.media-base-url=https://media.example.invalid/media");
    }
    @Test void defaultHasOnlyTheDisabledGateway() {
        context.run(c -> { assertThat(c).hasSingleBean(VideoGateway.class); assertThat(c).doesNotHaveBean(WvpVideoGateway.class); });
    }
    @Test void explicitConfigurationSelectsExactlyOneRealTransportWithoutStartupRequests() {
        enabled().run(c -> { assertThat(c).hasNotFailed(); assertThat(c).hasSingleBean(VideoGateway.class); assertThat(c).hasSingleBean(WvpVideoGateway.class); });
    }
    @Test void realWvpCannotBeEnabledUsingTheDemoIdentity() {
        enabled().withPropertyValues("spring.profiles.active=local", "video.demo-enabled=true", "server.address=127.0.0.1")
                .run(c -> assertThat(c).hasFailed());
    }
    @Test void missingServiceCredentialsFailStartup() {
        enabled().withPropertyValues("video.wvp.api-key=").run(c -> assertThat(c).hasFailed());
    }
    @Test void publicHttpMediaStillFailsByDefault() {
        enabled().withPropertyValues("video.wvp.media-base-url=http://203.0.113.10:8044")
                .run(c -> assertThat(c).hasFailed());
    }
    @Test void explicitIpOnlyTestConfigurationAllowsHttpMedia() {
        enabled().withPropertyValues("video.wvp.media-base-url=http://203.0.113.10:8044",
                "video.wvp.allow-insecure-media-for-test=true")
                .run(c -> assertThat(c).hasNotFailed());
    }
    @Test void testFlagDoesNotAllowArbitraryHttpHostnames() {
        enabled().withPropertyValues("video.wvp.media-base-url=http://media.example.invalid",
                "video.wvp.allow-insecure-media-for-test=true")
                .run(c -> assertThat(c).hasFailed());
    }
}
