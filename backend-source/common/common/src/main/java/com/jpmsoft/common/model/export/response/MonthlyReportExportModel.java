package com.jpmsoft.common.model.export.response;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 月报导出
 */
public class MonthlyReportExportModel {

    //项目表数据
    private String pId;
    private String pName;
    private String pOverview;
    private String pConsPeriod;
    private String pImpState;
    private Boolean pIsProjectApproval;
    private String pType;
    private String pDepartmentName;
    private BigDecimal pInvTotal;
    private String pInvCaliber;
    private String pPLanStartDate;
    private String pPlanCompleteDate;
    private String pImpCompanyName;
    private String pUseUnit;
    private String pBuildModel;

    //项目资金来源
    private BigDecimal fsOne;
    private BigDecimal fsTwo;
    private BigDecimal fsThree;
    private BigDecimal fsFour;
    private BigDecimal fsFive;
    private BigDecimal fsSix;
    private BigDecimal fsSeven;
    private BigDecimal fsAmtTotal;
    //投资计划
    private BigDecimal ipvDoneLastYear;
    private BigDecimal ipvPlannedInvestment;
    private String ipvTImgProgress;

    //月进度计划
    private String progressQuarterOne;
    private BigDecimal invQuarterOne;
    private String progressQuarterTwo;
    private BigDecimal invQuarterTwo;
    private String progressQuarterThree;
    private BigDecimal invQuarterThree;
    private String progressQuarterFour;
    private BigDecimal invQuarterFour;

    //工程报告
    private String prProUnder;
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
    private String prStartWork;
    private Integer prNumberPresent;
    private String prUrbanPlan;
    private String prDutyMain;
    private BigDecimal prAdjustInv;
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
    private BigDecimal prLastYearRate;
    private String prIsStartWork;
    private String prIsFinish;
    private BigDecimal  prPlannedLastInv;
    private BigDecimal prDoneLastYear;
    private BigDecimal prPlannedInvestment;
    private String prRefine;
    private String prSocialProject;
    private String prHandleAffairs;
    private String prOther;
    private String prImpState;

    //季报
    private String quarterImg1;
    private BigDecimal quarterComInv1;
    private BigDecimal quarterCumulativeInv1;
    private BigDecimal quarterRate1;
    private String quarterImg2;
    private BigDecimal quarterComInv2;
    private BigDecimal quarterCumulativeInv2;
    private BigDecimal quarterRate2;
    private String quarterImg3;
    private BigDecimal quarterComInv3;
    private BigDecimal quarterCumulativeInv3;
    private BigDecimal quarterRate3;
    private String quarterImg4;
    private BigDecimal quarterComInv4;
    private BigDecimal quarterCumulativeInv4;
    private BigDecimal quarterRate4;

    //    private BigDecimal prPlannedInvestment;
//    private String prTImgProgress;
//    private String prProgressQuarterOne;
//    private BigDecimal prInvQuarterOne;
//    private String prProgressQuarterTwo;
//    private BigDecimal prInvQuarterTwo;
//    private String prProgressQuarterThree;
//    private BigDecimal prInvQuarterThree;
//    private String prProgressQuarterFour;
//    private BigDecimal prInvQuarterFour;
    private Date wrDate;
    //年度计划清单
    private String apdCheckType;

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpOverview() {
        return pOverview;
    }

    public void setpOverview(String pOverview) {
        this.pOverview = pOverview;
    }

    public String getpConsPeriod() {
        return pConsPeriod;
    }

    public void setpConsPeriod(String pConsPeriod) {
        this.pConsPeriod = pConsPeriod;
    }

    public String getpImpState() {
        return pImpState;
    }

    public void setpImpState(String pImpState) {
        this.pImpState = pImpState;
    }

    public Boolean getpIsProjectApproval() {
        return pIsProjectApproval;
    }

    public void setpIsProjectApproval(Boolean pIsProjectApproval) {
        this.pIsProjectApproval = pIsProjectApproval;
    }

    public String getpType() {
        return pType;
    }

    public void setpType(String pType) {
        this.pType = pType;
    }

    public String getpDepartmentName() {
        return pDepartmentName;
    }

