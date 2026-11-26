package com.jpmsoft.common.model.request.proCp;

import com.jpmsoft.common.model.BaseFileRequest;

import java.util.Date;
import java.util.List;

/**
 * 工作办理记录
 */

public class ProjectCpWorkRecordRequest extends BaseFileRequest {

    private String cpwrId;
    private String cpdId;
    private String cpwrRemark;
    private String cpwrCreateUid;
    private String cpwrCreateUname;
    private Date cpwrCreateTime;
    private Boolean cpwrHomePush;
    private Date cpwrFillingTime;

    private String cpId;
    private String cpdName;
    private String pName;

    private List<String> cpwrCreateTimes;
    private List<String> cpdCreateTimes;
    private List<String> cpwrFillingTimes;

    public String getCpId() {
        return cpId;
    }

    public void setCpId(String cpId) {
        this.cpId = cpId;
    }

    public String getCpwrId() {
        return cpwrId;
    }

    public void setCpwrId(String cpwrId) {
        this.cpwrId = cpwrId;
    }

    public String getCpdId() {
        return cpdId;
    }

    public void setCpdId(String cpdId) {
        this.cpdId = cpdId;
    }

    public String getCpwrRemark() {
        return cpwrRemark;
    }

    public void setCpwrRemark(String cpwrRemark) {
        this.cpwrRemark = cpwrRemark;
    }

    public String getCpwrCreateUid() {
        return cpwrCreateUid;
    }

    public void setCpwrCreateUid(String cpwrCreateUid) {
        this.cpwrCreateUid = cpwrCreateUid;
    }

    public String getCpwrCreateUname() {
        return cpwrCreateUname;
    }

    public void setCpwrCreateUname(String cpwrCreateUname) {
        this.cpwrCreateUname = cpwrCreateUname;
    }

    public Date getCpwrCreateTime() {
        return cpwrCreateTime;
    }

    public void setCpwrCreateTime(Date cpwrCreateTime) {
        this.cpwrCreateTime = cpwrCreateTime;
    }

    public String getCpdName() {
        return cpdName;
    }

    public void setCpdName(String cpdName) {
        this.cpdName = cpdName;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public Boolean getCpwrHomePush() {
        return cpwrHomePush;
    }

    public void setCpwrHomePush(Boolean cpwrHomePush) {
        this.cpwrHomePush = cpwrHomePush;
    }

    public Date getCpwrFillingTime() {
        return cpwrFillingTime;
    }

    public void setCpwrFillingTime(Date cpwrFillingTime) {
        this.cpwrFillingTime = cpwrFillingTime;
    }

    public List<String> getCpwrCreateTimes() {
        return cpwrCreateTimes;
    }

    public void setCpwrCreateTimes(List<String> cpwrCreateTimes) {
        this.cpwrCreateTimes = cpwrCreateTimes;
    }

    public List<String> getCpdCreateTimes() {
        return cpdCreateTimes;
    }

    public void setCpdCreateTimes(List<String> cpdCreateTimes) {
        this.cpdCreateTimes = cpdCreateTimes;
    }

    public List<String> getCpwrFillingTimes() {
        return cpwrFillingTimes;
    }

    public void setCpwrFillingTimes(List<String> cpwrFillingTimes) {
        this.cpwrFillingTimes = cpwrFillingTimes;
    }
}
