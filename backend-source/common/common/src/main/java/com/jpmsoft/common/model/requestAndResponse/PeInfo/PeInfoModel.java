package com.jpmsoft.common.model.requestAndResponse.PeInfo;

import com.jpmsoft.common.model.BaseFileRequest;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 履约评价
 */
public class PeInfoModel extends BaseFileRequest implements Serializable {

    private static final long serialVersionUID = 2841903794724063654L;

    private String peId;
    private String peConId;
    private String peProjectId;
    private String pePm;
    private String pePmId;
    private String pePmTel;
    private String peComPrincipal;
    private String peComPrincipalTel;
    private Date peDate;
    private Integer peYear;
    private Integer peQuarter;
    private String peType;
    private String peCompanyId;
    private Float peTeamScore;
    private Date peTeamMeetingTime;
    private String peConType;
    private String peStage;
    private String peUnitId;
    private String peNames;
    private String peTeamOpinion;
    private Float peDrusScore;
    private String peDrusOpinion;
    private Float peQualityScore;
    private Float peSafetyScore;
    private Float peMaterialScore;
    private String peMaterialInspect;
    private Float peFinalScore;
    private String peFinalGrade;
    private String peGradeRule;
    private String peCreateUid;
    private String peCreateUname;
    private Date peCreateTime;
    private Integer peFlowState;
    private List<Integer> peFlowStates;
    private Date peFlowStateTime;

    private String peState;
    //合同履约开始时间
    private Date peConStartDate;
    //合同履约结束时间
    private Date peConEndDate;
    //履约单位名称
    private String peCompanyName;
    //评价单位名称
    private String peUnitName;
    //项目建设单位名称
    private String pBuildUnitName;
    //评价合同名称
    private String conName;
    //合同暂定总金额
    private String conMoneyTotal;
    //评价合同类型
    private String conType;
    //评价合同编码
    private String conCode;
    //评价项目名称
    private String pName;
    //是否有行政主管单位处罚记录
    private String peAdministrativeSanction;
    //是否有信访情况未处理
    private String pePetitionRecord;
    //评价表ID
    private String peFId;
    //备注
    private String peRemark;
    //加分清单
    private Float peExtraPoints;
    //扣分清单
    private Float peDeductPoints;
    //加分说明
    private String peExtraPointsRemark;
    //扣分说明
    private String peDeductPointsRemark;
    //是否参评
    private String peJoin;
    private String pePmUid;
    private String pePmName;
    //建设单位ID
    private String pConstructionUnit;
    private String pNameHistory;
    private String peCompanyNameHistory;
    private String peReportSeal;
    //代建子合同（分包合同备案）
    private String peSubContract;
    private String peContractCategory;
    private String peCompetentDepartment;
    private String scTitle;
    private String scName;
    private String scCode;
    private String peReportSealIds;
    //子集 履约评价详细
    private List<PeInfoDetailModel> peInfoDetails;

    private List<String> conMoneyTotals;
    private List<String> peDates;
    private List<String> peTeamScores;
    private List<String> peCreateTimes;

    //单位简称
    private String peCompanyAbbName;
    private String peUnitAbbName;

    public String getPeId() {
        return peId;
    }

    public void setPeId(String peId) {
        this.peId = peId;
    }

    public String getPeConId() {
        return peConId;
    }

    public void setPeConId(String peConId) {
        this.peConId = peConId;
    }

    public String getPeProjectId() {
        return peProjectId;
    }

    public void setPeProjectId(String peProjectId) {
        this.peProjectId = peProjectId;
    }

    public String getPePm() {
        return pePm;
    }

    public void setPePm(String pePm) {
        this.pePm = pePm;
    }

    public String getPePmId() {
        return pePmId;
    }

    public void setPePmId(String pePmId) {
        this.pePmId = pePmId;
    }

    public String getPePmTel() {
        return pePmTel;
    }

    public void setPePmTel(String pePmTel) {
        this.pePmTel = pePmTel;
    }

