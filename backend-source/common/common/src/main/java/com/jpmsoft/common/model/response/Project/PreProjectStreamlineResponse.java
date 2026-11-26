package com.jpmsoft.common.model.response.Project;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目  精简版
 */
public class PreProjectStreamlineResponse {

    private String pId;
    private String pParentId;
    private String pName;
    private String pApplyName;
    private String pCode;
    private String pProjectCode;
    private String pConstructionUnit;
    private String pAssignmentNo;  //任务书编号
    private String pProjectAbbreviation; //简称
    private String pProjectStage; //项目阶段
    private String pProjectStatus; //项目接收状态
    private String pCompetentDepartment;   //主管机构
    private String pMajorType; //重大项目类别
    private String pOverview;
    private String pRegion;
    private Float pLongitude;
    private Float pLatitude;
    private String pProjectDirector;
    private Integer pInterventionYear;
    private String pIsAgentConstruction;
    private String pAgentConstructionUnit;
    private String pDesignAndConstructionIntegrationUnit;
    private BigDecimal pCalculateRoughlyAmount;
    private String pNameInitials;//项目简称中文首字母

    private String pDesignAndConstructionIntegrationUnitName;
    private String pAgentConstructionUnitName;
    private String pCompetentDepartmentName;
    private String pConstructionUnitName;
    private String pParentName;
    private String pAddress;
    private BigDecimal pApproveBudget;
    private String pUseUnit;
    private String pUseUnitName;
    private Date pPlanStartDate;
    private Date pPlanCompleteDate;
    private Date pConstructionStart;
    private Date pConstructionEnd;
    private String pNature;
    private String pBuildModel;
    private String pType;

    /**
     * 是否有子项目
     */
    private Boolean havingChild;
    /**
     * 是否有权限操作
     */
    private Boolean canAction;

    private String pParentNameHistory;
    private String pConstructionUnitNameHistory;
    private String pAgentUnitNameHistory;
    private String pDesignUnitNameHistory;
    private String pNameHistory;
    private String pEstimateAmount;
    private String pEachDistrict;
    private BigDecimal pInvTotal;
    private String pApproveBudgetCode;
    private Date pApproveBudgetDate;
    private String pApprovalUnit;
    private Integer pAdvanceFund;
    private Integer pCorporateRisk;
    private String pSafetyFiling;
    private Float pLength;
    private Float pWidth;
    private Float pSiteArea;
    private String pSmartCode;

    //单位简称
    private String pConsAbbName;
    private String pAgentConsAbbName;
    private String pDesConsIntegAbbName;
    private String pImpCompany;
    private String ipvPType;
    private String managerName;
    private Integer pAuthType;
    //工程类别
    private String pSecType;
    private String pEngineeringspecialty;
    private BigDecimal pFinalAccounts;

    private String pApplyFile;

    private BigDecimal pRecordAmount;
    private BigDecimal pApprovalAmount;
    private String pProjectFormType;

    private String pCreateUserId;
    private String pCreateUserName;
    private Date pCreateTime;

    private Integer state;
    private Date stateTime;
    /**
     * 项目经理
     */
    private String pProjectManager;
    private String pProjectManagerName;

    @JsonProperty("pCategory")
    private String pCategory;

    @JsonProperty("pCampus")
    private String pCampus;

    @JsonProperty("pProjectDirectorTel")
    private String pProjectDirectorTel;

    @JsonProperty("pSourcesFunds")
    private String pSourcesFunds;

    @JsonProperty("pProfessionalType")
    private String pProfessionalType;

    @JsonProperty("pProfessionalTypeRemark")
    private String pProfessionalTypeRemark;

    public String getpProfessionalType() {
        return pProfessionalType;
    }

    public void setpProfessionalType(String pProfessionalType) {
        this.pProfessionalType = pProfessionalType;
    }

    public String getpProfessionalTypeRemark() {
        return pProfessionalTypeRemark;
    }

