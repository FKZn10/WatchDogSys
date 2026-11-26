package com.jpmsoft.jpbpm.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("Sys_Task_Process_User")
public class TaskProcessUser {
    @TableId(type = IdType.INPUT)
    private String  tpuId;
    private String  tpuTpId;
    private String  tpuUser;
    private String  tpuSendActivityid;
    private String teId;
    public String getTpuSendActivityid() {
        return tpuSendActivityid;
    }

    public void setTpuSendActivityid(String tpuSendActivityid) {
        this.tpuSendActivityid = tpuSendActivityid;
    }

    public String getTpuId() {
        return tpuId;
    }

    public void setTpuId(String tpuId) {
        this.tpuId = tpuId;
    }

    public String getTpuTpId() {
        return tpuTpId;
    }

    public void setTpuTpId(String tpuTpId) {
        this.tpuTpId = tpuTpId;
    }

    public String getTpuUser() {
        return tpuUser;
    }

    public void setTpuUser(String tpuUser) {
        this.tpuUser = tpuUser;
    }

    public String getTeId() {
        return teId;
    }

    public void setTeId(String teId) {
        this.teId = teId;
    }
}
