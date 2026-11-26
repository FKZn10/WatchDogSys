package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目信息
 */
@Data
public class PreProject {
    @TableId(type = IdType.ASSIGN_ID)
    private String pId;
    private String prId;
    private String pCode;
    private String pParentId;
    private String pName;
    private String pProjectAbbreviation;
    private String pOriginalName;
    private String pNameInitials;
    private String pProjectCode;
    private String pAddress;
    private String pRegion;
    private Float pConstructionArea;
    private Float pLongitude;
    private Float pLatitude;
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
    private String pType;
    private String pMajorType;
    private String pConstructionUnit;
    private String pCompetentDepartment;
    private String pProjectDirector;
    private String pProjectStage;
    private String pProjectStatus;
    private Date pPlanStartDate;
    private Date pPlanCompleteDate;
    private Integer pTotalDuration;
    private Date pActualStartTime;
    private Date pActualCompleteDate;
    private String pActualTotalDuration;
    private Date pInterventionDate;
    private Integer pInterventionYear;
    private Date pReceiptTime;
    private Date pAcceptanceRecordDate;
    private Date pHandoverDate;
    private String pIsProjectSummary;
    private String pUseUnit;
    private String pSourcesFunds;
    private BigDecimal pCalculateRoughlyAmount;
    private BigDecimal pEstimateAmount;
    private BigDecimal pApproveBudget;
    private String pApproveBudgetCode;
    private Date pApproveBudgetDate;
    private BigDecimal pFinalAccounts;
    private Date pFinalAccountsReportDate;
    private String pConstructionProjectPlanningPermitCode;
    private String pConstructionLandPlanningPermitcode;
    private String pOpinionsOnSiteSelectionForConstructionProjectsCode;
    private String pLandUseRightAssignmentContractCode;
    //可研批复文件编号
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
    private String pCreateUserName;
    private Date pCreateTime;
    private String pUpdateUserId;
    private Date pUpdateTime;
    private String pSerialNumber;
    private Boolean deleted;
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
    private String pCmMode;
    private String pAssignmentFile;
    private String pNameHistory;
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
    private Boolean pIsProjectApproval;
    private Float pBuildingScale;
    private String pBuildingUnit;
    private String pSuperviseType;
    private String pGroupType;
    private Integer state;
    private Date stateTime;
    private String pRemarkPlanned;
    private String pPlanNumber;
    private Date pConsRelease;
    private String pImpCompany;
    private String pImpState;
    private String pApplyTilte;
    private BigDecimal pInvTotal;
    private String pInvCaliber;
    private String pInvTilte;
    private String pInvCode;
    private Date pInvDate;
    private String pInvFile;
    private String pPassVideo;
    private String pBuildModel;
    private Integer pInterventionEndYear;
    private String pEachDistrict;
    private String pApprovalUnit;
    private Date pConstructionStart;
    private Date pConstructionEnd;
    private String pCheckType;
    private String pProgressSituation;
    private String pRecordNumber;

    private String pCategory;
    private String pEconomicFile;
    private String pProposalFile;
    private String pReportFile;
    private String pInvCategory;
    private Integer pAdvanceFund;
    private Integer pCorporateRisk;
    private String pSafetyFiling;
    private String cplId;
    private String pApplyName;
    private BigDecimal pFiscalBudget;
    private String teId;
    private String pCampus;
    private String pAuditUnit;
    private String pCostUnit;
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

    /**
     * 总项目规模
     */
    private String totalBuildingScale;
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
    //省市区县
    private String pAddressDetail;
    //初设批复文号
    private String pInitialApprovalNumber;
    //初设批复金额
    private BigDecimal pInitialApprovalAmount;
    //结算审计报告出具时间
    private Date pSettlementAuditReportDate;
    //结算审计审定金额
    private BigDecimal pSettlementAuditAmount;
    /**
     * 项目授权类型(内部用户)
     * null或者1正常鉴权
     * 2 项目完全按项目组织机构鉴权
     */
    private Integer pAuthType;
    //立项形式 01可研批复、02投资备案证、03其他
    private String pProjectFormType;
    //项目代码
    private String pProjectNumber;
    //批复投资估算
    private BigDecimal pApprovalInvestmentAmount;
    //可研申报单位
    private String pCanYanApplyUnit;
    //可研审批单位
    private String pCanYanApprovalUnit;
    //备案文号
    private String pRecordNum;
    //备案时间
    private Date pRecordDate;
    //备案文件Ids
    private String pRecordFile;
    //备案总投资
    private BigDecimal pRecordAmount;
    //项目法人单位
    private String pLegalUnit;
    //备案证核发单位
    private String pRecordIssueUnit;
    //立项文件上传
    private String pApplyFile;
    //立项日期
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
    //工程部经办人
    private String pEngineer;
    //工程部分管领导
    private String pEngineerLeader;
    //东华项目编号
    private String pDhCode;

}
