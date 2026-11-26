package com.jpmsoft.common.model.requestAndResponse.SecInfo;

import com.jpmsoft.common.model.BaseFileRequest;
import com.jpmsoft.common.model.response.SecInfo.SecProjectResponse;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 招标方案
 */
public class SecBiddingSchemeModel extends BaseFileRequest {

    private String bsId;
    private String secId;
    private BigDecimal bsProjectInvestment;
    private BigDecimal bsAnnualInvestment;
    private String bsApprovalType;
    private BigDecimal bsApprovedAmount;
    private Integer bsWhetherStandard;
    private String bsApprovalComments;
    private String bsCreateUid;
    private Date bsCreateTime;
    private String bsCreateUname;
    private Integer bsState;
    private Date bsStateTime;
    private String bsApprovalDoc;
    private String bsBasisDoc;
    private String bsBasisDocFile;
    private String bsContractType;
    private String bsContractNecessary;
    private String bsContractContent;
    private String bsDurationCondition;
    private String bsContractAmountType;
    private String bsContractAmount;
    private String bsPrerequisites;
    private String bsRegistration;
    private String bsContractingUnit;
    private String bsQualificationContractor;
    private String bsDetermineReason;
    private String bsOther;
    private BigDecimal bsHighestPrice;
    private String bsCalculationMethod;
    private String bsAbstract;
    private String bsProjectOverview;
    private String bsApprovalMatter;
    private String bsApprovalFlow;
    private String bsContractingType;
    private String bsTenderingUnit;
    private String bsAgencyId;
    private String bsAgentCon;
    private BigDecimal bsAgentConAmt;
    private String bsAgentProcure;
    private BigDecimal bsAgentProcureAmt;
    private String bsInvestType;
    private String bsBidPlatform;
    private BigDecimal bsControlPrice;
    private String bsPmAsk;
    private String bsConsortiumBid;
    private String bsEvaluation;
    private String bsCalibration;
    private String bsLeaderQualification;
    private String bsInstructions;
    private BigDecimal bsMoney;

    private String bsInstructionsIds;
    private String bsAgencyName;
    private String bsBasisDocFileIds;
    private String secContractingType;
    private String secName;
    private String secType;
    private String secConType;
    private BigDecimal secMoney;
    private List<SecProjectResponse> secProjects;
    //项目总投资（范围筛选）、
    private List<String> bsProjectInvestments;
    //年度计划资金下达额度（范围筛选）、
    private List<String> bsAnnualInvestments;
    // 投资批复金额（范围筛选）、
    private List<String> bsApprovedAmounts;
    // 创建时间（范围筛选）、
    private List<String> bsCreateTimes;
    private String bsApprovalDocIds;
    private String secContractingDepartmentName;
    private List<SecBiddingSchemeUnitDto> secBiddingSchemeUnits;

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getSecType() {
        return secType;
    }

    public void setSecType(String secType) {
        this.secType = secType;
    }

    public List<SecProjectResponse> getSecProjects() {
        return secProjects;
    }

    public void setSecProjects(List<SecProjectResponse> secProjects) {
        this.secProjects = secProjects;
    }

    public String getBsId() {
        return bsId;
    }

