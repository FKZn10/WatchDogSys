package com.jpmsoft.jpbpm.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("Sys_Flow_Next_User_Select")
public class SysFlowNextUserSelect {
    @TableId(type = IdType.INPUT)
    public String id;
    public String fileId;
    public String flowId;
    public String operateActId;
    public String operateUid;
    public String targetActId;
    public String targetUid;
    public Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getOperateActId() {
        return operateActId;
    }

    public void setOperateActId(String operateActId) {
        this.operateActId = operateActId;
    }

    public String getOperateUid() {
        return operateUid;
    }

    public void setOperateUid(String operateUid) {
        this.operateUid = operateUid;
    }

    public String getTargetActId() {
        return targetActId;
    }

    public void setTargetActId(String targetActId) {
        this.targetActId = targetActId;
    }

    public String getTargetUid() {
        return targetUid;
    }

    public void setTargetUid(String targetUid) {
        this.targetUid = targetUid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
