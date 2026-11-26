package com.jpmsoft.common.model.request.project;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.annotations.ClassInfo;
import com.jpmsoft.common.model.BaseFileRequest;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectDtlModel;
import com.jpmsoft.common.model.requestAndResponse.Project.ProConstructionAreaDtlModel;
import com.jpmsoft.common.model.requestAndResponse.Project.ProHouseDtlModel;
import com.jpmsoft.common.model.requestAndResponse.Project.ProSourcesFundsModel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class PreProjectRequest extends BaseFileRequest implements Serializable {

    private static final long serialVersionUID = 5297966162168453336L;

    private String pId;
    private String prId;
    @ClassInfo(title = "项目编码", type = "string", value = "pCode", dbValue = "P_Code")
    private String pCode;
    private String pParentId;
    @ClassInfo(title = "项目名称", type = "string", value = "projectName", dbValue = "P_Name", no = 1)
    private String pName;
    private String pProjectAbbreviation;
    private String pOriginalName;
    private String pNameInitials;
    private String pProjectCode;
    private String pAddress;
    @ClassInfo(title = "所属区域", type = "config", value = "pRegion", dictionary = "projectArea", dbValue = "P_Region")
    private String pRegion;
    private Float pConstructionArea;
    @ClassInfo(title = "项目经度(X)", type = "number", value = "pLongitude", dbValue = "P_Longitude")
    private Float pLongitude;
    @ClassInfo(title = "项目纬度(Y)", type = "number", value = "pLatitude", dbValue = "P_Latitude")
    private Float pLatitude;
    @ClassInfo(title = "项目概况", type = "string", value = "pOverview", dbValue = "P_Overview")
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
    @ClassInfo(title = "专业分类", type = "dictionary", value = "pType", dictionary = "JPMsoft_XMLB", dbValue = "P_Type", no = 4)
    private String pType;
    @ClassInfo(title = "重大项目类别", type = "dictionary", value = "pMajorType", dictionary = "JPMsoft_ZDXMLB", dbValue = "P_Major_Type")
    private String pMajorType;
    @ClassInfo(title = "项目管理单位", type = "organization", value = "pCompetentDepartment", dbValue = "P_Competent_Department", no = 9)
    private String pConstructionUnit;
    private String pConstructionUnitName;
    private String pCompetentDepartment;
    private String pProjectDirector;
    @ClassInfo(title = "项目阶段", type = "dictionary", value = "pProjectStage", dictionary = "JPMsoft_XMJD", dbValue = "P_Project_Stage")
    private String pProjectStage;
    private String pProjectStatus;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ClassInfo(title = "计划开工时间", type = "date", value = "pPlanStartDate", dbValue = "P_Plan_Start_Date")
    private Date pPlanStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ClassInfo(title = "计划竣工时间", type = "date", value = "pPlanCompleteDate", dbValue = "P_Plan_Complete_Date")
    private Date pPlanCompleteDate;
    @ClassInfo(title = "建设周期", type = "number", value = "pTotalDuration", dbValue = "P_Total_Duration")
    private Integer pTotalDuration;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ClassInfo(title = "实际开工时间", type = "date", value = "pActualStartTime", dbValue = "P_Actual_Start_Time")
    private Date pActualStartTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ClassInfo(title = "实际竣工时间", type = "date", value = "pActualCompleteDate", dbValue = "P_Actual_Complete_Date")
    private Date pActualCompleteDate;
    @ClassInfo(title = "实际建设周期", type = "number", value = "pActualTotalDuration", dbValue = "P_Actual_Total_Duration")
    private String pActualTotalDuration;
    private Date pInterventionDate;
    //建设年限
    @ClassInfo(title = "建设年限", type = "number", value = "pInterventionYear", dbValue = "P_Intervention_Year")
    private Integer pInterventionYear;
    private Date pReceiptTime;
    private Date pAcceptanceRecordDate;
    private Date pHandoverDate;
    private String pIsProjectSummary;
    @ClassInfo(title = "业主单位", type = "string", value = "pUseUnit", dbValue = "P_Use_Unit", no = 2)
    private String pUseUnit;
    @ClassInfo(title = "资金来源", type = "dictionary", value = "pSourcesFunds", dictionary = "JPMsoft_XMZJLY", dbValue = "P_Sources_Funds")
    private String pSourcesFunds;
    @ClassInfo(title = "匡算投资", type = "money", value = "pCalculateRoughlyAmount", dbValue = "P_Calculate_Roughly_Amount")
    private BigDecimal pCalculateRoughlyAmount;
    private BigDecimal pEstimateAmount;
    @ClassInfo(title = "批复投资估算", type = "money", value = "pApproveBudget", dbValue = "P_Approve_Budget")
    private BigDecimal pApproveBudget;
    @ClassInfo(title = "批复文号", type = "string", value = "pApproveBudgetCode", dbValue = "P_Approve_Budget_Code")
    private String pApproveBudgetCode;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ClassInfo(title = "批复日期", type = "date", value = "pApproveBudgetDate", dbValue = "P_Approve_Budget_Date")
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
    private String pStatus;
    private String pCreateUserId;
    @ClassInfo(title = "创建人", type = "string", value = "pCreateUserName", dbValue = "P_Create_User_Name", no = 10)
    private String pCreateUserName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ClassInfo(title = "创建时间", type = "date", value = "pCreateTime", dbValue = "P_Create_Time", no = 11)
    private Date pCreateTime;
    private String pUpdateUserId;
    private Date pUpdateTime;
    private String pSerialNumber;
    private String pAssignmentNo;

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
    private String pNature;
    private BigDecimal pReportedAmount;
    private String pApprovaDocument;
    private String pLeaderMeetingMinutes;
    private String pLeaderInstructions;
    private String pEstimateFile;

    private String pCmMode;
    private String pAssignmentFile;
    private String pAssignmentFileIds;
    //批复文件上传
    private String pCommitteeApprovaDocument;
    private String pCommitteeApprovaDocumentIds;
    private String pCityLeaderMeetingMinutes;
    private String pCityLeaderInstructions;
    private String pOther;
    private String pCityLeaderMeetingMinutesIds;
    private String pEstimateFileIds;
    private String pCityLeaderInstructionsIds;
    private String pApprovaDocumentIds;
    private String pLeaderMeetingMinutesIds;
    private String pLeaderInstructionsIds;
    private String pOtherIds;
    private String pProjectDirectorTel;
    private BigDecimal pConstructionCost;
    private BigDecimal pContractPrice;
    private BigDecimal pPurchasePrice;
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
    @ClassInfo(title = "是否取得立项批复", type = "dictionary", value = "pIsProjectApproval", dictionary = "JPMsoft_ZDTY", dbValue = "P_Is_Project_Approval")
    private Boolean pIsProjectApproval;
    @ClassInfo(title = "建筑规模", type = "number", value = "pBuildingScale", dbValue = "P_Building_Scale")
    private Float pBuildingScale;
    @ClassInfo(title = "建筑单位", type = "dictionary", value = "pBuildingUnit", dictionary = "JPMsoft_JZGMDW", dbValue = "P_Building_Unit")
    private String pBuildingUnit;
    @ClassInfo(title = "项目类别（监管）", type = "dictionary", value = "pSuperviseType", dictionary = "JPMsoft_XMLBJG", dbValue = "P_Supervise_Type")
    private String pSuperviseType;
    private String pGroupType;
    @ClassInfo(title = "流程状态", type = "dictionary", value = "state", dictionary = "JPMsoft_LCZT", dbValue = "state", no = 12)
    private Integer state;
    private Date stateTime;
    private String pRemarkPlanned;
    private String pPlanNumber;
    private Date pConsRelease;
    @ClassInfo(title = "代建单位/实施单位", type = "string", value = "pImpCompany", dbValue = "P_Imp_Company", no = 8)
    private String pImpCompany;
    private String pImpState;
    @ClassInfo(title = "文件标题", type = "string", value = "pApplyTilte", dbValue = "P_Apply_Tilte")
    private String pApplyTilte;
    @ClassInfo(title = "项目总投资", type = "money", value = "pInvTotal", dbValue = "P_Inv_Total", no = 5)
    private BigDecimal pInvTotal;
    @ClassInfo(title = "投资口径", type = "dictionary", value = "pInvCaliber", dictionary = "JPMsoft_TZKJ", dbValue = "P_Inv_Caliber")
    private String pInvCaliber;
    private String pInvTilte;
    private String pInvCode;
    private Date pInvDate;
    private String pInvFile;
    private String pPassVideo;
    private String pInvFileIds;
    private String pPassVideoIds;
    private String pOpinionFile;
    private String pPlanFile;
    private String pFeasibilityFile;
    private String pApproveFile;
    private String pApproveFileIds;
    private String pOpinionFileIds;
    private String pPlanFileIds;
    private String pFeasibilityFileIds;
    @ClassInfo(title = "建设模式", type = "dictionary", value = "pBuildModel", dictionary = "JPMsoft_JSMS", dbValue = "P_Build_Model", no = 3)
    private String pBuildModel;
    private Integer pInterventionEndYear;
    private String pEachDistrict;
    private String pApprovalUnit;
    @ClassInfo(title = "起止年月", type = "date", value = "pConstructionStart", dbValue = "P_Construction_Start", no = 6)
    private Date pConstructionStart;
    @ClassInfo(title = "竣工年月", type = "date", value = "pConstructionEnd", dbValue = "P_Construction_End", no = 7)
    private Date pConstructionEnd;
    private String pCheckType;
    private String pProgressSituation;
    private String pRecordNumber;
    private String pEconomicFile;
    private String pProposalFile;
    private String pReportFile;
    @ClassInfo(title = "投资类别", type = "dictionary", value = "pInvCategory", dictionary = "JPMsoft_TZLB", dbValue = "P_Inv_Category")
    private String pInvCategory;
    private Integer pAdvanceFund;
    private Integer pCorporateRisk;

    private String pSafetyFiling;
    private String cplId;
    private String pApplyName;
    private BigDecimal pFiscalBudget;
    private String pCampus;
    private String pAuditUnit;
    private String pCostUnit;
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
    //P_Content 实施内容
    private String pContent;
    //工程类别
    private String pSecType;
    //经费来源
    private String pFundsSources;

    private String pConstructionFileIds;
    private String pSbgjgzfFileIds;
    private String pGcjsqtFileIds;
    private String pYbFileIds;
    private String pEconomicFileIds;
    private String pProposalFileIds;
    private String pReportFileIds;
    //省市区县
    private String pAddressDetail;
    //初设批复文号
    @ClassInfo(title = "初设批复文号", type = "string", value = "pInitialApprovalNumber", dbValue = "P_Initial_Approval_Number")
    private String pInitialApprovalNumber;
    //初设批复金额
    private BigDecimal pInitialApprovalAmount;
    //结算审计报告出具时间
    private Date pSettlementAuditReportDate;
    //结算审计审定金额
    private BigDecimal pSettlementAuditAmount;
    /**
     * 筛选条件 排除项目分派单已使用项目
     * 1 ：筛选
     * other:不筛选
     */
    private Integer excludePa;
    /**
     * 是否筛选父合同即判断pParentId是否有值
     * null则不判断
     * true:pParentId有值
     * false:pParentId无值
     */
    private Boolean filterParentCollection;
    /**
     * 是否 根据当前时间（年月） 是否在建设起止年月区间 筛选
     * true:判断
     * null或false则不判断
     */
    private Boolean constructionFilter;
    //立项形式 01可研批复、02投资备案证、03其他
    @ClassInfo(title = "立项形式", type = "dictionary", value = "pProjectFormType", dictionary = "JPMsoft_LXXS", dbValue = "P_Project_Form_Type")
    private String pProjectFormType;
    //项目代码
    @ClassInfo(title = "项目代码", type = "string", value = "pProjectNumber", dbValue = "P_Project_Number")
    private String pProjectNumber;
    //批复投资估算
    private BigDecimal pApprovalInvestmentAmount;
    //可研申报单位
    private String pCanYanApplyUnit;
    //可研审批单位
    private String pCanYanApprovalUnit;
    //备案文号
    @ClassInfo(title = "备案证号", type = "string", value = "pRecordNum", dbValue = "P_Record_Num")
    private String pRecordNum;
    //备案时间
    @ClassInfo(title = "备案日期", type = "date", value = "pRecordDate", dbValue = "P_Record_Date")
    private Date pRecordDate;
    //备案文件Ids
    private String pRecordFile;
    private String pRecordFileIds;
    //备案总投资
    @ClassInfo(title = "备案总投资", type = "money", value = "pRecordAmount", dbValue = "P_Record_Amount")
    private BigDecimal pRecordAmount;
    //项目法人单位
    private String pLegalUnit;
    //备案证核发单位
    private String pRecordIssueUnit;
    //立项文件上传
    private String pApplyFile;
    private String pApplyFileIds;
    //立项日期
    @ClassInfo(title = "立项日期", type = "date", value = "pApplyDate", dbValue = "P_Apply_Date")
    private Date pApplyDate;
    //建设年限
    private String pBuildYear;
    //投资估算
    private BigDecimal pApprovalAmount;
    //建设形式
    private String pBuildType;
    //专业分类
    private String pProfessionalType;
    //移交接收单位
    private String pTransferUnit;
    //项目经理
    private String pProjectManager;
    //项目分管领导
    private String pProjectLeader;
    private String pSectionStatus;
    //工程部经办人
    private String pEngineer;
    //工程部分管领导
    private String pEngineerLeader;
    private String pEngineeringspecialty;
    /**
     * 城建项目管理大纲需要过滤掉为进度计划为审批通过的数据
     */
    private Boolean haveGantPlan;
    /**
     * 项目建筑
     */
    private List<ProConstructionAreaDtlModel> proConstructionAreaDtls;
    /**
     * 项目资金来源
     */
    private List<ProSourcesFundsModel> proSourcesFunds;
    /**
     * 项目投资计划
     */
    private List<ProSourcesFundsModel> proInvDetails;

    /**
     * 规划房屋详细
     */
    private List<ProHouseDtlModel> proHouseDtls;

    private List<ProFundNoDto> proFundNos;

    /**********************************台账筛选用字段******************************************/

    /**
     * 时间范围筛选
     */
    private String pCompetentDepartmentName;
    private String pDepartmentName;
    private List<String> pPreItemTimes;
    private List<String> pItemTimes;
    private List<String> pUpdateTimes;
    private List<String> pCreateTimes;
    private List<String> pFinalAccountsReportDates;
    private List<String> pApproveBudgetDates;
    private List<String> pReceiptTimes;
    private List<String> pAcceptanceRecordDates;
    private List<String> pHandoverDates;
    private List<String> pInterventionDates;
    private List<String> pActualStartTimes;
    private List<String> pActualCompleteDates;
    private List<String> pPlanStartDates;
    private List<String> pPlanCompleteDates;
    /**
     * 建筑总面积 区间值
     */
    private List<String> pConstructionAreas;
    /**
     * 经纬度
     */
    private List<String> pLongitudes;
    private List<String> pLatitudes;
    private List<String> pBuildingScales;
    private List<String> pInterventionYears;
    private List<String> pInterventionEndYears;
    private List<String> pInvTotals;
    private List<String> pInvDates;
    private List<String> pCalculateRoughlyAmounts;
    private List<String> pEstimateAmounts;
    private List<String> pApproveBudgets;
    private List<String> pTotalDurations;
    private List<String> pActualTotalDurations;
    private List<String> pConstructionStarts;
    private List<String> pConstructionEnds;
    //是否显示包含作废的数据 1 包含 0不包含
    private Integer containDiscard;
    private List<String> pCategorys;

    private List<String> projectIds;
    /**
     * 查询是否返回项目下合同数量
     */
    private boolean statisticsConNum;
    /**
     * 查询是否返回项目下归档文件数量
     * 查询返回项目下归档文件数量且排除没有归档文件的项目信息
     */
    private boolean statisticsArchive;
    /**
     * 项目下是否包含合同数据
     */
    private boolean containsContract;
    /**
     * 是否是年度项目计划审批通过的
     */
    private boolean containsAnnual;
    /**
     * 年度项目计划年度
     */
    private String apYear;
    private String conCategory;
    private String conType;
    private List<String> pConstructionCosts;

    /**
     * 总项目规模
     */
    private String totalBuildingScale;

    private String icmReportingMonth;

    private String icmReportingYear;

    private Boolean invPlanFlag;

    private Boolean noAuth;

    /**
     * 项目类别集合
     */
    private List<String> ipvTypeList;

    private List<String> pCompetentDepartmentList;

    /**
     * 开工计划完成情况
     */
    private List<String> planCompleteResultList;

    /**
     * 完工计划完成情况
     */
    private List<String> completeResultList;

    /**
     * 投资计划完成率
     */
    private Float monthCumulativeInvProportion;

    private List<String> transferStatusList;

    /**
     * 项目匡算
     */
    private List<PreProjectDtlModel> preProjectDtl;

    /**
     * 数据来源类型
     */
    private String dataSourceType;

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

    private String pCategory;

    private String prCommitteeProjectCode;
    private String prCommitteeApprovaDocument;
    private String prCityLeaderMeetingMinutes;
    private String prCityLeaderInstructions;
    private String prOther;

    private String prCommitteeApprovaDocumentFileIds;
    private String prCityLeaderMeetingMinutesFileIds;
    private String prCityLeaderInstructionsFileIds;
    private String prOtherFileIds;
    private String isGroupInvestment;
    private String projectNature;
    private String projectInvestmentCategory;
    private String projectCategoryDetails;
    private String projectOwner;
    private String isHistory;
    private String projectCategory;
    private String filterByPlanState;

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

    private String pprId;
    @JsonProperty("pInvestmentConstitute")
    private String pInvestmentConstitute;
    @JsonProperty("pBuildModelRemark")
    private String pBuildModelRemark;
    @JsonProperty("pDepartment")
    private String pDepartment;

    /**
     * 用于筛选区分是正式项目 还是储备项目
     * 城建（pImpState 03 储备）
     */
    private Boolean formalProject;

    // 是否过滤年度投资计划中存在的项目  01 - 过滤  其他  - 不过滤
    private String planFilter;

    // 根据传入的类型过滤
    private String outlineProjectFilter;

    private String ganttPlanFilter;

    // 过滤有招标计划的项目
    private Boolean tenderPlanFilter;

    // 排除指定的项目ID
    private List<String> excludePIds;

    // 排除指定的项目名称
    private List<String> excludePNames;

    private String fsCostCode;

    private String pprIdFilter;

    private String biddingPlanNew;

    private String biddingPlanOld;

    public String getBiddingPlanOld() {
        return biddingPlanOld;
    }

    public void setBiddingPlanOld(String biddingPlanOld) {
        this.biddingPlanOld = biddingPlanOld;
    }

    public String getBiddingPlanNew() {
        return biddingPlanNew;
    }

    public void setBiddingPlanNew(String biddingPlanNew) {
        this.biddingPlanNew = biddingPlanNew;
    }

    public String getPprIdFilter() {
        return pprIdFilter;
    }

    public void setPprIdFilter(String pprIdFilter) {
        this.pprIdFilter = pprIdFilter;
    }

    public String getFsCostCode() {
        return fsCostCode;
    }

    public void setFsCostCode(String fsCostCode) {
        this.fsCostCode = fsCostCode;
    }

    public List<String> getExcludePIds() {
        return excludePIds;
    }

    public void setExcludePIds(List<String> excludePIds) {
        this.excludePIds = excludePIds;
    }

    public List<String> getExcludePNames() {
        return excludePNames;
    }

    public void setExcludePNames(List<String> excludePNames) {
        this.excludePNames = excludePNames;
    }

    public String getpItemTimeNew() {
        return pItemTimeNew;
    }

    public void setpItemTimeNew(String pItemTimeNew) {
        this.pItemTimeNew = pItemTimeNew;
    }

    public Boolean getTenderPlanFilter() {
        return tenderPlanFilter;
    }

    public void setTenderPlanFilter(Boolean tenderPlanFilter) {
        this.tenderPlanFilter = tenderPlanFilter;
    }

    public String getGanttPlanFilter() {
        return ganttPlanFilter;
    }

    public void setGanttPlanFilter(String ganttPlanFilter) {
        this.ganttPlanFilter = ganttPlanFilter;
    }

    public String getOutlineProjectFilter() {
        return outlineProjectFilter;
    }

    public void setOutlineProjectFilter(String outlineProjectFilter) {
        this.outlineProjectFilter = outlineProjectFilter;
    }

    public String getPlanFilter() {
        return planFilter;
    }

    public void setPlanFilter(String planFilter) {
        this.planFilter = planFilter;
    }

    public String getpDepartment() {
        return pDepartment;
    }

    public void setpDepartment(String pDepartment) {
        this.pDepartment = pDepartment;
    }
    public Boolean getFormalProject() {
        return formalProject;
    }

    public void setFormalProject(Boolean formalProject) {
        this.formalProject = formalProject;
    }

    public String getpInvestmentConstitute() {
        return pInvestmentConstitute;
    }

    public void setpInvestmentConstitute(String pInvestmentConstitute) {
        this.pInvestmentConstitute = pInvestmentConstitute;
    }

    public String getpBuildModelRemark() {
        return pBuildModelRemark;
    }

    public void setpBuildModelRemark(String pBuildModelRemark) {
        this.pBuildModelRemark = pBuildModelRemark;
    }

    public String getPprId() {
        return pprId;
    }

    public void setPprId(String pprId) {
        this.pprId = pprId;
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

    private String projectInitiatingUnit;

    public String getFilterByPlanState() {
        return filterByPlanState;
    }

    public void setFilterByPlanState(String filterByPlanState) {
        this.filterByPlanState = filterByPlanState;
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

    public String getPrCommitteeApprovaDocumentFileIds() {
        return prCommitteeApprovaDocumentFileIds;
    }

    public void setPrCommitteeApprovaDocumentFileIds(String prCommitteeApprovaDocumentFileIds) {
        this.prCommitteeApprovaDocumentFileIds = prCommitteeApprovaDocumentFileIds;
    }

    public String getPrCityLeaderMeetingMinutesFileIds() {
        return prCityLeaderMeetingMinutesFileIds;
    }

    public void setPrCityLeaderMeetingMinutesFileIds(String prCityLeaderMeetingMinutesFileIds) {
        this.prCityLeaderMeetingMinutesFileIds = prCityLeaderMeetingMinutesFileIds;
    }

    public String getPrCityLeaderInstructionsFileIds() {
        return prCityLeaderInstructionsFileIds;
    }

    public void setPrCityLeaderInstructionsFileIds(String prCityLeaderInstructionsFileIds) {
        this.prCityLeaderInstructionsFileIds = prCityLeaderInstructionsFileIds;
    }

    public String getPrOtherFileIds() {
        return prOtherFileIds;
    }

    public void setPrOtherFileIds(String prOtherFileIds) {
        this.prOtherFileIds = prOtherFileIds;
    }

    public String getpCategory() {
        return pCategory;
    }

    public void setpCategory(String pCategory) {
        this.pCategory = pCategory;
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

    public String getDataSourceType() {
        return dataSourceType;
    }

    public void setDataSourceType(String dataSourceType) {
        this.dataSourceType = dataSourceType;
    }

    public List<PreProjectDtlModel> getPreProjectDtl() {
        return preProjectDtl;
    }

    public void setPreProjectDtl(List<PreProjectDtlModel> preProjectDtl) {
        this.preProjectDtl = preProjectDtl;
    }

    public List<String> getTransferStatusList() {
        return transferStatusList;
    }

    public void setTransferStatusList(List<String> transferStatusList) {
        this.transferStatusList = transferStatusList;
    }

    public List<String> getPlanCompleteResultList() {
        return planCompleteResultList;
    }

    public void setPlanCompleteResultList(List<String> planCompleteResultList) {
        this.planCompleteResultList = planCompleteResultList;
    }

    public List<String> getCompleteResultList() {
        return completeResultList;
    }

    public void setCompleteResultList(List<String> completeResultList) {
        this.completeResultList = completeResultList;
    }

    public Float getMonthCumulativeInvProportion() {
        return monthCumulativeInvProportion;
    }

    public void setMonthCumulativeInvProportion(Float monthCumulativeInvProportion) {
        this.monthCumulativeInvProportion = monthCumulativeInvProportion;
    }

    public List<String> getpCompetentDepartmentList() {
        return pCompetentDepartmentList;
    }

    public void setpCompetentDepartmentList(List<String> pCompetentDepartmentList) {
        this.pCompetentDepartmentList = pCompetentDepartmentList;
    }

    public List<String> getIpvTypeList() {
        return ipvTypeList;
    }

    public void setIpvTypeList(List<String> ipvTypeList) {
        this.ipvTypeList = ipvTypeList;
    }

    public Boolean getNoAuth() {
        return noAuth;
    }

    public void setNoAuth(Boolean noAuth) {
        this.noAuth = noAuth;
    }

    public Boolean getInvPlanFlag() {
        return invPlanFlag;
    }

    public void setInvPlanFlag(Boolean invPlanFlag) {
        this.invPlanFlag = invPlanFlag;
    }

    public String getIcmReportingMonth() {
        return icmReportingMonth;
    }

    public void setIcmReportingMonth(String icmReportingMonth) {
        this.icmReportingMonth = icmReportingMonth;
    }

    public String getIcmReportingYear() {
        return icmReportingYear;
    }

    public void setIcmReportingYear(String icmReportingYear) {
        this.icmReportingYear = icmReportingYear;
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

    public String getpConstructionUnitName() {
        return pConstructionUnitName;
    }

    public void setpConstructionUnitName(String pConstructionUnitName) {
        this.pConstructionUnitName = pConstructionUnitName;
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

    public String getpAssignmentNo() {
        return pAssignmentNo;
    }

    public void setpAssignmentNo(String pAssignmentNo) {
        this.pAssignmentNo = pAssignmentNo;
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

    public String getpEstimateFile() {
        return pEstimateFile;
    }

    public void setpEstimateFile(String pEstimateFile) {
        this.pEstimateFile = pEstimateFile;
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

    public String getpAssignmentFileIds() {
        return pAssignmentFileIds;
    }

    public void setpAssignmentFileIds(String pAssignmentFileIds) {
        this.pAssignmentFileIds = pAssignmentFileIds;
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

    public String getpCommitteeApprovaDocumentIds() {
        return pCommitteeApprovaDocumentIds;
    }

    public void setpCommitteeApprovaDocumentIds(String pCommitteeApprovaDocumentIds) {
        this.pCommitteeApprovaDocumentIds = pCommitteeApprovaDocumentIds;
    }

    public String getpCityLeaderMeetingMinutesIds() {
        return pCityLeaderMeetingMinutesIds;
    }

    public void setpCityLeaderMeetingMinutesIds(String pCityLeaderMeetingMinutesIds) {
        this.pCityLeaderMeetingMinutesIds = pCityLeaderMeetingMinutesIds;
    }

    public String getpEstimateFileIds() {
        return pEstimateFileIds;
    }

    public void setpEstimateFileIds(String pEstimateFileIds) {
        this.pEstimateFileIds = pEstimateFileIds;
    }

    public String getpCityLeaderInstructionsIds() {
        return pCityLeaderInstructionsIds;
    }

    public void setpCityLeaderInstructionsIds(String pCityLeaderInstructionsIds) {
        this.pCityLeaderInstructionsIds = pCityLeaderInstructionsIds;
    }

    public String getpApprovaDocumentIds() {
        return pApprovaDocumentIds;
    }

    public void setpApprovaDocumentIds(String pApprovaDocumentIds) {
        this.pApprovaDocumentIds = pApprovaDocumentIds;
    }

    public String getpLeaderMeetingMinutesIds() {
        return pLeaderMeetingMinutesIds;
    }

    public void setpLeaderMeetingMinutesIds(String pLeaderMeetingMinutesIds) {
        this.pLeaderMeetingMinutesIds = pLeaderMeetingMinutesIds;
    }

    public String getpLeaderInstructionsIds() {
        return pLeaderInstructionsIds;
    }

    public void setpLeaderInstructionsIds(String pLeaderInstructionsIds) {
        this.pLeaderInstructionsIds = pLeaderInstructionsIds;
    }

    public String getpOtherIds() {
        return pOtherIds;
    }

    public void setpOtherIds(String pOtherIds) {
        this.pOtherIds = pOtherIds;
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

    public String getpInvFileIds() {
        return pInvFileIds;
    }

    public void setpInvFileIds(String pInvFileIds) {
        this.pInvFileIds = pInvFileIds;
    }

    public String getpPassVideoIds() {
        return pPassVideoIds;
    }

    public void setpPassVideoIds(String pPassVideoIds) {
        this.pPassVideoIds = pPassVideoIds;
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

    public String getpApproveFileIds() {
        return pApproveFileIds;
    }

    public void setpApproveFileIds(String pApproveFileIds) {
        this.pApproveFileIds = pApproveFileIds;
    }

    public String getpOpinionFileIds() {
        return pOpinionFileIds;
    }

    public void setpOpinionFileIds(String pOpinionFileIds) {
        this.pOpinionFileIds = pOpinionFileIds;
    }

    public String getpPlanFileIds() {
        return pPlanFileIds;
    }

    public void setpPlanFileIds(String pPlanFileIds) {
        this.pPlanFileIds = pPlanFileIds;
    }

    public String getpFeasibilityFileIds() {
        return pFeasibilityFileIds;
    }

    public void setpFeasibilityFileIds(String pFeasibilityFileIds) {
        this.pFeasibilityFileIds = pFeasibilityFileIds;
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

    public Boolean getFilterParentCollection() {
        return filterParentCollection;
    }

    public void setFilterParentCollection(Boolean filterParentCollection) {
        this.filterParentCollection = filterParentCollection;
    }

    public Boolean getConstructionFilter() {
        return constructionFilter;
    }

    public void setConstructionFilter(Boolean constructionFilter) {
        this.constructionFilter = constructionFilter;
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

    public List<ProFundNoDto> getProFundNos() {
        return proFundNos;
    }

    public void setProFundNos(List<ProFundNoDto> proFundNos) {
        this.proFundNos = proFundNos;
    }

    public String getpCompetentDepartmentName() {
        return pCompetentDepartmentName;
    }

    public void setpCompetentDepartmentName(String pCompetentDepartmentName) {
        this.pCompetentDepartmentName = pCompetentDepartmentName;
    }

    public String getpDepartmentName() {
        return pDepartmentName;
    }

    public void setpDepartmentName(String pDepartmentName) {
        this.pDepartmentName = pDepartmentName;
    }

    public List<String> getpPreItemTimes() {
        return pPreItemTimes;
    }

    public void setpPreItemTimes(List<String> pPreItemTimes) {
        this.pPreItemTimes = pPreItemTimes;
    }

    public List<String> getpItemTimes() {
        return pItemTimes;
    }

    public void setpItemTimes(List<String> pItemTimes) {
        this.pItemTimes = pItemTimes;
    }

    public List<String> getpUpdateTimes() {
        return pUpdateTimes;
    }

    public void setpUpdateTimes(List<String> pUpdateTimes) {
        this.pUpdateTimes = pUpdateTimes;
    }

    public List<String> getpCreateTimes() {
        return pCreateTimes;
    }

    public void setpCreateTimes(List<String> pCreateTimes) {
        this.pCreateTimes = pCreateTimes;
    }

    public List<String> getpFinalAccountsReportDates() {
        return pFinalAccountsReportDates;
    }

    public void setpFinalAccountsReportDates(List<String> pFinalAccountsReportDates) {
        this.pFinalAccountsReportDates = pFinalAccountsReportDates;
    }

    public List<String> getpApproveBudgetDates() {
        return pApproveBudgetDates;
    }

    public void setpApproveBudgetDates(List<String> pApproveBudgetDates) {
        this.pApproveBudgetDates = pApproveBudgetDates;
    }

    public List<String> getpReceiptTimes() {
        return pReceiptTimes;
    }

    public void setpReceiptTimes(List<String> pReceiptTimes) {
        this.pReceiptTimes = pReceiptTimes;
    }

    public List<String> getpAcceptanceRecordDates() {
        return pAcceptanceRecordDates;
    }

    public void setpAcceptanceRecordDates(List<String> pAcceptanceRecordDates) {
        this.pAcceptanceRecordDates = pAcceptanceRecordDates;
    }

    public List<String> getpHandoverDates() {
        return pHandoverDates;
    }

    public void setpHandoverDates(List<String> pHandoverDates) {
        this.pHandoverDates = pHandoverDates;
    }

    public List<String> getpInterventionDates() {
        return pInterventionDates;
    }

    public void setpInterventionDates(List<String> pInterventionDates) {
        this.pInterventionDates = pInterventionDates;
    }

    public List<String> getpActualStartTimes() {
        return pActualStartTimes;
    }

    public void setpActualStartTimes(List<String> pActualStartTimes) {
        this.pActualStartTimes = pActualStartTimes;
    }

    public List<String> getpActualCompleteDates() {
        return pActualCompleteDates;
    }

    public void setpActualCompleteDates(List<String> pActualCompleteDates) {
        this.pActualCompleteDates = pActualCompleteDates;
    }

    public List<String> getpPlanStartDates() {
        return pPlanStartDates;
    }

    public void setpPlanStartDates(List<String> pPlanStartDates) {
        this.pPlanStartDates = pPlanStartDates;
    }

    public List<String> getpPlanCompleteDates() {
        return pPlanCompleteDates;
    }

    public void setpPlanCompleteDates(List<String> pPlanCompleteDates) {
        this.pPlanCompleteDates = pPlanCompleteDates;
    }

    public List<String> getpConstructionAreas() {
        return pConstructionAreas;
    }

    public void setpConstructionAreas(List<String> pConstructionAreas) {
        this.pConstructionAreas = pConstructionAreas;
    }

    public List<String> getpLongitudes() {
        return pLongitudes;
    }

    public void setpLongitudes(List<String> pLongitudes) {
        this.pLongitudes = pLongitudes;
    }

    public List<String> getpLatitudes() {
        return pLatitudes;
    }

    public void setpLatitudes(List<String> pLatitudes) {
        this.pLatitudes = pLatitudes;
    }

    public List<String> getpBuildingScales() {
        return pBuildingScales;
    }

    public void setpBuildingScales(List<String> pBuildingScales) {
        this.pBuildingScales = pBuildingScales;
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

    public List<String> getpInterventionYears() {
        return pInterventionYears;
    }

    public void setpInterventionYears(List<String> pInterventionYears) {
        this.pInterventionYears = pInterventionYears;
    }

    public List<String> getpInterventionEndYears() {
        return pInterventionEndYears;
    }

    public void setpInterventionEndYears(List<String> pInterventionEndYears) {
        this.pInterventionEndYears = pInterventionEndYears;
    }

    public List<String> getpInvTotals() {
        return pInvTotals;
    }

    public void setpInvTotals(List<String> pInvTotals) {
        this.pInvTotals = pInvTotals;
    }

    public List<String> getpInvDates() {
        return pInvDates;
    }

    public void setpInvDates(List<String> pInvDates) {
        this.pInvDates = pInvDates;
    }

    public List<String> getpCalculateRoughlyAmounts() {
        return pCalculateRoughlyAmounts;
    }

    public void setpCalculateRoughlyAmounts(List<String> pCalculateRoughlyAmounts) {
        this.pCalculateRoughlyAmounts = pCalculateRoughlyAmounts;
    }

    public List<String> getpEstimateAmounts() {
        return pEstimateAmounts;
    }

    public void setpEstimateAmounts(List<String> pEstimateAmounts) {
        this.pEstimateAmounts = pEstimateAmounts;
    }

    public List<String> getpApproveBudgets() {
        return pApproveBudgets;
    }

    public void setpApproveBudgets(List<String> pApproveBudgets) {
        this.pApproveBudgets = pApproveBudgets;
    }

    public List<String> getpTotalDurations() {
        return pTotalDurations;
    }

    public void setpTotalDurations(List<String> pTotalDurations) {
        this.pTotalDurations = pTotalDurations;
    }

    public List<String> getpActualTotalDurations() {
        return pActualTotalDurations;
    }

    public void setpActualTotalDurations(List<String> pActualTotalDurations) {
        this.pActualTotalDurations = pActualTotalDurations;
    }

    public String getpApprovalUnit() {
        return pApprovalUnit;
    }

    public void setpApprovalUnit(String pApprovalUnit) {
        this.pApprovalUnit = pApprovalUnit;
    }

    public Integer getExcludePa() {
        return excludePa;
    }

    public void setExcludePa(Integer excludePa) {
        this.excludePa = excludePa;
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

    public String getpConstructionFileIds() {
        return pConstructionFileIds;
    }

    public void setpConstructionFileIds(String pConstructionFileIds) {
        this.pConstructionFileIds = pConstructionFileIds;
    }

    public String getpSbgjgzfFileIds() {
        return pSbgjgzfFileIds;
    }

    public void setpSbgjgzfFileIds(String pSbgjgzfFileIds) {
        this.pSbgjgzfFileIds = pSbgjgzfFileIds;
    }

    public String getpGcjsqtFileIds() {
        return pGcjsqtFileIds;
    }

    public void setpGcjsqtFileIds(String pGcjsqtFileIds) {
        this.pGcjsqtFileIds = pGcjsqtFileIds;
    }

    public String getpYbFileIds() {
        return pYbFileIds;
    }

    public void setpYbFileIds(String pYbFileIds) {
        this.pYbFileIds = pYbFileIds;
    }

    public String getpEconomicFileIds() {
        return pEconomicFileIds;
    }

    public void setpEconomicFileIds(String pEconomicFileIds) {
        this.pEconomicFileIds = pEconomicFileIds;
    }

    public String getpProposalFileIds() {
        return pProposalFileIds;
    }

    public void setpProposalFileIds(String pProposalFileIds) {
        this.pProposalFileIds = pProposalFileIds;
    }

    public String getpReportFileIds() {
        return pReportFileIds;
    }

    public void setpReportFileIds(String pReportFileIds) {
        this.pReportFileIds = pReportFileIds;
    }

    public List<String> getpConstructionStarts() {
        return pConstructionStarts;
    }

    public void setpConstructionStarts(List<String> pConstructionStarts) {
        this.pConstructionStarts = pConstructionStarts;
    }

    public List<String> getpConstructionEnds() {
        return pConstructionEnds;
    }

    public void setpConstructionEnds(List<String> pConstructionEnds) {
        this.pConstructionEnds = pConstructionEnds;
    }

    public Integer getContainDiscard() {
        return containDiscard;
    }

    public void setContainDiscard(Integer containDiscard) {
        this.containDiscard = containDiscard;
    }

    public List<String> getpCategorys() {
        return pCategorys;
    }

    public void setpCategorys(List<String> pCategorys) {
        this.pCategorys = pCategorys;
    }

    public boolean isStatisticsConNum() {
        return statisticsConNum;
    }

    public void setStatisticsConNum(boolean statisticsConNum) {
        this.statisticsConNum = statisticsConNum;
    }

    public boolean isStatisticsArchive() {
        return statisticsArchive;
    }

    public void setStatisticsArchive(boolean statisticsArchive) {
        this.statisticsArchive = statisticsArchive;
    }

    public boolean isContainsContract() {
        return containsContract;
    }

    public void setContainsContract(boolean containsContract) {
        this.containsContract = containsContract;
    }

    public boolean isContainsAnnual() {
        return containsAnnual;
    }

    public void setContainsAnnual(boolean containsAnnual) {
        this.containsAnnual = containsAnnual;
    }

    public String getApYear() {
        return apYear;
    }

    public void setApYear(String apYear) {
        this.apYear = apYear;
    }

    public String getConCategory() {
        return conCategory;
    }

    public void setConCategory(String conCategory) {
        this.conCategory = conCategory;
    }

    public String getConType() {
        return conType;
    }

    public void setConType(String conType) {
        this.conType = conType;
    }

    public List<String> getpConstructionCosts() {
        return pConstructionCosts;
    }

    public void setpConstructionCosts(List<String> pConstructionCosts) {
        this.pConstructionCosts = pConstructionCosts;
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

    public String getpRecordFileIds() {
        return pRecordFileIds;
    }

    public void setpRecordFileIds(String pRecordFileIds) {
        this.pRecordFileIds = pRecordFileIds;
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

    public String getpApplyFileIds() {
        return pApplyFileIds;
    }

    public void setpApplyFileIds(String pApplyFileIds) {
        this.pApplyFileIds = pApplyFileIds;
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

    public String getpEngineeringspecialty() {
        return pEngineeringspecialty;
    }

    public void setpEngineeringspecialty(String pEngineeringspecialty) {
        this.pEngineeringspecialty = pEngineeringspecialty;
    }

    public Boolean getHaveGantPlan() {
        return haveGantPlan;
    }

    public void setHaveGantPlan(Boolean haveGantPlan) {
        this.haveGantPlan = haveGantPlan;
    }

    public String getpProjectFormType() {
        return pProjectFormType;
    }

    public void setpProjectFormType(String pProjectFormType) {
        this.pProjectFormType = pProjectFormType;
    }

    public String getpBuildYear() {
        return pBuildYear;
    }

    public void setpBuildYear(String pBuildYear) {
        this.pBuildYear = pBuildYear;
    }

    public String getpRecordFiles() {
        return pRecordFile;
    }

    public void setpRecordFiles(String pRecordFile) {
        this.pRecordFile = pRecordFile;
    }

    public String getpApplyFiles() {
        return pApplyFile;
    }

    public void setpApplyFiles(String pApplyFile) {
        this.pApplyFile = pApplyFile;
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

    public List<String> getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(List<String> projectIds) {
        this.projectIds = projectIds;
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
}
