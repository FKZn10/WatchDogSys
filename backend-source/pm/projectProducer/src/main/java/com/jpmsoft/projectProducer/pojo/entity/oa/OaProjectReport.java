package com.jpmsoft.projectProducer.pojo.entity.oa;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 工程周报、月报
 */
@Data
@TableName("OA_Project_Report")
public class OaProjectReport {

    @TableId(type = IdType.INPUT)
    private String prId;
    private String wrId;
    private String prProUnder;
    private String pId;
    private String prType;
    private String prConstruction;
    private String prSafeProduction;
    private String prInspection;
    private String prProcedures;
    private String prImgProgress;
    private BigDecimal prCompleteInv;
    private BigDecimal prYearInv;
    private BigDecimal prInvRate;
    private BigDecimal prMonthInv;
    private BigDecimal prMonthInvRate;
    private Boolean prWhetherLag;
    private String prReasonLag;
    private String prSuggest;
    private String prCreateUid;
    private String prCreateUname;
    private Date prCreateTime;
    private BigDecimal prDoneLastYear;
    private BigDecimal prPlannedInvestment;
    private String prTImgProgress;
    private String prProgressQuarterOne;
    private BigDecimal prInvQuarterOne;
    private String prProgressQuarterTwo;
    private BigDecimal prInvQuarterTwo;
    private String prProgressQuarterThree;
    private BigDecimal prInvQuarterThree;
    private String prProgressQuarterFour;
    private BigDecimal prInvQuarterFour;

    private String prUrbanPlan;
    private String prInvPlan;
    private String prSocialProject;
    private String prPractical;
    private String prRefine;
    private String prKeyWork;
    private String prKeyTask;
    private String prEconomicDeve;
    private String prBrokenRoad;
    private String prLivableCity;
    private String prUrbanAction;
    private String prRefuseClass;
    private String prParking;
    private String prDistrictConst;
    private String prRemark;
    private String prStartWork;
    private Integer prNumberPresent;
    private BigDecimal prAdjustInv;
    private String prDutyMain;

    private String prConstructionState;
    private Integer prInitiationPlan;
    private String prResponsibleDep;
    private String prResponsibleLeader;
    private BigDecimal prProjectInvestment;
    private String prInvestmentType;
    private String prIsBudget;
    private BigDecimal prBudgetInvestment;
    private String prMainContent;
    private String prAdjustmentType;
    private Date prAdjustmentTime;
    private String prQuarterlyImageSchedule;
    private BigDecimal prQuarterlyCompleteInvestment;
    private BigDecimal prQuarterlyFinishRate;
    private BigDecimal prLastYearRate;
    private String prIsStartWork;
    private String prIsFinish;
    private BigDecimal prPlannedLastInv;

    private String prHandleAffairs;
    private String prOther;
    private String prImpState;
    private String teId;
}
