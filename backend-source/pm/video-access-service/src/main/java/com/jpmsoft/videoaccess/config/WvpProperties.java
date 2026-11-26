package com.jpmsoft.videoaccess.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/** Service credentials come from the runtime environment, never from a browser request. */
@ConfigurationProperties(prefix = "video.wvp")
public class WvpProperties {
    private boolean enabled;
    private String baseUrl;
    private String apiKey;
    private String mediaBaseUrl;
    private boolean allowInsecureMediaForTest;
    private String zoneId = "Asia/Shanghai";
    private int connectTimeoutMs = 2000;
    private int readTimeoutMs = 15000;
    private int maxResponseBytes = 1048576;

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public String getBaseUrl() { return baseUrl; }
    public void setBaseUrl(String baseUrl) { this.baseUrl = baseUrl; }
    public String getApiKey() { return apiKey; }
    public void setApiKey(String apiKey) { this.apiKey = apiKey; }
    public String getMediaBaseUrl() { return mediaBaseUrl; }
    public void setMediaBaseUrl(String mediaBaseUrl) { this.mediaBaseUrl = mediaBaseUrl; }
    public boolean isAllowInsecureMediaForTest() { return allowInsecureMediaForTest; }
    public void setAllowInsecureMediaForTest(boolean value) { this.allowInsecureMediaForTest = value; }
    public String getZoneId() { return zoneId; }
    public void setZoneId(String zoneId) { this.zoneId = zoneId; }
    public int getConnectTimeoutMs() { return connectTimeoutMs; }
    public void setConnectTimeoutMs(int connectTimeoutMs) { this.connectTimeoutMs = connectTimeoutMs; }
    public int getReadTimeoutMs() { return readTimeoutMs; }
    public void setReadTimeoutMs(int readTimeoutMs) { this.readTimeoutMs = readTimeoutMs; }
    public int getMaxResponseBytes() { return maxResponseBytes; }
    public void setMaxResponseBytes(int maxResponseBytes) { this.maxResponseBytes = maxResponseBytes; }
}