    public String getPeComPrincipal() {
        return peComPrincipal;
    }

    public void setPeComPrincipal(String peComPrincipal) {
        this.peComPrincipal = peComPrincipal;
    }

    public String getPeComPrincipalTel() {
        return peComPrincipalTel;
    }

    public void setPeComPrincipalTel(String peComPrincipalTel) {
        this.peComPrincipalTel = peComPrincipalTel;
    }

    public Date getPeDate() {
        return peDate;
    }

    public void setPeDate(Date peDate) {
        this.peDate = peDate;
    }

    public Integer getPeYear() {
        return peYear;
    }

    public void setPeYear(Integer peYear) {
        this.peYear = peYear;
    }

    public Integer getPeQuarter() {
        return peQuarter;
    }

    public void setPeQuarter(Integer peQuarter) {
        this.peQuarter = peQuarter;
    }

    public String getPeType() {
        return peType;
    }

    public void setPeType(String peType) {
        this.peType = peType;
    }

    public String getPeCompanyId() {
        return peCompanyId;
    }

    public void setPeCompanyId(String peCompanyId) {
        this.peCompanyId = peCompanyId;
    }

    public Float getPeTeamScore() {
        return peTeamScore;
    }

    public void setPeTeamScore(Float peTeamScore) {
        this.peTeamScore = peTeamScore;
    }

    public Date getPeTeamMeetingTime() {
        return peTeamMeetingTime;
    }

    public void setPeTeamMeetingTime(Date peTeamMeetingTime) {
        this.peTeamMeetingTime = peTeamMeetingTime;
    }

    public String getPeConType() {
        return peConType;
    }

    public void setPeConType(String peConType) {
        this.peConType = peConType;
    }

    public String getPeStage() {
        return peStage;
    }

    public void setPeStage(String peStage) {
        this.peStage = peStage;
    }

    public String getPeUnitId() {
        return peUnitId;
    }

    public void setPeUnitId(String peUnitId) {
        this.peUnitId = peUnitId;
    }

    public String getPeNames() {
        return peNames;
    }

    public void setPeNames(String peNames) {
        this.peNames = peNames;
    }

    public String getPeTeamOpinion() {
        return peTeamOpinion;
    }

    public void setPeTeamOpinion(String peTeamOpinion) {
        this.peTeamOpinion = peTeamOpinion;
    }

    public Float getPeDrusScore() {
        return peDrusScore;
    }

    public void setPeDrusScore(Float peDrusScore) {
        this.peDrusScore = peDrusScore;
    }

    public String getPeDrusOpinion() {
        return peDrusOpinion;
    }

    public void setPeDrusOpinion(String peDrusOpinion) {
        this.peDrusOpinion = peDrusOpinion;
    }

    public Float getPeQualityScore() {
        return peQualityScore;
    }

    public void setPeQualityScore(Float peQualityScore) {
        this.peQualityScore = peQualityScore;
    }

    public Float getPeSafetyScore() {
        return peSafetyScore;
    }

    public void setPeSafetyScore(Float peSafetyScore) {
        this.peSafetyScore = peSafetyScore;
    }

    public Float getPeMaterialScore() {
        return peMaterialScore;
    }

    public void setPeMaterialScore(Float peMaterialScore) {
        this.peMaterialScore = peMaterialScore;
    }

    public String getPeMaterialInspect() {
        return peMaterialInspect;
    }

    public void setPeMaterialInspect(String peMaterialInspect) {
        this.peMaterialInspect = peMaterialInspect;
    }

    public Float getPeFinalScore() {
        return peFinalScore;
    }

    public void setPeFinalScore(Float peFinalScore) {
        this.peFinalScore = peFinalScore;
    }

    public String getPeFinalGrade() {
        return peFinalGrade;
    }

    public void setPeFinalGrade(String peFinalGrade) {
        this.peFinalGrade = peFinalGrade;
    }

    public String getPeGradeRule() {
        return peGradeRule;
    }

    public void setPeGradeRule(String peGradeRule) {
        this.peGradeRule = peGradeRule;
    }

