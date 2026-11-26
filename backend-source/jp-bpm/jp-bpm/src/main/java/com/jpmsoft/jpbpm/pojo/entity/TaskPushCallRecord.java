package com.jpmsoft.jpbpm.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("Task_Push_Call_Record")
public class TaskPushCallRecord {
    @TableId(type = IdType.INPUT)
    public String tpcrId;
    public String tpcrUrl;
    public String tpcrCenter;
    public Date tpcrCreateTime;
    public String tpcrState;
    private String teId;

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

    public String getTeId() {
        return teId;
    }

    public void setTeId(String teId) {
        this.teId = teId;
    }
}
