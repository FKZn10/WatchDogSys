package com.jpmsoft.videoaccess.api;

import com.jpmsoft.videoaccess.config.VideoProperties;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class HealthController {
    private final VideoProperties properties;
    private final Environment environment;
    public HealthController(VideoProperties properties, Environment environment) {
        this.properties = properties; this.environment = environment;
    }
    @GetMapping("/api/video/v1/health")
    public ApiResponse<Map<String, Object>> health() {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("application", "video-access-service");
        data.put("status", "UP");
        data.put("businessReady", false);
        data.put("registryEnabled", environment.getProperty("eureka.client.enabled", Boolean.class, false));
        return ApiResponse.ok(data, 0, properties.getMode());
    }
}