    public void setpDepartmentName(String pDepartmentName) {
        this.pDepartmentName = pDepartmentName;
    }

    public BigDecimal getpInvTotal() {
        return pInvTotal;
    }

    public void setpInvTotal(BigDecimal pInvTotal) {
        this.pInvTotal = pInvTotal;
    }

    public String getpInvCaliber() {
        return pInvCaliber;
    }

    public void setpInvCaliber(String pInvCaliber) {
        this.pInvCaliber = pInvCaliber;
    }

    public String getpPLanStartDate() {
        return pPLanStartDate;
    }

    public void setpPLanStartDate(String pPLanStartDate) {
        this.pPLanStartDate = pPLanStartDate;
    }

    public String getpPlanCompleteDate() {
        return pPlanCompleteDate;
    }

    public void setpPlanCompleteDate(String pPlanCompleteDate) {
        this.pPlanCompleteDate = pPlanCompleteDate;
    }

    public String getpImpCompanyName() {
        return pImpCompanyName;
    }

    public void setpImpCompanyName(String pImpCompanyName) {
        this.pImpCompanyName = pImpCompanyName;
    }

    public String getpUseUnit() {
        return pUseUnit;
    }

    public void setpUseUnit(String pUseUnit) {
        this.pUseUnit = pUseUnit;
    }

    public String getpBuildModel() {
        return pBuildModel;
    }

    public void setpBuildModel(String pBuildModel) {
        this.pBuildModel = pBuildModel;
    }

    public BigDecimal getFsOne() {
        return fsOne;
    }

    public void setFsOne(BigDecimal fsOne) {
        this.fsOne = fsOne;
    }

    public BigDecimal getFsTwo() {
        return fsTwo;
    }

    public void setFsTwo(BigDecimal fsTwo) {
        this.fsTwo = fsTwo;
    }

    public BigDecimal getFsThree() {
        return fsThree;
    }

    public void setFsThree(BigDecimal fsThree) {
        this.fsThree = fsThree;
    }

    public BigDecimal getFsFour() {
        return fsFour;
    }

    public void setFsFour(BigDecimal fsFour) {
        this.fsFour = fsFour;
    }

    public BigDecimal getFsFive() {
        return fsFive;
    }

    public void setFsFive(BigDecimal fsFive) {
        this.fsFive = fsFive;
    }

    public BigDecimal getFsSix() {
        return fsSix;
    }

    public void setFsSix(BigDecimal fsSix) {
        this.fsSix = fsSix;
    }

    public BigDecimal getFsSeven() {
        return fsSeven;
    }

    public void setFsSeven(BigDecimal fsSeven) {
        this.fsSeven = fsSeven;
    }

    public BigDecimal getFsAmtTotal() {
        return fsAmtTotal;
    }

    public void setFsAmtTotal(BigDecimal fsAmtTotal) {
        this.fsAmtTotal = fsAmtTotal;
    }

    public BigDecimal getIpvDoneLastYear() {
        return ipvDoneLastYear;
    }

    public void setIpvDoneLastYear(BigDecimal ipvDoneLastYear) {
        this.ipvDoneLastYear = ipvDoneLastYear;
    }

    public BigDecimal getIpvPlannedInvestment() {
        return ipvPlannedInvestment;
    }

    public void setIpvPlannedInvestment(BigDecimal ipvPlannedInvestment) {
        this.ipvPlannedInvestment = ipvPlannedInvestment;
    }

    public String getIpvTImgProgress() {
        return ipvTImgProgress;
    }

    public void setIpvTImgProgress(String ipvTImgProgress) {
        this.ipvTImgProgress = ipvTImgProgress;
    }

    public String getProgressQuarterOne() {
        return progressQuarterOne;
    }

    public void setProgressQuarterOne(String progressQuarterOne) {
        this.progressQuarterOne = progressQuarterOne;
    }

    public BigDecimal getInvQuarterOne() {
        return invQuarterOne;
    }

    public void setInvQuarterOne(BigDecimal invQuarterOne) {
        this.invQuarterOne = invQuarterOne;
    }

