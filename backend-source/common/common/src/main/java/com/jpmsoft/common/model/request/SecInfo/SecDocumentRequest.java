package com.jpmsoft.common.model.request.SecInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.annotations.ClassInfo;
import com.jpmsoft.common.model.BaseFileRequest;
import com.jpmsoft.common.model.response.SecInfo.SecProjectResponse;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 招标文件
 */
public class
SecDocumentRequest extends BaseFileRequest {
    private String dId;
    private String secId;
    private String dScope;
    private BigDecimal dAmount;
    private String dDeterminType;
    private String dPriceMode;
    private BigDecimal dUpperLimit;
    private String dReleaseVersion;
    private String dCreateUid;
    private String dCreateUname;
    private Date dCreateTime;
    private Integer dState;
    private Date dStateTime;
    private String dRemark;
    private String dFileName;
    private String dApprovalType;
    private String dCategory;
    private Integer dDuration;
    private String dFormalization;
    private String dFunction;
    private String dIsEvaluateSeparation;
    private Date dBidTime;
    private String dLocation;
    private String dPerson;
    private String dManageConsul;
    private String dAgentUnit;
    private String dFileCode;
    private String dApplyTilte;
    private String dApplyCode;
    private Date dApplyDate;
    private String dApplyFile;
    private String dOtherFile;
    private String dListFile;
    private String dFeatures;
    private String dExecutiveStandard;
    private String dBidderManager;
    private String dPerformanceGuaranteeType;
    private BigDecimal dPerformanceBond;
    private String dSourceFunds;
    private BigDecimal dRate;
    private String dOtherValuation;
    private String dOrganizeForm;
    private String dQualiReview;
    private Integer dBiddingCycle;
    private String dInvestorsQuali;
    private String dBiddingMethod;
    private Float dPerfBondRatio;
    private String dQualification;
    private String dAchievement;
    private String dEntrustedResult;
    private String dEntrustedCpy;
    private BigDecimal dEntrustedAmt;
    private String dPm;
    private Integer dPeriod;
    private Date dBeginTime;
    private String dTradingPlace;
    private String dReceiveJointBidd;
    private String dQuotationMethod;
    private String dBidderManagerCon;
    private String dApprovalMatter;
    private String dCid;
    private String dDid;
    private String dCnature;
    private String dDnature;
    private String dConType;
    private String dHead;
    private String dTitle;
    private String dPmId;
    private Date dEndTime;
    private Date dBidDocEndTime;
    private String dBidDocAddr;
    private String dBidRoom;
    private String dBidFinalFile;
    private String dProcure;
    //公告地址
    private String dUrl;
    //定标人数
    private Integer dCalibrationPersonnelNum;

    private String dCname;
    private String dDname;
    private String dPmName;
    private String dBidFinalFileIds;
    private String dApplyFileIds;
    private String dOtherFileIds;
    private String dListFileIds;
    private String secName;
    private String pName;
    private Integer secFlowState;
    private String dBidMonYear;
    //招标金额（范围筛选）、
    private List<String> dAmounts;
    //报价上限（范围筛选）、
    private List<String> dUpperLimits;
    //创建时间（范围筛选）、
    private List<String> dCreateTimes;
    private List<String> secMoneys;
    private List<SecInviteRosterDto> secInviteRosters;

    private String pId;
    private String dPrequalification;
    private BigDecimal dBidBudgetCost;
    private BigDecimal dBidControlPrice;
    private BigDecimal dProvisionalSum;
    private BigDecimal dEstimatedPrice;
    private Date dReviewDate;
    private String dBiddingPlatform;
    private String dAgency;
    private String dCostOrganization;
    private String dPricingMethod;
    private String dApprovalContent;
    private String dSpecialInstructions;
    private String dPaymentTerms;
    private String dCompilationUnit;
    private String dBidReviewUnit;
    private String dFileType;
    private String dAcceptanceDep;
    private String dBasePriceId;

    private Date dBidPubDate;


    private String dFunctionText;

    private String dIsTwoStage;

    private String pCategory;

    @JsonProperty("pNameList")
    @ClassInfo(title = "项目名称", type = "string", value = "pNameList", dbValue = "case when pre.P_Name is null then sp.P_Name else pre.P_Name end", no = 1)
    private List<String> pNameList;

    @ClassInfo(title = "标段名称", type = "string", value = "secNameList", dbValue = "sec.sec_Name", no = 2)
    private List<String> secNameList;

    @JsonProperty("pCategoryList")
    @ClassInfo(title = "项目分类", type = "dictionary", value = "pCategoryList", dbValue = "case when pre.P_Category is null then sp.p_Category else pre.P_Category end",dictionary = "JPMsoft_NDTZXMFL", no = 3)
    private List<String> pCategoryList;

    @JsonProperty("dCreateUnameList")
    @ClassInfo(title = "发起人", type = "string", value = "dCreateUnameList", dbValue = "mn.d_Create_Uname", no = 4)
    private List<String> dCreateUnameList;

    @JsonProperty("dCreateTimeStr")
    @ClassInfo(title = "发起时间", type = "date", value = "dCreateTimeStr", dbValue = "mn.d_Create_Time", no = 5)
    private String dCreateTimeStr;


    @JsonProperty("dStateList")
    @ClassInfo(title = "流程状态", type = "dictionary", value = "dStateList", dbValue = "mn.d_State",dictionary = "JPMsoft_LCZT", no = 6)
    private List<Integer> dStateList;

    /*
     * 项目规模
     */
    private String projectCategory;
    /*
     * 发起单位
     */
    private String projectInitiatingUnit;

    private String projectOwner;

    private String isNew;

    private List<SecProjectResponse> secProjects;

    /**
     * 控制价盖章文件
     */
    private String controlPriceFile;
    private String controlPriceFileIds;

    private List<String> participationOrFinished;

    private String dCalibrationMethod;

    private String dType;

    private BigDecimal dTenderSumLimit;

    private BigDecimal dBiddingControlRate;

    private BigDecimal dConversionControlPrice;

    public String getdCalibrationMethod() {
        return dCalibrationMethod;
    }

    public void setdCalibrationMethod(String dCalibrationMethod) {
        this.dCalibrationMethod = dCalibrationMethod;
    }

    public String getdType() {
        return dType;
    }

    public void setdType(String dType) {
        this.dType = dType;
    }

    public BigDecimal getdTenderSumLimit() {
        return dTenderSumLimit;
    }

    public void setdTenderSumLimit(BigDecimal dTenderSumLimit) {
        this.dTenderSumLimit = dTenderSumLimit;
    }

    public BigDecimal getdBiddingControlRate() {
        return dBiddingControlRate;
    }

    public void setdBiddingControlRate(BigDecimal dBiddingControlRate) {
        this.dBiddingControlRate = dBiddingControlRate;
    }

    public BigDecimal getdConversionControlPrice() {
        return dConversionControlPrice;
    }

    public void setdConversionControlPrice(BigDecimal dConversionControlPrice) {
        this.dConversionControlPrice = dConversionControlPrice;
    }

    public List<String> getParticipationOrFinished() {
        return participationOrFinished;
    }

    public void setParticipationOrFinished(List<String> participationOrFinished) {
        this.participationOrFinished = participationOrFinished;
    }

    public String getControlPriceFileIds() {
        return controlPriceFileIds;
    }

    public void setControlPriceFileIds(String controlPriceFileIds) {
        this.controlPriceFileIds = controlPriceFileIds;
    }

    public String getControlPriceFile() {
        return controlPriceFile;
    }

    public void setControlPriceFile(String controlPriceFile) {
        this.controlPriceFile = controlPriceFile;
    }

    public List<SecProjectResponse> getSecProjects() {
        return secProjects;
    }

    public void setSecProjects(List<SecProjectResponse> secProjects) {
        this.secProjects = secProjects;
    }

    public String getIsNew() {
        return isNew;
    }

    public void setIsNew(String isNew) {
        this.isNew = isNew;
    }

    public String getProjectOwner() {
        return projectOwner;
    }

    public void setProjectOwner(String projectOwner) {
        this.projectOwner = projectOwner;
    }

    public Date getdBidPubDate() {
        return dBidPubDate;
    }

    public void setdBidPubDate(Date dBidPubDate) {
        this.dBidPubDate = dBidPubDate;
    }

    public String getpCategory() {
        return pCategory;
    }

    public void setpCategory(String pCategory) {
        this.pCategory = pCategory;
    }

    public List<String> getpCategoryList() {
        return pCategoryList;
    }

    public void setpCategoryList(List<String> pCategoryList) {
        this.pCategoryList = pCategoryList;
    }

    public String getdCreateTimeStr() {
        return dCreateTimeStr;
    }

    public void setdCreateTimeStr(String dCreateTimeStr) {
        this.dCreateTimeStr = dCreateTimeStr;
    }

    public List<String> getpNameList() {
        return pNameList;
    }

    public void setpNameList(List<String> pNameList) {
        this.pNameList = pNameList;
    }

    public List<String> getSecNameList() {
        return secNameList;
    }

    public void setSecNameList(List<String> secNameList) {
        this.secNameList = secNameList;
    }


    public List<String> getdCreateUnameList() {
        return dCreateUnameList;
    }

    public void setdCreateUnameList(List<String> dCreateUnameList) {
        this.dCreateUnameList = dCreateUnameList;
    }


    public List<Integer> getdStateList() {
        return dStateList;
    }

    public void setdStateList(List<Integer> dStateList) {
        this.dStateList = dStateList;
    }

    public String getdFunctionText() {
        return dFunctionText;
    }

    public void setdFunctionText(String dFunctionText) {
        this.dFunctionText = dFunctionText;
    }

    public String getdIsTwoStage() {
        return dIsTwoStage;
    }

    public void setdIsTwoStage(String dIsTwoStage) {
        this.dIsTwoStage = dIsTwoStage;
    }

    @Override
    public String getpId() {
        return pId;
    }

    @Override
    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getdPrequalification() {
        return dPrequalification;
    }

    public void setdPrequalification(String dPrequalification) {
        this.dPrequalification = dPrequalification;
    }

    public BigDecimal getdBidBudgetCost() {
        return dBidBudgetCost;
    }

    public void setdBidBudgetCost(BigDecimal dBidBudgetCost) {
        this.dBidBudgetCost = dBidBudgetCost;
    }

    public BigDecimal getdBidControlPrice() {
        return dBidControlPrice;
    }

    public void setdBidControlPrice(BigDecimal dBidControlPrice) {
        this.dBidControlPrice = dBidControlPrice;
    }

    public BigDecimal getdProvisionalSum() {
        return dProvisionalSum;
    }

    public void setdProvisionalSum(BigDecimal dProvisionalSum) {
        this.dProvisionalSum = dProvisionalSum;
    }

    public BigDecimal getdEstimatedPrice() {
        return dEstimatedPrice;
    }

    public void setdEstimatedPrice(BigDecimal dEstimatedPrice) {
        this.dEstimatedPrice = dEstimatedPrice;
    }

    public Date getdReviewDate() {
        return dReviewDate;
    }

    public void setdReviewDate(Date dReviewDate) {
        this.dReviewDate = dReviewDate;
    }

    public String getdBiddingPlatform() {
        return dBiddingPlatform;
    }

    public void setdBiddingPlatform(String dBiddingPlatform) {
        this.dBiddingPlatform = dBiddingPlatform;
    }

    public String getdAgency() {
        return dAgency;
    }

    public void setdAgency(String dAgency) {
        this.dAgency = dAgency;
    }

    public String getdCostOrganization() {
        return dCostOrganization;
    }

    public void setdCostOrganization(String dCostOrganization) {
        this.dCostOrganization = dCostOrganization;
    }

    public String getdPricingMethod() {
        return dPricingMethod;
    }

    public void setdPricingMethod(String dPricingMethod) {
        this.dPricingMethod = dPricingMethod;
    }

    public String getdApprovalContent() {
        return dApprovalContent;
    }

    public void setdApprovalContent(String dApprovalContent) {
        this.dApprovalContent = dApprovalContent;
    }

    public String getdSpecialInstructions() {
        return dSpecialInstructions;
    }

    public void setdSpecialInstructions(String dSpecialInstructions) {
        this.dSpecialInstructions = dSpecialInstructions;
    }

    public String getdPaymentTerms() {
        return dPaymentTerms;
    }

    public void setdPaymentTerms(String dPaymentTerms) {
        this.dPaymentTerms = dPaymentTerms;
    }

    public String getdCompilationUnit() {
        return dCompilationUnit;
    }

    public void setdCompilationUnit(String dCompilationUnit) {
        this.dCompilationUnit = dCompilationUnit;
    }

    public String getdBidReviewUnit() {
        return dBidReviewUnit;
    }

    public void setdBidReviewUnit(String dBidReviewUnit) {
        this.dBidReviewUnit = dBidReviewUnit;
    }

    public String getdFileType() {
        return dFileType;
    }

    public void setdFileType(String dFileType) {
        this.dFileType = dFileType;
    }

    public String getdAcceptanceDep() {
        return dAcceptanceDep;
    }

    public void setdAcceptanceDep(String dAcceptanceDep) {
        this.dAcceptanceDep = dAcceptanceDep;
    }

    public String getdBasePriceId() {
        return dBasePriceId;
    }

    public void setdBasePriceId(String dBasePriceId) {
        this.dBasePriceId = dBasePriceId;
    }

    public String getdId() {
        return dId;
    }

    public void setdId(String dId) {
        this.dId = dId;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public String getdScope() {
        return dScope;
    }

    public void setdScope(String dScope) {
        this.dScope = dScope;
    }

    public BigDecimal getdAmount() {
        return dAmount;
    }

    public void setdAmount(BigDecimal dAmount) {
        this.dAmount = dAmount;
    }

    public String getdDeterminType() {
        return dDeterminType;
    }

    public void setdDeterminType(String dDeterminType) {
        this.dDeterminType = dDeterminType;
    }

    public String getdPriceMode() {
        return dPriceMode;
    }

    public void setdPriceMode(String dPriceMode) {
        this.dPriceMode = dPriceMode;
    }

    public BigDecimal getdUpperLimit() {
        return dUpperLimit;
    }

    public void setdUpperLimit(BigDecimal dUpperLimit) {
        this.dUpperLimit = dUpperLimit;
    }

    public String getdReleaseVersion() {
        return dReleaseVersion;
    }

    public void setdReleaseVersion(String dReleaseVersion) {
        this.dReleaseVersion = dReleaseVersion;
    }

    public String getdCreateUid() {
        return dCreateUid;
    }

    public void setdCreateUid(String dCreateUid) {
        this.dCreateUid = dCreateUid;
    }

    public String getdCreateUname() {
        return dCreateUname;
    }

    public void setdCreateUname(String dCreateUname) {
        this.dCreateUname = dCreateUname;
    }

    public Date getdCreateTime() {
        return dCreateTime;
    }

    public void setdCreateTime(Date dCreateTime) {
        this.dCreateTime = dCreateTime;
    }

    public Integer getdState() {
        return dState;
    }

    public void setdState(Integer dState) {
        this.dState = dState;
    }

    public Date getdStateTime() {
        return dStateTime;
    }

    public void setdStateTime(Date dStateTime) {
        this.dStateTime = dStateTime;
    }

    public String getdRemark() {
        return dRemark;
    }

    public void setdRemark(String dRemark) {
        this.dRemark = dRemark;
    }

    public String getdFileName() {
        return dFileName;
    }

    public void setdFileName(String dFileName) {
        this.dFileName = dFileName;
    }

    public String getdApprovalType() {
        return dApprovalType;
    }

    public void setdApprovalType(String dApprovalType) {
        this.dApprovalType = dApprovalType;
    }

    public String getdCategory() {
        return dCategory;
    }

    public void setdCategory(String dCategory) {
        this.dCategory = dCategory;
    }

    public Integer getdDuration() {
        return dDuration;
    }

    public void setdDuration(Integer dDuration) {
        this.dDuration = dDuration;
    }

    public String getdFormalization() {
        return dFormalization;
    }

    public void setdFormalization(String dFormalization) {
        this.dFormalization = dFormalization;
    }

    public String getdFunction() {
        return dFunction;
    }

    public void setdFunction(String dFunction) {
        this.dFunction = dFunction;
    }

    public String getdIsEvaluateSeparation() {
        return dIsEvaluateSeparation;
    }

    public void setdIsEvaluateSeparation(String dIsEvaluateSeparation) {
        this.dIsEvaluateSeparation = dIsEvaluateSeparation;
    }

    public Date getdBidTime() {
        return dBidTime;
    }

    public void setdBidTime(Date dBidTime) {
        this.dBidTime = dBidTime;
    }

    public String getdLocation() {
        return dLocation;
    }

    public void setdLocation(String dLocation) {
        this.dLocation = dLocation;
    }

    public String getdPerson() {
        return dPerson;
    }

    public void setdPerson(String dPerson) {
        this.dPerson = dPerson;
    }

    public String getdManageConsul() {
        return dManageConsul;
    }

    public void setdManageConsul(String dManageConsul) {
        this.dManageConsul = dManageConsul;
    }

    public String getdAgentUnit() {
        return dAgentUnit;
    }

    public void setdAgentUnit(String dAgentUnit) {
        this.dAgentUnit = dAgentUnit;
    }

    public String getdFileCode() {
        return dFileCode;
    }

    public void setdFileCode(String dFileCode) {
        this.dFileCode = dFileCode;
    }

    public String getdApplyTilte() {
        return dApplyTilte;
    }

    public void setdApplyTilte(String dApplyTilte) {
        this.dApplyTilte = dApplyTilte;
    }

    public String getdApplyCode() {
        return dApplyCode;
    }

    public void setdApplyCode(String dApplyCode) {
        this.dApplyCode = dApplyCode;
    }

    public Date getdApplyDate() {
        return dApplyDate;
    }

    public void setdApplyDate(Date dApplyDate) {
        this.dApplyDate = dApplyDate;
    }

    public String getdApplyFile() {
        return dApplyFile;
    }

    public void setdApplyFile(String dApplyFile) {
        this.dApplyFile = dApplyFile;
    }

    public String getdOtherFile() {
        return dOtherFile;
    }

    public void setdOtherFile(String dOtherFile) {
        this.dOtherFile = dOtherFile;
    }

    public String getdListFile() {
        return dListFile;
    }

    public void setdListFile(String dListFile) {
        this.dListFile = dListFile;
    }

    public String getdFeatures() {
        return dFeatures;
    }

    public void setdFeatures(String dFeatures) {
        this.dFeatures = dFeatures;
    }

    public String getdExecutiveStandard() {
        return dExecutiveStandard;
    }

    public void setdExecutiveStandard(String dExecutiveStandard) {
        this.dExecutiveStandard = dExecutiveStandard;
    }

    public String getdBidderManager() {
        return dBidderManager;
    }

    public void setdBidderManager(String dBidderManager) {
        this.dBidderManager = dBidderManager;
    }

    public String getdPerformanceGuaranteeType() {
        return dPerformanceGuaranteeType;
    }

    public void setdPerformanceGuaranteeType(String dPerformanceGuaranteeType) {
        this.dPerformanceGuaranteeType = dPerformanceGuaranteeType;
    }

    public BigDecimal getdPerformanceBond() {
        return dPerformanceBond;
    }

    public void setdPerformanceBond(BigDecimal dPerformanceBond) {
        this.dPerformanceBond = dPerformanceBond;
    }

    public String getdSourceFunds() {
        return dSourceFunds;
    }

    public void setdSourceFunds(String dSourceFunds) {
        this.dSourceFunds = dSourceFunds;
    }

    public BigDecimal getdRate() {
        return dRate;
    }

    public void setdRate(BigDecimal dRate) {
        this.dRate = dRate;
    }

    public String getdOtherValuation() {
        return dOtherValuation;
    }

    public void setdOtherValuation(String dOtherValuation) {
        this.dOtherValuation = dOtherValuation;
    }

    public String getdOrganizeForm() {
        return dOrganizeForm;
    }

    public void setdOrganizeForm(String dOrganizeForm) {
        this.dOrganizeForm = dOrganizeForm;
    }

    public String getdQualiReview() {
        return dQualiReview;
    }

    public void setdQualiReview(String dQualiReview) {
        this.dQualiReview = dQualiReview;
    }

    public Integer getdBiddingCycle() {
        return dBiddingCycle;
    }

    public void setdBiddingCycle(Integer dBiddingCycle) {
        this.dBiddingCycle = dBiddingCycle;
    }

    public String getdInvestorsQuali() {
        return dInvestorsQuali;
    }

    public void setdInvestorsQuali(String dInvestorsQuali) {
        this.dInvestorsQuali = dInvestorsQuali;
    }

    public String getdBiddingMethod() {
        return dBiddingMethod;
    }

    public void setdBiddingMethod(String dBiddingMethod) {
        this.dBiddingMethod = dBiddingMethod;
    }

    public Float getdPerfBondRatio() {
        return dPerfBondRatio;
    }

    public void setdPerfBondRatio(Float dPerfBondRatio) {
        this.dPerfBondRatio = dPerfBondRatio;
    }


    public String getdQualification() {
        return dQualification;
    }

    public void setdQualification(String dQualification) {
        this.dQualification = dQualification;
    }

    public String getdAchievement() {
        return dAchievement;
    }

    public void setdAchievement(String dAchievement) {
        this.dAchievement = dAchievement;
    }

    public String getdEntrustedResult() {
        return dEntrustedResult;
    }

    public void setdEntrustedResult(String dEntrustedResult) {
        this.dEntrustedResult = dEntrustedResult;
    }

    public String getdEntrustedCpy() {
        return dEntrustedCpy;
    }

    public void setdEntrustedCpy(String dEntrustedCpy) {
        this.dEntrustedCpy = dEntrustedCpy;
    }

    public BigDecimal getdEntrustedAmt() {
        return dEntrustedAmt;
    }

    public void setdEntrustedAmt(BigDecimal dEntrustedAmt) {
        this.dEntrustedAmt = dEntrustedAmt;
    }

    public String getdPm() {
        return dPm;
    }

    public void setdPm(String dPm) {
        this.dPm = dPm;
    }

    public Integer getdPeriod() {
        return dPeriod;
    }

    public void setdPeriod(Integer dPeriod) {
        this.dPeriod = dPeriod;
    }

    public Date getdBeginTime() {
        return dBeginTime;
    }

    public void setdBeginTime(Date dBeginTime) {
        this.dBeginTime = dBeginTime;
    }

    public String getdTradingPlace() {
        return dTradingPlace;
    }

    public void setdTradingPlace(String dTradingPlace) {
        this.dTradingPlace = dTradingPlace;
    }

    public String getdReceiveJointBidd() {
        return dReceiveJointBidd;
    }

    public void setdReceiveJointBidd(String dReceiveJointBidd) {
        this.dReceiveJointBidd = dReceiveJointBidd;
    }

    public String getdQuotationMethod() {
        return dQuotationMethod;
    }

    public void setdQuotationMethod(String dQuotationMethod) {
        this.dQuotationMethod = dQuotationMethod;
    }

    public String getdBidderManagerCon() {
        return dBidderManagerCon;
    }

    public void setdBidderManagerCon(String dBidderManagerCon) {
        this.dBidderManagerCon = dBidderManagerCon;
    }

    public String getdApprovalMatter() {
        return dApprovalMatter;
    }

    public void setdApprovalMatter(String dApprovalMatter) {
        this.dApprovalMatter = dApprovalMatter;
    }

    public String getdCid() {
        return dCid;
    }

    public void setdCid(String dCid) {
        this.dCid = dCid;
    }

    public String getdDid() {
        return dDid;
    }

    public void setdDid(String dDid) {
        this.dDid = dDid;
    }

    public String getdCnature() {
        return dCnature;
    }

    public void setdCnature(String dCnature) {
        this.dCnature = dCnature;
    }

    public String getdDnature() {
        return dDnature;
    }

    public void setdDnature(String dDnature) {
        this.dDnature = dDnature;
    }

    public String getdConType() {
        return dConType;
    }

    public void setdConType(String dConType) {
        this.dConType = dConType;
    }

    public String getdHead() {
        return dHead;
    }

    public void setdHead(String dHead) {
        this.dHead = dHead;
    }

    public String getdTitle() {
        return dTitle;
    }

    public void setdTitle(String dTitle) {
        this.dTitle = dTitle;
    }

    public String getdPmId() {
        return dPmId;
    }

    public void setdPmId(String dPmId) {
        this.dPmId = dPmId;
    }

    public Date getdEndTime() {
        return dEndTime;
    }

    public void setdEndTime(Date dEndTime) {
        this.dEndTime = dEndTime;
    }

    public Date getdBidDocEndTime() {
        return dBidDocEndTime;
    }

    public void setdBidDocEndTime(Date dBidDocEndTime) {
        this.dBidDocEndTime = dBidDocEndTime;
    }

    public String getdBidDocAddr() {
        return dBidDocAddr;
    }

    public void setdBidDocAddr(String dBidDocAddr) {
        this.dBidDocAddr = dBidDocAddr;
    }

    public String getdBidRoom() {
        return dBidRoom;
    }

    public void setdBidRoom(String dBidRoom) {
        this.dBidRoom = dBidRoom;
    }

    public String getdBidFinalFile() {
        return dBidFinalFile;
    }

    public void setdBidFinalFile(String dBidFinalFile) {
        this.dBidFinalFile = dBidFinalFile;
    }

    public String getdProcure() {
        return dProcure;
    }

    public void setdProcure(String dProcure) {
        this.dProcure = dProcure;
    }

    public String getdUrl() {
        return dUrl;
    }

    public void setdUrl(String dUrl) {
        this.dUrl = dUrl;
    }

    public Integer getdCalibrationPersonnelNum() {
        return dCalibrationPersonnelNum;
    }

    public void setdCalibrationPersonnelNum(Integer dCalibrationPersonnelNum) {
        this.dCalibrationPersonnelNum = dCalibrationPersonnelNum;
    }

    public String getdBidFinalFileIds() {
        return dBidFinalFileIds;
    }

    public void setdBidFinalFileIds(String dBidFinalFileIds) {
        this.dBidFinalFileIds = dBidFinalFileIds;
    }

    public String getdCname() {
        return dCname;
    }

    public void setdCname(String dCname) {
        this.dCname = dCname;
    }

    public String getdDname() {
        return dDname;
    }

    public void setdDname(String dDname) {
        this.dDname = dDname;
    }

    public String getdPmName() {
        return dPmName;
    }

    public void setdPmName(String dPmName) {
        this.dPmName = dPmName;
    }

    public String getdApplyFileIds() {
        return dApplyFileIds;
    }

    public void setdApplyFileIds(String dApplyFileIds) {
        this.dApplyFileIds = dApplyFileIds;
    }

    public String getdOtherFileIds() {
        return dOtherFileIds;
    }

    public void setdOtherFileIds(String dOtherFileIds) {
        this.dOtherFileIds = dOtherFileIds;
    }

    public String getdListFileIds() {
        return dListFileIds;
    }

    public void setdListFileIds(String dListFileIds) {
        this.dListFileIds = dListFileIds;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public Integer getSecFlowState() {
        return secFlowState;
    }

    public void setSecFlowState(Integer secFlowState) {
        this.secFlowState = secFlowState;
    }

    public String getdBidMonYear() {
        return dBidMonYear;
    }

    public void setdBidMonYear(String dBidMonYear) {
        this.dBidMonYear = dBidMonYear;
    }

    public List<String> getdAmounts() {
        return dAmounts;
    }

    public void setdAmounts(List<String> dAmounts) {
        this.dAmounts = dAmounts;
    }

    public List<String> getdUpperLimits() {
        return dUpperLimits;
    }

    public void setdUpperLimits(List<String> dUpperLimits) {
        this.dUpperLimits = dUpperLimits;
    }

    public List<String> getdCreateTimes() {
        return dCreateTimes;
    }

    public void setdCreateTimes(List<String> dCreateTimes) {
        this.dCreateTimes = dCreateTimes;
    }

    public List<String> getSecMoneys() {
        return secMoneys;
    }

    public void setSecMoneys(List<String> secMoneys) {
        this.secMoneys = secMoneys;
    }

    public List<SecInviteRosterDto> getSecInviteRosters() {
        return secInviteRosters;
    }

    public void setSecInviteRosters(List<SecInviteRosterDto> secInviteRosters) {
        this.secInviteRosters = secInviteRosters;
    }

    public String getProjectCategory() {
        return projectCategory;
    }

    public void setProjectCategory(String projectCategory) {
        this.projectCategory = projectCategory;
    }

    public String getProjectInitiatingUnit() {
        return projectInitiatingUnit;
    }

    public void setProjectInitiatingUnit(String projectInitiatingUnit) {
        this.projectInitiatingUnit = projectInitiatingUnit;
    }
}
