package com.jpmsoft.videoaccess.config;

import com.jpmsoft.videoaccess.api.ApiException;
import com.jpmsoft.videoaccess.dto.VideoRequests.*;
import com.jpmsoft.videoaccess.model.VideoModels.*;
import com.jpmsoft.videoaccess.port.VideoPorts.*;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Configuration
@EnableConfigurationProperties(VideoProperties.class)
public class AdapterConfiguration {
    @Bean
    public ProjectAccess projectAccess(VideoProperties properties, Environment environment) {
        boolean local = environment.acceptsProfiles(Profiles.of("local"));
        if (properties.getModule().isEnabled()) {
            if (!environment.acceptsProfiles(Profiles.of("module")) || local || properties.isDemoEnabled()) {
                throw new IllegalStateException("Platform module requires explicit module profile, without demo identity");
            }
            return new ProjectAccess() {
                // Scope agreed by the project manager: authentication belongs to the containing platform.
                public void requireIdentity() { }
                public void requireProject(String pId) {
                    if (pId == null || pId.trim().isEmpty() || pId.length() > 64) throw new ApiException(400, "INVALID_ARGUMENT", "缺少项目编号");
                }
                public com.jpmsoft.videoaccess.service.ptz.ControlActor requireControl(Camera camera) {
                    return new com.jpmsoft.videoaccess.service.ptz.ControlActor("PLATFORM_UNRESOLVED", "PLATFORM_UNRESOLVED");
                }
            };
        }
        if (properties.getCompanyDirectory().isEnabled() && (local || properties.isDemoEnabled())) {
            throw new IllegalStateException("Company directory cannot be combined with the local demo");
        }
        if (properties.isDemoEnabled()) {
            String bindAddress = environment.getProperty("server.address", "");
            if (!local || environment.acceptsProfiles(Profiles.of("registry"))
                    || environment.getProperty("eureka.client.enabled", Boolean.class, false)
                    || environment.getProperty("eureka.client.register-with-eureka", Boolean.class, false)
                    || !("127.0.0.1".equals(bindAddress) || "::1".equals(bindAddress))) {
                throw new IllegalStateException("Demo mode requires local profile, loopback binding and Eureka disabled");
            }
            return new ProjectAccess() {
                public void requireIdentity() { /* Explicit loopback-only local demo identity. */ }
                public void requireProject(String pId) {
                    if (!properties.getDemoProjectId().equals(pId)) {
                        throw new ApiException(403, "PROJECT_FORBIDDEN", "本地演示只允许配置的演示项目");
                    }
                }
            };
        }
        return new ProjectAccess() {
            public void requireIdentity() {
                throw new ApiException(503, "IDENTITY_NOT_CONFIGURED", "公司身份和项目权限适配器尚未接入");
            }
            public void requireProject(String pId) { requireIdentity(); }
        };
    }

    @Bean
    public CameraCatalog cameraCatalog(VideoProperties properties) {
        if (properties.getModule().isEnabled()) return new com.jpmsoft.videoaccess.adapter.company.ModuleCameraCatalog(properties.getModule());
        return new CameraCatalog() {
            private void requireCatalog() {
                if (!properties.isDemoEnabled()) {
                    throw new ApiException(503, "CATALOG_NOT_CONFIGURED", "内部平台摄像头资料适配器尚未接入");
                }
            }
            public CameraPage query(String pId, int page, int size) {
                requireCatalog();
                return new CameraPage(Collections.emptyList(), 0);
            }
            public Optional<Camera> findById(String sscId) {
                requireCatalog();
                return Optional.empty();
            }
        };
    }

    @Bean
    @ConditionalOnProperty(prefix = "video.wvp", name = "enabled", havingValue = "false", matchIfMissing = true)
    public VideoGateway videoGateway() {
        return new VideoGateway() {
            private ApiException unavailable() {
                return new ApiException(503, "VIDEO_PLATFORM_NOT_CONFIGURED", "国标视频平台适配器尚未接入");
            }
            public StreamInfo preview(Camera camera, Preview request) { throw unavailable(); }
            public void ptz(Camera camera, Ptz request) { throw unavailable(); }
            public List<RecordingSegment> recordings(Camera camera, TimeRange request) { throw unavailable(); }
            public StreamInfo startPlayback(Camera camera, TimeRange request) { throw unavailable(); }
        };
    }
}
