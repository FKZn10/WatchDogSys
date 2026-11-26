package com.jpmsoft.videoaccess;

import com.jpmsoft.videoaccess.config.AdapterConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import static org.assertj.core.api.Assertions.assertThat;

class DemoConfigurationTest {
    private final ApplicationContextRunner runner = new ApplicationContextRunner()
            .withUserConfiguration(AdapterConfiguration.class)
            .withPropertyValues("video.demo-enabled=true", "server.address=127.0.0.1");

    @Test void demoCannotBeEnabledOutsideLocalProfile() {
        runner.run(context -> assertThat(context).hasFailed());
    }
    @Test void companyDirectoryCannotUseLocalDemoPermissions() {
        runner.withInitializer(c -> c.getEnvironment().setActiveProfiles("local"))
                .withPropertyValues("video.company-directory.enabled=true")
                .run(context -> assertThat(context).hasFailed());
    }
    @Test void demoCannotListenOnAllNetworkInterfaces() {
        runner.withInitializer(c -> c.getEnvironment().setActiveProfiles("local"))
                .withPropertyValues("server.address=0.0.0.0")
                .run(context -> assertThat(context).hasFailed());
    }
    @Test void demoCannotRegisterToEureka() {
        runner.withInitializer(c -> c.getEnvironment().setActiveProfiles("local"))
                .withPropertyValues("eureka.client.enabled=true")
                .run(context -> assertThat(context).hasFailed());
    }
    @Test void demoAndRegistryProfilesCannotBeCombinedEvenWhenEurekaFlagIsOverridden() {
        runner.withInitializer(c -> c.getEnvironment().setActiveProfiles("local", "registry"))
                .withPropertyValues("eureka.client.enabled=false", "eureka.client.register-with-eureka=false")
                .run(context -> assertThat(context).hasFailed());
    }
}
