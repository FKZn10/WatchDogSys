package com.jpmsoft.videoaccess.service.ptz;

import java.util.Objects;

/** Platform operator context, never request JSON. Module fallback uses explicit UNRESOLVED values. */
public final class ControlActor {
    public final String tenantId, userId;
    public ControlActor(String tenantId, String userId) {
        if (!valid(tenantId) || !valid(userId)) throw new IllegalArgumentException("Verified control identity required");
        this.tenantId = tenantId; this.userId = userId;
    }
    private static boolean valid(String value) {
        return value != null && !value.trim().isEmpty() && value.length() <= 128
                && value.chars().noneMatch(c -> c < 32 || c == 127);
    }
    @Override public boolean equals(Object other) {
        if (!(other instanceof ControlActor)) return false;
        ControlActor actor = (ControlActor) other;
        return tenantId.equals(actor.tenantId) && userId.equals(actor.userId);
    }
    @Override public int hashCode() { return Objects.hash(tenantId, userId); }
}
