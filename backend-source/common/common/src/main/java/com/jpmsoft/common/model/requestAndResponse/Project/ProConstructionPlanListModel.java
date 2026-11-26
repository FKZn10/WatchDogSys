package com.jpmsoft.common.model.requestAndResponse.Project;

import com.jpmsoft.common.base.FileFields;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;
import com.jpmsoft.common.model.request.project.ProConstructionSourcesFundsDto;
import com.jpmsoft.common.model.request.project.ProFundsUsageDetailsDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 项目立项资金来源
 */

public class ProConstructionPlanListModel extends BaseFileRequest {

    @ProcessFields(type = ProcessFieldEnum.id)
    private String cplId;
    private String pcpId;
    private String cplPType;
    private String pCode;
    private String pName;
    private BigDecimal cplAccumulatedFunds;
    private BigDecimal cplAnnualAccumFund;
    private String cplInvestor;
    private String cplBuildContent;
    private String cplBuildNature;
    private String cplBuildPeriod;
    private BigDecimal cplPlannedInvestment;
    private String cplProgressLastYear;
    private BigDecimal cplMunicipalFunds;
    private BigDecimal cplCountyFunds;
    private BigDecimal cplEnterpriseFunds;
    private BigDecimal cplBankLoans;
    private BigDecimal cplOtherFunds;
    private String cplNowYearProgress;
    private Float cplLandArea;
    private Float cplNewLandArea;
    private String cplConstructionSite;
    private String cplMajorProjects;
    private String cplSpecialPlan;
    private String cplLiableCompany;
    private String cplLiableMan;
    private String cplRemarks;
    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date cplCreateTime;
    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String cplCreateUid;
    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String cplCreateUname;
    @FileFields(index = 1)
    private String cplFile;
    private String cplYear;
    public String cplAgency;
    public String cplCategory;
    public Date cplPlanStart;
    public Date cplPlanEnd;
    public Float cplLength;
    public Float cplWidth;
    public String cplDeclareDep;
    public String cplUseDep;
    public Boolean cplApproval;
    private String cplSource;
    private Boolean cplAppend;
    @ProcessFields(type = ProcessFieldEnum.state)
    private Integer state;
    @ProcessFields(type = ProcessFieldEnum.stateTime)
    private Date stateTime;

    private String cplLiableCompanyName;
    public String cplAgencyName;
    public String cplDeclareDepName;
    public String cplUseDepName;
    private String pcpFileName;
    private Integer pcpYear;
    private String pcpCreateUname;
    private Integer pcpState;
    @FileFields(index = 1, type = "cplFileIds")
    private String cplFileIds;
    /**
     * 是否追加的数据
     * true: 项目建设计划追加的数据
     * false: 项目建设计划数据
     */
    private Boolean queryAppend;
    private List<ProConstructionSourcesFundsDto> proConstructionSourcesFunds;
    private List<ProFundsUsageDetailsDto> proFundsUsageDetails;

    public String getCplId() {
        return cplId;
    }

    public void setCplId(String cplId) {
        this.cplId = cplId;
    }

    public String getPcpId() {
        return pcpId;
    }

    public void setPcpId(String pcpId) {
        this.pcpId = pcpId;
    }

    public String getCplPType() {
        return cplPType;
    }

    public void setCplPType(String cplPType) {
        this.cplPType = cplPType;
    }

    public String getpCode() {
        return pCode;
    }

