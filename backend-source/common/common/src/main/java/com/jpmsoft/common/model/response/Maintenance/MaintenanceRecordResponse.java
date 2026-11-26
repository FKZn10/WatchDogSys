package com.jpmsoft.common.model.response.Maintenance;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.ProjectCommonRes;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 维保记录
 */
@Data
public class MaintenanceRecordResponse extends ProjectCommonRes {

    private String mrId;
    private String pId;
    private String secId;
    private String conId;
    private String mrCId;
    private String mrType;
    private String mrPosition;
    private String mrPicBefore;
    private String mrPicAfter;
    private BigDecimal mrCost;
    private String mrCreateUid;
    private String mrCreateUname;
    private Date mrCreateTime;
    private Integer mrState;
    private Date mrStateTime;
    private String mrReportUid;
    private String mrReportUname;
    private String mrReportTel;
    private String mrAreaNumber;
    private String mrAddress;
    private Date mrReportTime;
    private String mrProblem;
    private String mrDispatchUid;
    private String mrDispatchUname;
    private Date mrDispatchTime;
    private String mrRepairUid;
    private String mrRepairUname;
    private String mrRepairFeedback;
    private String mrRepairState;
    private String metId;

    private String metName;
    private String conName;

    private String conCode;

    private String secName;
    private String mrCName;
    private String mrPicBeforeIds;
    private String mrPicAfterIds;
    private String mrCNameHistory;
    //单位简称
    private String mrAbbName;

    private String mrCampus;
    private String mrSerialNumber;
    private String mrIssuingUnit;
    private String mrRepairUnit;

    private String mrConstructUnitName;//没用

    private String mrConstructionName;//施工单位名称
    private String mrConstructionId;//施工单位id

    private Date mrEndTime;

    @JsonProperty("Jinfeihao")
    private String Jinfeihao;

    List<MaintenanceContentResponse> maintenanceContentList;

    List<MaintenanceFinanceResponse> maintenanceFinanceList;

    /**
     * 预算金额
     */
    private BigDecimal mrBudgetAmount;

    private String mrCategory;

    public String getConCode() {
        return conCode;
    }

    public void setConCode(String conCode) {
        this.conCode = conCode;
    }

    public String getJinfeihao() {
        return Jinfeihao;
    }

    public void setJinfeihao(String jinfeihao) {
        Jinfeihao = jinfeihao;
    }



    public Date getMrEndTime() {
        return mrEndTime;
    }

    public void setMrEndTime(Date mrEndTime) {
        this.mrEndTime = mrEndTime;
    }

    public String getMrConstructionId() {
        return mrConstructionId;
    }

    public void setMrConstructionId(String mrConstructionId) {
        this.mrConstructionId = mrConstructionId;
    }

    public String getMrConstructionName() {
        return mrConstructionName;
    }

    public void setMrConstructionName(String mrConstructionName) {
        this.mrConstructionName = mrConstructionName;
    }

    public String getMrConstructUnitName() {
        return mrConstructUnitName;
    }

    public void setMrConstructUnitName(String mrConstructUnitName) {
        this.mrConstructUnitName = mrConstructUnitName;
    }


    public List<MaintenanceFinanceResponse> getMaintenanceFinanceList() {
        return maintenanceFinanceList;
    }

    public void setMaintenanceFinanceList(List<MaintenanceFinanceResponse> maintenanceFinanceList) {
        this.maintenanceFinanceList = maintenanceFinanceList;
    }

    public List<MaintenanceContentResponse> getMaintenanceContentList() {
        return maintenanceContentList;
    }

    public void setMaintenanceContentList(List<MaintenanceContentResponse> maintenanceContentList) {
        this.maintenanceContentList = maintenanceContentList;
    }

    public String getconCode(String conCode) {
        return conCode;
    }
    public void setconCode() {
        this.conCode = conCode;
    }
    public String getMrId() {
        return mrId;
    }

