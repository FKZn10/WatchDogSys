package com.jpmsoft.gmgws.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jpmsoft.gmgws.pojo.entity.ConPayApplyFlow;
import com.jpmsoft.gmgws.pojo.entity.InvProjectPlan;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class PreProjectVo {

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

    private String ippDocNumber;


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
    private String pConstructionUnitName;
    private String pCompetentDepartment;
    private String pProjectDirector;
    private String pProjectStage;
    private String pProjectStatus;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date pPlanStartDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
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

    private List<InvProjectPlan> invProjectPlans;

    private List<ConPayApplyFlow> conPayApplyFlows;
    private List<ContractTotlaVo> contractTotlas;


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
    private String pInvFileIds;
    private String pPassVideoIds;
    private String pBuildModel;
    private Integer pInterventionEndYear;
    private String pEachDistrict;
    private String pApprovalUnit;
    private Date pConstructionStart;
    private Date pConstructionEnd;
    private String pCheckType;
    private String pProgressSituation;
    private String pRecordNumber;
    private String pEconomicFile;
    private String pProposalFile;
    private String pReportFile;
    private String pInvCategory;
    private Integer pAdvanceFund;
    private Integer pCorporateRisk;

    private String pSafetyFiling;
    private String cplId;

    private String pEconomicFileIds;
    private String pProposalFileIds;
    private String pReportFileIds;
}
