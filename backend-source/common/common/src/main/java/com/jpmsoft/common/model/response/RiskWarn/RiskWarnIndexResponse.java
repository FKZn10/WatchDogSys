package com.jpmsoft.common.model.response.RiskWarn;

import com.jpmsoft.common.model.request.RiskWarn.RiskWarnTableMapRequest;
import com.jpmsoft.common.model.request.RiskWarn.RiskWarnTableRequest;

import java.util.Date;
import java.util.List;

public class RiskWarnIndexResponse {
    private String rwiId;
    private String rwiCode;
    private String rwiType;
    private String rwiName;
    private String rwiDesc;
    private String rwiProblem;
    private String rwiCalculation_Rule;
    private String rwiValue;
    private String rwiBasis;
    private String rwiLevel;
    private String rwiCreate_Uid;
    private String rwiCreateUname;
    private Date rwiCreateTime;
    private Integer RwiState;
    private String dId;
    private String rwiPerson_Uid;
    private String rwiPersonUName;
    private Integer rwiProjectLeader;
    private String rwiBusinessInforUrl;
    private String rwiScreeningConditions;
    private String rwiQuerySQL;
    private List<RiskWarnTableRequest> RiskWarnTableList;
    private List<RiskWarnTableMapRequest> RiskWarnTableMapList;
    private Integer countstr;
    private Integer pending;


    public String getRwiId() {
        return rwiId;
    }

    public void setRwiId(String rwiId) {
        this.rwiId = rwiId;
    }

    public String getRwiCode() {
        return rwiCode;
    }

    public void setRwiCode(String rwiCode) {
        this.rwiCode = rwiCode;
    }

    public String getRwiType() {
        return rwiType;
    }

    public void setRwiType(String rwiType) {
        this.rwiType = rwiType;
    }

    public String getRwiName() {
        return rwiName;
    }

    public void setRwiName(String rwiName) {
        this.rwiName = rwiName;
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

    public String getRwiCalculation_Rule() {
        return rwiCalculation_Rule;
    }

    public void setRwiCalculation_Rule(String rwiCalculation_Rule) {
        this.rwiCalculation_Rule = rwiCalculation_Rule;
    }

    public String getRwiValue() {
        return rwiValue;
    }

    public void setRwiValue(String rwiValue) {
        this.rwiValue = rwiValue;
    }

    public String getRwiBasis() {
        return rwiBasis;
    }

    public void setRwiBasis(String rwiBasis) {
        this.rwiBasis = rwiBasis;
    }

    public String getRwiLevel() {
        return rwiLevel;
    }

    public void setRwiLevel(String rwiLevel) {
        this.rwiLevel = rwiLevel;
    }

    public String getRwiCreate_Uid() {
        return rwiCreate_Uid;
    }

    public void setRwiCreate_Uid(String rwiCreate_Uid) {
        this.rwiCreate_Uid = rwiCreate_Uid;
    }

    public String getRwiCreateUname() {
        return rwiCreateUname;
    }

    public void setRwiCreateUname(String rwiCreateUname) {
        this.rwiCreateUname = rwiCreateUname;
    }

    public Date getRwiCreateTime() {
        return rwiCreateTime;
    }

    public void setRwiCreateTime(Date rwiCreateTime) {
        this.rwiCreateTime = rwiCreateTime;
    }

    public Integer getRwiState() {
        return RwiState;
    }

    public void setRwiState(Integer rwiState) {
        RwiState = rwiState;
    }

    public String getdId() {
        return dId;
    }

    public void setdId(String dId) {
        this.dId = dId;
    }

    public String getRwiPerson_Uid() {
        return rwiPerson_Uid;
    }

    public void setRwiPerson_Uid(String rwiPerson_Uid) {
        this.rwiPerson_Uid = rwiPerson_Uid;
    }

    public String getRwiPersonUName() {
        return rwiPersonUName;
    }

    public void setRwiPersonUName(String rwiPersonUName) {
        this.rwiPersonUName = rwiPersonUName;
    }

    public Integer getRwiProjectLeader() {
        return rwiProjectLeader;
    }

    public void setRwiProjectLeader(Integer rwiProjectLeader) {
        this.rwiProjectLeader = rwiProjectLeader;
    }

    public String getRwiBusinessInforUrl() {
        return rwiBusinessInforUrl;
    }

    public void setRwiBusinessInforUrl(String rwiBusinessInforUrl) {
        this.rwiBusinessInforUrl = rwiBusinessInforUrl;
    }

    public String getRwiScreeningConditions() {
        return rwiScreeningConditions;
    }

    public void setRwiScreeningConditions(String rwiScreeningConditions) {
        this.rwiScreeningConditions = rwiScreeningConditions;
    }

    public String getRwiQuerySQL() {
        return rwiQuerySQL;
    }

    public void setRwiQuerySQL(String rwiQuerySQL) {
        this.rwiQuerySQL = rwiQuerySQL;
    }

    public List<RiskWarnTableRequest> getRiskWarnTableList() {
        return RiskWarnTableList;
    }

    public void setRiskWarnTableList(List<RiskWarnTableRequest> riskWarnTableList) {
        RiskWarnTableList = riskWarnTableList;
    }

    public List<RiskWarnTableMapRequest> getRiskWarnTableMapList() {
        return RiskWarnTableMapList;
    }

    public void setRiskWarnTableMapList(List<RiskWarnTableMapRequest> riskWarnTableMapList) {
        RiskWarnTableMapList = riskWarnTableMapList;
    }

    public Integer getCountstr() {
        return countstr;
    }

    public void setCountstr(Integer countstr) {
        this.countstr = countstr;
    }

    public Integer getPending() {
        return pending;
    }

    public void setPending(Integer pending) {
        this.pending = pending;
    }
}
