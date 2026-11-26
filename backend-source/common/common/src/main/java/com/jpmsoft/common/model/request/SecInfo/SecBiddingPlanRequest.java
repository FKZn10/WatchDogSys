package com.jpmsoft.common.model.request.SecInfo;

import com.jpmsoft.common.model.BaseFileRequest;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecInfoSubjectRemarkDTO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 招标计划
 */
public class SecBiddingPlanRequest extends BaseFileRequest {

    private String bpId;
    private String bpName;
    private Integer bpYear;
    private Integer bpMonth;
    private String bpRemark;
    private String bpCreateUid;
    private String bpCreateUname;
    private Date bpCreateTime;
    private Date stateTime;
    private Integer state;
    private String bpTenderee;
    private String bpApprovalTitle;
    private String bpApprovalNum;
    private Date bpApprovalTime;
    private String bpApprovalFile;

    private Date bpSecTime;
    private String bpCid;
    private String bpDid;
    private String bpCnature;
    private String bpDnature;
    private Date bpTime;
    private String bpSecContent;
    private BigDecimal bpGsAmt;
    private String bpBiddingType;
    private Date bpEndTime;
    private String bpSecName;
    private Boolean bpGczb;
    private String bpProScale;
    private BigDecimal bpProAmt;
    private String bpFundSource;
    private String bpScopeOverview;
    private String bpSm;
    private BigDecimal bpEsAmt;
    //委托类型
    private String bpEntrustmentType;
    /**
     * 集团是否为见证方 1：是 0：否
     */
    private Integer bpGroupWitness;
    /**
     * 项目投资控制情况
     */
    private String bpInvControlSituation;
    //管理类别
    private String bpManagementCategory;

    private String bpCname;
    private String bpDname;
    private String bpApprovalFileIds;
    private Integer secFlowState;
    private String pName;
    private String pBuildModel;
    private String pType;
    private String pUseUnit;
    private String pImpCompany;
    private String pCompetentDepartmentName;
    private List<String> pIds;
    private List<String> projectNameList;
    private List<String> pBuildModelList;
    private List<String> pTypeList;
    private List<String> pUseUnitList;
    private List<String> pImpCompanyList;
    private List<String> pCompetentDepartmentNameList;
    //招标计划 项目、招采关联
    private List<SecBiddingProjectRequest> secBiddingProjectList;
    //标段 招标计划项目关联
    private List<SecProjectRequest> secProjectList;
    private List<SecInfoRequest> secInfos;

    /**
     * 计划阶段
     */
    private String bpPlanPhase;

    /*
     * 项目规模
     */
    private String projectCategory;
    /*
     * 发起单位
     */
    private String projectInitiatingUnit;

    private String isNew;

    private List<SecInfoSubjectRemarkDTO> remarkList;

    private String responsibleLeader;

    private String responsibleLeaderName;

    private String reserveItems;

    /**
     * 是否录入估算
     */
    private String enterEstimate;

    private String pCategory;

    public String getpCategory() {
        return pCategory;
    }

    public void setpCategory(String pCategory) {
        this.pCategory = pCategory;
    }

    public String getEnterEstimate() {
        return enterEstimate;
    }

    public void setEnterEstimate(String enterEstimate) {
        this.enterEstimate = enterEstimate;
    }

    public String getReserveItems() {
        return reserveItems;
    }

    public void setReserveItems(String reserveItems) {
        this.reserveItems = reserveItems;
    }

    public String getResponsibleLeader() {
        return responsibleLeader;
    }

    public void setResponsibleLeader(String responsibleLeader) {
        this.responsibleLeader = responsibleLeader;
    }

    public String getResponsibleLeaderName() {
        return responsibleLeaderName;
    }

    public void setResponsibleLeaderName(String responsibleLeaderName) {
        this.responsibleLeaderName = responsibleLeaderName;
    }

    public List<SecInfoSubjectRemarkDTO> getRemarkList() {
        return remarkList;
    }

    public void setRemarkList(List<SecInfoSubjectRemarkDTO> remarkList) {
        this.remarkList = remarkList;
    }

    public String getIsNew() {
        return isNew;
    }