    public void setpProfessionalTypeRemark(String pProfessionalTypeRemark) {
        this.pProfessionalTypeRemark = pProfessionalTypeRemark;
    }

    public String getpSourcesFunds() {
        return pSourcesFunds;
    }

    public void setpSourcesFunds(String pSourcesFunds) {
        this.pSourcesFunds = pSourcesFunds;
    }

    public String getpProjectDirectorTel() {
        return pProjectDirectorTel;
    }

    public void setpProjectDirectorTel(String pProjectDirectorTel) {
        this.pProjectDirectorTel = pProjectDirectorTel;
    }

    public String getpCampus() {
        return pCampus;
    }

    public void setpCampus(String pCampus) {
        this.pCampus = pCampus;
    }

    public String getpCategory() {
        return pCategory;
    }

    public void setpCategory(String pCategory) {
        this.pCategory = pCategory;
    }

    public BigDecimal getpRecordAmount() {
        return pRecordAmount;
    }

    public void setpRecordAmount(BigDecimal pRecordAmount) {
        this.pRecordAmount = pRecordAmount;
    }

    public BigDecimal getpApprovalAmount() {
        return pApprovalAmount;
    }

    public void setpApprovalAmount(BigDecimal pApprovalAmount) {
        this.pApprovalAmount = pApprovalAmount;
    }

    public String getpProjectFormType() {
        return pProjectFormType;
    }

    public void setpProjectFormType(String pProjectFormType) {
        this.pProjectFormType = pProjectFormType;
    }

    public String getpApplyFile() {
        return pApplyFile;
    }

