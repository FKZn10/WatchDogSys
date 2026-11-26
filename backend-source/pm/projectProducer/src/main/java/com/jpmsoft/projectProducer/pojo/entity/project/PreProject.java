package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.common.annotations.SecretField;
import com.jpmsoft.projectProducer.annotations.ClassInfo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目信息
 */
@TableName("Pre_Project")
public class PreProject {

    @SecretField
    @TableId(type = IdType.INPUT)
    private String pId;
    @SecretField
    private String prId;
    @SecretField
    private String pCode;
    @SecretField
    private String pParentId;
    @SecretField
    private String pName;
    @SecretField
    private String pProjectAbbreviation;
    @SecretField
    private String pOriginalName;
    @SecretField
    private String pNameInitials;
    @SecretField
    private String pProjectCode;
    @SecretField
    private String pAddress;
    @SecretField
    private String pRegion;
    @SecretField
    private Float pConstructionArea;
    @SecretField
    private Float pLongitude;
    @SecretField
    private Float pLatitude;
    @SecretField
    private String pOverview;
    @SecretField
    private Float pVolumeRate;
    @SecretField
    private Float pLandArea;
    @SecretField
    private Integer pSeat;
    @SecretField
    private Integer pNumberOfElevators;
    @SecretField
    private Float pMaxHeight;
    @SecretField
    private Integer pParkingNumber;
    @SecretField
    private Integer pBuildingsNumber;
    @SecretField
    private Integer pDesignLife;
    @SecretField
    private String pSuperStructure;
    @SecretField
    private String pBasicStructure;
    @SecretField
    private Integer pGroundFloors;
    @SecretField
    private Integer pUnderGroundFloors;
    @SecretField
    private String pIsGas;
    @SecretField
    private Float pBasementArea;
    @SecretField
    private String pIsAirDefense;
    @SecretField
    private Float pAirDefenseArea;
    @SecretField
    private String pType;
    @SecretField
    private String pMajorType;
    @SecretField
    private String pConstructionUnit;
    @SecretField
    private String pCompetentDepartment;
    @SecretField
    private String pProjectDirector;
    @SecretField
    private String pProjectStage;
    @SecretField
    private String pProjectStatus;
    @SecretField
    private Date pPlanStartDate;
    @SecretField
    private Date pPlanCompleteDate;
    @SecretField
    private Integer pTotalDuration;
    @SecretField
    private Date pActualStartTime;
    @SecretField
    private Date pActualCompleteDate;
    @SecretField
    private String pActualTotalDuration;
    @SecretField
    private Date pInterventionDate;
    @SecretField
    private Integer pInterventionYear;
    @SecretField
    private Date pReceiptTime;
    @SecretField
    private Date pAcceptanceRecordDate;
    @SecretField
    private Date pHandoverDate;
    @SecretField
    private String pIsProjectSummary;
    @SecretField
    private String pUseUnit;
    @SecretField
    private String pSourcesFunds;
    @SecretField
    private BigDecimal pCalculateRoughlyAmount;
    @SecretField
    private BigDecimal pEstimateAmount;
    @SecretField
    private BigDecimal pApproveBudget;
    @SecretField
    private String pApproveBudgetCode;
    @SecretField
    private Date pApproveBudgetDate;
    @SecretField
    private BigDecimal pFinalAccounts;
    @SecretField
    private Date pFinalAccountsReportDate;
    @SecretField
    private String pConstructionProjectPlanningPermitCode;
    @SecretField
    private String pConstructionLandPlanningPermitcode;
    @SecretField
    private String pOpinionsOnSiteSelectionForConstructionProjectsCode;
    @SecretField
    private String pLandUseRightAssignmentContractCode;
    @SecretField
    //可研批复文件编号
    private String pFeasibilityStudyApprovalCode;
    @SecretField
    private String pEiaApprovalCode;
    @SecretField
    private String pIsAgentConstruction;
    @SecretField
    private String pAgentConstructionUnit;
    @SecretField
    private String pDesignGrade;
    @SecretField
    private String pIsPrefabricated;
    @SecretField
    private String pFourNewTechnologies;
    @SecretField
    private String pBimTechnology;
    @SecretField
    private String pIsEpc;
    @SecretField
    private String pIsSpongeCity;
    @SecretField
    private String pIsRegularMode;
    @SecretField
    private String pIsDesignAndConstructionIntegration;
    @SecretField
    private String pDesignAndConstructionIntegrationUnit;
    @SecretField
    private String pIsWholeProcessManagement;
    @SecretField
    private String pWholeProcessManagementUnit;
    @SecretField
    private String pConstructionCooperation;
    @SecretField
    private String pStatus;
    @SecretField
    private String pCreateUserId;
    @SecretField
    private String pCreateUserName;
    @SecretField
    private Date pCreateTime;
    @SecretField
    private String pUpdateUserId;
    @SecretField
    private Date pUpdateTime;
    @SecretField
    private String pSerialNumber;
    private Boolean deleted;
    @SecretField
    private String pAssignmentNo;

    @SecretField
    private BigDecimal pRaiseFunds;
    @SecretField
    private String pIntroduction;
    @SecretField
    private String pLandWarrant;
    @SecretField
    private Integer pParkingNumberUn;
    @SecretField
    private Integer pParkingNumberOn;

    @SecretField
    private String pItemNumber;
    @SecretField
    private String pPreItemNumber;
    @SecretField
    private Date pPreItemTime;
    @SecretField
    private Date pItemTime;
    @SecretField
    private Float pSiteArea;
    @SecretField
    private String pCmMode;
    @SecretField
    private String pAssignmentFile;
    @SecretField
    private String pNameHistory;
    @SecretField
    private String pCommitteeApprovaDocument;
    @SecretField
    private String pCityLeaderMeetingMinutes;
    @SecretField
    private String pCityLeaderInstructions;
    @SecretField
    private String pOther;
    @SecretField
    private String pNature;
    @SecretField
    private BigDecimal pReportedAmount;
    @SecretField
    private String pApprovaDocument;
    @SecretField
    private String pLeaderMeetingMinutes;
    @SecretField
    private String pLeaderInstructions;

    @SecretField
    private String pProjectDirectorTel;
    @SecretField
    private BigDecimal pConstructionCost;
    @SecretField
    private BigDecimal pContractPrice;
    @SecretField
    private BigDecimal pPurchasePrice;
    @SecretField
    private String pEstimateFile;
    @SecretField
    private String pScaleDescribe;

    @SecretField
    private Boolean pBim;
    @SecretField
    private Boolean pSmartSite;
    @SecretField
    private Boolean pManagementSystem;
    @SecretField
    private Boolean pNewTechnology;
    @SecretField
    private Boolean pNewMaterial;
    @SecretField
    private Boolean pNewCraft;
    @SecretField
    private Boolean pSaveEnergy;
    @SecretField
    private Boolean pSaveLand;
    @SecretField
    private Boolean pSaveWater;
    @SecretField
    private Boolean pSaveMaterial;
    @SecretField
    private Boolean pDigitalConstruction;
    @SecretField
    private String pGreenConstruction;
    @SecretField
    private Boolean pCentralizedConstruction;
    @SecretField
    private String pContractingMode;
    @SecretField
    private Boolean pProcessConsulting;
    @SecretField
    private Boolean pEngineerResponsible;
    @SecretField
    private Boolean pIsProjectApproval;
    @SecretField
    private Float pBuildingScale;
    @SecretField
    private String pBuildingUnit;
    @SecretField
    private String pSuperviseType;
    @SecretField
    private String pGroupType;
    private Integer state;
    private Date stateTime;
    @SecretField
    private String pRemarkPlanned;
    @SecretField
    private String pPlanNumber;
    @SecretField
    private Date pConsRelease;
    @SecretField
    private String pImpCompany;
    @SecretField
    private String pImpState;
    @SecretField
    private String pApplyTilte;
    @SecretField
    private BigDecimal pInvTotal;
    @SecretField
    private String pInvCaliber;
    @SecretField
    private String pInvTilte;
    @SecretField
    private String pInvCode;
    @SecretField
    private Date pInvDate;
    @SecretField
    private String pInvFile;
    @SecretField
    private String pPassVideo;
    @SecretField
    private String pBuildModel;
    @SecretField
    private Integer pInterventionEndYear;
    @SecretField
    private String pEachDistrict;
    @SecretField
    private String pApprovalUnit;
    @SecretField
    private Date pConstructionStart;
    @SecretField
    private Date pConstructionEnd;
    @SecretField
    private String pCheckType;
    @SecretField
    private String pProgressSituation;
    @SecretField
    private String pRecordNumber;

