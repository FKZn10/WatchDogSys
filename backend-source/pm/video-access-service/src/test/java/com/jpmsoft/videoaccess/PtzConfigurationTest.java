package com.jpmsoft.videoaccess;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmsoft.videoaccess.config.PtzConfiguration;
import com.jpmsoft.videoaccess.port.VideoPorts.VideoGateway;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class PtzConfigurationTest {
    final ApplicationContextRunner runner = new ApplicationContextRunner().withUserConfiguration(PtzConfiguration.class)
            .withBean(ObjectMapper.class, ObjectMapper::new).withBean(VideoGateway.class, () -> mock(VideoGateway.class));
    @Test void disabledDefaultStartsWithoutGrantingControl() { runner.run(c -> assertThat(c).hasNotFailed()); }
    @Test void enabledSingleProcessLockCannotDeclareMultipleInstances() {
        runner.withPropertyValues("video.ptz.single-instance-enabled=true", "video.ptz.instance-count=2")
                .run(c -> assertThat(c).hasFailed());
    }
    @Test void serviceRegistrationDoesNotImplyMultipleInstances() {
        runner.withInitializer(c -> c.getEnvironment().setActiveProfiles("registry"))
                .withPropertyValues("video.ptz.single-instance-enabled=true", "eureka.client.enabled=false")
                .run(c -> assertThat(c).hasNotFailed());
    }
}
