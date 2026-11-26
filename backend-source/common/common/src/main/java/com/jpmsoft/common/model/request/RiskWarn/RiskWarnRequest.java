package com.jpmsoft.common.model.request.RiskWarn;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseModeRequest;

import java.util.Date;
import java.util.List;

/**
 * 风险预警信息
 */
public class RiskWarnRequest extends BaseModeRequest {

    private String rwId;
    private String rwiId;
    private String pId;
    private String secId;
    private String conId;
    private String rwCode;
    private String rwPmId;
    private Date rwTime;
    private String dId;
    private String rwContent;
    private String rwFrom;
    private String rwBusinessInforKey;
    private String rwFlowActName;
    private String rwFlowUid;
    private String rwFlowUname;
    private String rwFlowUphone;
    private String rwFlowCId;
    private Date rwFlowReceiveTime;
    private Date rwFlowApprovalTime;
    private Date rwFlowEtlTime;
    private String rwHandleUid;
    private String rwHandleUname;
    private Date rwHandleTime;
    private String rwHandleContent;

    private String rwiLevel;
    private String pName;
    private String rwiName;
    private List<String> rwTimes;
    /** 预警创建时间范围 [起,止]（实际取关联线索 Risk_Warn_Clue.Rwc_Create_Time） */
    private List<String> rwCreateTimes;
    private String rwiDesc;
    private String rwiProblem;
    private Integer rwState;
    private String cId;

    private String oplId;

    @JsonProperty("tCode")
    private String tCode;

    public String getTCode() {
        return tCode;
    }

    public void setTCode(String tCode) {
        this.tCode = tCode;
    }

    public String getOplId() {
        return oplId;
    }

    public void setOplId(String oplId) {
        this.oplId = oplId;
    }

    public Integer getRwState() {
        return rwState;
    }

    public void setRwState(Integer rwState) {
        this.rwState = rwState;
    }

    public String getRwId() {
        return rwId;
    }

    public void setRwId(String rwId) {
        this.rwId = rwId;
    }

    public String getRwiId() {
        return rwiId;
    }

    public void setRwiId(String rwiId) {
        this.rwiId = rwiId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getRwCode() {
        return rwCode;
    }

    public void setRwCode(String rwCode) {
        this.rwCode = rwCode;
    }

    public String getRwPmId() {
        return rwPmId;
    }

    public void setRwPmId(String rwPmId) {
        this.rwPmId = rwPmId;
    }

    public Date getRwTime() {
        return rwTime;
    }

    public void setRwTime(Date rwTime) {
        this.rwTime = rwTime;
    }

    public String getdId() {
        return dId;
    }

    public void setdId(String dId) {
        this.dId = dId;
    }

    public String getRwContent() {
        return rwContent;
    }

    public void setRwContent(String rwContent) {
        this.rwContent = rwContent;
    }

    public String getRwFrom() {
        return rwFrom;
    }

    public void setRwFrom(String rwFrom) {
        this.rwFrom = rwFrom;
    }

    public String getRwBusinessInforKey() {
        return rwBusinessInforKey;
    }

    public void setRwBusinessInforKey(String rwBusinessInforKey) {
        this.rwBusinessInforKey = rwBusinessInforKey;
    }

    public String getRwFlowActName() {
        return rwFlowActName;
    }

    public void setRwFlowActName(String rwFlowActName) {
        this.rwFlowActName = rwFlowActName;
    }

    public String getRwFlowUid() {
        return rwFlowUid;
    }

    public void setRwFlowUid(String rwFlowUid) {
        this.rwFlowUid = rwFlowUid;
    }

    public String getRwFlowUname() {
        return rwFlowUname;
    }

    public void setRwFlowUname(String rwFlowUname) {
        this.rwFlowUname = rwFlowUname;
    }

    public String getRwFlowUphone() {
        return rwFlowUphone;
    }

    public void setRwFlowUphone(String rwFlowUphone) {
        this.rwFlowUphone = rwFlowUphone;
    }

    public String getRwFlowCId() {
        return rwFlowCId;
    }

    public void setRwFlowCId(String rwFlowCId) {
        this.rwFlowCId = rwFlowCId;
    }

    public Date getRwFlowReceiveTime() {
        return rwFlowReceiveTime;
    }

    public void setRwFlowReceiveTime(Date rwFlowReceiveTime) {
        this.rwFlowReceiveTime = rwFlowReceiveTime;
    }

    public Date getRwFlowApprovalTime() {
        return rwFlowApprovalTime;
    }

    public void setRwFlowApprovalTime(Date rwFlowApprovalTime) {
        this.rwFlowApprovalTime = rwFlowApprovalTime;
    }

    public Date getRwFlowEtlTime() {
        return rwFlowEtlTime;
    }

    public void setRwFlowEtlTime(Date rwFlowEtlTime) {
        this.rwFlowEtlTime = rwFlowEtlTime;
    }

    public String getRwHandleUid() {
        return rwHandleUid;
    }

    public void setRwHandleUid(String rwHandleUid) {
        this.rwHandleUid = rwHandleUid;
    }

    public String getRwHandleUname() {
        return rwHandleUname;
    }

    public void setRwHandleUname(String rwHandleUname) {
        this.rwHandleUname = rwHandleUname;
    }

    public Date getRwHandleTime() {
        return rwHandleTime;
    }

    public void setRwHandleTime(Date rwHandleTime) {
        this.rwHandleTime = rwHandleTime;
    }

    public String getRwHandleContent() {
        return rwHandleContent;
    }

    public void setRwHandleContent(String rwHandleContent) {
        this.rwHandleContent = rwHandleContent;
    }

    public String getRwiLevel() {
        return rwiLevel;
    }

    public void setRwiLevel(String rwiLevel) {
        this.rwiLevel = rwiLevel;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getRwiName() {
        return rwiName;
    }

    public void setRwiName(String rwiName) {
        this.rwiName = rwiName;
    }

    public List<String> getRwTimes() {
        return rwTimes;
    }

    public void setRwTimes(List<String> rwTimes) {
        this.rwTimes = rwTimes;
    }

    public List<String> getRwCreateTimes() {
        return rwCreateTimes;
    }

    public void setRwCreateTimes(List<String> rwCreateTimes) {
        this.rwCreateTimes = rwCreateTimes;
    }

    public String getRwiDesc() {
        return rwiDesc;
    }

    public void setRwiDesc(String rwiDesc) {
        this.rwiDesc = rwiDesc;
    }

    public String getRwiProblem() {
        return rwiProblem;
    }

    public void setRwiProblem(String rwiProblem) {
        this.rwiProblem = rwiProblem;
    }

    public String getcId() {
        return cId;
    }


    public void setcId(String cId) {
        this.cId = cId;
    }
}
