package com.jpmsoft.videoaccess;

import com.jpmsoft.videoaccess.adapter.company.ModuleCameraCatalog;
import com.jpmsoft.videoaccess.config.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import java.util.Arrays;
import static org.assertj.core.api.Assertions.*;

class ModuleConfigurationTest {
    @Test void moduleMustBeExplicitAndCannotMixWithDemo() {
        ApplicationContextRunner runner = new ApplicationContextRunner().withUserConfiguration(AdapterConfiguration.class)
                .withPropertyValues("video.module.enabled=true");
        runner.run(c -> assertThat(c).hasFailed());
        runner.withInitializer(c -> c.getEnvironment().setActiveProfiles("module", "local"))
                .run(c -> assertThat(c).hasFailed());
    }
    ModuleProperties.Binding binding() {
        ModuleProperties.Binding b = new ModuleProperties.Binding(); b.setSscId("internal"); b.setpId("project");
        return b;
    }
    @Test void partialOrMalformedGbBindingFailsStartup() {
        ModuleProperties p = new ModuleProperties(); ModuleProperties.Binding b = binding();
        b.setDeviceId("34020000001320000001"); p.setCameras(Arrays.asList(b));
        assertThatThrownBy(() -> new ModuleCameraCatalog(p)).isInstanceOf(IllegalArgumentException.class);
        b.setChannelId("guessed");
        assertThatThrownBy(() -> new ModuleCameraCatalog(p)).isInstanceOf(IllegalArgumentException.class);
    }
    @Test void duplicateInternalIdFailsAndUnboundCameraIsPreserved() {
        ModuleProperties p = new ModuleProperties(); p.setCameras(Arrays.asList(binding()));
        assertThat(new ModuleCameraCatalog(p).findById("internal").get().isBound()).isFalse();
        p.setCameras(Arrays.asList(binding(), binding()));
        assertThatThrownBy(() -> new ModuleCameraCatalog(p)).isInstanceOf(IllegalArgumentException.class);
    }
}
