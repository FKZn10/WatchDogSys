package com.jpmsoft.jpbpm.pojo.dto;

import com.jpmsoft.common.model.BasePageEntity;

import java.util.Date;

public class TaskPushCallRecordDto extends BasePageEntity {
    public String tpcrId;
    public String tpcrUrl;
    public String tpcrCenter;
    public Date tpcrCreateTime;
    public String tpcrState;
    private String tpcrCreateTimeStart;
    private String tpcrCreateTimeEnd;
    public String getTpcrId() {
        return tpcrId;
    }

    public void setTpcrId(String tpcrId) {
        this.tpcrId = tpcrId;
    }

    public String getTpcrUrl() {
        return tpcrUrl;
    }

    public void setTpcrUrl(String tpcrUrl) {
        this.tpcrUrl = tpcrUrl;
    }

    public String getTpcrCenter() {
        return tpcrCenter;
    }

    public void setTpcrCenter(String tpcrCenter) {
        this.tpcrCenter = tpcrCenter;
    }

    public Date getTpcrCreateTime() {
        return tpcrCreateTime;
    }

    public void setTpcrCreateTime(Date tpcrCreateTime) {
        this.tpcrCreateTime = tpcrCreateTime;
    }

    public String getTpcrState() {
        return tpcrState;
    }

    public void setTpcrState(String tpcrState) {
        this.tpcrState = tpcrState;
    }

    public String getTpcrCreateTimeStart() {
        return tpcrCreateTimeStart;
    }

    public void setTpcrCreateTimeStart(String tpcrCreateTimeStart) {
        this.tpcrCreateTimeStart = tpcrCreateTimeStart;
    }

    public String getTpcrCreateTimeEnd() {
        return tpcrCreateTimeEnd;
    }

    public void setTpcrCreateTimeEnd(String tpcrCreateTimeEnd) {
        this.tpcrCreateTimeEnd = tpcrCreateTimeEnd;
    }
}
