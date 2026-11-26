package com.jpmsoft.common.model.request.Contract;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.annotations.ClassInfo;
import com.jpmsoft.common.model.BaseFileRequest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 变更费用申报
 */
public class ConEcFeeRequest extends BaseFileRequest {

    private String ecfId;
    private String ecId;
    private String ecfContractor;
    private String ecfSupervisor;
    private String ecfCode;
    private String ecfImplementation;
    private String ecfGrade;
    private Integer ecfApplyMoneyType;
    private BigDecimal ecfApplyMoney;
    private Integer ecfDurationType;
    private Integer ecfDuration;
    private String ecfRemark;
    private String ecfCreateUid;
    private String ecfCreateUname;
    private Date ecfCreateTime;
    private Integer ecfState;
    private Date ecfStateTime;
    private String ecfAnnex;
    private Float ecfProportion;
    private String ecfPm;
    private String ecfApplyUnit;
    private String ecfType;
    @JsonProperty("pId")
    private String pId;
    private String conId;
    private String ecfContent;
    private BigDecimal ecfApplyMoneyTotal;
    private String ecfChangeType;
    private String dcoId;

    private String pName;
    private String conName;
    private String ecfContractorName;
    private String ecfSupervisorName;
    private String conPartyBName;
    private String dcoName;
    private String dcoCode;
    private String dcoProjectName;
    private String dcoSubCode;

    private List<String> ecfApplyMoneys;
    private List<String> ecfDurations;
    private List<String> ecfCreateTimes;

    private BigDecimal ecfPreliminaryAmount;


    private String payType;

    private List<ConEcFeeRecordDto> conMeasureRecords;

    private String ecChangeType;

    private String ecConstructionName;

    @JsonProperty("pNameList")
    @ClassInfo(title = "项目名称", type = "string", value = "pNameList", dbValue = "prect.P_Name",no = 1)
    private List<String> pNameList;

    @ClassInfo(title = "合同名称", type = "string", value = "conNameList", dbValue = "con.Con_Name",no = 2)
    private List<String> conNameList;

    @ClassInfo(title = "变更类型", type = "dictionary", value = "ecChangeTypeList", dbValue = "ce.Ec_Change_Type",dictionary = "JPMsoft_BGLX",no = 3)
    private List<String> ecChangeTypeList;

    @ClassInfo(title = "创建人", type = "string", value = "createUnameList", dbValue = "mn.Ecf_Create_Uname",no = 4)
    private List<String> createUnameList;

    @ClassInfo(title = "流程状态", type = "dictionary", value = "stateList", dbValue = "mn.ecf_state",dictionary = "JPMsoft_LCZT",no = 5)
    private List<Integer> stateList;

    @ClassInfo(title = "实施单位", type = "string", value = "ecConstructionNameList", dbValue = "ce.ec_Construction_Name",no = 6)
    private List<String> ecConstructionNameList;

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getEcChangeType() {
        return ecChangeType;
    }

    public void setEcChangeType(String ecChangeType) {
        this.ecChangeType = ecChangeType;
    }

    public String getEcConstructionName() {
        return ecConstructionName;
    }

    public void setEcConstructionName(String ecConstructionName) {
        this.ecConstructionName = ecConstructionName;
    }

    public List<String> getpNameList() {
        return pNameList;
    }

    public void setpNameList(List<String> pNameList) {
        this.pNameList = pNameList;
    }

    public List<String> getConNameList() {
        return conNameList;
    }

    public void setConNameList(List<String> conNameList) {
        this.conNameList = conNameList;
    }

    public List<String> getEcChangeTypeList() {
        return ecChangeTypeList;
    }

    public void setEcChangeTypeList(List<String> ecChangeTypeList) {
        this.ecChangeTypeList = ecChangeTypeList;
    }

    public List<String> getCreateUnameList() {
        return createUnameList;
    }