    public String getPeCreateUid() {
        return peCreateUid;
    }

    public void setPeCreateUid(String peCreateUid) {
        this.peCreateUid = peCreateUid;
    }

    public String getPeCreateUname() {
        return peCreateUname;
    }

    public void setPeCreateUname(String peCreateUname) {
        this.peCreateUname = peCreateUname;
    }

    public Date getPeCreateTime() {
        return peCreateTime;
    }

    public void setPeCreateTime(Date peCreateTime) {
        this.peCreateTime = peCreateTime;
    }

    public Integer getPeFlowState() {
        return peFlowState;
    }

    public void setPeFlowState(Integer peFlowState) {
        this.peFlowState = peFlowState;
    }

    public Date getPeFlowStateTime() {
        return peFlowStateTime;
    }

    public void setPeFlowStateTime(Date peFlowStateTime) {
        this.peFlowStateTime = peFlowStateTime;
    }

    public String getPeState() {
        return peState;
    }

    public void setPeState(String peState) {
        this.peState = peState;
    }

    public Date getPeConStartDate() {
        return peConStartDate;
    }

    public void setPeConStartDate(Date peConStartDate) {
        this.peConStartDate = peConStartDate;
    }

    public Date getPeConEndDate() {
        return peConEndDate;
    }

    public void setPeConEndDate(Date peConEndDate) {
        this.peConEndDate = peConEndDate;
    }

    public String getPeCompanyName() {
        return peCompanyName;
    }

    public void setPeCompanyName(String peCompanyName) {
        this.peCompanyName = peCompanyName;
    }

    public String getPeUnitName() {
        return peUnitName;
    }

    public void setPeUnitName(String peUnitName) {
        this.peUnitName = peUnitName;
    }

    public String getpBuildUnitName() {
        return pBuildUnitName;
    }

