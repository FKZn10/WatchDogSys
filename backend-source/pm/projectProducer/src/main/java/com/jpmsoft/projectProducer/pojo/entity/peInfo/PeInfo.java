package com.jpmsoft.projectProducer.pojo.entity.peInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * 履约评价
 */
@TableName("Pe_Info")
public class PeInfo {

    @TableId(type = IdType.INPUT)
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
    private Date peFlowStateTime;
    private String peState;
    private Date peConStartDate;
    private Date peConEndDate;
    private String peAdministrativeSanction;
    private String pePetitionRecord;
    private String peFId;
    private String peRemark;

    private Float peExtraPoints;
    private Float peDeductPoints;
    private String peExtraPointsRemark;
    private String peDeductPointsRemark;
    private String peJoin;
    private String pePmUid;
    private String peReportSeal;
    private String peSubContract;
    private String peContractCategory;
    private String peCompetentDepartment;
    private String teId;


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

    public String getPePmUid() {
        return pePmUid;
    }

    public void setPePmUid(String pePmUid) {
        this.pePmUid = pePmUid;
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

    public String getPeCompetentDepartment() {
        return peCompetentDepartment;
    }

    public void setPeCompetentDepartment(String peCompetentDepartment) {
        this.peCompetentDepartment = peCompetentDepartment;
    }

    public String getTeId() {
        return teId;
    }

    public void setTeId(String teId) {
        this.teId = teId;
    }
}
