package com.jpmsoft.common.model.response.Project;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.annotations.ClassInfo;
import com.jpmsoft.common.model.request.project.PpOwnerUnitDTO;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectDtlModel;
import com.jpmsoft.common.model.requestAndResponse.Project.ProConstructionAreaDtlModel;
import com.jpmsoft.common.model.requestAndResponse.Project.ProHouseDtlModel;
import com.jpmsoft.common.model.requestAndResponse.Project.ProSourcesFundsModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class PreProjectResponse implements Serializable {

    private static final long serialVersionUID = -5140439511243624542L;

    private String pId;
    private String prId;
    @ClassInfo(title = "项目编号",type="string",value = "pCode")
    private String pCode;
    private String pParentId;
    @ClassInfo(title = "项目名称",type="string",value = "pName")
    private String pName;
    private String pProjectAbbreviation;
    private String pOriginalName;
    private String pNameInitials;
    private String pProjectCode;
    private String pAddress;
    @ClassInfo(title = "所属区域",type="string",value = "pOverview")
    private String pRegion;
    private Float pConstructionArea;
    @ClassInfo(title = "项目经度(X)",type="number",value = "pLongitude")
    private Float pLongitude;
    @ClassInfo(title = "项目纬度(Y)",type="number",value = "pLatitude")
    private Float pLatitude;
    @ClassInfo(title = "项目概况",type="string",value = "pOverview")
    private String pOverview;
    private Float pVolumeRate;
    private Float pLandArea;
    private Integer pSeat;
    private Integer pNumberOfElevators;
    private Float pMaxHeight;
    private Integer pParkingNumber;
    private Integer pBuildingsNumber;
    private Integer pDesignLife;
    private String pSuperStructure;
    private String pBasicStructure;
    private Integer pGroundFloors;
    private Integer pUnderGroundFloors;
    private String pIsGas;
    private Float pBasementArea;
    private String pIsAirDefense;
    private Float pAirDefenseArea;
    @ClassInfo(title = "专业类别",type="dictionary",value = "pType",dictionary = "JPMsoft_XMLB")
    private String pType;
    @ClassInfo(title = "重大项目类别",type="dictionary",value = "pMajorType",dictionary = "JPMsoft_ZDXMLB")
    private String pMajorType;
    private String pConstructionUnit;
    @ClassInfo(title = "全过程咨询/项目管理单位",type="string",value = "pCompetentDepartment")
    private String pCompetentDepartment;
    private String pProjectDirector;
    @ClassInfo(title = "项目阶段",type="dictionary",value = "pProjectStage",dictionary = "JPMsoft_XMJD")
    private String pProjectStage;
    private String pProjectStatus;
    @ClassInfo(title = "计划开工时间",type="date",value = "pPlanStartDate")
    private Date pPlanStartDate;
    @ClassInfo(title = "计划竣工时间",type="date",value = "pPlanCompleteDate")
    private Date pPlanCompleteDate;
    @ClassInfo(title = "建设周期",type="number",value = "pTotalDuration")
    private Integer pTotalDuration;
    @ClassInfo(title = "实际开工时间",type="date",value = "pActualStartTime")
    private Date pActualStartTime;
    @ClassInfo(title = "实际竣工时间",type="date",value = "pActualCompleteDate")
    private Date pActualCompleteDate;
    @ClassInfo(title = "实际建设周期",type="number",value = "pActualTotalDuration")
    private String pActualTotalDuration;
    private Date pInterventionDate;
    private Integer pInterventionYear;
    private Date pReceiptTime;
    private Date pAcceptanceRecordDate;
    private Date pHandoverDate;
    private String pIsProjectSummary;
    @ClassInfo(title = "业主单位",type="string",value = "pUseUnit")
    private String pUseUnit;
    @ClassInfo(title = "资金来源",type="dictionary",value = "pSourcesFunds",dictionary = "JPMsoft_XMZJLY")
    private String pSourcesFunds;
    @ClassInfo(title = "匡算投资",type="money",value = "pCalculateRoughlyAmount")
    private BigDecimal pCalculateRoughlyAmount;
    private BigDecimal pEstimateAmount;
    @ClassInfo(title = "批复金额",type="money",value = "pApproveBudget")
    private BigDecimal pApproveBudget;
    @ClassInfo(title = "批复编号",type="string",value = "pApproveBudgetCode")
    private String pApproveBudgetCode;
    @ClassInfo(title = "批复时间",type="date",value = "pApproveBudgetDate")
    private Date pApproveBudgetDate;
    private BigDecimal pFinalAccounts;
    private Date pFinalAccountsReportDate;
    private String pConstructionProjectPlanningPermitCode;
    private String pConstructionLandPlanningPermitcode;
    private String pOpinionsOnSiteSelectionForConstructionProjectsCode;
    private String pLandUseRightAssignmentContractCode;
    private String pFeasibilityStudyApprovalCode;
    private String pEiaApprovalCode;
    private String pIsAgentConstruction;
    private String pAgentConstructionUnit;
    private String pDesignGrade;
    private String pIsPrefabricated;
    private String pFourNewTechnologies;
    private String pBimTechnology;
    private String pIsEpc;
    private String pIsSpongeCity;
    private String pIsRegularMode;
    private String pIsDesignAndConstructionIntegration;
    private String pDesignAndConstructionIntegrationUnit;
    private String pIsWholeProcessManagement;
    private String pWholeProcessManagementUnit;
    private String pConstructionCooperation;

    private String pProjectFormTypeRemark;
    private String pBuildModelRemark;
    private String pProfessionalTypeRemark;

    private String pBuildTypeRemark;
    private String pStatus;
    private String pCreateUserId;
    @ClassInfo(title = "创建人",type="string",value = "pCreateUserName")
    private String pCreateUserName;
    @ClassInfo(title = "创建时间",type="date",value = "pCreateTime")
    private Date pCreateTime;
    private String pUpdateUserId;

    private String pCategory;
    private Date pUpdateTime;
    private String pSerialNumber;
    private Boolean deleted;
    private String pConstructionUnitName;
    private String pAgentConstructionUnitName;
    private String pDesignAndConstructionIntegrationUnitName;
    private String pParentName;
    private String pParentCode;
    private String pAssignmentNo;
    @ClassInfo(title = "投资类别",type="dictionary",value = "pInvCategory",dictionary = "JPMsoft_TZLB")
    private String pInvCategory;
    private Integer pAdvanceFund;
    private Integer pCorporateRisk;
    private BigDecimal pRaiseFunds;
    private String pIntroduction;
    private String pLandWarrant;
    private Integer pParkingNumberUn;
    private Integer pParkingNumberOn;

    private String pItemNumber;
    private String pPreItemNumber;
    private Date pPreItemTime;
    private Date pItemTime;
    private Float pSiteArea;
    private String pCmMode;
    private String pAssignmentFile;
    /**
     * 主管部门名称
     */
    private String pCompetentDepartmentName;

    private String pCommitteeApprovaDocument;
    private String pCityLeaderMeetingMinutes;
    private String pCityLeaderInstructions;
    private String pOther;
    private String pNature;
    private BigDecimal pReportedAmount;
    private String pApprovaDocument;
    private String pLeaderMeetingMinutes;
    private String pLeaderInstructions;
    private String pProjectDirectorTel;
    private BigDecimal pConstructionCost;
    private BigDecimal pContractPrice;
    private BigDecimal pPurchasePrice;
    private String pEstimateFile;
    private String pScaleDescribe;
    private Boolean pBim;
    private Boolean pSmartSite;
    private Boolean pManagementSystem;
    private Boolean pNewTechnology;
    private Boolean pNewMaterial;
    private Boolean pNewCraft;
    private Boolean pSaveEnergy;
    private Boolean pSaveLand;
    private Boolean pSaveWater;
    private Boolean pSaveMaterial;
    private Boolean pDigitalConstruction;
    private String pGreenConstruction;
    private Boolean pCentralizedConstruction;
    private String pContractingMode;
    private Boolean pProcessConsulting;
    private Boolean pEngineerResponsible;
    @ClassInfo(title = "是否取得立项批复",type="boolean",value = "pIsProjectApproval")
    private Boolean pIsProjectApproval;
    @ClassInfo(title = "建筑规模",type="number",value = "pBuildingScale")
    private Float pBuildingScale;
    @ClassInfo(title = "建筑单位",type="dictionary",value = "pBuildingUnit",dictionary = "JPMsoft_JZGMDW")
    private String pBuildingUnit;
    @ClassInfo(title = "立项审批单位",type="string",value = "pApprovalUnit")
    private String pApprovalUnit;
    @ClassInfo(title = "起始年月",type="date",value = "pConstructionStart")
    private Date pConstructionStart;
    @ClassInfo(title = "竣工年月",type="date",value = "pConstructionEnd")
    private Date pConstructionEnd;
    private String pCheckType;
    private String pProgressSituation;
    private String pRecordNumber;
    private String pEconomicFile;
    private String pProposalFile;
    private String pReportFile;
    @ClassInfo(title = "安全监督备案号",type="string",value = "pSafetyFiling")
    private String pSafetyFiling;
    private String cplId;
    private String pApplyName;
    private BigDecimal pFiscalBudget;
    private String pCampus;
    private String pAuditUnit;
    private String pCostUnit;
    private String pAuditUnitName;
    private String pCostUnitName;
    private String cplName;
    private Integer childProjectSum = 0;
    /**
     * 项目建筑
     */
    private List<ProConstructionAreaDtlModel> proConstructionAreaDtls;
    /**
     * 项目资金来源
     */
    private List<ProSourcesFundsModel> proSourcesFunds;

    /**
     * 项目投资详情
     */
    private List<ProSourcesFundsModel> proInvDetails;
    /**
     * 规划房屋详细
     */
    private List<ProHouseDtlModel> proHouseDtls;
    private List<ProFundNoVo> proFundNos;
    private String fileRelationId;
    /**
     * 是否有子项目
     */
    private Boolean havingChild;
    private String pParentNameHistory;
    private String pConstructionUnitNameHistory;
    private String pAgentUnitNameHistory;
    private String pDesignUnitNameHistory;
    private String pNameHistory;
    @ClassInfo(title = "项目类别（监管）",type="string",value = "pSuperviseType")
    private String pSuperviseType;
    private String pGroupType;
    @ClassInfo(title = "流程状态",type="dictionary",value = "state",dictionary = "JPMsoft_LCZT")
    private Integer state;
    private Date stateTime;
    private String pRemarkPlanned;
    private String pPlanNumber;
    private Date pConsRelease;
    @ClassInfo(title = "代建单位/实施单位",type="string",value = "pImpCompany")
    private String pImpCompany;
    private String pImpState;
    @ClassInfo(title = "文件标题",type="string",value = "pApplyTilte")
    private String pApplyTilte;
    @ClassInfo(title = "项目总投资",type="money",value = "pInvTotal")
    private BigDecimal pInvTotal;
    @ClassInfo(title = "投资口径",type="dictionary",value = "pInvCaliber",dictionary = "JPMsoft_TZKJ")
    private String pInvCaliber;
    private String pInvTilte;
    private String pInvCode;
    private Date pInvDate;
    private String pInvFile;
    private String pPassVideo;
    @ClassInfo(title = "建设模式",type="dictionary",value = "pBuildModel",dictionary = "JPMsoft_JSMS")
    private String pBuildModel;
    private Integer pInterventionEndYear;
    private String pEachDistrict;
    private String pOpinionFile;
    private String pPlanFile;
    private String pFeasibilityFile;
    private String pApproveFile;
    private BigDecimal pSbgjgzfAmt;
    private BigDecimal pGcjsqtAmt;
    private BigDecimal pYbAmt;
    private String pConstructionFile;
    private String pSbgjgzfFile;
    private String pGcjsqtFile;
    private String pYbFile;
    private Float pRate;
    private Float pWeight;
    private Float pLength;
    private Float pWidth;
    //P_Contact 联系人
    private String pContact;
    private String pContactPhone;
    //P_Repair_Project 修缮项目所涉及的专项审核
    private String pRepairProject;
    //P_Project_Basis 立项依据
    private String pProjectBasis;
    //工程部经办人
    private String pEngineer;
    //工程部分管领导
    private String pEngineerLeader;
    //工程部经办人
    private String pEngineerName;
    //工程部分管领导
    private String pEngineerLeaderName;
    //P_Content 实施内容
    private String pContent;
    //工程类别
    private String pSecType;
    //经费来源
    private String pFundsSources;
    //详细地址
    private String pAddressDetail;
    //初设批复文号
    @ClassInfo(title = "初设批复文号",type="string",value = "pInitialApprovalNumber")
    private String pInitialApprovalNumber;
    //初设批复金额
    private BigDecimal pInitialApprovalAmount;
    //结算审计报告出具时间
    private Date pSettlementAuditReportDate;
    //结算审计审定金额
    private BigDecimal pSettlementAuditAmount;
    //单位简称
    private String pConsAbbName;
    private String pAgentConsAbbName;
    private String pDesConsIntegAbbName;
    //合同数量
    private int conNumTotal;
    /**
     * 合同金额汇总
     */
    private BigDecimal conMoneyTotalSum = BigDecimal.ZERO;
    /**
     * 已审批的合同支付金额。
     */
    private BigDecimal conPayAmount = BigDecimal.ZERO;
    /**
     * 合同支付金额占合同金额的百分比。
     */
    private BigDecimal conPayRatio = BigDecimal.ZERO;
    /**
     * 项目最新全景记录的文件关联值。
     */
    private String ppoFile;
    /**
     * 项目下已归档的文件数量
     */
    private int archiveFileSum;

    private List<Map> conTypeStatistics;
    private String faType;

    private List<PreProjectDtlModel> preProjectDtl;



    public  List<PreProjectDtlModel> getPreProjectDtl() {
        return preProjectDtl;
    }
    public void setPreProjectDtl(List<PreProjectDtlModel> preProjectDtl) {
        this.preProjectDtl = preProjectDtl;
    }


    /**
     * 总项目规模
     */
    private String totalBuildingScale;
    private String pProjectFormType;
    private String pProjectNumber;
    private BigDecimal pApprovalInvestmentAmount;
    private String pRecordNum;
    private String pCanYanApplyUnit;
    private String pCanYanApprovalUnit;
    private Date pRecordDate;
    private String pRecordFile;
    private String pRecordAmount;
    private String pLegalUnit;
    private String pRecordIssueUnit;
    private String pApplyFile;
    private Date pApplyDate;
    private BigDecimal pApprovalAmount;
    private String pInvestmentConstitute;
    private String pBuildType;
    private String pBuildYear;
    private String pProfessionalType;
    private String pTransferUnit;
    private String pProjectManager;
    private String pProjectLeader;
    private String pCanYanApplyUnitName;
    private String pCanYanApprovalUnitName;
    private String pLegalUnitName;
    private String pRecordIssueUnitName;
    private String pTransferUnitName;
    private String pProjectManagerName;
    private String pProjectLeaderName;
    private String pDepartmentName;
    private String pUseUnitName;

    //东华项目编号
    private String pDhCode;

    //土地费
    private BigDecimal pTdAmount;

    //建设期贷款利息
    private BigDecimal pJsqdkLx;

    //项目法人
    private String pLegalPerson;

    //    综合计划部联系人
    private String comprehensivePlanDepartmentContactUserId;
    private String comprehensivePlanDepartmentContactUserName;
    //    办公室联系人
    private String officeContactUserId;
    private String officeContactUserName;
    //    工程技术部联系人
    private String comprehensiveTechnologyDepartmentContactUserId;
    private String comprehensiveTechnologyDepartmentContactUserName;
    //    项目管理部联系人
    private String pmDepartmentContactUserId;
    private String pmDepartmentContactUserName;

    private String prCommitteeProjectCode;
    private String prCommitteeApprovaDocument;
    private String prCityLeaderMeetingMinutes;
    private String prCityLeaderInstructions;
    private String prOther;
    private String pDepartment;

    // 项目经理
    private String projectManger;

    // 项目分管领导
    private String projectLeader;


    // 项目经理
    private String projectMangerName;

    // 项目分管领导
    private String projectLeaderName;
    private String isGroupInvestment;
    private String projectNature;
    private String projectInvestmentCategory;
    private String projectCategoryDetails;
    private String projectOwner;
    private String isHistory;
    private String projectCategory;
    private String projectInitiatingUnit;
    private List<PpOwnerUnitDTO> ppOwnerUnits;


    @JsonProperty("pItemTimeNew")
    private String pItemTimeNew;
    @JsonProperty("pFeasibilityTitle")
    private String pFeasibilityTitle;
    @JsonProperty("pFeasibilityTime")
    private String pFeasibilityTime;
    @JsonProperty("pApproveBudgetName")
    private String pApproveBudgetName;
    @JsonProperty("pApproveBudgetDateNew")
    private String pApproveBudgetDateNew;
    @JsonProperty("pConstructionTime")
    private String pConstructionTime;
    @JsonProperty("pConstructionLandTime")
    private String pConstructionLandTime;
    @JsonProperty("pOpinionsOnTime")
    private String pOpinionsOnTime;
    @JsonProperty("pLandUseTime")
    private String pLandUseTime;
    @JsonProperty("pEngineeringConstructionCode")
    private String pEngineeringConstructionCode;
    @JsonProperty("pEngineeringConstructionTime")
    private String pEngineeringConstructionTime;
    @JsonProperty("pInvestmentConstituteRemark")
    private String pInvestmentConstituteRemark;

    private String pprId;
    private Integer projectYear;
    private String dataSourceType;

    // 设计变更 现场签证 未报批金额
    private BigDecimal ecChangeVisaAmount;

    // 设计变更 现场签证 已报批金额
    private BigDecimal ecChangeVisaApprovedAmount;

    private String pProjectStageOrganization;
    // 变更签证台账 暂列金
    private BigDecimal zanLieReportedAmount;
    // 变更签证台账 暂估价
    private BigDecimal zanGuReportedAmount;

    public BigDecimal getZanLieReportedAmount() {
        return zanLieReportedAmount;
    }

    public void setZanLieReportedAmount(BigDecimal zanLieReportedAmount) {
        this.zanLieReportedAmount = zanLieReportedAmount;
    }

    public BigDecimal getZanGuReportedAmount() {
        return zanGuReportedAmount;
    }

    public void setZanGuReportedAmount(BigDecimal zanGuReportedAmount) {
        this.zanGuReportedAmount = zanGuReportedAmount;
    }

    public String getpProjectStageOrganization() {
        return pProjectStageOrganization;
    }

    public void setpProjectStageOrganization(String pProjectStageOrganization) {
        this.pProjectStageOrganization = pProjectStageOrganization;
    }

    public BigDecimal getEcChangeVisaAmount() {
        return ecChangeVisaAmount;
    }

    public void setEcChangeVisaAmount(BigDecimal ecChangeVisaAmount) {
        this.ecChangeVisaAmount = ecChangeVisaAmount;
    }

    public BigDecimal getEcChangeVisaApprovedAmount() {
        return ecChangeVisaApprovedAmount;
    }

    public void setEcChangeVisaApprovedAmount(BigDecimal ecChangeVisaApprovedAmount) {
        this.ecChangeVisaApprovedAmount = ecChangeVisaApprovedAmount;
    }

    public String getpItemTimeNew() {
        return pItemTimeNew;
    }

    public void setpItemTimeNew(String pItemTimeNew) {
        this.pItemTimeNew = pItemTimeNew;
    }

    public String getDataSourceType() {
        return dataSourceType;
    }

    public void setDataSourceType(String dataSourceType) {
        this.dataSourceType = dataSourceType;
    }

    public String getPprId() {
        return pprId;
    }

    public void setPprId(String pprId) {
        this.pprId = pprId;
    }

    public String getpInvestmentConstituteRemark() {
        return pInvestmentConstituteRemark;
    }

    public void setpInvestmentConstituteRemark(String pInvestmentConstituteRemark) {
        this.pInvestmentConstituteRemark = pInvestmentConstituteRemark;
    }

    public String getPItemTimeNew() {
        return pItemTimeNew;
    }

    public void setPItemTimeNew(String pItemTimeNew) {
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

    public List<PpOwnerUnitDTO> getPpOwnerUnits() {
        return ppOwnerUnits;
    }

    public void setPpOwnerUnits(List<PpOwnerUnitDTO> ppOwnerUnits) {
        this.ppOwnerUnits = ppOwnerUnits;
    }

    public String getProjectMangerName() {
        return projectMangerName;
    }

    public void setProjectMangerName(String projectMangerName) {
        this.projectMangerName = projectMangerName;
    }

    public String getProjectLeaderName() {
        return projectLeaderName;
    }

    public void setProjectLeaderName(String projectLeaderName) {
        this.projectLeaderName = projectLeaderName;
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

    public String getpProjectFormTypeRemark() {
        return pProjectFormTypeRemark;
    }

    public void setpProjectFormTypeRemark(String pProjectFormTypeRemark) {
        this.pProjectFormTypeRemark = pProjectFormTypeRemark;
    }

    public String getpBuildModelRemark() {
        return pBuildModelRemark;
    }

    public void setpBuildModelRemark(String pBuildModelRemark) {
        this.pBuildModelRemark = pBuildModelRemark;
    }

    public String getpProfessionalTypeRemark() {
        return pProfessionalTypeRemark;
    }

    public void setpProfessionalTypeRemark(String pProfessionalTypeRemark) {
        this.pProfessionalTypeRemark = pProfessionalTypeRemark;
    }

    public String getpBuildTypeRemark() {
        return pBuildTypeRemark;
    }

    public void setpBuildTypeRemark(String pBuildTypeRemark) {
        this.pBuildTypeRemark = pBuildTypeRemark;
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

    public String getpConstructionUnitName() {
        return pConstructionUnitName;
    }

    public void setpConstructionUnitName(String pConstructionUnitName) {
        this.pConstructionUnitName = pConstructionUnitName;
    }

    public String getpAgentConstructionUnitName() {
        return pAgentConstructionUnitName;
    }

    public void setpAgentConstructionUnitName(String pAgentConstructionUnitName) {
        this.pAgentConstructionUnitName = pAgentConstructionUnitName;
    }

    public String getpDesignAndConstructionIntegrationUnitName() {
        return pDesignAndConstructionIntegrationUnitName;
    }

    public void setpDesignAndConstructionIntegrationUnitName(String pDesignAndConstructionIntegrationUnitName) {
        this.pDesignAndConstructionIntegrationUnitName = pDesignAndConstructionIntegrationUnitName;
    }

    public String getpParentName() {
        return pParentName;
    }

    public void setpParentName(String pParentName) {
        this.pParentName = pParentName;
    }

    public String getpParentCode() {
        return pParentCode;
    }

    public void setpParentCode(String pParentCode) {
        this.pParentCode = pParentCode;
    }

    public String getpAssignmentNo() {
        return pAssignmentNo;
    }

    public void setpAssignmentNo(String pAssignmentNo) {
        this.pAssignmentNo = pAssignmentNo;
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

    public String getpCompetentDepartmentName() {
        return pCompetentDepartmentName;
    }

    public void setpCompetentDepartmentName(String pCompetentDepartmentName) {
        this.pCompetentDepartmentName = pCompetentDepartmentName;
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

    public List<ProConstructionAreaDtlModel> getProConstructionAreaDtls() {
        return proConstructionAreaDtls;
    }

    public void setProConstructionAreaDtls(List<ProConstructionAreaDtlModel> proConstructionAreaDtls) {
        this.proConstructionAreaDtls = proConstructionAreaDtls;
    }

    public List<ProSourcesFundsModel> getProSourcesFunds() {
        return proSourcesFunds;
    }

    public void setProSourcesFunds(List<ProSourcesFundsModel> proSourcesFunds) {
        this.proSourcesFunds = proSourcesFunds;
    }

    public List<ProSourcesFundsModel> getProInvDetails() {
        return proInvDetails;
    }

    public void setProInvDetails(List<ProSourcesFundsModel> proInvDetails) {
        this.proInvDetails = proInvDetails;
    }

    public List<ProHouseDtlModel> getProHouseDtls() {
        return proHouseDtls;
    }

    public void setProHouseDtls(List<ProHouseDtlModel> proHouseDtls) {
        this.proHouseDtls = proHouseDtls;
    }

    public List<ProFundNoVo> getProFundNos() {
        return proFundNos;
    }

    public void setProFundNos(List<ProFundNoVo> proFundNos) {
        this.proFundNos = proFundNos;
    }

    public String getFileRelationId() {
        return fileRelationId;
    }

    public void setFileRelationId(String fileRelationId) {
        this.fileRelationId = fileRelationId;
    }

    public Boolean getHavingChild() {
        return havingChild;
    }

    public void setHavingChild(Boolean havingChild) {
        this.havingChild = havingChild;
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

    public String getpAuditUnitName() {
        return pAuditUnitName;
    }

    public void setpAuditUnitName(String pAuditUnitName) {
        this.pAuditUnitName = pAuditUnitName;
    }

    public String getpCostUnitName() {
        return pCostUnitName;
    }

    public void setpCostUnitName(String pCostUnitName) {
        this.pCostUnitName = pCostUnitName;
    }

    public String getCplName() {
        return cplName;
    }

    public void setCplName(String cplName) {
        this.cplName = cplName;
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

    public int getConNumTotal() {
        return conNumTotal;
    }

    public void setConNumTotal(int conNumTotal) {
        this.conNumTotal = conNumTotal;
    }

    public BigDecimal getConMoneyTotalSum() {
        return conMoneyTotalSum;
    }

    public void setConMoneyTotalSum(BigDecimal conMoneyTotalSum) {
        this.conMoneyTotalSum = conMoneyTotalSum;
    }

    public BigDecimal getConPayRatio() {
        return conPayRatio;
    }

    public void setConPayRatio(BigDecimal conPayRatio) {
        this.conPayRatio = conPayRatio;
    }

    public String getPpoFile() {
        return ppoFile;
    }

    public void setPpoFile(String ppoFile) {
        this.ppoFile = ppoFile;
    }

    public BigDecimal getConPayAmount() {
        return conPayAmount;
    }

    public void setConPayAmount(BigDecimal conPayAmount) {
        this.conPayAmount = conPayAmount;
    }

    public int getArchiveFileSum() {
        return archiveFileSum;
    }

    public void setArchiveFileSum(int archiveFileSum) {
        this.archiveFileSum = archiveFileSum;
    }

    public List<Map> getConTypeStatistics() {
        return conTypeStatistics;
    }

    public void setConTypeStatistics(List<Map> conTypeStatistics) {
        this.conTypeStatistics = conTypeStatistics;
    }

    public String getFaType() {
        return faType;
    }

    public String getpCategory() {
        return pCategory;
    }

    public void setpCategory(String pCategory) {
        this.pCategory = pCategory;
    }

    public void setFaType(String faType) {
        this.faType = faType;
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

    public String getpProjectFormType() {
        return pProjectFormType;
    }

    public void setpProjectFormType(String pProjectFormType) {
        this.pProjectFormType = pProjectFormType;
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

    public String getpRecordNum() {
        return pRecordNum;
    }

    public void setpRecordNum(String pRecordNum) {
        this.pRecordNum = pRecordNum;
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

    public Date getpRecordDate() {
        return pRecordDate;
    }

    public void setpRecordDate(Date pRecordDate) {
        this.pRecordDate = pRecordDate;
    }

    public String getpRecordFile() {
        return pRecordFile;
    }

    public void setpRecordFile(String pRecordFile) {
        this.pRecordFile = pRecordFile;
    }

    public String getpRecordAmount() {
        return pRecordAmount;
    }

    public void setpRecordAmount(String pRecordAmount) {
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

    public String getpApplyFile() {
        return pApplyFile;
    }

    public void setpApplyFile(String pApplyFile) {
        this.pApplyFile = pApplyFile;
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

    public String getpInvestmentConstitute() {
        return pInvestmentConstitute;
    }

    public void setpInvestmentConstitute(String pInvestmentConstitute) {
        this.pInvestmentConstitute = pInvestmentConstitute;
    }

    public String getpBuildType() {
        return pBuildType;
    }

    public void setpBuildType(String pBuildType) {
        this.pBuildType = pBuildType;
    }

    public String getpBuildYear() {
        return pBuildYear;
    }

    public void setpBuildYear(String pBuildYear) {
        this.pBuildYear = pBuildYear;
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

    public String getpCanYanApplyUnitName() {
        return pCanYanApplyUnitName;
    }

    public void setpCanYanApplyUnitName(String pCanYanApplyUnitName) {
        this.pCanYanApplyUnitName = pCanYanApplyUnitName;
    }

    public String getpCanYanApprovalUnitName() {
        return pCanYanApprovalUnitName;
    }

    public void setpCanYanApprovalUnitName(String pCanYanApprovalUnitName) {
        this.pCanYanApprovalUnitName = pCanYanApprovalUnitName;
    }

    public String getpLegalUnitName() {
        return pLegalUnitName;
    }

    public void setpLegalUnitName(String pLegalUnitName) {
        this.pLegalUnitName = pLegalUnitName;
    }

    public String getpRecordIssueUnitName() {
        return pRecordIssueUnitName;
    }

    public void setpRecordIssueUnitName(String pRecordIssueUnitName) {
        this.pRecordIssueUnitName = pRecordIssueUnitName;
    }

    public String getpTransferUnitName() {
        return pTransferUnitName;
    }

    public void setpTransferUnitName(String pTransferUnitName) {
        this.pTransferUnitName = pTransferUnitName;
    }

    public String getpProjectManagerName() {
        return pProjectManagerName;
    }

    public void setpProjectManagerName(String pProjectManagerName) {
        this.pProjectManagerName = pProjectManagerName;
    }

    public String getpProjectLeaderName() {
        return pProjectLeaderName;
    }

    public void setpProjectLeaderName(String pProjectLeaderName) {
        this.pProjectLeaderName = pProjectLeaderName;
    }

    public String getpDepartmentName() {
        return pDepartmentName;
    }

    public void setpDepartmentName(String pDepartmentName) {
        this.pDepartmentName = pDepartmentName;
    }

    public String getpUseUnitName() {
        return pUseUnitName;
    }

    public void setpUseUnitName(String pUseUnitName) {
        this.pUseUnitName = pUseUnitName;
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

    public String getpEngineerName() {
        return pEngineerName;
    }

    public void setpEngineerName(String pEngineerName) {
        this.pEngineerName = pEngineerName;
    }

    public String getpEngineerLeaderName() {
        return pEngineerLeaderName;
    }

    public void setpEngineerLeaderName(String pEngineerLeaderName) {
        this.pEngineerLeaderName = pEngineerLeaderName;
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

    public String getpDepartment() {
        return pDepartment;
    }

    public void setpDepartment(String pDepartment) {
        this.pDepartment = pDepartment;
    }

    public Integer getChildProjectSum() {
        return childProjectSum;
    }

    public void setChildProjectSum(Integer childProjectSum) {
        this.childProjectSum = childProjectSum;
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

    public Integer getProjectYear() {
        return projectYear;
    }

    public void setProjectYear(Integer projectYear) {
        this.projectYear = projectYear;
    }
}
