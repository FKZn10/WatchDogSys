package com.jpmsoft.videoaccess.adapter.company;

import com.jpmsoft.videoaccess.config.ModuleProperties;
import com.jpmsoft.videoaccess.model.VideoModels.*;
import com.jpmsoft.videoaccess.port.VideoPorts.CameraCatalog;
import java.util.*;
import java.util.stream.Collectors;

/** Server-owned bindings. No shared DB writes, guessed identifiers or client supplied upstream IDs. */
public final class ModuleCameraCatalog implements CameraCatalog {
    private final Map<String, Camera> cameras;
    public ModuleCameraCatalog(ModuleProperties properties) {
        Map<String, Camera> copy = new LinkedHashMap<>();
        if (properties.getCameras() == null || properties.getCameras().size() > 512) throw new IllegalArgumentException("Invalid module camera count");
        for (ModuleProperties.Binding b : properties.getCameras()) {
            if (b == null || !id(b.getSscId()) || !id(b.getpId())) throw new IllegalArgumentException("Module cameras require internal camera/project IDs");
            boolean unbound = empty(b.getDeviceId()) && empty(b.getChannelId());
            if (!unbound && !(gb(b.getDeviceId()) && gb(b.getChannelId()))) throw new IllegalArgumentException("A GB binding requires both 20-digit IDs");
            Camera camera = new Camera(b.getSscId(), b.getpId(), b.getName() == null ? "未命名摄像头" : b.getName(),
                    b.getLocation() == null ? "" : b.getLocation(), b.getDeviceId(), b.getChannelId(), b.getPtzSupported());
            if (copy.put(camera.sscId, camera) != null) throw new IllegalArgumentException("Duplicate internal camera ID");
        }
        cameras = Collections.unmodifiableMap(copy);
    }
    private boolean id(String v) { return v != null && v.matches("[A-Za-z0-9_-]{1,64}"); }
    private boolean gb(String v) { return v != null && v.matches("[0-9]{20}"); }
    private boolean empty(String v) { return v == null || v.trim().isEmpty(); }
    public Optional<Camera> findById(String id) { return Optional.ofNullable(cameras.get(id)); }
    public CameraPage query(String project, int page, int size) {
        if (page < 1 || size < 1 || size > 100) throw new IllegalArgumentException("Invalid pagination");
        List<Camera> matches = cameras.values().stream().filter(c -> c.pId.equals(project)).collect(Collectors.toList());
        int start = (int)Math.min((long)(page - 1) * size, matches.size());
        return new CameraPage(new ArrayList<>(matches.subList(start, Math.min(start + size, matches.size()))), matches.size());
    }
}
