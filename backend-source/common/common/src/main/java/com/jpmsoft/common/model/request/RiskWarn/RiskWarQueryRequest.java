package com.jpmsoft.common.model.request.RiskWarn;

import com.jpmsoft.common.model.BaseModeRequest;

public class RiskWarQueryRequest  extends BaseModeRequest {
    private String startTime;
    private String endTime;
    private String uaranteeprocessed;
    private String cName;
    private String pName;
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getUaranteeprocessed() {
        return uaranteeprocessed;
    }

    public void setUaranteeprocessed(String uaranteeprocessed) {
        this.uaranteeprocessed = uaranteeprocessed;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }
}
