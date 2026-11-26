package com.jpmsoft.common.model.requestAndResponse.Oa;

import com.jpmsoft.common.model.ProjectCommonRes;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 工程周报、月报
 */
public class OaProjectReportModel extends ProjectCommonRes {

    private String prId;
    private String wrId;
    private String pId;
    private String prType;
    private String prConstruction;
    private String prSafeProduction;
    private String prInspection;
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

    private String wrTitle;
    private Date wrDate;
    private Integer wrQuarter;


    public String getPrId() {
        return prId;
    }

    public void setPrId(String prId) {
        this.prId = prId;
    }

    public String getWrId() {
        return wrId;
    }

    public void setWrId(String wrId) {
        this.wrId = wrId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getPrType() {
        return prType;
    }

    public void setPrType(String prType) {
        this.prType = prType;
    }

    public String getPrConstruction() {
        return prConstruction;
    }

    public void setPrConstruction(String prConstruction) {
        this.prConstruction = prConstruction;
    }

    public String getPrSafeProduction() {
        return prSafeProduction;
    }

    public void setPrSafeProduction(String prSafeProduction) {
        this.prSafeProduction = prSafeProduction;
    }

    public String getPrInspection() {
        return prInspection;
    }

    public void setPrInspection(String prInspection) {
        this.prInspection = prInspection;
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

    public String getPrCreateUid() {
        return prCreateUid;
    }

    public void setPrCreateUid(String prCreateUid) {
        this.prCreateUid = prCreateUid;
    }

    public String getPrCreateUname() {
        return prCreateUname;
    }

    public void setPrCreateUname(String prCreateUname) {
        this.prCreateUname = prCreateUname;
    }

    public Date getPrCreateTime() {
        return prCreateTime;
    }

    public void setPrCreateTime(Date prCreateTime) {
        this.prCreateTime = prCreateTime;
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

    public String getPrTImgProgress() {
        return prTImgProgress;
    }

    public void setPrTImgProgress(String prTImgProgress) {
        this.prTImgProgress = prTImgProgress;
    }

    public String getPrProgressQuarterOne() {
        return prProgressQuarterOne;
    }

    public void setPrProgressQuarterOne(String prProgressQuarterOne) {
        this.prProgressQuarterOne = prProgressQuarterOne;
    }

    public BigDecimal getPrInvQuarterOne() {
        return prInvQuarterOne;
    }

    public void setPrInvQuarterOne(BigDecimal prInvQuarterOne) {
        this.prInvQuarterOne = prInvQuarterOne;
    }

    public String getPrProgressQuarterTwo() {
        return prProgressQuarterTwo;
    }

    public void setPrProgressQuarterTwo(String prProgressQuarterTwo) {
        this.prProgressQuarterTwo = prProgressQuarterTwo;
    }

    public BigDecimal getPrInvQuarterTwo() {
        return prInvQuarterTwo;
    }

    public void setPrInvQuarterTwo(BigDecimal prInvQuarterTwo) {
        this.prInvQuarterTwo = prInvQuarterTwo;
    }

    public String getPrProgressQuarterThree() {
        return prProgressQuarterThree;
    }

    public void setPrProgressQuarterThree(String prProgressQuarterThree) {
        this.prProgressQuarterThree = prProgressQuarterThree;
    }

    public BigDecimal getPrInvQuarterThree() {
        return prInvQuarterThree;
    }

    public void setPrInvQuarterThree(BigDecimal prInvQuarterThree) {
        this.prInvQuarterThree = prInvQuarterThree;
    }

    public String getPrProgressQuarterFour() {
        return prProgressQuarterFour;
    }

    public void setPrProgressQuarterFour(String prProgressQuarterFour) {
        this.prProgressQuarterFour = prProgressQuarterFour;
    }

    public BigDecimal getPrInvQuarterFour() {
        return prInvQuarterFour;
    }

    public void setPrInvQuarterFour(BigDecimal prInvQuarterFour) {
        this.prInvQuarterFour = prInvQuarterFour;
    }

    public String getPrUrbanPlan() {
        return prUrbanPlan;
    }

    public void setPrUrbanPlan(String prUrbanPlan) {
        this.prUrbanPlan = prUrbanPlan;
    }

    public String getPrInvPlan() {
        return prInvPlan;
    }

    public void setPrInvPlan(String prInvPlan) {
        this.prInvPlan = prInvPlan;
    }

    public String getPrSocialProject() {
        return prSocialProject;
    }

    public void setPrSocialProject(String prSocialProject) {
        this.prSocialProject = prSocialProject;
    }

    public String getPrPractical() {
        return prPractical;
    }

    public void setPrPractical(String prPractical) {
        this.prPractical = prPractical;
    }

    public String getPrRefine() {
        return prRefine;
    }

    public void setPrRefine(String prRefine) {
        this.prRefine = prRefine;
    }

    public String getPrKeyWork() {
        return prKeyWork;
    }

    public void setPrKeyWork(String prKeyWork) {
        this.prKeyWork = prKeyWork;
    }

    public String getPrKeyTask() {
        return prKeyTask;
    }

    public void setPrKeyTask(String prKeyTask) {
        this.prKeyTask = prKeyTask;
    }

    public String getPrEconomicDeve() {
        return prEconomicDeve;
    }

    public void setPrEconomicDeve(String prEconomicDeve) {
        this.prEconomicDeve = prEconomicDeve;
    }

    public String getPrBrokenRoad() {
        return prBrokenRoad;
    }

    public void setPrBrokenRoad(String prBrokenRoad) {
        this.prBrokenRoad = prBrokenRoad;
    }

    public String getPrLivableCity() {
        return prLivableCity;
    }

    public void setPrLivableCity(String prLivableCity) {
        this.prLivableCity = prLivableCity;
    }

    public String getPrUrbanAction() {
        return prUrbanAction;
    }

    public void setPrUrbanAction(String prUrbanAction) {
        this.prUrbanAction = prUrbanAction;
    }

    public String getPrRefuseClass() {
        return prRefuseClass;
    }

    public void setPrRefuseClass(String prRefuseClass) {
        this.prRefuseClass = prRefuseClass;
    }

    public String getPrParking() {
        return prParking;
    }

    public void setPrParking(String prParking) {
        this.prParking = prParking;
    }

    public String getPrDistrictConst() {
        return prDistrictConst;
    }

    public void setPrDistrictConst(String prDistrictConst) {
        this.prDistrictConst = prDistrictConst;
    }

    public String getPrRemark() {
        return prRemark;
    }

    public void setPrRemark(String prRemark) {
        this.prRemark = prRemark;
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

    public BigDecimal getPrAdjustInv() {
        return prAdjustInv;
    }

    public void setPrAdjustInv(BigDecimal prAdjustInv) {
        this.prAdjustInv = prAdjustInv;
    }

    public String getPrDutyMain() {
        return prDutyMain;
    }

    public void setPrDutyMain(String prDutyMain) {
        this.prDutyMain = prDutyMain;
    }

    public String getPrConstructionState() {
        return prConstructionState;
    }

    public void setPrConstructionState(String prConstructionState) {
        this.prConstructionState = prConstructionState;
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

    public String getPrQuarterlyImageSchedule() {
        return prQuarterlyImageSchedule;
    }

    public void setPrQuarterlyImageSchedule(String prQuarterlyImageSchedule) {
        this.prQuarterlyImageSchedule = prQuarterlyImageSchedule;
    }

    public BigDecimal getPrQuarterlyCompleteInvestment() {
        return prQuarterlyCompleteInvestment;
    }

    public void setPrQuarterlyCompleteInvestment(BigDecimal prQuarterlyCompleteInvestment) {
        this.prQuarterlyCompleteInvestment = prQuarterlyCompleteInvestment;
    }

    public BigDecimal getPrQuarterlyFinishRate() {
        return prQuarterlyFinishRate;
    }

    public void setPrQuarterlyFinishRate(BigDecimal prQuarterlyFinishRate) {
        this.prQuarterlyFinishRate = prQuarterlyFinishRate;
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

    public String getWrTitle() {
        return wrTitle;
    }

    public void setWrTitle(String wrTitle) {
        this.wrTitle = wrTitle;
    }

    public Date getWrDate() {
        return wrDate;
    }

    public void setWrDate(Date wrDate) {
        this.wrDate = wrDate;
    }


    public Integer getWrQuarter() {
        return wrQuarter;
    }

    public void setWrQuarter(Integer wrQuarter) {
        this.wrQuarter = wrQuarter;
    }
}
