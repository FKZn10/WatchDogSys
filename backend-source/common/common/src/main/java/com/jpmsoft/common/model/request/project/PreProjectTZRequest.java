package com.jpmsoft.common.model.request.project;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.annotations.ClassInfo;
import com.jpmsoft.common.model.BaseFileRequest;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectDtlModel;
import com.jpmsoft.common.model.requestAndResponse.Project.ProConstructionAreaDtlModel;
import com.jpmsoft.common.model.requestAndResponse.Project.ProHouseDtlModel;
import com.jpmsoft.common.model.requestAndResponse.Project.ProSourcesFundsModel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
@Data
public class PreProjectTZRequest extends BaseFileRequest implements Serializable {

    private static final long serialVersionUID = 5297966162168453336L;

    private String pId;
    private String prId;
    @ClassInfo(title = "项目编码",type="string",value = "projectCode",dbValue = "mn.P_Code")
    private List<String> projectCode;
    private String pParentId;
    @ClassInfo(title = "项目名称",type="string",value = "projectName",dbValue = "mn.P_Name",no = 1)
    private List<String> projectName;
    private String pProjectAbbreviation;
    private String pOriginalName;
    private String pNameInitials;
    private String pProjectCode;
    private String pAddress;
    @ClassInfo(title = "所属区域",type="config",value = "projectRegion",dictionary = "projectArea",dbValue = "mn.P_Region")
    private String projectRegion;
    private Float pConstructionArea;
    @ClassInfo(title = "项目经度(X)",type="number",value = "projectLongitude",dbValue = "mn.P_Longitude")
    private Float projectLongitude;
    @ClassInfo(title = "项目纬度(Y)",type="number",value = "projectLatitude",dbValue = "mn.P_Latitude")
    private Float projectLatitude;
    @ClassInfo(title = "项目概况",type="string",value = "projectOverview",dbValue = "mn.P_Overview")
    private List<String> projectOverview;
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
    //@ClassInfo(title = "专业分类",type="dictionary",value = "projectType",dictionary = "JPMsoft_XMLB",dbValue = "P_Type" ,no = 4)
    private List<String> projectType;
    //@ClassInfo(title = "重大项目类别",type="dictionary",value = "projectMajorType",dictionary = "JPMsoft_ZDXMLB",dbValue = "P_Major_Type")
    private List<String> projectMajorType;
    private String projectConstructionUnit;
    private String pConstructionUnitName;
    @JsonProperty("pCompetentDepartment")
    @ClassInfo(title = "项目管理单位",type="organization",value = "pCompetentDepartment",dbValue = "mn.P_Competent_Department",no = 9)
    private String pCompetentDepartment;
    private String pProjectDirector;
    // @ClassInfo(title = "项目阶段",type="dictionary",value = "projectProjectStage",dictionary = "JPMsoft_XMJD",dbValue = "P_Project_Stage")
    @ClassInfo(title = "项目阶段",type="string",value = "projectProjectStage")
    private List<String> projectProjectStage;
    private String pProjectStatus;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    //@ClassInfo(title = "计划开工时间",type="date",value = "projectPlanStartDate",dbValue = "P_Plan_Start_Date")
    private Date projectPlanStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    //@ClassInfo(title = "计划竣工时间",type="date",value = "projectPlanCompleteDate",dbValue = "P_Plan_Complete_Date")
    private Date projectPlanCompleteDate;
    //@ClassInfo(title = "建设周期",type="number",value = "projectTotalDuration",dbValue = "P_Total_Duration")
    private List<Integer> projectTotalDuration;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    //@ClassInfo(title = "实际开工时间",type="date",value = "projectActualStartTime",dbValue = "P_Actual_Start_Time")
    private Date projectActualStartTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    //@ClassInfo(title = "实际竣工时间",type="date",value = "projectActualCompleteDate",dbValue = "P_Actual_Complete_Date")
    private Date projectActualCompleteDate;
    //@ClassInfo(title = "实际建设周期",type="number",value = "projectActualTotalDuration",dbValue = "P_Actual_Total_Duration")
    private List<String> projectActualTotalDuration;
    private Date pInterventionDate;
    private Integer pInterventionYear;
    private Date pReceiptTime;
    private Date pAcceptanceRecordDate;
    private Date pHandoverDate;
    private String pIsProjectSummary;
    private List<String> projectUseUnit;
    //@ClassInfo(title = "资金来源",type="dictionary",value = "projectSourcesFunds",dictionary = "JPMsoft_XMZJLY",dbValue = "P_Sources_Funds")
    private List<String> projectSourcesFunds;
    //@ClassInfo(title = "匡算投资",type="money",value = "projectCalculateRoughlyAmount",dbValue = "P_Calculate_Roughly_Amount")
    private List<BigDecimal> projectCalculateRoughlyAmount;
    private BigDecimal pEstimateAmount;
    @ClassInfo(title = "批复投资估算",type="money",value = "projectApproveBudget",dbValue = "mn.P_Approve_Budget")
    private List<BigDecimal> projectApproveBudget;
    @ClassInfo(title = "批复文号",type="string",value = "projectApproveBudgetCode",dbValue = "mn.P_Approve_Budget_Code")
    private List<String> projectApproveBudgetCode;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ClassInfo(title = "批复日期",type="date",value = "projectApproveBudgetDate",dbValue = "mn.P_Approve_Budget_Date")
    private Date projectApproveBudgetDate;
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
    @ClassInfo(title = "创建人",type="string",value = "projectCreateUserName",dbValue = "mn.P_Create_User_Name",no = 10)
    private List<String> projectCreateUserName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ClassInfo(title = "创建时间",type="date",value = "projectCreateTime",dbValue = "mn.P_Create_Time",no = 11)
    private Date projectCreateTime;
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
    private String pCommitteeApprovaDocument;
    private String pCityLeaderMeetingMinutes;
    private String pCityLeaderInstructions;
    private String pOther;
    private String pCommitteeApprovaDocumentIds;
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
    //@ClassInfo(title = "是否取得立项批复",type="dictionary",value = "projectIsProjectApproval",dictionary = "JPMsoft_ZDTY",dbValue = "P_Is_Project_Approval")
    private List<String> projectIsProjectApproval;
    //@ClassInfo(title = "建筑规模",type="number",value = "pBuildingScale",dbValue = "P_Building_Scale")
    private Float projectBuildingScale;
    //@ClassInfo(title = "建筑单位",type="dictionary",value = "projectBuildingUnit",dictionary = "JPMsoft_JZGMDW",dbValue = "P_Building_Unit")
    private List<String> projectBuildingUnit;
    //@ClassInfo(title = "项目类别（监管）",type="dictionary",value = "projectSuperviseType",dictionary = "JPMsoft_XMLBJG",dbValue = "P_Supervise_Type")
    private List<String> projectSuperviseType;
    private String pGroupType;
    // @ClassInfo(title = "流程状态",type="dictionary",value = "state",dictionary = "JPMsoft_LCZT",dbValue = "mn.state",no = 12)
    private List<Integer> state;
    @ClassInfo(title = "流程状态",type="dictionary",value = "stateList",dictionary = "JPMsoft_LCZT",dbValue = "mn.state",no = 12)
    private List<Integer> stateList;
    private Date stateTime;
    private String pRemarkPlanned;
    private String pPlanNumber;
    private Date pConsRelease;
    //@ClassInfo(title = "代建单位/实施单位",type="string",value = "projectImpCompany",dbValue = "P_Imp_Company",no = 8)
    private List<String> projectImpCompany;
    private String pImpState;
    //@ClassInfo(title = "文件标题",type="string",value = "projectApplyTilte",dbValue = "P_Apply_Tilte")
    private List<String> projectApplyTilte;
    //@ClassInfo(title = "项目总投资",type="money",value = "projectInvTotal",dbValue = "P_Inv_Total",no = 5)
    private List<BigDecimal> projectInvTotal;
   // @ClassInfo(title = "投资口径",type="dictionary",value = "projectInvCaliber",dictionary = "JPMsoft_TZKJ",dbValue = "P_Inv_Caliber")
    private List<String> projectInvCaliber;
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
    @ClassInfo(title = "建设模式",type="dictionary",value = "projectBuildModel",dictionary = "JPMsoft_JSMS",dbValue = "mn.P_Build_Model",no = 3)
    private List<String> projectBuildModel;
    private Integer pInterventionEndYear;
    private String pEachDistrict;
    private String pApprovalUnit;
    //@ClassInfo(title = "起止年月",type="date",value = "pConstructionStart",dbValue = "P_Construction_Start",no = 6)
    private Date pConstructionStart;
    //@ClassInfo(title = "竣工年月",type="date",value = "pConstructionEnd",dbValue = "P_Construction_End",no = 7)
    private Date pConstructionEnd;
    private String pCheckType;
    private String pProgressSituation;
    private String pRecordNumber;
    private String pEconomicFile;
    private String pProposalFile;
    private String pReportFile;
    //@ClassInfo(title = "投资类别",type="dictionary",value = "projectInvCategory",dictionary = "JPMsoft_TZLB",dbValue = "P_Inv_Category")
    private List<String> projectInvCategory;
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
    //@ClassInfo(title = "初设批复文号",type="string",value = "projectInitialApprovalNumber",dbValue = "P_Initial_Approval_Number")
    private List<String> projectInitialApprovalNumber;
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
    @ClassInfo(title = "立项形式",type="dictionary",value = "pProjectFormType",dictionary = "JPMsoft_LXXS",dbValue = "mn.P_Project_Form_Type")
    private List<String> pProjectFormType;
    //项目代码
    @ClassInfo(title = "项目代码",type="string",value = "pProjectNumber",dbValue = "mn.P_Project_Number")
    private List<String> pProjectNumber;
    //批复投资估算
    @ClassInfo(title = "批复投资估算",type="money",value = "pApprovalInvestmentAmount",dbValue = "mn.P_Approval_Investment_Amount")
    private List<BigDecimal> pApprovalInvestmentAmount;
    //可研申报单位
    @ClassInfo(title = "项目法人单位",type="company",value = "pCanYanApplyUnit",dbValue = "mn.P_Can_Yan_Apply_Unit")
    private List<String> pCanYanApplyUnit;
    //可研审批单位
    @ClassInfo(title = "项目法人单位",type="company",value = "pCanYanApprovalUnit",dbValue = "mn.P_Can_Yan_Approval_Unit")
    private List<String> pCanYanApprovalUnit;
    //备案文号
    @ClassInfo(title = "备案证号",type="string",value = "pRecordNum",dbValue = "mn.P_Record_Num")
    private List<String> pRecordNum;
    //备案时间
    @ClassInfo(title = "备案日期",type="date",value = "pRecordDate",dbValue = "mn.P_Record_Date")
    private Date pRecordDate;
    //备案文件Ids
    private String pRecordFile;
    private String pRecordFileIds;
    //备案总投资
    @ClassInfo(title = "备案总投资",type="money",value = "pRecordAmount",dbValue = "mn.P_Record_Amount")
    private List<BigDecimal> pRecordAmount;
    //项目法人单位
    @ClassInfo(title = "项目法人单位",type="company",value = "pLegalUnit",dbValue = "mn.P_Legal_Unit")
    private List<String> pLegalUnit;
    //备案证核发单位
    @ClassInfo(title = "备案证核发单位",type="company",value = "pRecordIssueUnit",dbValue = "mn.P_Record_Issue_Unit")
    private List<String> pRecordIssueUnit;
    //立项文件上传
    private String pApplyFile;
    private String pApplyFileIds;
    //立项日期
    @ClassInfo(title = "立项日期",type="date",value = "pApplyDate",dbValue = "mn.P_Apply_Date")
    private Date pApplyDate;
    //建设年限
    @ClassInfo(title = "建设年限",type="string",value = "pBuildYear",dbValue = "mn.P_Build_Year")
    private List<String> pBuildYear;
    //投资估算
    @ClassInfo(title = "投资估算",type="money",value = "pApprovalAmount",dbValue = "mn.P_Approval_Amount")
    private List<BigDecimal> pApprovalAmount;
    //建设形式
    @ClassInfo(title = "建设形式",type="dictionary",value = "pBuildType",dictionary = "JPMsoft_JSXS",dbValue = "mn.P_Build_Type")
    private List<String> pBuildType;
    //专业分类
    @ClassInfo(title = "专业分类",type="dictionary",value = "pProfessionalType",dictionary = "JPMsoft_XMLB",dbValue = "mn.P_Professional_Type")
    private List<String> pProfessionalType;
    //移交接收单位
    @ClassInfo(title = "移交接收单位",type="company",value = "pTransferUnit",dbValue = "mn.P_Transfer_Unit")
    private List<String> pTransferUnit;
    //项目经理
    @ClassInfo(title = "项目经理",type="person",value = "pProjectManager",dbValue = "mn.P_Project_Manager")
    private List<String> pProjectManager;
    //项目分管领导
    @ClassInfo(title = "项目分管领导",type="person",value = "pProjectLeader",dbValue = "mn.P_Project_Leader")
    private List<String> pProjectLeader;
    @ClassInfo(title = "管理单位所属部门",type="department",value = "projectDepartment",dbValue = "mn.P_Department")
    private String projectDepartment;

    //工程部经办人
    @ClassInfo(title = "工程部经办人",type="person",value = "pEngineer",dbValue = "mn.P_Engineer")
    private List<String>  pEngineer;
    //工程部分管领导
    @ClassInfo(title = "工程部分管领导",type="person",value = "pEngineerLeader",dbValue = "mn.P_Engineer_Leader")
    private List<String>  pEngineerLeader;
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
    private List<String>  pConstructionCosts;

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
     *  项目匡算
     */
    private List<PreProjectDtlModel> preProjectDtl;
    /**
     * 分支机构
     */
    @ClassInfo(title = "分支机构",type="department",value = "pDepartment",dbValue = "mn.P_Department")
    @JsonProperty("pDepartment")
    private String  pDepartment;

    @JsonProperty("pProjectStageOrganization")
    private String pProjectStageOrganization;
    @ClassInfo(title = "项目阶段状态",type="dictionary",value = "pProjectStageOrganizations",dictionary = "JPMsoft_XMJDZT",dbValue = "(case when mn.State != 3 and mn.State != 7 and mn.ppr_id is not null then '00' else gms.P_Project_Stage end)")
    @JsonProperty("pProjectStageOrganizations")
    private List<String> pProjectStageOrganizations;

    private String noReserves;
}
