package com.jpmsoft.common.model.response.Archive;

import java.util.Date;

/**
 * 档案借阅
 */

public class ArchiveBorrowResponse extends ArchiveFileResponse {

    private String abId;
    private String afId;
    private String abCreateUid;
    private String abCreateUname;
    private Date abCreateTime;
    private Date abTime;
    private Date abReturnTime;
    private String abUse;
    private String abApproverUid;
    private String abApproverUname;
    private String abOperatorUid;
    private String abOperatorUname;
    private String abRemark;
    private Integer abState;
    private Date abStateTime;

    public String getAbId() {
        return abId;
    }

    public void setAbId(String abId) {
        this.abId = abId;
    }

    public String getAfId() {
        return afId;
    }

    public void setAfId(String afId) {
        this.afId = afId;
    }

    public String getAbCreateUid() {
        return abCreateUid;
    }

    public void setAbCreateUid(String abCreateUid) {
        this.abCreateUid = abCreateUid;
    }

    public String getAbCreateUname() {
        return abCreateUname;
    }

    public void setAbCreateUname(String abCreateUname) {
        this.abCreateUname = abCreateUname;
    }

    public Date getAbCreateTime() {
        return abCreateTime;
    }

    public void setAbCreateTime(Date abCreateTime) {
        this.abCreateTime = abCreateTime;
    }

    public Date getAbTime() {
        return abTime;
    }

    public void setAbTime(Date abTime) {
        this.abTime = abTime;
    }

    public Date getAbReturnTime() {
        return abReturnTime;
    }

    public void setAbReturnTime(Date abReturnTime) {
        this.abReturnTime = abReturnTime;
    }

    public String getAbUse() {
        return abUse;
    }

    public void setAbUse(String abUse) {
        this.abUse = abUse;
    }

    public String getAbApproverUid() {
        return abApproverUid;
    }

    public void setAbApproverUid(String abApproverUid) {
        this.abApproverUid = abApproverUid;
    }

    public String getAbApproverUname() {
        return abApproverUname;
    }

    public void setAbApproverUname(String abApproverUname) {
        this.abApproverUname = abApproverUname;
    }

    public String getAbOperatorUid() {
        return abOperatorUid;
    }

    public void setAbOperatorUid(String abOperatorUid) {
        this.abOperatorUid = abOperatorUid;
    }

    public String getAbOperatorUname() {
        return abOperatorUname;
    }

    public void setAbOperatorUname(String abOperatorUname) {
        this.abOperatorUname = abOperatorUname;
    }

    public String getAbRemark() {
        return abRemark;
    }

    public void setAbRemark(String abRemark) {
        this.abRemark = abRemark;
    }

    public Integer getAbState() {
        return abState;
    }

    public void setAbState(Integer abState) {
        this.abState = abState;
    }

    public Date getAbStateTime() {
        return abStateTime;
    }

    public void setAbStateTime(Date abStateTime) {
        this.abStateTime = abStateTime;
    }
}
