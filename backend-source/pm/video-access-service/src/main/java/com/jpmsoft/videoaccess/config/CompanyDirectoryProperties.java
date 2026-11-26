package com.jpmsoft.videoaccess.config;

/** No account or token is stored in configuration; every request uses the caller's token. */
public class CompanyDirectoryProperties {
    private boolean enabled;
    private String listUrl;
    private int connectTimeoutMs = 2000;
    private int readTimeoutMs = 5000;
    private int maxResponseBytes = 1048576;

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public String getListUrl() { return listUrl; }
    public void setListUrl(String listUrl) { this.listUrl = listUrl; }
    public int getConnectTimeoutMs() { return connectTimeoutMs; }
    public void setConnectTimeoutMs(int connectTimeoutMs) { this.connectTimeoutMs = connectTimeoutMs; }
    public int getReadTimeoutMs() { return readTimeoutMs; }
    public void setReadTimeoutMs(int readTimeoutMs) { this.readTimeoutMs = readTimeoutMs; }
    public int getMaxResponseBytes() { return maxResponseBytes; }
    public void setMaxResponseBytes(int maxResponseBytes) { this.maxResponseBytes = maxResponseBytes; }
}