    public String getProgressQuarterTwo() {
        return progressQuarterTwo;
    }

    public void setProgressQuarterTwo(String progressQuarterTwo) {
        this.progressQuarterTwo = progressQuarterTwo;
    }

    public BigDecimal getInvQuarterTwo() {
        return invQuarterTwo;
    }

    public void setInvQuarterTwo(BigDecimal invQuarterTwo) {
        this.invQuarterTwo = invQuarterTwo;
    }

    public String getProgressQuarterThree() {
        return progressQuarterThree;
    }

    public void setProgressQuarterThree(String progressQuarterThree) {
        this.progressQuarterThree = progressQuarterThree;
    }

    public BigDecimal getInvQuarterThree() {
        return invQuarterThree;
    }

    public void setInvQuarterThree(BigDecimal invQuarterThree) {
        this.invQuarterThree = invQuarterThree;
    }

    public String getProgressQuarterFour() {
        return progressQuarterFour;
    }

    public void setProgressQuarterFour(String progressQuarterFour) {
        this.progressQuarterFour = progressQuarterFour;
    }

    public BigDecimal getInvQuarterFour() {
        return invQuarterFour;
    }

    public void setInvQuarterFour(BigDecimal invQuarterFour) {
        this.invQuarterFour = invQuarterFour;
    }

    public String getPrProUnder() {
        return prProUnder;
    }

    public void setPrProUnder(String prProUnder) {
        this.prProUnder = prProUnder;
    }

    public String getPrProcedures() {
        return prProcedures;
    }

    public void setPrProcedures(String prProcedures) {
        this.prProcedures = prProcedures;
    }

    public String getPrImgProgress() {
        return prImgProgress;
    }

    public void setPrImgProgress(String prImgProgress) {
        this.prImgProgress = prImgProgress;
    }

    public BigDecimal getPrCompleteInv() {
        return prCompleteInv;
    }

    public void setPrCompleteInv(BigDecimal prCompleteInv) {
        this.prCompleteInv = prCompleteInv;
    }

    public BigDecimal getPrYearInv() {
        return prYearInv;
    }

    public void setPrYearInv(BigDecimal prYearInv) {
        this.prYearInv = prYearInv;
    }

    public BigDecimal getPrInvRate() {
        return prInvRate;
    }

    public void setPrInvRate(BigDecimal prInvRate) {
        this.prInvRate = prInvRate;
    }

    public BigDecimal getPrMonthInv() {
        return prMonthInv;
    }

    public void setPrMonthInv(BigDecimal prMonthInv) {
        this.prMonthInv = prMonthInv;
    }

    public BigDecimal getPrMonthInvRate() {
        return prMonthInvRate;
    }

    public void setPrMonthInvRate(BigDecimal prMonthInvRate) {
        this.prMonthInvRate = prMonthInvRate;
    }

    public Boolean getPrWhetherLag() {
        return prWhetherLag;
    }

    public void setPrWhetherLag(Boolean prWhetherLag) {
        this.prWhetherLag = prWhetherLag;
    }

    public String getPrReasonLag() {
        return prReasonLag;
    }

    public void setPrReasonLag(String prReasonLag) {
        this.prReasonLag = prReasonLag;
    }

    public String getPrSuggest() {
        return prSuggest;
    }

    public void setPrSuggest(String prSuggest) {
        this.prSuggest = prSuggest;
    }

    public String getPrStartWork() {
        return prStartWork;
    }

    public void setPrStartWork(String prStartWork) {
        this.prStartWork = prStartWork;
    }

    public Integer getPrNumberPresent() {
        return prNumberPresent;
    }

    public void setPrNumberPresent(Integer prNumberPresent) {
        this.prNumberPresent = prNumberPresent;
    }

    public String getPrUrbanPlan() {
        return prUrbanPlan;
    }

    public void setPrUrbanPlan(String prUrbanPlan) {
        this.prUrbanPlan = prUrbanPlan;
    }

    public String getPrDutyMain() {
        return prDutyMain;
    }

    public void setPrDutyMain(String prDutyMain) {
        this.prDutyMain = prDutyMain;
    }

