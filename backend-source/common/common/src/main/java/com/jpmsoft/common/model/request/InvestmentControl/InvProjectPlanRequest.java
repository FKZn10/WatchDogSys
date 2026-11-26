package com.jpmsoft.common.model.request.InvestmentControl;

import com.jpmsoft.common.model.BaseModeRequest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 政府投资项目计划
 */

public class InvProjectPlanRequest extends BaseModeRequest {
    private String ippId;
    private String ifrId;
    private String pId;
    private String ippConstructionScale;
    private String ippPType;
    private BigDecimal ippAccumulatedFunds;
    private BigDecimal ippFundsArranged;
    private BigDecimal ippAnnualAccumFund;
    private BigDecimal ippReleaseFunds;
    private BigDecimal ippAnnualReleaseFunds;
    private String ippDocNumber;
    private String ippYear;
    private String ippFile;
    private String ippCreateUid;
    private String ippCreateUname;
    private Date ippCreateTime;
    private String ippInvestor;
    private String ippBuildContent;
    private String ippBuildNature;
    private Integer ippBuildPeriod;
    private BigDecimal ippPlanInv;
    private String ippProgressLastYear;
    private BigDecimal ippMunicipalFunds;
    private BigDecimal ippCountyFunds;
    private BigDecimal ippEnterpriseFunds;
    private BigDecimal ippBankLoans;
    private BigDecimal ippOtherFunds;
    private String ippNowYearProgress;
    private String ippLandArea;
    private String ippNewLandArea;
    private String ippConstructionSite;
    private String ippMajorProjects;
    private String ippSpecialPlan;
    private String ippLiableCompany;
    private String ippLiableMan;
    private String ippRemarks;

    private String pName;
    private String pCode;
    private String pBudgetUnit;
    private String ippFileIds;
    private String ifrReplyCode;
    //判断查询数据是直接创建的还是关联主表创建的
    private Boolean haveIfrId;
    private String ippBudgetItemName;
    private String ippNdrcCode;
    private String ippPlanDocNo;
    private String ippSourceFunds;
    private String ippReleaseType;
    private BigDecimal ippRemainingAmount;

    private List<String> ippPlanInvs;
    private List<String> ippAccumulatedFundses;
    private List<String> ippAnnualReleaseFundses;
    private List<String> ippBuildPeriods;

    public String getIppId() {
        return ippId;
    }

    public void setIppId(String ippId) {
        this.ippId = ippId;
    }

    public String getIfrId() {
        return ifrId;
    }

