package com.jpmsoft.common.model.request.Maintenance;

import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 维保记录
 */
@Data
public class MaintenanceRecordRequest extends BaseFileRequest {

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
    private String pName;
    private String conName;
    private String mrPicBeforeIds;
    private String mrPicAfterIds;
    private List<String> mrCosts;
    private List<String> mrCreateTimes;

    private String mrSerialNumber;//流水号

    private String mrConstructUnitName;//没用

    private String mrConstructionName;//施工单位名称
    private String mrConstructionId;//施工单位id

    private String mrCampus;

    private String mfCode;

    // 是否已经在零星结算 流程中 或 流程结束
    private Boolean piecemealSettlementFlowFlag;

    private List<String> mrReportTimes;

    private String jinfeihao;

    /**
     * 报修部门Id
     */
    private String mrReportDepartmentId;
    /**
     * 报修部门名称
     */
    private String mrReportDepartmentName;
    /**
     * 报修金额（预）
     */
    private BigDecimal mrReportAmountPre;

    /**
     * 报修是否包含施工图 0-否 1-是
     */
    private Integer mrReportIsContainsConstructionDrawing;

    /**
     * 报修施工图Id
     */
    private String mrReportConstructionDrawingId;

    /**
     * 报修内容描述
     */
    private String mrReportContext;

    /**
     * 报修附件上传
     */
    private String mrReportFile;
    private String mrReportFileIDs;

    /**
     * 预算金额
     */
    private BigDecimal mrBudgetAmount;

    private String mrCategory;

    /**
     * 传了就是创建人、审批人鉴权
     * 01 开启
     */
    private String checkAuthority;

    private String mrIssuingUnit;



    public String getJinfeihao() {
        return jinfeihao;
    }

    public void setJinfeihao(String jinfeihao) {
        this.jinfeihao = jinfeihao;
    }

    public Boolean getPiecemealSettlementFlowFlag() {
        return piecemealSettlementFlowFlag;
    }

    public void setPiecemealSettlementFlowFlag(Boolean piecemealSettlementFlowFlag) {
        this.piecemealSettlementFlowFlag = piecemealSettlementFlowFlag;
    }

    public List<String> getMrReportTimes() {
        return mrReportTimes;
    }

    public void setMrReportTimes(List<String> mrReportTimes) {
        this.mrReportTimes = mrReportTimes;
    }

    public String getMfCode() {
        return mfCode;
    }

    public void setMfCode(String mfCode) {
        this.mfCode = mfCode;
    }

    public String getMrCampus() {
        return mrCampus;
    }

    public void setMrCampus(String mrCampus) {
        this.mrCampus = mrCampus;
    }

    public String getMrConstructionName() {
        return mrConstructionName;
    }

    public void setMrConstructionName(String mrConstructionName) {
        this.mrConstructionName = mrConstructionName;
    }

    public String getMrConstructionId() {
        return mrConstructionId;
    }

    public void setMrConstructionId(String mrConstructionId) {
        this.mrConstructionId = mrConstructionId;
    }

    public String getMrConstructUnitName() {
        return mrConstructUnitName;
    }

    public void setMrConstructUnitName(String mrConstructUnitName) {
        this.mrConstructUnitName = mrConstructUnitName;
    }

    public String getMrSerialNumber() {
        return mrSerialNumber;
    }

    public void setMrSerialNumber(String mrSerialNumber) {
        this.mrSerialNumber = mrSerialNumber;
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

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
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

    public List<String> getMrCosts() {
        return mrCosts;
    }

    public void setMrCosts(List<String> mrCosts) {
        this.mrCosts = mrCosts;
    }

    public List<String> getMrCreateTimes() {
        return mrCreateTimes;
    }

    public void setMrCreateTimes(List<String> mrCreateTimes) {
        this.mrCreateTimes = mrCreateTimes;
    }
}