    @SecretField
    private String pCategory;
    @SecretField
    private String pEconomicFile;
    @SecretField
    private String pProposalFile;
    @SecretField
    private String pReportFile;
    @SecretField
    private String pInvCategory;
    @SecretField
    private Integer pAdvanceFund;
    @SecretField
    private Integer pCorporateRisk;
    @SecretField
    private String pSafetyFiling;
    @SecretField
    private String cplId;
    @SecretField
    private String pApplyName;
    @SecretField
    private BigDecimal pFiscalBudget;
    @SecretField
    private String teId;
    @SecretField
    private String pCampus;
    @SecretField
    private String pAuditUnit;
    @SecretField
    private String pCostUnit;
    @SecretField
    private String pOpinionFile;
    @SecretField
    private String pPlanFile;
    @SecretField
    private String pFeasibilityFile;
    @SecretField
    private String pApproveFile;
    @SecretField
    private BigDecimal pSbgjgzfAmt;
    @SecretField
    private BigDecimal pGcjsqtAmt;
    @SecretField
    private BigDecimal pYbAmt;
    @SecretField
    private String pConstructionFile;
    @SecretField
    private String pSbgjgzfFile;
    @SecretField
    private String pGcjsqtFile;
    @SecretField
    private String pYbFile;
    @SecretField
    private Float pRate;
    @SecretField
    private Float pWeight;
    @SecretField
    private Float pLength;
    @SecretField
    private Float pWidth;

    /**
     * 总项目规模
     */
    @SecretField
    private String totalBuildingScale;
    //P_Contact 联系人
    @SecretField
    private String pContact;
    @SecretField
    private String pContactPhone;
    @SecretField
    //P_Repair_Project 修缮项目所涉及的专项审核
    private String pRepairProject;
    @SecretField
    //P_Project_Basis 立项依据
    private String pProjectBasis;
    //P_Content 实施内容
    @SecretField
    private String pContent;
    //工程类别
    @SecretField
    private String pSecType;
    //经费来源
    @SecretField
    private String pFundsSources;
    //省市区县
    @SecretField
    private String pAddressDetail;
    //初设批复文号
    @ClassInfo(title = "初设批复文号",type="string",value = "pInitialApprovalNumber")
    @SecretField
    private String pInitialApprovalNumber;
    //初设批复金额
    @SecretField
    private BigDecimal pInitialApprovalAmount;
    //结算审计报告出具时间
    @SecretField
    private Date pSettlementAuditReportDate;
    //结算审计审定金额
    @SecretField
    private BigDecimal pSettlementAuditAmount;
    /**
     * 项目授权类型(内部用户)
     * null或者1正常鉴权
     * 2 项目完全按项目组织机构鉴权
     */
    @SecretField
    private Integer pAuthType;
    //立项形式 01可研批复、02投资备案证、03其他
    @SecretField
    private String pProjectFormType;
    //项目代码
    @SecretField
    private String pProjectNumber;
    //批复投资估算
    @SecretField
    private BigDecimal pApprovalInvestmentAmount;
    //可研申报单位
    @SecretField
    private String pCanYanApplyUnit;
    //可研审批单位
    @SecretField
    private String pCanYanApprovalUnit;
    //备案文号
    @SecretField
    private String pRecordNum;
    //备案时间
    @SecretField
    private Date pRecordDate;
    //备案文件Ids
    @SecretField
    private String pRecordFile;
    //备案总投资
    @SecretField
    private BigDecimal pRecordAmount;
    //项目法人单位
    @SecretField
    private String pLegalUnit;
    //备案证核发单位
    @SecretField
    private String pRecordIssueUnit;
    //立项文件上传
    @SecretField
    private String pApplyFile;
    //立项日期
    @SecretField
    private Date pApplyDate;
    //建设年限
    @SecretField
    private String pBuildYear;
    //投资估算
    @SecretField
    private BigDecimal pApprovalAmount;
    //建设形式
    @SecretField
    private String pBuildType;
    //专业分类
    @SecretField
    private String pProfessionalType;
    //移交接收单位
    @SecretField
    private String pTransferUnit;
    //项目经理
    @SecretField
    private String pProjectManager;
    //项目分管领导
    @SecretField
    private String pProjectLeader;
    //工程部经办人
    @SecretField
    private String pEngineer;
    //工程部分管领导
    @SecretField
    private String pEngineerLeader;
    //东华项目编号
    @SecretField
    private String pDhCode;

    //土地费
    @SecretField
    private BigDecimal pTdAmount;

    //建设期贷款利息
    @SecretField
    private BigDecimal pJsqdkLx;
    //项目法人
    @SecretField
    private String pLegalPerson;

    //    综合计划部联系人
    @SecretField
    private String comprehensivePlanDepartmentContactUserId;
    @SecretField
    private String comprehensivePlanDepartmentContactUserName;
    //    办公室联系人
    @SecretField
    private String officeContactUserId;
    @SecretField
    private String officeContactUserName;
    //    工程技术部联系人
    @SecretField
    private String comprehensiveTechnologyDepartmentContactUserId;
    @SecretField
    private String comprehensiveTechnologyDepartmentContactUserName;
    //    项目管理部联系人
    @SecretField
    private String pmDepartmentContactUserId;
    @SecretField
    private String pmDepartmentContactUserName;
    @SecretField
    private String pSectionStatus;

    @SecretField
    private String prCommitteeProjectCode;
    @SecretField
    private String prCommitteeApprovaDocument;
    @SecretField
    private String prCityLeaderMeetingMinutes;
    @SecretField
    private String prCityLeaderInstructions;
    @SecretField
    private String prOther;

    // 项目经理
    @SecretField
    private String projectManger;

    // 项目分管领导
    @SecretField
    private String projectLeader;
    @SecretField
    private String isGroupInvestment;
    @SecretField
    private String projectNature;
    @SecretField
    private String projectInvestmentCategory;
    @SecretField
    private String projectCategoryDetails;
    @SecretField
    private String projectOwner;
    @SecretField
    private String isHistory;
    @SecretField
    private String projectCategory;
    @SecretField
    private String projectInitiatingUnit;

    @SecretField
    private String pInvestmentConstituteRemark;

    @SecretField
    private String pItemTimeNew;
    @SecretField
    private String pFeasibilityTitle;
    @SecretField
    private String pFeasibilityTime;
    @SecretField
    private String pApproveBudgetName;
    @SecretField
    private String pApproveBudgetDateNew;
    @SecretField
    private String pConstructionTime;
    @SecretField
    private String pConstructionLandTime;
    @SecretField
    private String pOpinionsOnTime;
    @SecretField
    private String pLandUseTime;
    @SecretField
    private String pEngineeringConstructionCode;
    @SecretField
    private String pEngineeringConstructionTime;

    @SecretField
    private String pDepartment;

    @SecretField
    private String pInvestmentConstitute;

    @SecretField
    private String pprId;

    @SecretField
    private String pImageProgress;
    private String sign;

    public String getpImageProgress() {
        return pImageProgress;
    }

    public void setpImageProgress(String pImageProgress) {
        this.pImageProgress = pImageProgress;
    }

    public String getPprId() {
        return pprId;
    }

    public void setPprId(String pprId) {
        this.pprId = pprId;
    }

    public String getpInvestmentConstitute() {
        return pInvestmentConstitute;
    }

