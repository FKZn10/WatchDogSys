package com.jpmsoft.common.model.requestAndResponse.Oa;


import com.jpmsoft.common.model.BaseFileRequest;

import java.util.Date;

/**
 * 请假申请
 */
public class OaAttendanceLeaveModel extends BaseFileRequest {

    private String alId;
    private String alType;
    private Date alStartTime;
    private Date alEndTime;
    private Float alHours;
    private String alUid;
    private String alUname;
    private String alReason;
    private String alRemark;
    private String alCreateUid;
    private String alCreateUname;
    private Date alCreateTime;
    private Integer alState;
    private Date alStateTime;

    /**
     * 实际请假结束时间
     */
    private Date alActualEndTime;

    /**
     * 消假审批状态
     */
    private String approveStatus;

    /**
     * 消假表id
     */
    private String eliminateId;


    public String getAlId() {
        return alId;
    }

    public void setAlId(String alId) {
        this.alId = alId;
    }

    public String getAlType() {
        return alType;
    }

    public void setAlType(String alType) {
        this.alType = alType;
    }

    public Date getAlStartTime() {
        return alStartTime;
    }

    public void setAlStartTime(Date alStartTime) {
        this.alStartTime = alStartTime;
    }

    public Date getAlEndTime() {
        return alEndTime;
    }

    public void setAlEndTime(Date alEndTime) {
        this.alEndTime = alEndTime;
    }

    public Float getAlHours() {
        return alHours;
    }

    public void setAlHours(Float alHours) {
        this.alHours = alHours;
    }

    public String getAlUid() {
        return alUid;
    }

    public void setAlUid(String alUid) {
        this.alUid = alUid;
    }

    public String getAlUname() {
        return alUname;
    }

    public void setAlUname(String alUname) {
        this.alUname = alUname;
    }

    public String getAlReason() {
        return alReason;
    }

    public void setAlReason(String alReason) {
        this.alReason = alReason;
    }

    public String getAlRemark() {
        return alRemark;
    }

    public void setAlRemark(String alRemark) {
        this.alRemark = alRemark;
    }

    public String getAlCreateUid() {
        return alCreateUid;
    }

    public void setAlCreateUid(String alCreateUid) {
        this.alCreateUid = alCreateUid;
    }

    public String getAlCreateUname() {
        return alCreateUname;
    }

    public void setAlCreateUname(String alCreateUname) {
        this.alCreateUname = alCreateUname;
    }

    public Date getAlCreateTime() {
        return alCreateTime;
    }

    public void setAlCreateTime(Date alCreateTime) {
        this.alCreateTime = alCreateTime;
    }

    public Integer getAlState() {
        return alState;
    }

    public void setAlState(Integer alState) {
        this.alState = alState;
    }

    public Date getAlStateTime() {
        return alStateTime;
    }

    public void setAlStateTime(Date alStateTime) {
        this.alStateTime = alStateTime;
    }

    public Date getAlActualEndTime() {
        return alActualEndTime;
    }

    public void setAlActualEndTime(Date alActualEndTime) {
        this.alActualEndTime = alActualEndTime;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getEliminateId() {
        return eliminateId;
    }

    public void setEliminateId(String eliminateId) {
        this.eliminateId = eliminateId;
    }
}