    public void setpApplyFile(String pApplyFile) {
        this.pApplyFile = pApplyFile;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getIpvPType() {
        return ipvPType;
    }

    public void setIpvPType(String ipvPType) {
        this.ipvPType = ipvPType;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getpParentId() {
        return pParentId;
    }

    public void setpParentId(String pParentId) {
        this.pParentId = pParentId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpApplyName() {
        return pApplyName;
    }

    public void setpApplyName(String pApplyName) {
        this.pApplyName = pApplyName;
    }

    public String getpCode() {
        return pCode;
    }

    public void setpCode(String pCode) {
        this.pCode = pCode;
    }

    public String getpProjectCode() {
        return pProjectCode;
    }

    public void setpProjectCode(String pProjectCode) {
        this.pProjectCode = pProjectCode;
    }

    public String getpConstructionUnit() {
        return pConstructionUnit;
    }

    public void setpConstructionUnit(String pConstructionUnit) {
        this.pConstructionUnit = pConstructionUnit;
    }

    public String getpAssignmentNo() {
        return pAssignmentNo;
    }

    public void setpAssignmentNo(String pAssignmentNo) {
        this.pAssignmentNo = pAssignmentNo;
    }

    public String getpProjectAbbreviation() {
        return pProjectAbbreviation;
    }

    public void setpProjectAbbreviation(String pProjectAbbreviation) {
        this.pProjectAbbreviation = pProjectAbbreviation;
    }

    public String getpProjectStage() {
        return pProjectStage;
    }

    public void setpProjectStage(String pProjectStage) {
        this.pProjectStage = pProjectStage;
    }

    public String getpProjectStatus() {
        return pProjectStatus;
    }

    public void setpProjectStatus(String pProjectStatus) {
        this.pProjectStatus = pProjectStatus;
    }

    public String getpCompetentDepartment() {
        return pCompetentDepartment;
    }

    public void setpCompetentDepartment(String pCompetentDepartment) {
        this.pCompetentDepartment = pCompetentDepartment;
    }

    public String getpMajorType() {
        return pMajorType;
    }

    public void setpMajorType(String pMajorType) {
        this.pMajorType = pMajorType;
    }

    public String getpOverview() {
        return pOverview;
    }

    public void setpOverview(String pOverview) {
        this.pOverview = pOverview;
    }

    public String getpRegion() {
        return pRegion;
    }

    public void setpRegion(String pRegion) {
        this.pRegion = pRegion;
    }

    public Float getpLongitude() {
        return pLongitude;
    }

    public void setpLongitude(Float pLongitude) {
        this.pLongitude = pLongitude;
    }

    public Float getpLatitude() {
        return pLatitude;
    }

    public void setpLatitude(Float pLatitude) {
        this.pLatitude = pLatitude;
    }

    public String getpProjectDirector() {
        return pProjectDirector;
    }

    public void setpProjectDirector(String pProjectDirector) {
        this.pProjectDirector = pProjectDirector;
    }

    public Integer getpInterventionYear() {
        return pInterventionYear;
    }

    public void setpInterventionYear(Integer pInterventionYear) {
        this.pInterventionYear = pInterventionYear;
    }

    public String getpIsAgentConstruction() {
        return pIsAgentConstruction;
    }

    public void setpIsAgentConstruction(String pIsAgentConstruction) {
        this.pIsAgentConstruction = pIsAgentConstruction;
    }

    public String getpAgentConstructionUnit() {
        return pAgentConstructionUnit;
    }

    public void setpAgentConstructionUnit(String pAgentConstructionUnit) {
        this.pAgentConstructionUnit = pAgentConstructionUnit;
    }

    public String getpDesignAndConstructionIntegrationUnit() {
        return pDesignAndConstructionIntegrationUnit;
    }

    public void setpDesignAndConstructionIntegrationUnit(String pDesignAndConstructionIntegrationUnit) {
        this.pDesignAndConstructionIntegrationUnit = pDesignAndConstructionIntegrationUnit;
    }

    public BigDecimal getpCalculateRoughlyAmount() {
        return pCalculateRoughlyAmount;
    }

    public void setpCalculateRoughlyAmount(BigDecimal pCalculateRoughlyAmount) {
        this.pCalculateRoughlyAmount = pCalculateRoughlyAmount;
    }

    public String getpNameInitials() {
        return pNameInitials;
    }

    public void setpNameInitials(String pNameInitials) {
        this.pNameInitials = pNameInitials;
    }

    public String getpDesignAndConstructionIntegrationUnitName() {
        return pDesignAndConstructionIntegrationUnitName;
    }

    public void setpDesignAndConstructionIntegrationUnitName(String pDesignAndConstructionIntegrationUnitName) {
        this.pDesignAndConstructionIntegrationUnitName = pDesignAndConstructionIntegrationUnitName;
    }

    public String getpAgentConstructionUnitName() {
        return pAgentConstructionUnitName;
    }

    public void setpAgentConstructionUnitName(String pAgentConstructionUnitName) {
        this.pAgentConstructionUnitName = pAgentConstructionUnitName;
    }

    public String getpCompetentDepartmentName() {
        return pCompetentDepartmentName;
    }

    public void setpCompetentDepartmentName(String pCompetentDepartmentName) {
        this.pCompetentDepartmentName = pCompetentDepartmentName;
    }

    public String getpConstructionUnitName() {
        return pConstructionUnitName;
    }

    public void setpConstructionUnitName(String pConstructionUnitName) {
        this.pConstructionUnitName = pConstructionUnitName;
    }

    public String getpParentName() {
        return pParentName;
    }

    public void setpParentName(String pParentName) {
        this.pParentName = pParentName;
    }

    public String getpAddress() {
        return pAddress;
    }

    public void setpAddress(String pAddress) {
        this.pAddress = pAddress;
    }

    public BigDecimal getpApproveBudget() {
        return pApproveBudget;
    }

    public void setpApproveBudget(BigDecimal pApproveBudget) {
        this.pApproveBudget = pApproveBudget;
    }

    public String getpUseUnit() {
        return pUseUnit;
    }

    public void setpUseUnit(String pUseUnit) {
        this.pUseUnit = pUseUnit;
    }

    public String getpUseUnitName() {
        return pUseUnitName;
    }

    public void setpUseUnitName(String pUseUnitName) {
        this.pUseUnitName = pUseUnitName;
    }

    public Date getpPlanStartDate() {
        return pPlanStartDate;
    }

    public void setpPlanStartDate(Date pPlanStartDate) {
        this.pPlanStartDate = pPlanStartDate;
    }

    public Date getpPlanCompleteDate() {
        return pPlanCompleteDate;
    }

    public void setpPlanCompleteDate(Date pPlanCompleteDate) {
        this.pPlanCompleteDate = pPlanCompleteDate;
    }

    public Date getpConstructionStart() {
        return pConstructionStart;
    }

    public void setpConstructionStart(Date pConstructionStart) {
        this.pConstructionStart = pConstructionStart;
    }

    public Date getpConstructionEnd() {
        return pConstructionEnd;
    }

    public void setpConstructionEnd(Date pConstructionEnd) {
        this.pConstructionEnd = pConstructionEnd;
    }

    public Boolean getHavingChild() {
        return havingChild;
    }

    public void setHavingChild(Boolean havingChild) {
        this.havingChild = havingChild;
    }

    public Boolean getCanAction() {
        return canAction;
    }

    public void setCanAction(Boolean canAction) {
        this.canAction = canAction;
    }

    public String getpParentNameHistory() {
        return pParentNameHistory;
    }

    public void setpParentNameHistory(String pParentNameHistory) {
        this.pParentNameHistory = pParentNameHistory;
    }

    public String getpConstructionUnitNameHistory() {
        return pConstructionUnitNameHistory;
    }

    public void setpConstructionUnitNameHistory(String pConstructionUnitNameHistory) {
        this.pConstructionUnitNameHistory = pConstructionUnitNameHistory;
    }

    public String getpAgentUnitNameHistory() {
        return pAgentUnitNameHistory;
    }

    public void setpAgentUnitNameHistory(String pAgentUnitNameHistory) {
        this.pAgentUnitNameHistory = pAgentUnitNameHistory;
    }

    public String getpDesignUnitNameHistory() {
        return pDesignUnitNameHistory;
    }

    public void setpDesignUnitNameHistory(String pDesignUnitNameHistory) {
        this.pDesignUnitNameHistory = pDesignUnitNameHistory;
    }

    public String getpNameHistory() {
        return pNameHistory;
    }

    public void setpNameHistory(String pNameHistory) {
        this.pNameHistory = pNameHistory;
    }

    public String getpEstimateAmount() {
        return pEstimateAmount;
    }

    public void setpEstimateAmount(String pEstimateAmount) {
        this.pEstimateAmount = pEstimateAmount;
    }

    public String getpEachDistrict() {
        return pEachDistrict;
    }

    public void setpEachDistrict(String pEachDistrict) {
        this.pEachDistrict = pEachDistrict;
    }

    public BigDecimal getpInvTotal() {
        return pInvTotal;
    }

    public void setpInvTotal(BigDecimal pInvTotal) {
        this.pInvTotal = pInvTotal;
    }

    public String getpApproveBudgetCode() {
        return pApproveBudgetCode;
    }

    public void setpApproveBudgetCode(String pApproveBudgetCode) {
        this.pApproveBudgetCode = pApproveBudgetCode;
    }

    public Date getpApproveBudgetDate() {
        return pApproveBudgetDate;
    }

    public void setpApproveBudgetDate(Date pApproveBudgetDate) {
        this.pApproveBudgetDate = pApproveBudgetDate;
    }

    public String getpApprovalUnit() {
        return pApprovalUnit;
    }

    public void setpApprovalUnit(String pApprovalUnit) {
        this.pApprovalUnit = pApprovalUnit;
    }

    public Integer getpAdvanceFund() {
        return pAdvanceFund;
    }

    public void setpAdvanceFund(Integer pAdvanceFund) {
        this.pAdvanceFund = pAdvanceFund;
    }

    public Integer getpCorporateRisk() {
        return pCorporateRisk;
    }

    public void setpCorporateRisk(Integer pCorporateRisk) {
        this.pCorporateRisk = pCorporateRisk;
    }

    public String getpSafetyFiling() {
        return pSafetyFiling;
    }

    public void setpSafetyFiling(String pSafetyFiling) {
        this.pSafetyFiling = pSafetyFiling;
    }

    public Float getpLength() {
        return pLength;
    }

    public void setpLength(Float pLength) {
        this.pLength = pLength;
    }

    public Float getpWidth() {
        return pWidth;
    }

    public void setpWidth(Float pWidth) {
        this.pWidth = pWidth;
    }

    public Float getpSiteArea() {
        return pSiteArea;
    }

    public void setpSiteArea(Float pSiteArea) {
        this.pSiteArea = pSiteArea;
    }

    public String getpSmartCode() {
        return pSmartCode;
    }

    public void setpSmartCode(String pSmartCode) {
        this.pSmartCode = pSmartCode;
    }

    public String getpConsAbbName() {
        return pConsAbbName;
    }

    public void setpConsAbbName(String pConsAbbName) {
        this.pConsAbbName = pConsAbbName;
    }

    public String getpAgentConsAbbName() {
        return pAgentConsAbbName;
    }

    public void setpAgentConsAbbName(String pAgentConsAbbName) {
        this.pAgentConsAbbName = pAgentConsAbbName;
    }

    public String getpDesConsIntegAbbName() {
        return pDesConsIntegAbbName;
    }

    public void setpDesConsIntegAbbName(String pDesConsIntegAbbName) {
        this.pDesConsIntegAbbName = pDesConsIntegAbbName;
    }

    public String getpNature() {
        return pNature;
    }

    public void setpNature(String pNature) {
        this.pNature = pNature;
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

    public String getpImpCompany() {
        return pImpCompany;
    }

    public void setpImpCompany(String pImpCompany) {
        this.pImpCompany = pImpCompany;
    }

    public Integer getpAuthType() {
        return pAuthType;
    }

    public void setpAuthType(Integer pAuthType) {
        this.pAuthType = pAuthType;
    }

    public String getpSecType() {
        return pSecType;
    }

    public void setpSecType(String pSecType) {
        this.pSecType = pSecType;
    }

    public String getpEngineeringspecialty() {
        return pEngineeringspecialty;
    }

    public void setpEngineeringspecialty(String pEngineeringspecialty) {
        this.pEngineeringspecialty = pEngineeringspecialty;
    }

    public BigDecimal getpFinalAccounts() {
        return pFinalAccounts;
    }

    public void setpFinalAccounts(BigDecimal pFinalAccounts) {
        this.pFinalAccounts = pFinalAccounts;
    }

    public String getpCreateUserId() {
        return pCreateUserId;
    }

    public void setpCreateUserId(String pCreateUserId) {
        this.pCreateUserId = pCreateUserId;
    }

    public String getpCreateUserName() {
        return pCreateUserName;
    }

    public void setpCreateUserName(String pCreateUserName) {
        this.pCreateUserName = pCreateUserName;
    }

    public Date getpCreateTime() {
        return pCreateTime;
    }

    public void setpCreateTime(Date pCreateTime) {
        this.pCreateTime = pCreateTime;
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

    public String getpProjectManager() {
        return pProjectManager;
    }

    public void setpProjectManager(String pProjectManager) {
        this.pProjectManager = pProjectManager;
    }

    public String getpProjectManagerName() {
        return pProjectManagerName;
    }

    public void setpProjectManagerName(String pProjectManagerName) {
        this.pProjectManagerName = pProjectManagerName;
    }

    // =====================================================================================
    // 以下字段为「单项目平台-菜单动态化」需求新增（docs/68、docs/70）
    // 用途：让 /evaluate/v2/menu/projects 的返回与 PM 的 getProjectListNoAuth 同结构，
    //      并额外带上「项目申报」信息与「省试点项目」信息。PM 原有列表页不 select 这些列时值为 null，不影响原逻辑。
    // =====================================================================================

    /** 项目申报ID（project_register_info.id）。决策 C1：对外一律用申报ID；经 project_register_info.project_id 关联项目表 */
    private String registerId;

    /** 项目类型（project_register_info.building_type，字典 JPMsoft_ZPXMLX：01 房建 / 02 市政 / 03 装饰） */
    private String buildingType;

    /** 申报流程状态（project_register_info.state） */
    private Integer registerState;

    /** 监测状态（project_register_info.detect_status，03 = 已取消） */
    private String detectStatus;

    /** 申报类型（project_register_info.declare_Type；驾驶舱口径：02 = 市试点，03 = 省试点） */
    private String declareType;

    /** 自评分（project_register_info.total_score），综合完成率的分母 */
    private BigDecimal selfTotalScore;

    /** 动态分（project_register_info.dynamic_score），单项目平台填报得分 */
    private BigDecimal dynamicScore;

    /** 省试点记录ID（prov_pilot_project.id；本行命中省试点时有值） */
    private String pilotId;

    /** 省试点年度（prov_pilot_project.annual_year） */
    private Integer pilotAnnualYear;

    /** 省试点批次（prov_pilot_project.batch） */
    private String pilotBatch;

    /** 省试点项目所在区（prov_pilot_project.district） */
    private String pilotDistrict;

    /** 省试点申报单位（prov_pilot_project.declare_unit，名称串，不是单位ID） */
    private String pilotDeclareUnit;

    /** 关联的市试点项目（prov_pilot_project.related_city_project_id = 项目申报ID） */
    private String relatedCityProjectId;

    /** 关联的市试点项目名称（prov_pilot_project.related_city_project_name） */
    private String relatedCityProjectName;

    public String getRegisterId() {
        return registerId;
    }

    public void setRegisterId(String registerId) {
        this.registerId = registerId;
    }

    public String getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(String buildingType) {
        this.buildingType = buildingType;
    }

    public Integer getRegisterState() {
        return registerState;
    }

    public void setRegisterState(Integer registerState) {
        this.registerState = registerState;
    }

    public String getDetectStatus() {
        return detectStatus;
    }

    public void setDetectStatus(String detectStatus) {
        this.detectStatus = detectStatus;
    }

    public String getDeclareType() {
        return declareType;
    }

    public void setDeclareType(String declareType) {
        this.declareType = declareType;
    }

    public BigDecimal getSelfTotalScore() {
        return selfTotalScore;
    }

    public void setSelfTotalScore(BigDecimal selfTotalScore) {
        this.selfTotalScore = selfTotalScore;
    }

    public BigDecimal getDynamicScore() {
        return dynamicScore;
    }

    public void setDynamicScore(BigDecimal dynamicScore) {
        this.dynamicScore = dynamicScore;
    }




    public String getPilotId() {
        return pilotId;
    }

    public void setPilotId(String pilotId) {
        this.pilotId = pilotId;
    }

    public Integer getPilotAnnualYear() {
        return pilotAnnualYear;
    }

    public void setPilotAnnualYear(Integer pilotAnnualYear) {
        this.pilotAnnualYear = pilotAnnualYear;
    }

    public String getPilotBatch() {
        return pilotBatch;
    }

    public void setPilotBatch(String pilotBatch) {
        this.pilotBatch = pilotBatch;
    }

    public String getPilotDistrict() {
        return pilotDistrict;
    }

    public void setPilotDistrict(String pilotDistrict) {
        this.pilotDistrict = pilotDistrict;
    }

    public String getPilotDeclareUnit() {
        return pilotDeclareUnit;
    }

    public void setPilotDeclareUnit(String pilotDeclareUnit) {
        this.pilotDeclareUnit = pilotDeclareUnit;
    }

    public String getRelatedCityProjectId() {
        return relatedCityProjectId;
    }

    public void setRelatedCityProjectId(String relatedCityProjectId) {
        this.relatedCityProjectId = relatedCityProjectId;
    }

    public String getRelatedCityProjectName() {
        return relatedCityProjectName;
    }

    public void setRelatedCityProjectName(String relatedCityProjectName) {
        this.relatedCityProjectName = relatedCityProjectName;
    }
}