    public void setpCode(String pCode) {
        this.pCode = pCode;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public BigDecimal getCplAccumulatedFunds() {
        return cplAccumulatedFunds;
    }

    public void setCplAccumulatedFunds(BigDecimal cplAccumulatedFunds) {
        this.cplAccumulatedFunds = cplAccumulatedFunds;
    }

    public BigDecimal getCplAnnualAccumFund() {
        return cplAnnualAccumFund;
    }

    public void setCplAnnualAccumFund(BigDecimal cplAnnualAccumFund) {
        this.cplAnnualAccumFund = cplAnnualAccumFund;
    }

    public String getCplInvestor() {
        return cplInvestor;
    }

    public void setCplInvestor(String cplInvestor) {
        this.cplInvestor = cplInvestor;
    }

    public String getCplBuildContent() {
        return cplBuildContent;
    }

    public void setCplBuildContent(String cplBuildContent) {
        this.cplBuildContent = cplBuildContent;
    }

    public String getCplBuildNature() {
        return cplBuildNature;
    }

    public void setCplBuildNature(String cplBuildNature) {
        this.cplBuildNature = cplBuildNature;
    }

    public String getCplBuildPeriod() {
        return cplBuildPeriod;
    }

    public void setCplBuildPeriod(String cplBuildPeriod) {
        this.cplBuildPeriod = cplBuildPeriod;
    }

    public BigDecimal getCplPlannedInvestment() {
        return cplPlannedInvestment;
    }

    public void setCplPlannedInvestment(BigDecimal cplPlannedInvestment) {
        this.cplPlannedInvestment = cplPlannedInvestment;
    }

    public String getCplProgressLastYear() {
        return cplProgressLastYear;
    }

    public void setCplProgressLastYear(String cplProgressLastYear) {
        this.cplProgressLastYear = cplProgressLastYear;
    }

    public BigDecimal getCplMunicipalFunds() {
        return cplMunicipalFunds;
    }

    public void setCplMunicipalFunds(BigDecimal cplMunicipalFunds) {
        this.cplMunicipalFunds = cplMunicipalFunds;
    }

    public BigDecimal getCplCountyFunds() {
        return cplCountyFunds;
    }

    public void setCplCountyFunds(BigDecimal cplCountyFunds) {
        this.cplCountyFunds = cplCountyFunds;
    }

    public BigDecimal getCplEnterpriseFunds() {
        return cplEnterpriseFunds;
    }

    public void setCplEnterpriseFunds(BigDecimal cplEnterpriseFunds) {
        this.cplEnterpriseFunds = cplEnterpriseFunds;
    }

    public BigDecimal getCplBankLoans() {
        return cplBankLoans;
    }

    public void setCplBankLoans(BigDecimal cplBankLoans) {
        this.cplBankLoans = cplBankLoans;
    }

    public BigDecimal getCplOtherFunds() {
        return cplOtherFunds;
    }

    public void setCplOtherFunds(BigDecimal cplOtherFunds) {
        this.cplOtherFunds = cplOtherFunds;
    }

    public String getCplNowYearProgress() {
        return cplNowYearProgress;
    }

    public void setCplNowYearProgress(String cplNowYearProgress) {
        this.cplNowYearProgress = cplNowYearProgress;
    }

    public Float getCplLandArea() {
        return cplLandArea;
    }

    public void setCplLandArea(Float cplLandArea) {
        this.cplLandArea = cplLandArea;
    }

    public Float getCplNewLandArea() {
        return cplNewLandArea;
    }

    public void setCplNewLandArea(Float cplNewLandArea) {
        this.cplNewLandArea = cplNewLandArea;
    }

    public String getCplConstructionSite() {
        return cplConstructionSite;
    }

    public void setCplConstructionSite(String cplConstructionSite) {
        this.cplConstructionSite = cplConstructionSite;
    }

    public String getCplMajorProjects() {
        return cplMajorProjects;
    }

    public void setCplMajorProjects(String cplMajorProjects) {
        this.cplMajorProjects = cplMajorProjects;
    }

    public String getCplSpecialPlan() {
        return cplSpecialPlan;
    }

    public void setCplSpecialPlan(String cplSpecialPlan) {
        this.cplSpecialPlan = cplSpecialPlan;
    }

    public String getCplLiableCompany() {
        return cplLiableCompany;
    }

    public void setCplLiableCompany(String cplLiableCompany) {
        this.cplLiableCompany = cplLiableCompany;
    }

    public String getCplLiableMan() {
        return cplLiableMan;
    }

    public void setCplLiableMan(String cplLiableMan) {
        this.cplLiableMan = cplLiableMan;
    }

    public String getCplRemarks() {
        return cplRemarks;
    }

    public void setCplRemarks(String cplRemarks) {
        this.cplRemarks = cplRemarks;
    }

    public Date getCplCreateTime() {
        return cplCreateTime;
    }

    public void setCplCreateTime(Date cplCreateTime) {
        this.cplCreateTime = cplCreateTime;
    }

    public String getCplCreateUid() {
        return cplCreateUid;
    }

    public void setCplCreateUid(String cplCreateUid) {
        this.cplCreateUid = cplCreateUid;
    }

    public String getCplCreateUname() {
        return cplCreateUname;
    }

    public void setCplCreateUname(String cplCreateUname) {
        this.cplCreateUname = cplCreateUname;
    }

    public String getCplFile() {
        return cplFile;
    }

    public void setCplFile(String cplFile) {
        this.cplFile = cplFile;
    }

    public String getCplYear() {
        return cplYear;
    }

    public void setCplYear(String cplYear) {
        this.cplYear = cplYear;
    }

    public String getCplAgency() {
        return cplAgency;
    }

    public void setCplAgency(String cplAgency) {
        this.cplAgency = cplAgency;
    }

    public String getCplCategory() {
        return cplCategory;
    }

    public void setCplCategory(String cplCategory) {
        this.cplCategory = cplCategory;
    }

    public Date getCplPlanStart() {
        return cplPlanStart;
    }

    public void setCplPlanStart(Date cplPlanStart) {
        this.cplPlanStart = cplPlanStart;
    }

    public Date getCplPlanEnd() {
        return cplPlanEnd;
    }

    public void setCplPlanEnd(Date cplPlanEnd) {
        this.cplPlanEnd = cplPlanEnd;
    }

    public Float getCplLength() {
        return cplLength;
    }

    public void setCplLength(Float cplLength) {
        this.cplLength = cplLength;
    }

    public Float getCplWidth() {
        return cplWidth;
    }

    public void setCplWidth(Float cplWidth) {
        this.cplWidth = cplWidth;
    }

    public String getCplDeclareDep() {
        return cplDeclareDep;
    }

    public void setCplDeclareDep(String cplDeclareDep) {
        this.cplDeclareDep = cplDeclareDep;
    }

    public String getCplUseDep() {
        return cplUseDep;
    }

    public void setCplUseDep(String cplUseDep) {
        this.cplUseDep = cplUseDep;
    }

    public Boolean getCplApproval() {
        return cplApproval;
    }

    public void setCplApproval(Boolean cplApproval) {
        this.cplApproval = cplApproval;
    }

    public String getCplSource() {
        return cplSource;
    }

    public void setCplSource(String cplSource) {
        this.cplSource = cplSource;
    }

    public Boolean getCplAppend() {
        return cplAppend;
    }

    public void setCplAppend(Boolean cplAppend) {
        this.cplAppend = cplAppend;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getStateTime() {
        return stateTime;
    }

    public void setStateTime(Date stateTime) {
        this.stateTime = stateTime;
    }

    public String getCplAgencyName() {
        return cplAgencyName;
    }

    public void setCplAgencyName(String cplAgencyName) {
        this.cplAgencyName = cplAgencyName;
    }

    public String getCplLiableCompanyName() {
        return cplLiableCompanyName;
    }

    public void setCplLiableCompanyName(String cplLiableCompanyName) {
        this.cplLiableCompanyName = cplLiableCompanyName;
    }

    public String getCplDeclareDepName() {
        return cplDeclareDepName;
    }

    public void setCplDeclareDepName(String cplDeclareDepName) {
        this.cplDeclareDepName = cplDeclareDepName;
    }

    public String getCplUseDepName() {
        return cplUseDepName;
    }

    public void setCplUseDepName(String cplUseDepName) {
        this.cplUseDepName = cplUseDepName;
    }

    public String getPcpFileName() {
        return pcpFileName;
    }

    public void setPcpFileName(String pcpFileName) {
        this.pcpFileName = pcpFileName;
    }

    public Integer getPcpYear() {
        return pcpYear;
    }

    public void setPcpYear(Integer pcpYear) {
        this.pcpYear = pcpYear;
    }

    public String getPcpCreateUname() {
        return pcpCreateUname;
    }

    public void setPcpCreateUname(String pcpCreateUname) {
        this.pcpCreateUname = pcpCreateUname;
    }

    public Integer getPcpState() {
        return pcpState;
    }

    public void setPcpState(Integer pcpState) {
        this.pcpState = pcpState;
    }

    public String getCplFileIds() {
        return cplFileIds;
    }

    public void setCplFileIds(String cplFileIds) {
        this.cplFileIds = cplFileIds;
    }

    public Boolean getQueryAppend() {
        return queryAppend;
    }

    public void setQueryAppend(Boolean queryAppend) {
        this.queryAppend = queryAppend;
    }

    public List<ProConstructionSourcesFundsDto> getProConstructionSourcesFunds() {
        return proConstructionSourcesFunds;
    }

    public void setProConstructionSourcesFunds(List<ProConstructionSourcesFundsDto> proConstructionSourcesFunds) {
        this.proConstructionSourcesFunds = proConstructionSourcesFunds;
    }

    public List<ProFundsUsageDetailsDto> getProFundsUsageDetails() {
        return proFundsUsageDetails;
    }

    public void setProFundsUsageDetails(List<ProFundsUsageDetailsDto> proFundsUsageDetails) {
        this.proFundsUsageDetails = proFundsUsageDetails;
    }
}