    public void setMrId(String mrId) {
        this.mrId = mrId;
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

    public String getMrCId() {
        return mrCId;
    }

    public void setMrCId(String mrCId) {
        this.mrCId = mrCId;
    }

    public String getMrType() {
        return mrType;
    }

    public void setMrType(String mrType) {
        this.mrType = mrType;
    }

    public String getMrPosition() {
        return mrPosition;
    }

    public void setMrPosition(String mrPosition) {
        this.mrPosition = mrPosition;
    }

    public String getMrPicBefore() {
        return mrPicBefore;
    }

    public void setMrPicBefore(String mrPicBefore) {
        this.mrPicBefore = mrPicBefore;
    }

    public String getMrPicAfter() {
        return mrPicAfter;
    }

    public void setMrPicAfter(String mrPicAfter) {
        this.mrPicAfter = mrPicAfter;
    }

    public BigDecimal getMrCost() {
        return mrCost;
    }

    public void setMrCost(BigDecimal mrCost) {
        this.mrCost = mrCost;
    }

    public String getMrCreateUid() {
        return mrCreateUid;
    }

    public void setMrCreateUid(String mrCreateUid) {
        this.mrCreateUid = mrCreateUid;
    }

    public String getMrCreateUname() {
        return mrCreateUname;
    }

    public void setMrCreateUname(String mrCreateUname) {
        this.mrCreateUname = mrCreateUname;
    }

    public Date getMrCreateTime() {
        return mrCreateTime;
    }

    public void setMrCreateTime(Date mrCreateTime) {
        this.mrCreateTime = mrCreateTime;
    }

    public Integer getMrState() {
        return mrState;
    }

    public void setMrState(Integer mrState) {
        this.mrState = mrState;
    }

    public Date getMrStateTime() {
        return mrStateTime;
    }

    public void setMrStateTime(Date mrStateTime) {
        this.mrStateTime = mrStateTime;
    }

    public String getMrReportUid() {
        return mrReportUid;
    }

    public void setMrReportUid(String mrReportUid) {
        this.mrReportUid = mrReportUid;
    }

    public String getMrReportUname() {
        return mrReportUname;
    }

    public void setMrReportUname(String mrReportUname) {
        this.mrReportUname = mrReportUname;
    }

    public String getMrReportTel() {
        return mrReportTel;
    }

    public void setMrReportTel(String mrReportTel) {
        this.mrReportTel = mrReportTel;
    }

    public String getMrAreaNumber() {
        return mrAreaNumber;
    }

    public void setMrAreaNumber(String mrAreaNumber) {
        this.mrAreaNumber = mrAreaNumber;
    }

    public String getMrAddress() {
        return mrAddress;
    }

    public void setMrAddress(String mrAddress) {
        this.mrAddress = mrAddress;
    }

    public Date getMrReportTime() {
        return mrReportTime;
    }

    public void setMrReportTime(Date mrReportTime) {
        this.mrReportTime = mrReportTime;
    }

    public String getMrProblem() {
        return mrProblem;
    }

    public void setMrProblem(String mrProblem) {
        this.mrProblem = mrProblem;
    }

    public String getMrDispatchUid() {
        return mrDispatchUid;
    }

    public void setMrDispatchUid(String mrDispatchUid) {
        this.mrDispatchUid = mrDispatchUid;
    }

    public String getMrDispatchUname() {
        return mrDispatchUname;
    }

    public void setMrDispatchUname(String mrDispatchUname) {
        this.mrDispatchUname = mrDispatchUname;
    }

    public Date getMrDispatchTime() {
        return mrDispatchTime;
    }

    public void setMrDispatchTime(Date mrDispatchTime) {
        this.mrDispatchTime = mrDispatchTime;
    }

    public String getMrRepairUid() {
        return mrRepairUid;
    }

    public void setMrRepairUid(String mrRepairUid) {
        this.mrRepairUid = mrRepairUid;
    }

    public String getMrRepairUname() {
        return mrRepairUname;
    }

    public void setMrRepairUname(String mrRepairUname) {
        this.mrRepairUname = mrRepairUname;
    }

    public String getMrRepairFeedback() {
        return mrRepairFeedback;
    }

    public void setMrRepairFeedback(String mrRepairFeedback) {
        this.mrRepairFeedback = mrRepairFeedback;
    }

    public String getMrRepairState() {
        return mrRepairState;
    }

    public void setMrRepairState(String mrRepairState) {
        this.mrRepairState = mrRepairState;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }


    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getMrCName() {
        return mrCName;
    }

    public void setMrCName(String mrCName) {
        this.mrCName = mrCName;
    }

    public String getMrPicBeforeIds() {
        return mrPicBeforeIds;
    }

    public void setMrPicBeforeIds(String mrPicBeforeIds) {
        this.mrPicBeforeIds = mrPicBeforeIds;
    }

    public String getMrPicAfterIds() {
        return mrPicAfterIds;
    }

    public void setMrPicAfterIds(String mrPicAfterIds) {
        this.mrPicAfterIds = mrPicAfterIds;
    }

    public String getMrCNameHistory() {
        return mrCNameHistory;
    }

    public void setMrCNameHistory(String mrCNameHistory) {
        this.mrCNameHistory = mrCNameHistory;
    }

    public String getMetId() {
        return metId;
    }

    public void setMetId(String metId) {
        this.metId = metId;
    }

    public String getMetName() {
        return metName;
    }

    public void setMetName(String metName) {
        this.metName = metName;
    }

    public String getMrAbbName() {
        return mrAbbName;
    }

    public void setMrAbbName(String mrAbbName) {
        this.mrAbbName = mrAbbName;
    }

    public String getMrCampus() {
        return mrCampus;
    }

    public void setMrCampus(String mrCampus) {
        this.mrCampus = mrCampus;
    }

    public String getMrSerialNumber() {
        return mrSerialNumber;
    }

    public void setMrSerialNumber(String mrSerialNumber) {
        this.mrSerialNumber = mrSerialNumber;
    }

    public String getMrIssuingUnit() {
        return mrIssuingUnit;
    }

    public void setMrIssuingUnit(String mrIssuingUnit) {
        this.mrIssuingUnit = mrIssuingUnit;
    }

    public String getMrRepairUnit() {
        return mrRepairUnit;
    }

    public void setMrRepairUnit(String mrRepairUnit) {
        this.mrRepairUnit = mrRepairUnit;
    }
}