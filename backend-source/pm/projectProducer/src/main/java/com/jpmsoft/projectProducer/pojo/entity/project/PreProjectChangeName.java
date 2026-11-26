package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("Pre_ProjectChangeName")
public class PreProjectChangeName {

    @TableId(type = IdType.INPUT)
    private String pcnId;
    private String pId;
    private String pcnOldName;
    private String pcnNewName;
    private String pcnUserId;
    private Date pcnCreateTime;
    private String pcnModifyReason;
    private String pcnAttachment;
    private String pcnCreateUid;
    private String pcnCreateUname;
    private Integer pcnFlowstate;
    private Date pcnFlowdate;
    private String teId;

    public String getPcnId() {
        return pcnId;
    }

    public void setPcnId(String pcnId) {
        this.pcnId = pcnId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getPcnOldName() {
        return pcnOldName;
    }

    public void setPcnOldName(String pcnOldName) {
        this.pcnOldName = pcnOldName;
    }

    public String getPcnNewName() {
        return pcnNewName;
    }

    public void setPcnNewName(String pcnNewName) {
        this.pcnNewName = pcnNewName;
    }

    public String getPcnUserId() {
        return pcnUserId;
    }

    public void setPcnUserId(String pcnUserId) {
        this.pcnUserId = pcnUserId;
    }

    public Date getPcnCreateTime() {
        return pcnCreateTime;
    }

    public void setPcnCreateTime(Date pcnCreateTime) {
        this.pcnCreateTime = pcnCreateTime;
    }

    public String getPcnModifyReason() {
        return pcnModifyReason;
    }

    public void setPcnModifyReason(String pcnModifyReason) {
        this.pcnModifyReason = pcnModifyReason;
    }

    public String getPcnAttachment() {
        return pcnAttachment;
    }

    public void setPcnAttachment(String pcnAttachment) {
        this.pcnAttachment = pcnAttachment;
    }

    public String getPcnCreateUid() {
        return pcnCreateUid;
    }

    public void setPcnCreateUid(String pcnCreateUid) {
        this.pcnCreateUid = pcnCreateUid;
    }

    public String getPcnCreateUname() {
        return pcnCreateUname;
    }

    public void setPcnCreateUname(String pcnCreateUname) {
        this.pcnCreateUname = pcnCreateUname;
    }

    public Integer getPcnFlowstate() {
        return pcnFlowstate;
    }

    public void setPcnFlowstate(Integer pcnFlowstate) {
        this.pcnFlowstate = pcnFlowstate;
    }

    public Date getPcnFlowdate() {
        return pcnFlowdate;
    }

    public void setPcnFlowdate(Date pcnFlowdate) {
        this.pcnFlowdate = pcnFlowdate;
    }

    public String getTeId() {
        return teId;
    }

    public void setTeId(String teId) {
        this.teId = teId;
    }
}