    public void setCreateUnameList(List<String> createUnameList) {
        this.createUnameList = createUnameList;
    }

    public List<Integer> getStateList() {
        return stateList;
    }

    public void setStateList(List<Integer> stateList) {
        this.stateList = stateList;
    }

    public List<String> getEcConstructionNameList() {
        return ecConstructionNameList;
    }

    public void setEcConstructionNameList(List<String> ecConstructionNameList) {
        this.ecConstructionNameList = ecConstructionNameList;
    }

    public List<ConEcFeeRecordDto> getConMeasureRecords() {
        return conMeasureRecords;
    }

    public void setConMeasureRecords(List<ConEcFeeRecordDto> conMeasureRecords) {
        this.conMeasureRecords = conMeasureRecords;
    }

    public BigDecimal getEcfPreliminaryAmount() {
        return ecfPreliminaryAmount;
    }

    public void setEcfPreliminaryAmount(BigDecimal ecfPreliminaryAmount) {
        this.ecfPreliminaryAmount = ecfPreliminaryAmount;
    }

    public String getEcfId() {
        return ecfId;
    }

    public void setEcfId(String ecfId) {
        this.ecfId = ecfId;
    }

    public String getEcId() {
        return ecId;
    }

    public void setEcId(String ecId) {
        this.ecId = ecId;
    }

    public String getEcfContractor() {
        return ecfContractor;
    }

    public void setEcfContractor(String ecfContractor) {
        this.ecfContractor = ecfContractor;
    }

    public String getEcfSupervisor() {
        return ecfSupervisor;
    }

    public void setEcfSupervisor(String ecfSupervisor) {
        this.ecfSupervisor = ecfSupervisor;
    }

    public String getEcfCode() {
        return ecfCode;
    }

    public void setEcfCode(String ecfCode) {
        this.ecfCode = ecfCode;
    }

    public String getEcfImplementation() {
        return ecfImplementation;
    }

    public void setEcfImplementation(String ecfImplementation) {
        this.ecfImplementation = ecfImplementation;
    }

    public String getEcfGrade() {
        return ecfGrade;
    }

    public void setEcfGrade(String ecfGrade) {
        this.ecfGrade = ecfGrade;
    }

    public Integer getEcfApplyMoneyType() {
        return ecfApplyMoneyType;
    }

    public void setEcfApplyMoneyType(Integer ecfApplyMoneyType) {
        this.ecfApplyMoneyType = ecfApplyMoneyType;
    }

    public BigDecimal getEcfApplyMoney() {
        return ecfApplyMoney;
    }

    public void setEcfApplyMoney(BigDecimal ecfApplyMoney) {
        this.ecfApplyMoney = ecfApplyMoney;
    }

    public Integer getEcfDurationType() {
        return ecfDurationType;
    }

    public void setEcfDurationType(Integer ecfDurationType) {
        this.ecfDurationType = ecfDurationType;
    }

    public Integer getEcfDuration() {
        return ecfDuration;
    }

    public void setEcfDuration(Integer ecfDuration) {
        this.ecfDuration = ecfDuration;
    }

    public String getEcfRemark() {
        return ecfRemark;
    }

    public void setEcfRemark(String ecfRemark) {
        this.ecfRemark = ecfRemark;
    }

    public String getEcfCreateUid() {
        return ecfCreateUid;
    }

    public void setEcfCreateUid(String ecfCreateUid) {
        this.ecfCreateUid = ecfCreateUid;
    }

    public String getEcfCreateUname() {
        return ecfCreateUname;
    }

    public void setEcfCreateUname(String ecfCreateUname) {
        this.ecfCreateUname = ecfCreateUname;
    }

    public Date getEcfCreateTime() {
        return ecfCreateTime;
    }

    public void setEcfCreateTime(Date ecfCreateTime) {
        this.ecfCreateTime = ecfCreateTime;
    }

    public Integer getEcfState() {
        return ecfState;
    }

