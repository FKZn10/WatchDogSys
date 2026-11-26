package com.jpmsoft.jpbpm.pojo.vo;

import java.util.Date;

public class ApprovalDurationVo {
    private Date minDoTime;
    private Date maxDoTime;

    public Date getMinDoTime() {
        return minDoTime;
    }

    public void setMinDoTime(Date minDoTime) {
        this.minDoTime = minDoTime;
    }

    public Date getMaxDoTime() {
        return maxDoTime;
    }

    public void setMaxDoTime(Date maxDoTime) {
        this.maxDoTime = maxDoTime;
    }
}