    public void setpInvestmentConstitute(String pInvestmentConstitute) {
        this.pInvestmentConstitute = pInvestmentConstitute;
    }

    public String getpDepartment() {
        return pDepartment;
    }

    public void setpDepartment(String pDepartment) {
        this.pDepartment = pDepartment;
    }

    public String getpItemTimeNew() {
        return pItemTimeNew;
    }

    public void setpItemTimeNew(String pItemTimeNew) {
        this.pItemTimeNew = pItemTimeNew;
    }

    public String getpFeasibilityTitle() {
        return pFeasibilityTitle;
    }

    public void setpFeasibilityTitle(String pFeasibilityTitle) {
        this.pFeasibilityTitle = pFeasibilityTitle;
    }

    public String getpFeasibilityTime() {
        return pFeasibilityTime;
    }

    public void setpFeasibilityTime(String pFeasibilityTime) {
        this.pFeasibilityTime = pFeasibilityTime;
    }

    public String getpApproveBudgetName() {
        return pApproveBudgetName;
    }

    public void setpApproveBudgetName(String pApproveBudgetName) {
        this.pApproveBudgetName = pApproveBudgetName;
    }

    public String getpApproveBudgetDateNew() {
        return pApproveBudgetDateNew;
    }

    public void setpApproveBudgetDateNew(String pApproveBudgetDateNew) {
        this.pApproveBudgetDateNew = pApproveBudgetDateNew;
    }

    public String getpConstructionTime() {
        return pConstructionTime;
    }

    public void setpConstructionTime(String pConstructionTime) {
        this.pConstructionTime = pConstructionTime;
    }

    public String getpConstructionLandTime() {
        return pConstructionLandTime;
    }

    public void setpConstructionLandTime(String pConstructionLandTime) {
        this.pConstructionLandTime = pConstructionLandTime;
    }

    public String getpOpinionsOnTime() {
        return pOpinionsOnTime;
    }

    public void setpOpinionsOnTime(String pOpinionsOnTime) {
        this.pOpinionsOnTime = pOpinionsOnTime;
    }

    public String getpLandUseTime() {
        return pLandUseTime;
    }

    public void setpLandUseTime(String pLandUseTime) {
        this.pLandUseTime = pLandUseTime;
    }

    public String getpEngineeringConstructionCode() {
        return pEngineeringConstructionCode;
    }

    public void setpEngineeringConstructionCode(String pEngineeringConstructionCode) {
        this.pEngineeringConstructionCode = pEngineeringConstructionCode;
    }

    public String getpEngineeringConstructionTime() {
        return pEngineeringConstructionTime;
    }

    public void setpEngineeringConstructionTime(String pEngineeringConstructionTime) {
        this.pEngineeringConstructionTime = pEngineeringConstructionTime;
    }

    public String getpInvestmentConstituteRemark() {
        return pInvestmentConstituteRemark;
    }

    public void setpInvestmentConstituteRemark(String pInvestmentConstituteRemark) {
        this.pInvestmentConstituteRemark = pInvestmentConstituteRemark;
    }

    public String getProjectManger() {
        return projectManger;
    }

    public void setProjectManger(String projectManger) {
        this.projectManger = projectManger;
    }

    public String getProjectLeader() {
        return projectLeader;
    }

    public void setProjectLeader(String projectLeader) {
        this.projectLeader = projectLeader;
    }
    public String getPrCommitteeProjectCode() {
        return prCommitteeProjectCode;
    }

    public void setPrCommitteeProjectCode(String prCommitteeProjectCode) {
        this.prCommitteeProjectCode = prCommitteeProjectCode;
    }

    public String getPrCommitteeApprovaDocument() {
        return prCommitteeApprovaDocument;
    }

    public void setPrCommitteeApprovaDocument(String prCommitteeApprovaDocument) {
        this.prCommitteeApprovaDocument = prCommitteeApprovaDocument;
    }

    public String getPrCityLeaderMeetingMinutes() {
        return prCityLeaderMeetingMinutes;
    }

    public void setPrCityLeaderMeetingMinutes(String prCityLeaderMeetingMinutes) {
        this.prCityLeaderMeetingMinutes = prCityLeaderMeetingMinutes;
    }

    public String getPrCityLeaderInstructions() {
        return prCityLeaderInstructions;
    }

    public void setPrCityLeaderInstructions(String prCityLeaderInstructions) {
        this.prCityLeaderInstructions = prCityLeaderInstructions;
    }

    public String getPrOther() {
        return prOther;
    }

    public void setPrOther(String prOther) {
        this.prOther = prOther;
    }

    public String getComprehensivePlanDepartmentContactUserId() {
        return comprehensivePlanDepartmentContactUserId;
    }

    public void setComprehensivePlanDepartmentContactUserId(String comprehensivePlanDepartmentContactUserId) {
        this.comprehensivePlanDepartmentContactUserId = comprehensivePlanDepartmentContactUserId;
    }

    public String getComprehensivePlanDepartmentContactUserName() {
        return comprehensivePlanDepartmentContactUserName;
    }

    public void setComprehensivePlanDepartmentContactUserName(String comprehensivePlanDepartmentContactUserName) {
        this.comprehensivePlanDepartmentContactUserName = comprehensivePlanDepartmentContactUserName;
    }

    public String getOfficeContactUserId() {
        return officeContactUserId;
    }

    public void setOfficeContactUserId(String officeContactUserId) {
        this.officeContactUserId = officeContactUserId;
    }

    public String getOfficeContactUserName() {
        return officeContactUserName;
    }

    public void setOfficeContactUserName(String officeContactUserName) {
        this.officeContactUserName = officeContactUserName;
    }

    public String getComprehensiveTechnologyDepartmentContactUserId() {
        return comprehensiveTechnologyDepartmentContactUserId;
    }

    public void setComprehensiveTechnologyDepartmentContactUserId(String comprehensiveTechnologyDepartmentContactUserId) {
        this.comprehensiveTechnologyDepartmentContactUserId = comprehensiveTechnologyDepartmentContactUserId;
    }

    public String getComprehensiveTechnologyDepartmentContactUserName() {
        return comprehensiveTechnologyDepartmentContactUserName;
    }

    public void setComprehensiveTechnologyDepartmentContactUserName(String comprehensiveTechnologyDepartmentContactUserName) {
        this.comprehensiveTechnologyDepartmentContactUserName = comprehensiveTechnologyDepartmentContactUserName;
    }

    public String getPmDepartmentContactUserId() {
        return pmDepartmentContactUserId;
    }

    public void setPmDepartmentContactUserId(String pmDepartmentContactUserId) {
        this.pmDepartmentContactUserId = pmDepartmentContactUserId;
    }

    public String getPmDepartmentContactUserName() {
        return pmDepartmentContactUserName;
    }

    public void setPmDepartmentContactUserName(String pmDepartmentContactUserName) {
        this.pmDepartmentContactUserName = pmDepartmentContactUserName;
    }

    public String getpDhCode() {
        return pDhCode;
    }

    public void setpDhCode(String pDhCode) {
        this.pDhCode = pDhCode;
    }

    public String getTotalBuildingScale() {
        return totalBuildingScale;
    }

