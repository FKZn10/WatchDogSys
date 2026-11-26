package com.jpmsoft.jpbpm.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 流程设置活动处理后自动抄送的用户
 */
@TableName("SYS_CarbonCopyUser")
public class CarbonCopyUser {

    @TableId(type = IdType.INPUT)
    public String ccuId;
    public String ccActid;
    public String ccUser;
    public String ccFlowid;
    private String teId;
    
    public String getCcuId() {
        return ccuId;
    }

    public void setCcuId(String ccuId) {
        this.ccuId = ccuId;
    }

    public String getCcActid() {
        return ccActid;
    }

    public void setCcActid(String ccActid) {
        this.ccActid = ccActid;
    }

    public String getCcUser() {
        return ccUser;
    }

    public void setCcUser(String ccUser) {
        this.ccUser = ccUser;
    }

    public String getCcFlowid() {
        return ccFlowid;
    }

    public void setCcFlowid(String ccFlowid) {
        this.ccFlowid = ccFlowid;
    }

    public String getTeId() {
        return teId;
    }

    public void setTeId(String teId) {
        this.teId = teId;
    }
}
