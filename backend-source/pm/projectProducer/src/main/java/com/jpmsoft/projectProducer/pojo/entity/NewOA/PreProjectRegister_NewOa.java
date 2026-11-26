package com.jpmsoft.projectProducer.pojo.entity.NewOA;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目注册
 */
@Data
@TableName("Pre_ProjectRegister")
public class PreProjectRegister_NewOa {
    @TableId(type = IdType.INPUT)
    private String prId;
    private String prName;
    private String prCode;
    private String prCategory;
    private String prType;
    private String prProjectStatus;
    private String prSourcesOfBunds;
    private Float prBuildingScale;
    private BigDecimal prEstimatedAmount;
    private String prReason;
    private String prUseUnitID; //用户单位ID（Add）
    private String prSimpleName; //项目简称（Add）
    private String prUseUnit;
    private String prCommitteeProjectCode;
    private String prCommitteeApprovaDocument;
    private String prCityLeaderMeetingMinutes;
    private String prCityLeaderInstructions;
    private String prUseUnitLetter;
    private String prLandApproval;
    private String prOther;
    private String prRemark;
    private String prApplicantId;
    private Date prCreateTime;
    private Integer prApprovalStatus;
    private Date prApprovalTime;
    private String prStatus;
    private String prCreateUserName;
    private String prNature;
    private BigDecimal prReportedAmount;
    private String prApprovaDocument;
    private String prLeaderMeetingMinutes;
    private String prLeaderInstructions;
    private BigDecimal prConstructionCost;
    private BigDecimal prContractPrice;
    private BigDecimal prPurchasePrice;
    private String prConstructionUnitId;
    private Date prPlanStart;
    private Date prPlanEnd;
    private String prPmName;
    private String prPmTel;
    private String prEstimateFile;
    private String prScaleDescribe;
    private String prInvNature;
    private String prBuildingUnit;
    private String prMajorType;
    private String prCompetentDepartment;
    private Integer prInterventionYear;
    private String prProjectStage;
    private String prSafetyFiling;

    private String cplId;
    private String prApplyName;
    private BigDecimal prFiscalBudget;
    private String teId;
    private String prAddress;
    //项目总投资批复金额
    private BigDecimal prApproveBudget;
    private String prCampus;
    private Integer prYear;

    private String prOpinionFile;
    private String prPlanFile;
    private String prFeasibilityFile;
    private String prApproveFile;
}
