package com.jpmsoft.videoaccess.config;

import java.util.ArrayList;
import java.util.List;

/** Module functional configuration, not an identity/permission database. */
public class ModuleProperties {
    private boolean enabled;
    private List<Binding> cameras = new ArrayList<>();
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public List<Binding> getCameras() { return cameras; }
    public void setCameras(List<Binding> cameras) { this.cameras = cameras; }
    public static class Binding {
        private String sscId, pId, name, location, deviceId, channelId;
        private Boolean ptzSupported;
        public String getSscId() { return sscId; } public void setSscId(String v) { sscId=v; }
        public String getpId() { return pId; } public void setpId(String v) { pId=v; }
        public String getName() { return name; } public void setName(String v) { name=v; }
        public String getLocation() { return location; } public void setLocation(String v) { location=v; }
        public String getDeviceId() { return deviceId; } public void setDeviceId(String v) { deviceId=v; }
        public String getChannelId() { return channelId; } public void setChannelId(String v) { channelId=v; }
        public Boolean getPtzSupported() { return ptzSupported; } public void setPtzSupported(Boolean v) { ptzSupported=v; }
    }
}