    public void setIfrId(String ifrId) {
        this.ifrId = ifrId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getIppConstructionScale() {
        return ippConstructionScale;
    }

    public void setIppConstructionScale(String ippConstructionScale) {
        this.ippConstructionScale = ippConstructionScale;
    }

    public String getIppPType() {
        return ippPType;
    }

    public void setIppPType(String ippPType) {
        this.ippPType = ippPType;
    }

    public BigDecimal getIppAccumulatedFunds() {
        return ippAccumulatedFunds;
    }

    public void setIppAccumulatedFunds(BigDecimal ippAccumulatedFunds) {
        this.ippAccumulatedFunds = ippAccumulatedFunds;
    }

    public BigDecimal getIppFundsArranged() {
        return ippFundsArranged;
    }

    public void setIppFundsArranged(BigDecimal ippFundsArranged) {
        this.ippFundsArranged = ippFundsArranged;
    }

    public BigDecimal getIppAnnualAccumFund() {
        return ippAnnualAccumFund;
    }

    public void setIppAnnualAccumFund(BigDecimal ippAnnualAccumFund) {
        this.ippAnnualAccumFund = ippAnnualAccumFund;
    }

    public BigDecimal getIppReleaseFunds() {
        return ippReleaseFunds;
    }

    public void setIppReleaseFunds(BigDecimal ippReleaseFunds) {
        this.ippReleaseFunds = ippReleaseFunds;
    }

    public BigDecimal getIppAnnualReleaseFunds() {
        return ippAnnualReleaseFunds;
    }

    public void setIppAnnualReleaseFunds(BigDecimal ippAnnualReleaseFunds) {
        this.ippAnnualReleaseFunds = ippAnnualReleaseFunds;
    }

    public String getIppDocNumber() {
        return ippDocNumber;
    }

    public void setIppDocNumber(String ippDocNumber) {
        this.ippDocNumber = ippDocNumber;
    }

    public String getIppYear() {
        return ippYear;
    }

    public void setIppYear(String ippYear) {
        this.ippYear = ippYear;
    }

    public String getIppFile() {
        return ippFile;
    }

    public void setIppFile(String ippFile) {
        this.ippFile = ippFile;
    }

    public String getIppCreateUid() {
        return ippCreateUid;
    }

    public void setIppCreateUid(String ippCreateUid) {
        this.ippCreateUid = ippCreateUid;
    }

    public String getIppCreateUname() {
        return ippCreateUname;
    }

    public void setIppCreateUname(String ippCreateUname) {
        this.ippCreateUname = ippCreateUname;
    }

    public Date getIppCreateTime() {
        return ippCreateTime;
    }

    public void setIppCreateTime(Date ippCreateTime) {
        this.ippCreateTime = ippCreateTime;
    }

    public String getpCode() {
        return pCode;
    }

    public void setpCode(String pCode) {
        this.pCode = pCode;
    }

    public String getpBudgetUnit() {
        return pBudgetUnit;
    }

    public void setpBudgetUnit(String pBudgetUnit) {
        this.pBudgetUnit = pBudgetUnit;
    }

    public String getIppInvestor() {
        return ippInvestor;
    }

    public void setIppInvestor(String ippInvestor) {
        this.ippInvestor = ippInvestor;
    }

    public String getIppBuildContent() {
        return ippBuildContent;
    }

    public void setIppBuildContent(String ippBuildContent) {
        this.ippBuildContent = ippBuildContent;
    }

    public String getIppBuildNature() {
        return ippBuildNature;
    }

    public void setIppBuildNature(String ippBuildNature) {
        this.ippBuildNature = ippBuildNature;
    }

    public Integer getIppBuildPeriod() {
        return ippBuildPeriod;
    }

    public void setIppBuildPeriod(Integer ippBuildPeriod) {
        this.ippBuildPeriod = ippBuildPeriod;
    }

    public BigDecimal getIppPlanInv() {
        return ippPlanInv;
    }

    public void setIppPlanInv(BigDecimal ippPlanInv) {
        this.ippPlanInv = ippPlanInv;
    }

    public String getIppProgressLastYear() {
        return ippProgressLastYear;
    }

    public void setIppProgressLastYear(String ippProgressLastYear) {
        this.ippProgressLastYear = ippProgressLastYear;
    }

    public BigDecimal getIppMunicipalFunds() {
        return ippMunicipalFunds;
    }

    public void setIppMunicipalFunds(BigDecimal ippMunicipalFunds) {
        this.ippMunicipalFunds = ippMunicipalFunds;
    }

    public BigDecimal getIppCountyFunds() {
        return ippCountyFunds;
    }

    public void setIppCountyFunds(BigDecimal ippCountyFunds) {
        this.ippCountyFunds = ippCountyFunds;
    }

    public BigDecimal getIppEnterpriseFunds() {
        return ippEnterpriseFunds;
    }

    public void setIppEnterpriseFunds(BigDecimal ippEnterpriseFunds) {
        this.ippEnterpriseFunds = ippEnterpriseFunds;
    }

    public BigDecimal getIppBankLoans() {
        return ippBankLoans;
    }

    public void setIppBankLoans(BigDecimal ippBankLoans) {
        this.ippBankLoans = ippBankLoans;
    }

    public BigDecimal getIppOtherFunds() {
        return ippOtherFunds;
    }

    public void setIppOtherFunds(BigDecimal ippOtherFunds) {
        this.ippOtherFunds = ippOtherFunds;
    }

    public String getIppNowYearProgress() {
        return ippNowYearProgress;
    }

    public void setIppNowYearProgress(String ippNowYearProgress) {
        this.ippNowYearProgress = ippNowYearProgress;
    }

    public String getIppLandArea() {
        return ippLandArea;
    }

    public void setIppLandArea(String ippLandArea) {
        this.ippLandArea = ippLandArea;
    }

    public String getIppNewLandArea() {
        return ippNewLandArea;
    }

    public void setIppNewLandArea(String ippNewLandArea) {
        this.ippNewLandArea = ippNewLandArea;
    }

    public String getIppConstructionSite() {
        return ippConstructionSite;
    }

    public void setIppConstructionSite(String ippConstructionSite) {
        this.ippConstructionSite = ippConstructionSite;
    }

    public String getIppMajorProjects() {
        return ippMajorProjects;
    }

    public void setIppMajorProjects(String ippMajorProjects) {
        this.ippMajorProjects = ippMajorProjects;
    }

    public String getIppSpecialPlan() {
        return ippSpecialPlan;
    }

    public void setIppSpecialPlan(String ippSpecialPlan) {
        this.ippSpecialPlan = ippSpecialPlan;
    }

    public String getIppLiableCompany() {
        return ippLiableCompany;
    }

    public void setIppLiableCompany(String ippLiableCompany) {
        this.ippLiableCompany = ippLiableCompany;
    }

    public String getIppLiableMan() {
        return ippLiableMan;
    }

    public void setIppLiableMan(String ippLiableMan) {
        this.ippLiableMan = ippLiableMan;
    }

    public String getIppRemarks() {
        return ippRemarks;
    }

    public void setIppRemarks(String ippRemarks) {
        this.ippRemarks = ippRemarks;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getIppFileIds() {
        return ippFileIds;
    }

    public void setIppFileIds(String ippFileIds) {
        this.ippFileIds = ippFileIds;
    }

    public String getIfrReplyCode() {
        return ifrReplyCode;
    }

    public void setIfrReplyCode(String ifrReplyCode) {
        this.ifrReplyCode = ifrReplyCode;
    }

    public Boolean getHaveIfrId() {
        return haveIfrId;
    }

    public void setHaveIfrId(Boolean haveIfrId) {
        this.haveIfrId = haveIfrId;
    }

    public String getIppBudgetItemName() {
        return ippBudgetItemName;
    }

    public void setIppBudgetItemName(String ippBudgetItemName) {
        this.ippBudgetItemName = ippBudgetItemName;
    }

    public String getIppNdrcCode() {
        return ippNdrcCode;
    }

    public void setIppNdrcCode(String ippNdrcCode) {
        this.ippNdrcCode = ippNdrcCode;
    }

    public String getIppPlanDocNo() {
        return ippPlanDocNo;
    }

    public void setIppPlanDocNo(String ippPlanDocNo) {
        this.ippPlanDocNo = ippPlanDocNo;
    }

    public String getIppSourceFunds() {
        return ippSourceFunds;
    }

    public void setIppSourceFunds(String ippSourceFunds) {
        this.ippSourceFunds = ippSourceFunds;
    }

    public String getIppReleaseType() {
        return ippReleaseType;
    }

    public void setIppReleaseType(String ippReleaseType) {
        this.ippReleaseType = ippReleaseType;
    }

    public BigDecimal getIppRemainingAmount() {
        return ippRemainingAmount;
    }

    public void setIppRemainingAmount(BigDecimal ippRemainingAmount) {
        this.ippRemainingAmount = ippRemainingAmount;
    }

    public List<String> getIppPlanInvs() {
        return ippPlanInvs;
    }

    public void setIppPlanInvs(List<String> ippPlanInvs) {
        this.ippPlanInvs = ippPlanInvs;
    }

    public List<String> getIppAccumulatedFundses() {
        return ippAccumulatedFundses;
    }

    public void setIppAccumulatedFundses(List<String> ippAccumulatedFundses) {
        this.ippAccumulatedFundses = ippAccumulatedFundses;
    }

    public List<String> getIppAnnualReleaseFundses() {
        return ippAnnualReleaseFundses;
    }

    public void setIppAnnualReleaseFundses(List<String> ippAnnualReleaseFundses) {
        this.ippAnnualReleaseFundses = ippAnnualReleaseFundses;
    }

    public List<String> getIppBuildPeriods() {
        return ippBuildPeriods;
    }

    public void setIppBuildPeriods(List<String> ippBuildPeriods) {
        this.ippBuildPeriods = ippBuildPeriods;
    }
}