    public void setBsId(String bsId) {
        this.bsId = bsId;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public BigDecimal getBsProjectInvestment() {
        return bsProjectInvestment;
    }

    public void setBsProjectInvestment(BigDecimal bsProjectInvestment) {
        this.bsProjectInvestment = bsProjectInvestment;
    }

    public BigDecimal getBsAnnualInvestment() {
        return bsAnnualInvestment;
    }

    public void setBsAnnualInvestment(BigDecimal bsAnnualInvestment) {
        this.bsAnnualInvestment = bsAnnualInvestment;
    }

    public String getBsApprovalType() {
        return bsApprovalType;
    }

    public void setBsApprovalType(String bsApprovalType) {
        this.bsApprovalType = bsApprovalType;
    }

    public BigDecimal getBsApprovedAmount() {
        return bsApprovedAmount;
    }

    public void setBsApprovedAmount(BigDecimal bsApprovedAmount) {
        this.bsApprovedAmount = bsApprovedAmount;
    }

    public Integer getBsWhetherStandard() {
        return bsWhetherStandard;
    }

    public void setBsWhetherStandard(Integer bsWhetherStandard) {
        this.bsWhetherStandard = bsWhetherStandard;
    }

    public String getBsApprovalComments() {
        return bsApprovalComments;
    }

    public void setBsApprovalComments(String bsApprovalComments) {
        this.bsApprovalComments = bsApprovalComments;
    }

    public String getBsCreateUid() {
        return bsCreateUid;
    }

    public void setBsCreateUid(String bsCreateUid) {
        this.bsCreateUid = bsCreateUid;
    }

    public Date getBsCreateTime() {
        return bsCreateTime;
    }

    public void setBsCreateTime(Date bsCreateTime) {
        this.bsCreateTime = bsCreateTime;
    }

    public String getBsCreateUname() {
        return bsCreateUname;
    }

    public void setBsCreateUname(String bsCreateUname) {
        this.bsCreateUname = bsCreateUname;
    }

    public Integer getBsState() {
        return bsState;
    }

    public void setBsState(Integer bsState) {
        this.bsState = bsState;
    }

    public Date getBsStateTime() {
        return bsStateTime;
    }

    public void setBsStateTime(Date bsStateTime) {
        this.bsStateTime = bsStateTime;
    }

    public List<String> getBsProjectInvestments() {
        return bsProjectInvestments;
    }

    public void setBsProjectInvestments(List<String> bsProjectInvestments) {
        this.bsProjectInvestments = bsProjectInvestments;
    }

    public List<String> getBsAnnualInvestments() {
        return bsAnnualInvestments;
    }

    public void setBsAnnualInvestments(List<String> bsAnnualInvestments) {
        this.bsAnnualInvestments = bsAnnualInvestments;
    }

    public List<String> getBsApprovedAmounts() {
        return bsApprovedAmounts;
    }

    public void setBsApprovedAmounts(List<String> bsApprovedAmounts) {
        this.bsApprovedAmounts = bsApprovedAmounts;
    }

    public List<String> getBsCreateTimes() {
        return bsCreateTimes;
    }

    public void setBsCreateTimes(List<String> bsCreateTimes) {
        this.bsCreateTimes = bsCreateTimes;
    }

    public String getBsApprovalDoc() {
        return bsApprovalDoc;
    }

    public void setBsApprovalDoc(String bsApprovalDoc) {
        this.bsApprovalDoc = bsApprovalDoc;
    }

    public String getBsBasisDoc() {
        return bsBasisDoc;
    }

    public void setBsBasisDoc(String bsBasisDoc) {
        this.bsBasisDoc = bsBasisDoc;
    }

    public String getBsBasisDocFile() {
        return bsBasisDocFile;
    }

    public void setBsBasisDocFile(String bsBasisDocFile) {
        this.bsBasisDocFile = bsBasisDocFile;
    }

    public String getBsContractType() {
        return bsContractType;
    }

    public void setBsContractType(String bsContractType) {
        this.bsContractType = bsContractType;
    }

    public String getBsContractNecessary() {
        return bsContractNecessary;
    }

    public void setBsContractNecessary(String bsContractNecessary) {
        this.bsContractNecessary = bsContractNecessary;
    }

    public String getBsContractContent() {
        return bsContractContent;
    }

    public void setBsContractContent(String bsContractContent) {
        this.bsContractContent = bsContractContent;
    }

    public String getBsDurationCondition() {
        return bsDurationCondition;
    }

    public void setBsDurationCondition(String bsDurationCondition) {
        this.bsDurationCondition = bsDurationCondition;
    }

    public String getBsContractAmountType() {
        return bsContractAmountType;
    }

    public void setBsContractAmountType(String bsContractAmountType) {
        this.bsContractAmountType = bsContractAmountType;
    }

    public String getBsContractAmount() {
        return bsContractAmount;
    }

    public void setBsContractAmount(String bsContractAmount) {
        this.bsContractAmount = bsContractAmount;
    }

    public String getBsPrerequisites() {
        return bsPrerequisites;
    }

    public void setBsPrerequisites(String bsPrerequisites) {
        this.bsPrerequisites = bsPrerequisites;
    }

    public String getBsRegistration() {
        return bsRegistration;
    }

    public void setBsRegistration(String bsRegistration) {
        this.bsRegistration = bsRegistration;
    }

    public String getBsContractingUnit() {
        return bsContractingUnit;
    }

    public void setBsContractingUnit(String bsContractingUnit) {
        this.bsContractingUnit = bsContractingUnit;
    }

    public String getBsQualificationContractor() {
        return bsQualificationContractor;
    }

    public void setBsQualificationContractor(String bsQualificationContractor) {
        this.bsQualificationContractor = bsQualificationContractor;
    }

    public String getBsDetermineReason() {
        return bsDetermineReason;
    }

    public void setBsDetermineReason(String bsDetermineReason) {
        this.bsDetermineReason = bsDetermineReason;
    }

    public String getBsOther() {
        return bsOther;
    }

    public void setBsOther(String bsOther) {
        this.bsOther = bsOther;
    }

    public BigDecimal getBsHighestPrice() {
        return bsHighestPrice;
    }

    public void setBsHighestPrice(BigDecimal bsHighestPrice) {
        this.bsHighestPrice = bsHighestPrice;
    }

    public String getBsCalculationMethod() {
        return bsCalculationMethod;
    }

    public void setBsCalculationMethod(String bsCalculationMethod) {
        this.bsCalculationMethod = bsCalculationMethod;
    }

    public String getBsAbstract() {
        return bsAbstract;
    }

    public void setBsAbstract(String bsAbstract) {
        this.bsAbstract = bsAbstract;
    }

    public String getBsProjectOverview() {
        return bsProjectOverview;
    }

    public void setBsProjectOverview(String bsProjectOverview) {
        this.bsProjectOverview = bsProjectOverview;
    }

    public String getBsApprovalMatter() {
        return bsApprovalMatter;
    }

    public void setBsApprovalMatter(String bsApprovalMatter) {
        this.bsApprovalMatter = bsApprovalMatter;
    }

    public String getBsApprovalFlow() {
        return bsApprovalFlow;
    }

    public void setBsApprovalFlow(String bsApprovalFlow) {
        this.bsApprovalFlow = bsApprovalFlow;
    }

    public String getBsContractingType() {
        return bsContractingType;
    }

    public void setBsContractingType(String bsContractingType) {
        this.bsContractingType = bsContractingType;
    }

    public String getBsTenderingUnit() {
        return bsTenderingUnit;
    }

    public void setBsTenderingUnit(String bsTenderingUnit) {
        this.bsTenderingUnit = bsTenderingUnit;
    }

    public String getBsAgencyId() {
        return bsAgencyId;
    }

    public void setBsAgencyId(String bsAgencyId) {
        this.bsAgencyId = bsAgencyId;
    }

    public String getBsAgentCon() {
        return bsAgentCon;
    }

    public void setBsAgentCon(String bsAgentCon) {
        this.bsAgentCon = bsAgentCon;
    }

    public BigDecimal getBsAgentConAmt() {
        return bsAgentConAmt;
    }

    public void setBsAgentConAmt(BigDecimal bsAgentConAmt) {
        this.bsAgentConAmt = bsAgentConAmt;
    }

    public String getBsAgentProcure() {
        return bsAgentProcure;
    }

    public void setBsAgentProcure(String bsAgentProcure) {
        this.bsAgentProcure = bsAgentProcure;
    }

    public BigDecimal getBsAgentProcureAmt() {
        return bsAgentProcureAmt;
    }

    public void setBsAgentProcureAmt(BigDecimal bsAgentProcureAmt) {
        this.bsAgentProcureAmt = bsAgentProcureAmt;
    }

    public String getBsInvestType() {
        return bsInvestType;
    }

    public void setBsInvestType(String bsInvestType) {
        this.bsInvestType = bsInvestType;
    }

    public String getBsBidPlatform() {
        return bsBidPlatform;
    }

    public void setBsBidPlatform(String bsBidPlatform) {
        this.bsBidPlatform = bsBidPlatform;
    }

    public BigDecimal getBsControlPrice() {
        return bsControlPrice;
    }

    public void setBsControlPrice(BigDecimal bsControlPrice) {
        this.bsControlPrice = bsControlPrice;
    }

    public String getBsPmAsk() {
        return bsPmAsk;
    }

    public void setBsPmAsk(String bsPmAsk) {
        this.bsPmAsk = bsPmAsk;
    }

    public String getBsConsortiumBid() {
        return bsConsortiumBid;
    }

    public void setBsConsortiumBid(String bsConsortiumBid) {
        this.bsConsortiumBid = bsConsortiumBid;
    }

    public String getBsEvaluation() {
        return bsEvaluation;
    }

    public void setBsEvaluation(String bsEvaluation) {
        this.bsEvaluation = bsEvaluation;
    }

    public String getBsCalibration() {
        return bsCalibration;
    }

    public void setBsCalibration(String bsCalibration) {
        this.bsCalibration = bsCalibration;
    }

    public String getBsLeaderQualification() {
        return bsLeaderQualification;
    }

    public void setBsLeaderQualification(String bsLeaderQualification) {
        this.bsLeaderQualification = bsLeaderQualification;
    }

    public String getBsInstructions() {
        return bsInstructions;
    }

    public void setBsInstructions(String bsInstructions) {
        this.bsInstructions = bsInstructions;
    }

    public String getBsInstructionsIds() {
        return bsInstructionsIds;
    }

    public BigDecimal getBsMoney() {
        return bsMoney;
    }

    public void setBsMoney(BigDecimal bsMoney) {
        this.bsMoney = bsMoney;
    }

    public void setBsInstructionsIds(String bsInstructionsIds) {
        this.bsInstructionsIds = bsInstructionsIds;
    }

    public String getBsAgencyName() {
        return bsAgencyName;
    }

    public void setBsAgencyName(String bsAgencyName) {
        this.bsAgencyName = bsAgencyName;
    }

    public String getBsBasisDocFileIds() {
        return bsBasisDocFileIds;
    }

    public void setBsBasisDocFileIds(String bsBasisDocFileIds) {
        this.bsBasisDocFileIds = bsBasisDocFileIds;
    }

    public String getSecContractingType() {
        return secContractingType;
    }

    public void setSecContractingType(String secContractingType) {
        this.secContractingType = secContractingType;
    }

    public String getBsApprovalDocIds() {
        return bsApprovalDocIds;
    }

    public void setBsApprovalDocIds(String bsApprovalDocIds) {
        this.bsApprovalDocIds = bsApprovalDocIds;
    }

    public List<SecBiddingSchemeUnitDto> getSecBiddingSchemeUnits() {
        return secBiddingSchemeUnits;
    }

    public void setSecBiddingSchemeUnits(List<SecBiddingSchemeUnitDto> secBiddingSchemeUnits) {
        this.secBiddingSchemeUnits = secBiddingSchemeUnits;
    }

    public String getSecContractingDepartmentName() {
        return secContractingDepartmentName;
    }

    public void setSecContractingDepartmentName(String secContractingDepartmentName) {
        this.secContractingDepartmentName = secContractingDepartmentName;
    }

    public String getSecConType() {
        return secConType;
    }

    public void setSecConType(String secConType) {
        this.secConType = secConType;
    }

    public BigDecimal getSecMoney() {
        return secMoney;
    }

    public void setSecMoney(BigDecimal secMoney) {
        this.secMoney = secMoney;
    }
}
