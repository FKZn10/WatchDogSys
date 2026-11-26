package com.jpmsoft.videoaccess.service.ptz;

import com.jpmsoft.videoaccess.model.VideoModels.Camera;

public interface ControlAudit {
    /** Do not include request token, lease ID, vendor secrets, or media URL. */
    void record(ControlActor actor, Camera camera, String action, String outcome);
}
