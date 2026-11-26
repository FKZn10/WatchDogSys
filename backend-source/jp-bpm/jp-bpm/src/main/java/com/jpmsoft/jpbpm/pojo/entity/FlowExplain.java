package com.jpmsoft.jpbpm.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.util.Date;

@TableName("SYS_FlowExplain")
public class FlowExplain {

    @TableId(type = IdType.INPUT)
    public String feId;
    public String feMemberid;
    public String feSolutionid;
    public String feContent;
    public Date feTime;
    public BigDecimal feOrder;
    private String teId;

    public String getFeId() {
        return feId;
    }

    public void setFeId(String feId) {
        this.feId = feId;
    }

    public String getFeMemberid() {
        return feMemberid;
    }

    public void setFeMemberid(String feMemberid) {
        this.feMemberid = feMemberid;
    }

    public String getFeSolutionid() {
        return feSolutionid;
    }

    public void setFeSolutionid(String feSolutionid) {
        this.feSolutionid = feSolutionid;
    }

    public String getFeContent() {
        return feContent;
    }

    public void setFeContent(String feContent) {
        this.feContent = feContent;
    }

    public Date getFeTime() {
        return feTime;
    }

    public void setFeTime(Date feTime) {
        this.feTime = feTime;
    }

    public BigDecimal getFeOrder() {
        return feOrder;
    }

    public void setFeOrder(BigDecimal feOrder) {
        this.feOrder = feOrder;
    }

    public String getTeId() {
        return teId;
    }

    public void setTeId(String teId) {
        this.teId = teId;
    }
}
