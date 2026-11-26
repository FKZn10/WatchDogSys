package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.common.annotations.SecretField;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 项目注册
 */
@Data
@TableName("Pre_ProjectRegister")
public class PreProjectRegister {

    @SecretField
    @TableId(type = IdType.INPUT)
    private String prId;
    @SecretField
    private String prName;
    @SecretField
    private String prCode;
    @SecretField
    private String prCategory;
    @SecretField
    private String prType;
    @SecretField
    private String prProjectStatus;
    @SecretField
    private String prSourcesOfBunds;
    @SecretField
    private Float prBuildingScale;
    @SecretField
    private BigDecimal prEstimatedAmount;
    @SecretField
    private String prReason;
    @SecretField
    private String prUseUnit;
    @SecretField
    private String prCommitteeProjectCode;
    @SecretField
    private String prCommitteeApprovaDocument;
    @SecretField
    private String prCityLeaderMeetingMinutes;
    @SecretField
    private String prCityLeaderInstructions;
    @SecretField
    private String prUseUnitLetter;
    @SecretField
    private String prLandApproval;
    @SecretField
    private String prOther;
    @SecretField
    private String prRemark;
    @SecretField
    private String prApplicantId;
    @SecretField
    private Date prCreateTime;
    private Integer prApprovalStatus;
    private Date prApprovalTime;
    private String prStatus;
    @SecretField
    private String prCreateUserName;
    @SecretField
    private String prNature;
    @SecretField
    private BigDecimal prReportedAmount;
    @SecretField
    private String prApprovaDocument;
    @SecretField
    private String prLeaderMeetingMinutes;
    @SecretField
    private String prLeaderInstructions;
    @SecretField
    private BigDecimal prConstructionCost;
    @SecretField
    private BigDecimal prContractPrice;
    @SecretField
    private BigDecimal prPurchasePrice;
    @SecretField
    private String prConstructionUnitId;
    @SecretField
    private Date prPlanStart;
    @SecretField
    private Date prPlanEnd;
    @SecretField
    private String prPmName;
    @SecretField
    private String prPmTel;
    @SecretField
    private String prEstimateFile;
    @SecretField
    private String prScaleDescribe;
    @SecretField
    private String prInvNature;
    @SecretField
    private String prBuildingUnit;
    @SecretField
    private String prMajorType;
    @SecretField
    private String prCompetentDepartment;
    @SecretField
    private Integer prInterventionYear;
    @SecretField
    private String prProjectStage;
    @SecretField
    private String prSafetyFiling;

    @SecretField
    private String cplId;
    @SecretField
    private String prApplyName;
    @SecretField
    private BigDecimal prFiscalBudget;
    @SecretField
    private String teId;
    @SecretField
    private String prAddress;
    //项目总投资批复金额
    @SecretField
    private BigDecimal prApproveBudget;
    @SecretField
    private String prCampus;
    @SecretField
    private Integer prYear;

    @SecretField
    private String prOpinionFile;
    @SecretField
    private String prPlanFile;
    @SecretField
    private String prFeasibilityFile;
    @SecretField
    private String prApproveFile;
    @SecretField
    private Float prLength;
    @SecretField
    private Float prWidth;
    @SecretField
    private Float prLandArea;

    @SecretField
    private String PrAbbreviation;
    @SecretField
    private BigDecimal PrGcjsqtAmt;
    @SecretField
    private BigDecimal PrTdAmount;
    @SecretField
    private BigDecimal PrYbAmt;
    @SecretField
    private BigDecimal PrJsqdkLx;
    @SecretField
    private Date PrPlanStartDate;
    @SecretField
    private Date PrPlanCompleteDate;

    private String sign;
}