    public void setIsNew(String isNew) {
        this.isNew = isNew;
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

    public String getBpPlanPhase() {
        return bpPlanPhase;
    }

    public void setBpPlanPhase(String bpPlanPhase) {
        this.bpPlanPhase = bpPlanPhase;
    }

    public String getBpId() {
        return bpId;
    }

    public void setBpId(String bpId) {
        this.bpId = bpId;
    }

    public String getBpName() {
        return bpName;
    }

    public void setBpName(String bpName) {
        this.bpName = bpName;
    }

    public String getpBuildModel() {
        return pBuildModel;
    }

    public void setpBuildModel(String pBuildModel) {
        this.pBuildModel = pBuildModel;
    }

    public String getpType() {
        return pType;
    }

    public void setpType(String pType) {
        this.pType = pType;
    }

    public String getpUseUnit() {
        return pUseUnit;
    }

    public void setpUseUnit(String pUseUnit) {
        this.pUseUnit = pUseUnit;
    }

    public String getpImpCompany() {
        return pImpCompany;
    }

    public void setpImpCompany(String pImpCompany) {
        this.pImpCompany = pImpCompany;
    }

    @Override
    public String getpCompetentDepartmentName() {
        return pCompetentDepartmentName;
    }

    @Override
    public void setpCompetentDepartmentName(String pCompetentDepartmentName) {
        this.pCompetentDepartmentName = pCompetentDepartmentName;
    }

    public List<String> getProjectNameList() {
        return projectNameList;
    }

    public void setProjectNameList(List<String> projectNameList) {
        this.projectNameList = projectNameList;
    }

    public List<String> getpBuildModelList() {
        return pBuildModelList;
    }

    public void setpBuildModelList(List<String> pBuildModelList) {
        this.pBuildModelList = pBuildModelList;
    }

    public List<String> getpTypeList() {
        return pTypeList;
    }

    public void setpTypeList(List<String> pTypeList) {
        this.pTypeList = pTypeList;
    }

    public List<String> getpUseUnitList() {
        return pUseUnitList;
    }

    public void setpUseUnitList(List<String> pUseUnitList) {
        this.pUseUnitList = pUseUnitList;
    }

    public List<String> getpImpCompanyList() {
        return pImpCompanyList;
    }

    public void setpImpCompanyList(List<String> pImpCompanyList) {
        this.pImpCompanyList = pImpCompanyList;
    }

    public List<String> getpCompetentDepartmentNameList() {
        return pCompetentDepartmentNameList;
    }

    public void setpCompetentDepartmentNameList(List<String> pCompetentDepartmentNameList) {
        this.pCompetentDepartmentNameList = pCompetentDepartmentNameList;
    }

    public Integer getBpYear() {
        return bpYear;
    }

    public void setBpYear(Integer bpYear) {
        this.bpYear = bpYear;
    }

    public Integer getBpMonth() {
        return bpMonth;
    }

    public void setBpMonth(Integer bpMonth) {
        this.bpMonth = bpMonth;
    }

    public String getBpRemark() {
        return bpRemark;
    }

    public void setBpRemark(String bpRemark) {
        this.bpRemark = bpRemark;
    }

    public String getBpCreateUid() {
        return bpCreateUid;
    }

    public void setBpCreateUid(String bpCreateUid) {
        this.bpCreateUid = bpCreateUid;
    }

    public String getBpCreateUname() {
        return bpCreateUname;
    }

    public void setBpCreateUname(String bpCreateUname) {
        this.bpCreateUname = bpCreateUname;
    }

    public Date getBpCreateTime() {
        return bpCreateTime;
    }

    public void setBpCreateTime(Date bpCreateTime) {
        this.bpCreateTime = bpCreateTime;
    }

    public Date getStateTime() {
        return stateTime;
    }

    public void setStateTime(Date stateTime) {
        this.stateTime = stateTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getBpTenderee() {
        return bpTenderee;
    }

    public void setBpTenderee(String bpTenderee) {
        this.bpTenderee = bpTenderee;
    }

    public String getBpApprovalTitle() {
        return bpApprovalTitle;
    }

    public void setBpApprovalTitle(String bpApprovalTitle) {
        this.bpApprovalTitle = bpApprovalTitle;
    }

    public String getBpApprovalNum() {
        return bpApprovalNum;
    }

    public void setBpApprovalNum(String bpApprovalNum) {
        this.bpApprovalNum = bpApprovalNum;
    }

    public Date getBpApprovalTime() {
        return bpApprovalTime;
    }

    public void setBpApprovalTime(Date bpApprovalTime) {
        this.bpApprovalTime = bpApprovalTime;
    }

    public String getBpApprovalFile() {
        return bpApprovalFile;
    }

    public void setBpApprovalFile(String bpApprovalFile) {
        this.bpApprovalFile = bpApprovalFile;
    }

    public String getBpApprovalFileIds() {
        return bpApprovalFileIds;
    }

    public void setBpApprovalFileIds(String bpApprovalFileIds) {
        this.bpApprovalFileIds = bpApprovalFileIds;
    }

    public Date getBpSecTime() {
        return bpSecTime;
    }

    public void setBpSecTime(Date bpSecTime) {
        this.bpSecTime = bpSecTime;
    }

    public String getBpCid() {
        return bpCid;
    }

    public void setBpCid(String bpCid) {
        this.bpCid = bpCid;
    }

    public String getBpDid() {
        return bpDid;
    }

    public void setBpDid(String bpDid) {
        this.bpDid = bpDid;
    }

    public String getBpCnature() {
        return bpCnature;
    }

    public void setBpCnature(String bpCnature) {
        this.bpCnature = bpCnature;
    }

    public String getBpDnature() {
        return bpDnature;
    }

    public void setBpDnature(String bpDnature) {
        this.bpDnature = bpDnature;
    }

    public Date getBpTime() {
        return bpTime;
    }

    public void setBpTime(Date bpTime) {
        this.bpTime = bpTime;
    }

    public String getBpSecContent() {
        return bpSecContent;
    }

    public void setBpSecContent(String bpSecContent) {
        this.bpSecContent = bpSecContent;
    }

    public BigDecimal getBpGsAmt() {
        return bpGsAmt;
    }

    public void setBpGsAmt(BigDecimal bpGsAmt) {
        this.bpGsAmt = bpGsAmt;
    }

    public String getBpBiddingType() {
        return bpBiddingType;
    }

    public void setBpBiddingType(String bpBiddingType) {
        this.bpBiddingType = bpBiddingType;
    }

    public Date getBpEndTime() {
        return bpEndTime;
    }

    public void setBpEndTime(Date bpEndTime) {
        this.bpEndTime = bpEndTime;
    }

    public String getBpSecName() {
        return bpSecName;
    }

    public void setBpSecName(String bpSecName) {
        this.bpSecName = bpSecName;
    }

    public Boolean getBpGczb() {
        return bpGczb;
    }

    public void setBpGczb(Boolean bpGczb) {
        this.bpGczb = bpGczb;
    }

    public String getBpProScale() {
        return bpProScale;
    }

    public void setBpProScale(String bpProScale) {
        this.bpProScale = bpProScale;
    }

    public BigDecimal getBpProAmt() {
        return bpProAmt;
    }

    public void setBpProAmt(BigDecimal bpProAmt) {
        this.bpProAmt = bpProAmt;
    }

    public String getBpFundSource() {
        return bpFundSource;
    }

    public void setBpFundSource(String bpFundSource) {
        this.bpFundSource = bpFundSource;
    }

    public String getBpScopeOverview() {
        return bpScopeOverview;
    }

    public void setBpScopeOverview(String bpScopeOverview) {
        this.bpScopeOverview = bpScopeOverview;
    }

    public String getBpSm() {
        return bpSm;
    }

    public void setBpSm(String bpSm) {
        this.bpSm = bpSm;
    }

    public BigDecimal getBpEsAmt() {
        return bpEsAmt;
    }

    public void setBpEsAmt(BigDecimal bpEsAmt) {
        this.bpEsAmt = bpEsAmt;
    }


    public String getBpEntrustmentType() {
        return bpEntrustmentType;
    }

    public void setBpEntrustmentType(String bpEntrustmentType) {
        this.bpEntrustmentType = bpEntrustmentType;
    }

    public Integer getBpGroupWitness() {
        return bpGroupWitness;
    }

    public void setBpGroupWitness(Integer bpGroupWitness) {
        this.bpGroupWitness = bpGroupWitness;
    }

    public String getBpInvControlSituation() {
        return bpInvControlSituation;
    }

    public void setBpInvControlSituation(String bpInvControlSituation) {
        this.bpInvControlSituation = bpInvControlSituation;
    }

    public String getBpManagementCategory() {
        return bpManagementCategory;
    }

    public void setBpManagementCategory(String bpManagementCategory) {
        this.bpManagementCategory = bpManagementCategory;
    }

    public String getBpCname() {
        return bpCname;
    }

    public void setBpCname(String bpCname) {
        this.bpCname = bpCname;
    }

    public String getBpDname() {
        return bpDname;
    }

    public void setBpDname(String bpDname) {
        this.bpDname = bpDname;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public List<String> getpIds() {
        return pIds;
    }

    public void setpIds(List<String> pIds) {
        this.pIds = pIds;
    }

    public List<SecBiddingProjectRequest> getSecBiddingProjectList() {
        return secBiddingProjectList;
    }

    public void setSecBiddingProjectList(List<SecBiddingProjectRequest> secBiddingProjectList) {
        this.secBiddingProjectList = secBiddingProjectList;
    }

    public List<SecProjectRequest> getSecProjectList() {
        return secProjectList;
    }

    public void setSecProjectList(List<SecProjectRequest> secProjectList) {
        this.secProjectList = secProjectList;
    }

    public List<SecInfoRequest> getSecInfos() {
        return secInfos;
    }

    public void setSecInfos(List<SecInfoRequest> secInfos) {
        this.secInfos = secInfos;
    }

    public Integer getSecFlowState() {
        return secFlowState;
    }

    public void setSecFlowState(Integer secFlowState) {
        this.secFlowState = secFlowState;
    }
}