    public void setEcfState(Integer ecfState) {
        this.ecfState = ecfState;
    }

    public Date getEcfStateTime() {
        return ecfStateTime;
    }

    public void setEcfStateTime(Date ecfStateTime) {
        this.ecfStateTime = ecfStateTime;
    }

    public String getEcfAnnex() {
        return ecfAnnex;
    }

    public void setEcfAnnex(String ecfAnnex) {
        this.ecfAnnex = ecfAnnex;
    }

    public Float getEcfProportion() {
        return ecfProportion;
    }

    public void setEcfProportion(Float ecfProportion) {
        this.ecfProportion = ecfProportion;
    }

    public String getEcfPm() {
        return ecfPm;
    }

    public void setEcfPm(String ecfPm) {
        this.ecfPm = ecfPm;
    }

    public String getEcfApplyUnit() {
        return ecfApplyUnit;
    }

    public void setEcfApplyUnit(String ecfApplyUnit) {
        this.ecfApplyUnit = ecfApplyUnit;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getEcfContent() {
        return ecfContent;
    }

    public void setEcfContent(String ecfContent) {
        this.ecfContent = ecfContent;
    }

    public BigDecimal getEcfApplyMoneyTotal() {
        return ecfApplyMoneyTotal;
    }

    public void setEcfApplyMoneyTotal(BigDecimal ecfApplyMoneyTotal) {
        this.ecfApplyMoneyTotal = ecfApplyMoneyTotal;
    }

    public String getEcfChangeType() {
        return ecfChangeType;
    }

    public void setEcfChangeType(String ecfChangeType) {
        this.ecfChangeType = ecfChangeType;
    }

    public String getDcoId() {
        return dcoId;
    }

    public void setDcoId(String dcoId) {
        this.dcoId = dcoId;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getEcfType() {
        return ecfType;
    }

    public void setEcfType(String ecfType) {
        this.ecfType = ecfType;
    }

    @Override
    public String getpId() {
        return pId;
    }

    @Override
    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getEcfContractorName() {
        return ecfContractorName;
    }

    public void setEcfContractorName(String ecfContractorName) {
        this.ecfContractorName = ecfContractorName;
    }

    public String getEcfSupervisorName() {
        return ecfSupervisorName;
    }

    public void setEcfSupervisorName(String ecfSupervisorName) {
        this.ecfSupervisorName = ecfSupervisorName;
    }

    public String getConPartyBName() {
        return conPartyBName;
    }

    public void setConPartyBName(String conPartyBName) {
        this.conPartyBName = conPartyBName;
    }

    public String getDcoName() {
        return dcoName;
    }

    public void setDcoName(String dcoName) {
        this.dcoName = dcoName;
    }

    public String getDcoCode() {
        return dcoCode;
    }

    public void setDcoCode(String dcoCode) {
        this.dcoCode = dcoCode;
    }

    public String getDcoProjectName() {
        return dcoProjectName;
    }

    public void setDcoProjectName(String dcoProjectName) {
        this.dcoProjectName = dcoProjectName;
    }

    public String getDcoSubCode() {
        return dcoSubCode;
    }

    public void setDcoSubCode(String dcoSubCode) {
        this.dcoSubCode = dcoSubCode;
    }

    public List<String> getEcfApplyMoneys() {
        return ecfApplyMoneys;
    }

    public void setEcfApplyMoneys(List<String> ecfApplyMoneys) {
        this.ecfApplyMoneys = ecfApplyMoneys;
    }

    public List<String> getEcfDurations() {
        return ecfDurations;
    }

    public void setEcfDurations(List<String> ecfDurations) {
        this.ecfDurations = ecfDurations;
    }

    public List<String> getEcfCreateTimes() {
        return ecfCreateTimes;
    }

    public void setEcfCreateTimes(List<String> ecfCreateTimes) {
        this.ecfCreateTimes = ecfCreateTimes;
    }
}