    public BigDecimal getPrAdjustInv() {
        return prAdjustInv;
    }

    public void setPrAdjustInv(BigDecimal prAdjustInv) {
        this.prAdjustInv = prAdjustInv;
    }

    public Integer getPrInitiationPlan() {
        return prInitiationPlan;
    }

    public void setPrInitiationPlan(Integer prInitiationPlan) {
        this.prInitiationPlan = prInitiationPlan;
    }

    public String getPrResponsibleDep() {
        return prResponsibleDep;
    }

    public void setPrResponsibleDep(String prResponsibleDep) {
        this.prResponsibleDep = prResponsibleDep;
    }

    public String getPrResponsibleLeader() {
        return prResponsibleLeader;
    }

    public void setPrResponsibleLeader(String prResponsibleLeader) {
        this.prResponsibleLeader = prResponsibleLeader;
    }

    public BigDecimal getPrProjectInvestment() {
        return prProjectInvestment;
    }

    public void setPrProjectInvestment(BigDecimal prProjectInvestment) {
        this.prProjectInvestment = prProjectInvestment;
    }

    public String getPrInvestmentType() {
        return prInvestmentType;
    }

    public void setPrInvestmentType(String prInvestmentType) {
        this.prInvestmentType = prInvestmentType;
    }

    public String getPrIsBudget() {
        return prIsBudget;
    }

    public void setPrIsBudget(String prIsBudget) {
        this.prIsBudget = prIsBudget;
    }

    public BigDecimal getPrBudgetInvestment() {
        return prBudgetInvestment;
    }

    public void setPrBudgetInvestment(BigDecimal prBudgetInvestment) {
        this.prBudgetInvestment = prBudgetInvestment;
    }

    public String getPrMainContent() {
        return prMainContent;
    }

    public void setPrMainContent(String prMainContent) {
        this.prMainContent = prMainContent;
    }

    public String getPrAdjustmentType() {
        return prAdjustmentType;
    }

    public void setPrAdjustmentType(String prAdjustmentType) {
        this.prAdjustmentType = prAdjustmentType;
    }

    public Date getPrAdjustmentTime() {
        return prAdjustmentTime;
    }

    public void setPrAdjustmentTime(Date prAdjustmentTime) {
        this.prAdjustmentTime = prAdjustmentTime;
    }

    public BigDecimal getPrLastYearRate() {
        return prLastYearRate;
    }

    public void setPrLastYearRate(BigDecimal prLastYearRate) {
        this.prLastYearRate = prLastYearRate;
    }

    public String getPrIsStartWork() {
        return prIsStartWork;
    }

    public void setPrIsStartWork(String prIsStartWork) {
        this.prIsStartWork = prIsStartWork;
    }

    public String getPrIsFinish() {
        return prIsFinish;
    }

    public void setPrIsFinish(String prIsFinish) {
        this.prIsFinish = prIsFinish;
    }

    public BigDecimal getPrPlannedLastInv() {
        return prPlannedLastInv;
    }

    public void setPrPlannedLastInv(BigDecimal prPlannedLastInv) {
        this.prPlannedLastInv = prPlannedLastInv;
    }

    public BigDecimal getPrDoneLastYear() {
        return prDoneLastYear;
    }

    public void setPrDoneLastYear(BigDecimal prDoneLastYear) {
        this.prDoneLastYear = prDoneLastYear;
    }

    public BigDecimal getPrPlannedInvestment() {
        return prPlannedInvestment;
    }

    public void setPrPlannedInvestment(BigDecimal prPlannedInvestment) {
        this.prPlannedInvestment = prPlannedInvestment;
    }

    public String getPrRefine() {
        return prRefine;
    }

    public void setPrRefine(String prRefine) {
        this.prRefine = prRefine;
    }

    public String getPrSocialProject() {
        return prSocialProject;
    }

    public void setPrSocialProject(String prSocialProject) {
        this.prSocialProject = prSocialProject;
    }

    public String getPrHandleAffairs() {
        return prHandleAffairs;
    }

    public void setPrHandleAffairs(String prHandleAffairs) {
        this.prHandleAffairs = prHandleAffairs;
    }