    public void setTotalBuildingScale(String totalBuildingScale) {
        this.totalBuildingScale = totalBuildingScale;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getPrId() {
        return prId;
    }

    public void setPrId(String prId) {
        this.prId = prId;
    }

    public String getpCode() {
        return pCode;
    }

    public void setpCode(String pCode) {
        this.pCode = pCode;
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

    public String getpProjectAbbreviation() {
        return pProjectAbbreviation;
    }

    public void setpProjectAbbreviation(String pProjectAbbreviation) {
        this.pProjectAbbreviation = pProjectAbbreviation;
    }

    public String getpOriginalName() {
        return pOriginalName;
    }

    public void setpOriginalName(String pOriginalName) {
        this.pOriginalName = pOriginalName;
    }

    public String getpNameInitials() {
        return pNameInitials;
    }

    public void setpNameInitials(String pNameInitials) {
        this.pNameInitials = pNameInitials;
    }

    public String getpProjectCode() {
        return pProjectCode;
    }

    public void setpProjectCode(String pProjectCode) {
        this.pProjectCode = pProjectCode;
    }

    public String getpAddress() {
        return pAddress;
    }

    public void setpAddress(String pAddress) {
        this.pAddress = pAddress;
    }

    public String getpRegion() {
        return pRegion;
    }

    public void setpRegion(String pRegion) {
        this.pRegion = pRegion;
    }

    public Float getpConstructionArea() {
        return pConstructionArea;
    }

    public void setpConstructionArea(Float pConstructionArea) {
        this.pConstructionArea = pConstructionArea;
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

    public String getpOverview() {
        return pOverview;
    }

    public void setpOverview(String pOverview) {
        this.pOverview = pOverview;
    }

    public Float getpVolumeRate() {
        return pVolumeRate;
    }

    public void setpVolumeRate(Float pVolumeRate) {
        this.pVolumeRate = pVolumeRate;
    }

    public Float getpLandArea() {
        return pLandArea;
    }

    public void setpLandArea(Float pLandArea) {
        this.pLandArea = pLandArea;
    }

    public Integer getpSeat() {
        return pSeat;
    }

    public void setpSeat(Integer pSeat) {
        this.pSeat = pSeat;
    }

    public Integer getpNumberOfElevators() {
        return pNumberOfElevators;
    }

    public void setpNumberOfElevators(Integer pNumberOfElevators) {
        this.pNumberOfElevators = pNumberOfElevators;
    }

    public Float getpMaxHeight() {
        return pMaxHeight;
    }

    public void setpMaxHeight(Float pMaxHeight) {
        this.pMaxHeight = pMaxHeight;
    }

    public Integer getpParkingNumber() {
        return pParkingNumber;
    }

    public void setpParkingNumber(Integer pParkingNumber) {
        this.pParkingNumber = pParkingNumber;
    }

    public Integer getpBuildingsNumber() {
        return pBuildingsNumber;
    }

    public void setpBuildingsNumber(Integer pBuildingsNumber) {
        this.pBuildingsNumber = pBuildingsNumber;
    }

    public Integer getpDesignLife() {
        return pDesignLife;
    }

    public void setpDesignLife(Integer pDesignLife) {
        this.pDesignLife = pDesignLife;
    }

    public String getpSuperStructure() {
        return pSuperStructure;
    }

    public void setpSuperStructure(String pSuperStructure) {
        this.pSuperStructure = pSuperStructure;
    }

    public String getpBasicStructure() {
        return pBasicStructure;
    }

    public void setpBasicStructure(String pBasicStructure) {
        this.pBasicStructure = pBasicStructure;
    }

    public Integer getpGroundFloors() {
        return pGroundFloors;
    }

    public void setpGroundFloors(Integer pGroundFloors) {
        this.pGroundFloors = pGroundFloors;
    }

    public Integer getpUnderGroundFloors() {
        return pUnderGroundFloors;
    }

    public void setpUnderGroundFloors(Integer pUnderGroundFloors) {
        this.pUnderGroundFloors = pUnderGroundFloors;
    }

    public String getpIsGas() {
        return pIsGas;
    }

    public void setpIsGas(String pIsGas) {
        this.pIsGas = pIsGas;
    }

    public Float getpBasementArea() {
        return pBasementArea;
    }

    public void setpBasementArea(Float pBasementArea) {
        this.pBasementArea = pBasementArea;
    }

    public String getpIsAirDefense() {
        return pIsAirDefense;
    }

    public void setpIsAirDefense(String pIsAirDefense) {
        this.pIsAirDefense = pIsAirDefense;
    }

    public Float getpAirDefenseArea() {
        return pAirDefenseArea;
    }

    public void setpAirDefenseArea(Float pAirDefenseArea) {
        this.pAirDefenseArea = pAirDefenseArea;
    }

    public String getpType() {
        return pType;
    }

    public void setpType(String pType) {
        this.pType = pType;
    }

    public String getpMajorType() {
        return pMajorType;
    }

    public void setpMajorType(String pMajorType) {
        this.pMajorType = pMajorType;
    }

    public String getpConstructionUnit() {
        return pConstructionUnit;
    }

    public void setpConstructionUnit(String pConstructionUnit) {
        this.pConstructionUnit = pConstructionUnit;
    }

    public String getpCompetentDepartment() {
        return pCompetentDepartment;
    }

    public void setpCompetentDepartment(String pCompetentDepartment) {
        this.pCompetentDepartment = pCompetentDepartment;
    }

    public String getpProjectDirector() {
        return pProjectDirector;
    }

    public void setpProjectDirector(String pProjectDirector) {
        this.pProjectDirector = pProjectDirector;
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

    public Integer getpTotalDuration() {
        return pTotalDuration;
    }

    public void setpTotalDuration(Integer pTotalDuration) {
        this.pTotalDuration = pTotalDuration;
    }

    public Date getpActualStartTime() {
        return pActualStartTime;
    }

    public void setpActualStartTime(Date pActualStartTime) {
        this.pActualStartTime = pActualStartTime;
    }

    public Date getpActualCompleteDate() {
        return pActualCompleteDate;
    }

    public void setpActualCompleteDate(Date pActualCompleteDate) {
        this.pActualCompleteDate = pActualCompleteDate;
    }

    public String getpActualTotalDuration() {
        return pActualTotalDuration;
    }

    public void setpActualTotalDuration(String pActualTotalDuration) {
        this.pActualTotalDuration = pActualTotalDuration;
    }

    public Date getpInterventionDate() {
        return pInterventionDate;
    }

    public void setpInterventionDate(Date pInterventionDate) {
        this.pInterventionDate = pInterventionDate;
    }

    public Integer getpInterventionYear() {
        return pInterventionYear;
    }

    public void setpInterventionYear(Integer pInterventionYear) {
        this.pInterventionYear = pInterventionYear;
    }

    public Date getpReceiptTime() {
        return pReceiptTime;
    }

    public void setpReceiptTime(Date pReceiptTime) {
        this.pReceiptTime = pReceiptTime;
    }

    public Date getpAcceptanceRecordDate() {
        return pAcceptanceRecordDate;
    }

    public void setpAcceptanceRecordDate(Date pAcceptanceRecordDate) {
        this.pAcceptanceRecordDate = pAcceptanceRecordDate;
    }

    public Date getpHandoverDate() {
        return pHandoverDate;
    }

    public void setpHandoverDate(Date pHandoverDate) {
        this.pHandoverDate = pHandoverDate;
    }

    public String getpIsProjectSummary() {
        return pIsProjectSummary;
    }

    public void setpIsProjectSummary(String pIsProjectSummary) {
        this.pIsProjectSummary = pIsProjectSummary;
    }

    public String getpUseUnit() {
        return pUseUnit;
    }

    public void setpUseUnit(String pUseUnit) {
        this.pUseUnit = pUseUnit;
    }

    public String getpSourcesFunds() {
        return pSourcesFunds;
    }

    public void setpSourcesFunds(String pSourcesFunds) {
        this.pSourcesFunds = pSourcesFunds;
    }

    public BigDecimal getpCalculateRoughlyAmount() {
        return pCalculateRoughlyAmount;
    }

    public void setpCalculateRoughlyAmount(BigDecimal pCalculateRoughlyAmount) {
        this.pCalculateRoughlyAmount = pCalculateRoughlyAmount;
    }

    public BigDecimal getpEstimateAmount() {
        return pEstimateAmount;
    }

    public void setpEstimateAmount(BigDecimal pEstimateAmount) {
        this.pEstimateAmount = pEstimateAmount;
    }

    public BigDecimal getpApproveBudget() {
        return pApproveBudget;
    }

    public void setpApproveBudget(BigDecimal pApproveBudget) {
        this.pApproveBudget = pApproveBudget;
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

    public BigDecimal getpFinalAccounts() {
        return pFinalAccounts;
    }

    public void setpFinalAccounts(BigDecimal pFinalAccounts) {
        this.pFinalAccounts = pFinalAccounts;
    }

    public Date getpFinalAccountsReportDate() {
        return pFinalAccountsReportDate;
    }

    public void setpFinalAccountsReportDate(Date pFinalAccountsReportDate) {
        this.pFinalAccountsReportDate = pFinalAccountsReportDate;
    }

    public String getpConstructionProjectPlanningPermitCode() {
        return pConstructionProjectPlanningPermitCode;
    }

    public void setpConstructionProjectPlanningPermitCode(String pConstructionProjectPlanningPermitCode) {
        this.pConstructionProjectPlanningPermitCode = pConstructionProjectPlanningPermitCode;
    }

    public String getpConstructionLandPlanningPermitcode() {
        return pConstructionLandPlanningPermitcode;
    }

    public void setpConstructionLandPlanningPermitcode(String pConstructionLandPlanningPermitcode) {
        this.pConstructionLandPlanningPermitcode = pConstructionLandPlanningPermitcode;
    }

    public String getpOpinionsOnSiteSelectionForConstructionProjectsCode() {
        return pOpinionsOnSiteSelectionForConstructionProjectsCode;
    }

    public void setpOpinionsOnSiteSelectionForConstructionProjectsCode(String pOpinionsOnSiteSelectionForConstructionProjectsCode) {
        this.pOpinionsOnSiteSelectionForConstructionProjectsCode = pOpinionsOnSiteSelectionForConstructionProjectsCode;
    }

    public String getpLandUseRightAssignmentContractCode() {
        return pLandUseRightAssignmentContractCode;
    }

    public void setpLandUseRightAssignmentContractCode(String pLandUseRightAssignmentContractCode) {
        this.pLandUseRightAssignmentContractCode = pLandUseRightAssignmentContractCode;
    }

    public String getpFeasibilityStudyApprovalCode() {
        return pFeasibilityStudyApprovalCode;
    }

    public void setpFeasibilityStudyApprovalCode(String pFeasibilityStudyApprovalCode) {
        this.pFeasibilityStudyApprovalCode = pFeasibilityStudyApprovalCode;
    }

    public String getpEiaApprovalCode() {
        return pEiaApprovalCode;
    }

    public void setpEiaApprovalCode(String pEiaApprovalCode) {
        this.pEiaApprovalCode = pEiaApprovalCode;
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

    public String getpDesignGrade() {
        return pDesignGrade;
    }

    public void setpDesignGrade(String pDesignGrade) {
        this.pDesignGrade = pDesignGrade;
    }

    public String getpIsPrefabricated() {
        return pIsPrefabricated;
    }

    public void setpIsPrefabricated(String pIsPrefabricated) {
        this.pIsPrefabricated = pIsPrefabricated;
    }

    public String getpFourNewTechnologies() {
        return pFourNewTechnologies;
    }

    public void setpFourNewTechnologies(String pFourNewTechnologies) {
        this.pFourNewTechnologies = pFourNewTechnologies;
    }

    public String getpBimTechnology() {
        return pBimTechnology;
    }

    public void setpBimTechnology(String pBimTechnology) {
        this.pBimTechnology = pBimTechnology;
    }

    public String getpIsEpc() {
        return pIsEpc;
    }

    public void setpIsEpc(String pIsEpc) {
        this.pIsEpc = pIsEpc;
    }

    public String getpIsSpongeCity() {
        return pIsSpongeCity;
    }

    public void setpIsSpongeCity(String pIsSpongeCity) {
        this.pIsSpongeCity = pIsSpongeCity;
    }

    public String getpIsRegularMode() {
        return pIsRegularMode;
    }

    public void setpIsRegularMode(String pIsRegularMode) {
        this.pIsRegularMode = pIsRegularMode;
    }

    public String getpIsDesignAndConstructionIntegration() {
        return pIsDesignAndConstructionIntegration;
    }

    public void setpIsDesignAndConstructionIntegration(String pIsDesignAndConstructionIntegration) {
        this.pIsDesignAndConstructionIntegration = pIsDesignAndConstructionIntegration;
    }

    public String getpDesignAndConstructionIntegrationUnit() {
        return pDesignAndConstructionIntegrationUnit;
    }

    public void setpDesignAndConstructionIntegrationUnit(String pDesignAndConstructionIntegrationUnit) {
        this.pDesignAndConstructionIntegrationUnit = pDesignAndConstructionIntegrationUnit;
    }

    public String getpIsWholeProcessManagement() {
        return pIsWholeProcessManagement;
    }

    public void setpIsWholeProcessManagement(String pIsWholeProcessManagement) {
        this.pIsWholeProcessManagement = pIsWholeProcessManagement;
    }

    public String getpWholeProcessManagementUnit() {
        return pWholeProcessManagementUnit;
    }

    public void setpWholeProcessManagementUnit(String pWholeProcessManagementUnit) {
        this.pWholeProcessManagementUnit = pWholeProcessManagementUnit;
    }

    public String getpConstructionCooperation() {
        return pConstructionCooperation;
    }

    public void setpConstructionCooperation(String pConstructionCooperation) {
        this.pConstructionCooperation = pConstructionCooperation;
    }

    public String getpStatus() {
        return pStatus;
    }

    public void setpStatus(String pStatus) {
        this.pStatus = pStatus;
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

    public String getpUpdateUserId() {
        return pUpdateUserId;
    }

    public void setpUpdateUserId(String pUpdateUserId) {
        this.pUpdateUserId = pUpdateUserId;
    }

    public Date getpUpdateTime() {
        return pUpdateTime;
    }

    public void setpUpdateTime(Date pUpdateTime) {
        this.pUpdateTime = pUpdateTime;
    }

    public String getpSerialNumber() {
        return pSerialNumber;
    }

    public void setpSerialNumber(String pSerialNumber) {
        this.pSerialNumber = pSerialNumber;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }


    public BigDecimal getpRaiseFunds() {
        return pRaiseFunds;
    }

    public void setpRaiseFunds(BigDecimal pRaiseFunds) {
        this.pRaiseFunds = pRaiseFunds;
    }

    public String getpIntroduction() {
        return pIntroduction;
    }

    public void setpIntroduction(String pIntroduction) {
        this.pIntroduction = pIntroduction;
    }

    public String getpLandWarrant() {
        return pLandWarrant;
    }

    public void setpLandWarrant(String pLandWarrant) {
        this.pLandWarrant = pLandWarrant;
    }

    public Integer getpParkingNumberUn() {
        return pParkingNumberUn;
    }

    public void setpParkingNumberUn(Integer pParkingNumberUn) {
        this.pParkingNumberUn = pParkingNumberUn;
    }

    public Integer getpParkingNumberOn() {
        return pParkingNumberOn;
    }

    public void setpParkingNumberOn(Integer pParkingNumberOn) {
        this.pParkingNumberOn = pParkingNumberOn;
    }

    public String getpItemNumber() {
        return pItemNumber;
    }

    public void setpItemNumber(String pItemNumber) {
        this.pItemNumber = pItemNumber;
    }

    public String getpPreItemNumber() {
        return pPreItemNumber;
    }

    public void setpPreItemNumber(String pPreItemNumber) {
        this.pPreItemNumber = pPreItemNumber;
    }

    public Date getpPreItemTime() {
        return pPreItemTime;
    }

    public void setpPreItemTime(Date pPreItemTime) {
        this.pPreItemTime = pPreItemTime;
    }

    public Date getpItemTime() {
        return pItemTime;
    }

    public void setpItemTime(Date pItemTime) {
        this.pItemTime = pItemTime;
    }

    public Float getpSiteArea() {
        return pSiteArea;
    }

    public void setpSiteArea(Float pSiteArea) {
        this.pSiteArea = pSiteArea;
    }

    public String getpCmMode() {
        return pCmMode;
    }

    public void setpCmMode(String pCmMode) {
        this.pCmMode = pCmMode;
    }

    public String getpAssignmentFile() {
        return pAssignmentFile;
    }

    public void setpAssignmentFile(String pAssignmentFile) {
        this.pAssignmentFile = pAssignmentFile;
    }

    public String getpNameHistory() {
        return pNameHistory;
    }

    public void setpNameHistory(String pNameHistory) {
        this.pNameHistory = pNameHistory;
    }

    public String getpCommitteeApprovaDocument() {
        return pCommitteeApprovaDocument;
    }

    public void setpCommitteeApprovaDocument(String pCommitteeApprovaDocument) {
        this.pCommitteeApprovaDocument = pCommitteeApprovaDocument;
    }

    public String getpCityLeaderMeetingMinutes() {
        return pCityLeaderMeetingMinutes;
    }

    public void setpCityLeaderMeetingMinutes(String pCityLeaderMeetingMinutes) {
        this.pCityLeaderMeetingMinutes = pCityLeaderMeetingMinutes;
    }

    public String getpCityLeaderInstructions() {
        return pCityLeaderInstructions;
    }

    public void setpCityLeaderInstructions(String pCityLeaderInstructions) {
        this.pCityLeaderInstructions = pCityLeaderInstructions;
    }

    public String getpOther() {
        return pOther;
    }

    public void setpOther(String pOther) {
        this.pOther = pOther;
    }


    public String getpNature() {
        return pNature;
    }

    public void setpNature(String pNature) {
        this.pNature = pNature;
    }

    public BigDecimal getpReportedAmount() {
        return pReportedAmount;
    }

    public void setpReportedAmount(BigDecimal pReportedAmount) {
        this.pReportedAmount = pReportedAmount;
    }

    public String getpApprovaDocument() {
        return pApprovaDocument;
    }

    public void setpApprovaDocument(String pApprovaDocument) {
        this.pApprovaDocument = pApprovaDocument;
    }

    public String getpLeaderMeetingMinutes() {
        return pLeaderMeetingMinutes;
    }

    public void setpLeaderMeetingMinutes(String pLeaderMeetingMinutes) {
        this.pLeaderMeetingMinutes = pLeaderMeetingMinutes;
    }

    public String getpLeaderInstructions() {
        return pLeaderInstructions;
    }

    public void setpLeaderInstructions(String pLeaderInstructions) {
        this.pLeaderInstructions = pLeaderInstructions;
    }

    public String getpProjectDirectorTel() {
        return pProjectDirectorTel;
    }

    public void setpProjectDirectorTel(String pProjectDirectorTel) {
        this.pProjectDirectorTel = pProjectDirectorTel;
    }

    public BigDecimal getpConstructionCost() {
        return pConstructionCost;
    }

    public void setpConstructionCost(BigDecimal pConstructionCost) {
        this.pConstructionCost = pConstructionCost;
    }

    public BigDecimal getpContractPrice() {
        return pContractPrice;
    }

    public void setpContractPrice(BigDecimal pContractPrice) {
        this.pContractPrice = pContractPrice;
    }

    public BigDecimal getpPurchasePrice() {
        return pPurchasePrice;
    }

    public void setpPurchasePrice(BigDecimal pPurchasePrice) {
        this.pPurchasePrice = pPurchasePrice;
    }


    public String getpAssignmentNo() {
        return pAssignmentNo;
    }

    public void setpAssignmentNo(String pAssignmentNo) {
        this.pAssignmentNo = pAssignmentNo;
    }

    public String getpEstimateFile() {
        return pEstimateFile;
    }

    public void setpEstimateFile(String pEstimateFile) {
        this.pEstimateFile = pEstimateFile;
    }

    public String getpScaleDescribe() {
        return pScaleDescribe;
    }

    public void setpScaleDescribe(String pScaleDescribe) {
        this.pScaleDescribe = pScaleDescribe;
    }


    public Boolean getpBim() {
        return pBim;
    }

    public void setpBim(Boolean pBim) {
        this.pBim = pBim;
    }

    public Boolean getpSmartSite() {
        return pSmartSite;
    }

    public void setpSmartSite(Boolean pSmartSite) {
        this.pSmartSite = pSmartSite;
    }

    public Boolean getpManagementSystem() {
        return pManagementSystem;
    }

    public void setpManagementSystem(Boolean pManagementSystem) {
        this.pManagementSystem = pManagementSystem;
    }

    public Boolean getpNewTechnology() {
        return pNewTechnology;
    }

    public void setpNewTechnology(Boolean pNewTechnology) {
        this.pNewTechnology = pNewTechnology;
    }

    public Boolean getpNewMaterial() {
        return pNewMaterial;
    }

    public void setpNewMaterial(Boolean pNewMaterial) {
        this.pNewMaterial = pNewMaterial;
    }

    public Boolean getpNewCraft() {
        return pNewCraft;
    }

    public void setpNewCraft(Boolean pNewCraft) {
        this.pNewCraft = pNewCraft;
    }

    public Boolean getpSaveEnergy() {
        return pSaveEnergy;
    }

    public void setpSaveEnergy(Boolean pSaveEnergy) {
        this.pSaveEnergy = pSaveEnergy;
    }

    public Boolean getpSaveLand() {
        return pSaveLand;
    }

    public void setpSaveLand(Boolean pSaveLand) {
        this.pSaveLand = pSaveLand;
    }

    public Boolean getpSaveWater() {
        return pSaveWater;
    }

    public void setpSaveWater(Boolean pSaveWater) {
        this.pSaveWater = pSaveWater;
    }

    public Boolean getpSaveMaterial() {
        return pSaveMaterial;
    }

    public void setpSaveMaterial(Boolean pSaveMaterial) {
        this.pSaveMaterial = pSaveMaterial;
    }

    public Boolean getpDigitalConstruction() {
        return pDigitalConstruction;
    }

    public void setpDigitalConstruction(Boolean pDigitalConstruction) {
        this.pDigitalConstruction = pDigitalConstruction;
    }

    public String getpGreenConstruction() {
        return pGreenConstruction;
    }

    public void setpGreenConstruction(String pGreenConstruction) {
        this.pGreenConstruction = pGreenConstruction;
    }

    public Boolean getpCentralizedConstruction() {
        return pCentralizedConstruction;
    }

    public void setpCentralizedConstruction(Boolean pCentralizedConstruction) {
        this.pCentralizedConstruction = pCentralizedConstruction;
    }

    public String getpContractingMode() {
        return pContractingMode;
    }

    public void setpContractingMode(String pContractingMode) {
        this.pContractingMode = pContractingMode;
    }

    public Boolean getpProcessConsulting() {
        return pProcessConsulting;
    }

    public void setpProcessConsulting(Boolean pProcessConsulting) {
        this.pProcessConsulting = pProcessConsulting;
    }

    public Boolean getpEngineerResponsible() {
        return pEngineerResponsible;
    }

    public void setpEngineerResponsible(Boolean pEngineerResponsible) {
        this.pEngineerResponsible = pEngineerResponsible;
    }

    public Boolean getpIsProjectApproval() {
        return pIsProjectApproval;
    }

    public void setpIsProjectApproval(Boolean pIsProjectApproval) {
        this.pIsProjectApproval = pIsProjectApproval;
    }

    public Float getpBuildingScale() {
        return pBuildingScale;
    }

    public void setpBuildingScale(Float pBuildingScale) {
        this.pBuildingScale = pBuildingScale;
    }

    public String getpBuildingUnit() {
        return pBuildingUnit;
    }

    public void setpBuildingUnit(String pBuildingUnit) {
        this.pBuildingUnit = pBuildingUnit;
    }


    public String getpSuperviseType() {
        return pSuperviseType;
    }

    public void setpSuperviseType(String pSuperviseType) {
        this.pSuperviseType = pSuperviseType;
    }

    public String getpGroupType() {
        return pGroupType;
    }

    public void setpGroupType(String pGroupType) {
        this.pGroupType = pGroupType;
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

    public String getpRemarkPlanned() {
        return pRemarkPlanned;
    }

    public void setpRemarkPlanned(String pRemarkPlanned) {
        this.pRemarkPlanned = pRemarkPlanned;
    }

    public String getpPlanNumber() {
        return pPlanNumber;
    }

    public void setpPlanNumber(String pPlanNumber) {
        this.pPlanNumber = pPlanNumber;
    }

    public Date getpConsRelease() {
        return pConsRelease;
    }

    public void setpConsRelease(Date pConsRelease) {
        this.pConsRelease = pConsRelease;
    }

    public String getpImpCompany() {
        return pImpCompany;
    }

    public void setpImpCompany(String pImpCompany) {
        this.pImpCompany = pImpCompany;
    }

    public String getpImpState() {
        return pImpState;
    }

    public void setpImpState(String pImpState) {
        this.pImpState = pImpState;
    }

    public String getpApplyTilte() {
        return pApplyTilte;
    }

    public void setpApplyTilte(String pApplyTilte) {
        this.pApplyTilte = pApplyTilte;
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

    public String getpInvTilte() {
        return pInvTilte;
    }

    public void setpInvTilte(String pInvTilte) {
        this.pInvTilte = pInvTilte;
    }

    public String getpInvCode() {
        return pInvCode;
    }

    public void setpInvCode(String pInvCode) {
        this.pInvCode = pInvCode;
    }

    public Date getpInvDate() {
        return pInvDate;
    }

    public void setpInvDate(Date pInvDate) {
        this.pInvDate = pInvDate;
    }

    public String getpInvFile() {
        return pInvFile;
    }

    public void setpInvFile(String pInvFile) {
        this.pInvFile = pInvFile;
    }

    public String getpPassVideo() {
        return pPassVideo;
    }

    public void setpPassVideo(String pPassVideo) {
        this.pPassVideo = pPassVideo;
    }

    public String getpBuildModel() {
        return pBuildModel;
    }

    public void setpBuildModel(String pBuildModel) {
        this.pBuildModel = pBuildModel;
    }

    public Integer getpInterventionEndYear() {
        return pInterventionEndYear;
    }

    public void setpInterventionEndYear(Integer pInterventionEndYear) {
        this.pInterventionEndYear = pInterventionEndYear;
    }

    public String getpEachDistrict() {
        return pEachDistrict;
    }

    public void setpEachDistrict(String pEachDistrict) {
        this.pEachDistrict = pEachDistrict;
    }


    public String getpApprovalUnit() {
        return pApprovalUnit;
    }

    public void setpApprovalUnit(String pApprovalUnit) {
        this.pApprovalUnit = pApprovalUnit;
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

    public String getpCheckType() {
        return pCheckType;
    }

    public void setpCheckType(String pCheckType) {
        this.pCheckType = pCheckType;
    }

    public String getpProgressSituation() {
        return pProgressSituation;
    }

    public void setpProgressSituation(String pProgressSituation) {
        this.pProgressSituation = pProgressSituation;
    }

    public String getpRecordNumber() {
        return pRecordNumber;
    }

    public void setpRecordNumber(String pRecordNumber) {
        this.pRecordNumber = pRecordNumber;
    }

    public String getpCategory() {
        return pCategory;
    }

    public void setpCategory(String pCategory) {
        this.pCategory = pCategory;
    }

    public String getpEconomicFile() {
        return pEconomicFile;
    }

    public void setpEconomicFile(String pEconomicFile) {
        this.pEconomicFile = pEconomicFile;
    }

    public String getpProposalFile() {
        return pProposalFile;
    }

    public void setpProposalFile(String pProposalFile) {
        this.pProposalFile = pProposalFile;
    }

    public String getpReportFile() {
        return pReportFile;
    }

    public void setpReportFile(String pReportFile) {
        this.pReportFile = pReportFile;
    }

    public String getpInvCategory() {
        return pInvCategory;
    }

    public void setpInvCategory(String pInvCategory) {
        this.pInvCategory = pInvCategory;
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

    public String getCplId() {
        return cplId;
    }

    public void setCplId(String cplId) {
        this.cplId = cplId;
    }

    public String getpApplyName() {
        return pApplyName;
    }

    public void setpApplyName(String pApplyName) {
        this.pApplyName = pApplyName;
    }

    public BigDecimal getpFiscalBudget() {
        return pFiscalBudget;
    }

    public void setpFiscalBudget(BigDecimal pFiscalBudget) {
        this.pFiscalBudget = pFiscalBudget;
    }

    public String getTeId() {
        return teId;
    }

    public void setTeId(String teId) {
        this.teId = teId;
    }

    public String getpCampus() {
        return pCampus;
    }

    public void setpCampus(String pCampus) {
        this.pCampus = pCampus;
    }

    public String getpAuditUnit() {
        return pAuditUnit;
    }

    public void setpAuditUnit(String pAuditUnit) {
        this.pAuditUnit = pAuditUnit;
    }

    public String getpCostUnit() {
        return pCostUnit;
    }

    public void setpCostUnit(String pCostUnit) {
        this.pCostUnit = pCostUnit;
    }

    public String getpOpinionFile() {
        return pOpinionFile;
    }

    public void setpOpinionFile(String pOpinionFile) {
        this.pOpinionFile = pOpinionFile;
    }

    public String getpPlanFile() {
        return pPlanFile;
    }

    public void setpPlanFile(String pPlanFile) {
        this.pPlanFile = pPlanFile;
    }

    public String getpFeasibilityFile() {
        return pFeasibilityFile;
    }

    public void setpFeasibilityFile(String pFeasibilityFile) {
        this.pFeasibilityFile = pFeasibilityFile;
    }

    public String getpApproveFile() {
        return pApproveFile;
    }

    public void setpApproveFile(String pApproveFile) {
        this.pApproveFile = pApproveFile;
    }

    public BigDecimal getpSbgjgzfAmt() {
        return pSbgjgzfAmt;
    }

    public void setpSbgjgzfAmt(BigDecimal pSbgjgzfAmt) {
        this.pSbgjgzfAmt = pSbgjgzfAmt;
    }

    public BigDecimal getpGcjsqtAmt() {
        return pGcjsqtAmt;
    }

    public void setpGcjsqtAmt(BigDecimal pGcjsqtAmt) {
        this.pGcjsqtAmt = pGcjsqtAmt;
    }

    public BigDecimal getpYbAmt() {
        return pYbAmt;
    }

    public void setpYbAmt(BigDecimal pYbAmt) {
        this.pYbAmt = pYbAmt;
    }

    public String getpConstructionFile() {
        return pConstructionFile;
    }

    public void setpConstructionFile(String pConstructionFile) {
        this.pConstructionFile = pConstructionFile;
    }

    public String getpSbgjgzfFile() {
        return pSbgjgzfFile;
    }

    public void setpSbgjgzfFile(String pSbgjgzfFile) {
        this.pSbgjgzfFile = pSbgjgzfFile;
    }

    public String getpGcjsqtFile() {
        return pGcjsqtFile;
    }

    public void setpGcjsqtFile(String pGcjsqtFile) {
        this.pGcjsqtFile = pGcjsqtFile;
    }

    public String getpYbFile() {
        return pYbFile;
    }

    public void setpYbFile(String pYbFile) {
        this.pYbFile = pYbFile;
    }

    public Float getpRate() {
        return pRate;
    }

    public void setpRate(Float pRate) {
        this.pRate = pRate;
    }

    public Float getpWeight() {
        return pWeight;
    }

    public void setpWeight(Float pWeight) {
        this.pWeight = pWeight;
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

    public String getpContact() {
        return pContact;
    }

    public void setpContact(String pContact) {
        this.pContact = pContact;
    }

    public String getpContactPhone() {
        return pContactPhone;
    }

    public void setpContactPhone(String pContactPhone) {
        this.pContactPhone = pContactPhone;
    }

    public String getpRepairProject() {
        return pRepairProject;
    }

    public void setpRepairProject(String pRepairProject) {
        this.pRepairProject = pRepairProject;
    }

    public String getpProjectBasis() {
        return pProjectBasis;
    }

    public void setpProjectBasis(String pProjectBasis) {
        this.pProjectBasis = pProjectBasis;
    }

    public String getpContent() {
        return pContent;
    }

    public void setpContent(String pContent) {
        this.pContent = pContent;
    }

    public String getpSecType() {
        return pSecType;
    }

    public void setpSecType(String pSecType) {
        this.pSecType = pSecType;
    }

    public String getpFundsSources() {
        return pFundsSources;
    }

    public void setpFundsSources(String pFundsSources) {
        this.pFundsSources = pFundsSources;
    }

    public String getpAddressDetail() {
        return pAddressDetail;
    }

    public void setpAddressDetail(String pAddressDetail) {
        this.pAddressDetail = pAddressDetail;
    }

    public String getpInitialApprovalNumber() {
        return pInitialApprovalNumber;
    }

    public void setpInitialApprovalNumber(String pInitialApprovalNumber) {
        this.pInitialApprovalNumber = pInitialApprovalNumber;
    }

    public BigDecimal getpInitialApprovalAmount() {
        return pInitialApprovalAmount;
    }

    public void setpInitialApprovalAmount(BigDecimal pInitialApprovalAmount) {
        this.pInitialApprovalAmount = pInitialApprovalAmount;
    }

    public String getpProjectNumber() {
        return pProjectNumber;
    }

    public void setpProjectNumber(String pProjectNumber) {
        this.pProjectNumber = pProjectNumber;
    }

    public BigDecimal getpApprovalInvestmentAmount() {
        return pApprovalInvestmentAmount;
    }

    public void setpApprovalInvestmentAmount(BigDecimal pApprovalInvestmentAmount) {
        this.pApprovalInvestmentAmount = pApprovalInvestmentAmount;
    }

    public String getpCanYanApplyUnit() {
        return pCanYanApplyUnit;
    }

    public void setpCanYanApplyUnit(String pCanYanApplyUnit) {
        this.pCanYanApplyUnit = pCanYanApplyUnit;
    }

    public String getpCanYanApprovalUnit() {
        return pCanYanApprovalUnit;
    }

    public void setpCanYanApprovalUnit(String pCanYanApprovalUnit) {
        this.pCanYanApprovalUnit = pCanYanApprovalUnit;
    }

    public String getpRecordNum() {
        return pRecordNum;
    }

    public void setpRecordNum(String pRecordNum) {
        this.pRecordNum = pRecordNum;
    }

    public Date getpRecordDate() {
        return pRecordDate;
    }

    public void setpRecordDate(Date pRecordDate) {
        this.pRecordDate = pRecordDate;
    }


    public BigDecimal getpRecordAmount() {
        return pRecordAmount;
    }

    public void setpRecordAmount(BigDecimal pRecordAmount) {
        this.pRecordAmount = pRecordAmount;
    }

    public String getpLegalUnit() {
        return pLegalUnit;
    }

    public void setpLegalUnit(String pLegalUnit) {
        this.pLegalUnit = pLegalUnit;
    }

    public String getpRecordIssueUnit() {
        return pRecordIssueUnit;
    }

    public void setpRecordIssueUnit(String pRecordIssueUnit) {
        this.pRecordIssueUnit = pRecordIssueUnit;
    }

    public Date getpApplyDate() {
        return pApplyDate;
    }

    public void setpApplyDate(Date pApplyDate) {
        this.pApplyDate = pApplyDate;
    }

    public BigDecimal getpApprovalAmount() {
        return pApprovalAmount;
    }

    public void setpApprovalAmount(BigDecimal pApprovalAmount) {
        this.pApprovalAmount = pApprovalAmount;
    }

    public String getpBuildType() {
        return pBuildType;
    }

    public void setpBuildType(String pBuildType) {
        this.pBuildType = pBuildType;
    }

    public String getpProfessionalType() {
        return pProfessionalType;
    }

    public void setpProfessionalType(String pProfessionalType) {
        this.pProfessionalType = pProfessionalType;
    }

    public String getpTransferUnit() {
        return pTransferUnit;
    }

    public void setpTransferUnit(String pTransferUnit) {
        this.pTransferUnit = pTransferUnit;
    }

    public String getpProjectManager() {
        return pProjectManager;
    }

    public void setpProjectManager(String pProjectManager) {
        this.pProjectManager = pProjectManager;
    }

    public String getpProjectLeader() {
        return pProjectLeader;
    }

    public void setpProjectLeader(String pProjectLeader) {
        this.pProjectLeader = pProjectLeader;
    }

    public Date getpSettlementAuditReportDate() {
        return pSettlementAuditReportDate;
    }

    public void setpSettlementAuditReportDate(Date pSettlementAuditReportDate) {
        this.pSettlementAuditReportDate = pSettlementAuditReportDate;
    }

    public BigDecimal getpSettlementAuditAmount() {
        return pSettlementAuditAmount;
    }

    public void setpSettlementAuditAmount(BigDecimal pSettlementAuditAmount) {
        this.pSettlementAuditAmount = pSettlementAuditAmount;
    }

    public Integer getpAuthType() {
        return pAuthType;
    }

    public void setpAuthType(Integer pAuthType) {
        this.pAuthType = pAuthType;
    }

    public String getpProjectFormType() {
        return pProjectFormType;
    }

    public void setpProjectFormType(String pProjectFormType) {
        this.pProjectFormType = pProjectFormType;
    }

    public String getpRecordFile() {
        return pRecordFile;
    }

    public void setpRecordFile(String pRecordFile) {
        this.pRecordFile = pRecordFile;
    }

    public String getpApplyFile() {
        return pApplyFile;
    }

    public void setpApplyFile(String pApplyFile) {
        this.pApplyFile = pApplyFile;
    }

    public String getpBuildYear() {
        return pBuildYear;
    }

    public void setpBuildYear(String pBuildYear) {
        this.pBuildYear = pBuildYear;
    }

    public String getpEngineer() {
        return pEngineer;
    }

    public void setpEngineer(String pEngineer) {
        this.pEngineer = pEngineer;
    }

    public String getpEngineerLeader() {
        return pEngineerLeader;
    }

    public void setpEngineerLeader(String pEngineerLeader) {
        this.pEngineerLeader = pEngineerLeader;
    }

    public BigDecimal getpTdAmount() {
        return pTdAmount;
    }

    public void setpTdAmount(BigDecimal pTdAmount) {
        this.pTdAmount = pTdAmount;
    }

    public BigDecimal getpJsqdkLx() {
        return pJsqdkLx;
    }

    public void setpJsqdkLx(BigDecimal pJsqdkLx) {
        this.pJsqdkLx = pJsqdkLx;
    }

    public String getpLegalPerson() {
        return pLegalPerson;
    }

    public void setpLegalPerson(String pLegalPerson) {
        this.pLegalPerson = pLegalPerson;
    }

    public String getpSectionStatus() {
        return pSectionStatus;
    }

    public void setpSectionStatus(String pSectionStatus) {
        this.pSectionStatus = pSectionStatus;
    }

    public String getIsGroupInvestment() {
        return isGroupInvestment;
    }

    public void setIsGroupInvestment(String isGroupInvestment) {
        this.isGroupInvestment = isGroupInvestment;
    }

    public String getProjectNature() {
        return projectNature;
    }

    public void setProjectNature(String projectNature) {
        this.projectNature = projectNature;
    }

    public String getProjectInvestmentCategory() {
        return projectInvestmentCategory;
    }

    public void setProjectInvestmentCategory(String projectInvestmentCategory) {
        this.projectInvestmentCategory = projectInvestmentCategory;
    }

    public String getProjectCategoryDetails() {
        return projectCategoryDetails;
    }

    public void setProjectCategoryDetails(String projectCategoryDetails) {
        this.projectCategoryDetails = projectCategoryDetails;
    }

    public String getProjectOwner() {
        return projectOwner;
    }

    public void setProjectOwner(String projectOwner) {
        this.projectOwner = projectOwner;
    }

    public String getIsHistory() {
        return isHistory;
    }

    public void setIsHistory(String isHistory) {
        this.isHistory = isHistory;
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

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