    public void setpBuildUnitName(String pBuildUnitName) {
        this.pBuildUnitName = pBuildUnitName;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getConMoneyTotal() {
        return conMoneyTotal;
    }

    public void setConMoneyTotal(String conMoneyTotal) {
        this.conMoneyTotal = conMoneyTotal;
    }

    public String getConType() {
        return conType;
    }

    public void setConType(String conType) {
        this.conType = conType;
    }

    public String getConCode() {
        return conCode;
    }

    public void setConCode(String conCode) {
        this.conCode = conCode;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getPeAdministrativeSanction() {
        return peAdministrativeSanction;
    }

    public void setPeAdministrativeSanction(String peAdministrativeSanction) {
        this.peAdministrativeSanction = peAdministrativeSanction;
    }

    public String getPePetitionRecord() {
        return pePetitionRecord;
    }

    public void setPePetitionRecord(String pePetitionRecord) {
        this.pePetitionRecord = pePetitionRecord;
    }

    public String getPeFId() {
        return peFId;
    }

    public void setPeFId(String peFId) {
        this.peFId = peFId;
    }

    public String getPeRemark() {
        return peRemark;
    }

    public void setPeRemark(String peRemark) {
        this.peRemark = peRemark;
    }

    public Float getPeExtraPoints() {
        return peExtraPoints;
    }

    public void setPeExtraPoints(Float peExtraPoints) {
        this.peExtraPoints = peExtraPoints;
    }

    public Float getPeDeductPoints() {
        return peDeductPoints;
    }

    public void setPeDeductPoints(Float peDeductPoints) {
        this.peDeductPoints = peDeductPoints;
    }

    public String getPeExtraPointsRemark() {
        return peExtraPointsRemark;
    }

    public void setPeExtraPointsRemark(String peExtraPointsRemark) {
        this.peExtraPointsRemark = peExtraPointsRemark;
    }

    public String getPeDeductPointsRemark() {
        return peDeductPointsRemark;
    }

    public void setPeDeductPointsRemark(String peDeductPointsRemark) {
        this.peDeductPointsRemark = peDeductPointsRemark;
    }

    public String getPeJoin() {
        return peJoin;
    }

    public void setPeJoin(String peJoin) {
        this.peJoin = peJoin;
    }

    public String getpConstructionUnit() {
        return pConstructionUnit;
    }

    public void setpConstructionUnit(String pConstructionUnit) {
        this.pConstructionUnit = pConstructionUnit;
    }

    public List<PeInfoDetailModel> getPeInfoDetails() {
        return peInfoDetails;
    }

    public void setPeInfoDetails(List<PeInfoDetailModel> peInfoDetails) {
        this.peInfoDetails = peInfoDetails;
    }

    public String getpNameHistory() {
        return pNameHistory;
    }

    public void setpNameHistory(String pNameHistory) {
        this.pNameHistory = pNameHistory;
    }

    public String getPeCompanyNameHistory() {
        return peCompanyNameHistory;
    }

    public void setPeCompanyNameHistory(String peCompanyNameHistory) {
        this.peCompanyNameHistory = peCompanyNameHistory;
    }

    public String getPeReportSeal() {
        return peReportSeal;
    }

    public void setPeReportSeal(String peReportSeal) {
        this.peReportSeal = peReportSeal;
    }


    public String getPeSubContract() {
        return peSubContract;
    }

    public void setPeSubContract(String peSubContract) {
        this.peSubContract = peSubContract;
    }

    public String getPeContractCategory() {
        return peContractCategory;
    }

    public void setPeContractCategory(String peContractCategory) {
        this.peContractCategory = peContractCategory;
    }


    public String getScTitle() {
        return scTitle;
    }

    public void setScTitle(String scTitle) {
        this.scTitle = scTitle;
    }

    public String getScName() {
        return scName;
    }

    public void setScName(String scName) {
        this.scName = scName;
    }

    public String getScCode() {
        return scCode;
    }

    public void setScCode(String scCode) {
        this.scCode = scCode;
    }

    public String getPeReportSealIds() {
        return peReportSealIds;
    }

    public void setPeReportSealIds(String peReportSealIds) {
        this.peReportSealIds = peReportSealIds;
    }

    public List<String> getConMoneyTotals() {
        return conMoneyTotals;
    }

    public void setConMoneyTotals(List<String> conMoneyTotals) {
        this.conMoneyTotals = conMoneyTotals;
    }

    public List<String> getPeDates() {
        return peDates;
    }

    public void setPeDates(List<String> peDates) {
        this.peDates = peDates;
    }

    public List<String> getPeTeamScores() {
        return peTeamScores;
    }

    public void setPeTeamScores(List<String> peTeamScores) {
        this.peTeamScores = peTeamScores;
    }

    public List<String> getPeCreateTimes() {
        return peCreateTimes;
    }

    public void setPeCreateTimes(List<String> peCreateTimes) {
        this.peCreateTimes = peCreateTimes;
    }


    public String getPePmUid() {
        return pePmUid;
    }

    public void setPePmUid(String pePmUid) {
        this.pePmUid = pePmUid;
    }

    public String getPePmName() {
        return pePmName;
    }

    public void setPePmName(String pePmName) {
        this.pePmName = pePmName;
    }

    public String getPeCompetentDepartment() {
        return peCompetentDepartment;
    }

    public void setPeCompetentDepartment(String peCompetentDepartment) {
        this.peCompetentDepartment = peCompetentDepartment;
    }

    public String getPeCompanyAbbName() {
        return peCompanyAbbName;
    }

    public void setPeCompanyAbbName(String peCompanyAbbName) {
        this.peCompanyAbbName = peCompanyAbbName;
    }

    public String getPeUnitAbbName() {
        return peUnitAbbName;
    }

    public void setPeUnitAbbName(String peUnitAbbName) {
        this.peUnitAbbName = peUnitAbbName;
    }

    public List<Integer> getPeFlowStates() {
        return peFlowStates;
    }

    public void setPeFlowStates(List<Integer> peFlowStates) {
        this.peFlowStates = peFlowStates;
    }
}