    public String getPrOther() {
        return prOther;
    }

    public void setPrOther(String prOther) {
        this.prOther = prOther;
    }

    public String getPrImpState() {
        return prImpState;
    }

    public void setPrImpState(String prImpState) {
        this.prImpState = prImpState;
    }

    public String getQuarterImg1() {
        return quarterImg1;
    }

    public void setQuarterImg1(String quarterImg1) {
        this.quarterImg1 = quarterImg1;
    }

    public BigDecimal getQuarterComInv1() {
        return quarterComInv1;
    }

    public void setQuarterComInv1(BigDecimal quarterComInv1) {
        this.quarterComInv1 = quarterComInv1;
    }

    public BigDecimal getQuarterCumulativeInv1() {
        return quarterCumulativeInv1;
    }

    public void setQuarterCumulativeInv1(BigDecimal quarterCumulativeInv1) {
        this.quarterCumulativeInv1 = quarterCumulativeInv1;
    }

    public BigDecimal getQuarterRate1() {
        return quarterRate1;
    }

    public void setQuarterRate1(BigDecimal quarterRate1) {
        this.quarterRate1 = quarterRate1;
    }

    public String getQuarterImg2() {
        return quarterImg2;
    }

    public void setQuarterImg2(String quarterImg2) {
        this.quarterImg2 = quarterImg2;
    }

    public BigDecimal getQuarterComInv2() {
        return quarterComInv2;
    }

    public void setQuarterComInv2(BigDecimal quarterComInv2) {
        this.quarterComInv2 = quarterComInv2;
    }

    public BigDecimal getQuarterCumulativeInv2() {
        return quarterCumulativeInv2;
    }

    public void setQuarterCumulativeInv2(BigDecimal quarterCumulativeInv2) {
        this.quarterCumulativeInv2 = quarterCumulativeInv2;
    }

    public BigDecimal getQuarterRate2() {
        return quarterRate2;
    }

    public void setQuarterRate2(BigDecimal quarterRate2) {
        this.quarterRate2 = quarterRate2;
    }

    public String getQuarterImg3() {
        return quarterImg3;
    }

    public void setQuarterImg3(String quarterImg3) {
        this.quarterImg3 = quarterImg3;
    }

    public BigDecimal getQuarterComInv3() {
        return quarterComInv3;
    }

    public void setQuarterComInv3(BigDecimal quarterComInv3) {
        this.quarterComInv3 = quarterComInv3;
    }

    public BigDecimal getQuarterCumulativeInv3() {
        return quarterCumulativeInv3;
    }

    public void setQuarterCumulativeInv3(BigDecimal quarterCumulativeInv3) {
        this.quarterCumulativeInv3 = quarterCumulativeInv3;
    }

    public BigDecimal getQuarterRate3() {
        return quarterRate3;
    }

    public void setQuarterRate3(BigDecimal quarterRate3) {
        this.quarterRate3 = quarterRate3;
    }

    public String getQuarterImg4() {
        return quarterImg4;
    }

    public void setQuarterImg4(String quarterImg4) {
        this.quarterImg4 = quarterImg4;
    }

    public BigDecimal getQuarterComInv4() {
        return quarterComInv4;
    }

    public void setQuarterComInv4(BigDecimal quarterComInv4) {
        this.quarterComInv4 = quarterComInv4;
    }

    public BigDecimal getQuarterCumulativeInv4() {
        return quarterCumulativeInv4;
    }

    public void setQuarterCumulativeInv4(BigDecimal quarterCumulativeInv4) {
        this.quarterCumulativeInv4 = quarterCumulativeInv4;
    }

    public BigDecimal getQuarterRate4() {
        return quarterRate4;
    }

    public void setQuarterRate4(BigDecimal quarterRate4) {
        this.quarterRate4 = quarterRate4;
    }

    public Date getWrDate() {
        return wrDate;
    }

    public void setWrDate(Date wrDate) {
        this.wrDate = wrDate;
    }

    public String getApdCheckType() {
        return apdCheckType;
    }

    public void setApdCheckType(String apdCheckType) {
        this.apdCheckType = apdCheckType;
    }
}
