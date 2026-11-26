package com.jpmsoft.common.model.requestAndResponse.Project;

import com.jpmsoft.common.model.BaseFileRequest;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class PreProjectChangeModel extends BaseFileRequest implements Serializable {

    private static final long serialVersionUID = -4783864843472827115L;

    private String pcId;
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
    private Boolean pIsProjectApproval;
    private Float pBuildingScale;
    private String pBuildingUnit;
    private String pSuperviseType;
    private String pGroupType;

    //项目流程附件
    private String pRelationFile;
    private String pRelationFileIds;
    private String pcCreateUid;
    private String pcCreateUname;
    private Date pcCreateTime;
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
    private String pInvFileIds;
    private String pBuildModel;
    private Integer pInterventionEndYear;
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
    private String pEconomicFileIds;
    private String pProposalFileIds;
    private String pReportFileIds;
    private String pInvCategory;
    private Integer pAdvanceFund;
    private Integer pCorporateRisk;
    private String pSafetyFiling;

    /**
     * 项目建筑
     */
    private List<PcConstructionAreaDtlModel> pcConstructionAreaDtls;
    /**
     * 项目资金来源
     */
    private List<PcSourcesFundsModel> pcSourcesFunds;
    /**
     * 项目投资详情
     */
    private List<PcSourcesFundsModel> pcInvDetails;


    private String pConstructionUnitNameHistory;
    private String pAgentUnitNameHistory;
    private String pDesignUnitNameHistory;
    private String pAgentConstructionUnitName;
    private String pDesignAndConstructionIntegrationUnitName;
    private String pParentName;
    private String pParentNameHistory;
    private String pParentCode;
    //单位简称
    private String pAgentConsAbbName;
    private String pDesConsIntegAbbName;
    /**
     * 是否暂存
     */
    private Boolean tempSave;

    public String getPcId() {
        return pcId;
    }

    public void setPcId(String pcId) {
        this.pcId = pcId;
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

    public String getpRelationFile() {
        return pRelationFile;
    }

    public void setpRelationFile(String pRelationFile) {
        this.pRelationFile = pRelationFile;
    }

    public String getpRelationFileIds() {
        return pRelationFileIds;
    }

    public void setpRelationFileIds(String pRelationFileIds) {
        this.pRelationFileIds = pRelationFileIds;
    }

    public String getPcCreateUid() {
        return pcCreateUid;
    }

    public void setPcCreateUid(String pcCreateUid) {
        this.pcCreateUid = pcCreateUid;
    }

    public String getPcCreateUname() {
        return pcCreateUname;
    }

    public void setPcCreateUname(String pcCreateUname) {
        this.pcCreateUname = pcCreateUname;
    }

    public Date getPcCreateTime() {
        return pcCreateTime;
    }

    public void setPcCreateTime(Date pcCreateTime) {
        this.pcCreateTime = pcCreateTime;
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

    public String getpInvFileIds() {
        return pInvFileIds;
    }

    public void setpInvFileIds(String pInvFileIds) {
        this.pInvFileIds = pInvFileIds;
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

    public List<PcConstructionAreaDtlModel> getPcConstructionAreaDtls() {
        return pcConstructionAreaDtls;
    }

    public void setPcConstructionAreaDtls(List<PcConstructionAreaDtlModel> pcConstructionAreaDtls) {
        this.pcConstructionAreaDtls = pcConstructionAreaDtls;
    }

    public List<PcSourcesFundsModel> getPcSourcesFunds() {
        return pcSourcesFunds;
    }

    public void setPcSourcesFunds(List<PcSourcesFundsModel> pcSourcesFunds) {
        this.pcSourcesFunds = pcSourcesFunds;
    }


    public List<PcSourcesFundsModel> getPcInvDetails() {
        return pcInvDetails;
    }

    public void setPcInvDetails(List<PcSourcesFundsModel> pcInvDetails) {
        this.pcInvDetails = pcInvDetails;
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

    public String getpParentNameHistory() {
        return pParentNameHistory;
    }

    public void setpParentNameHistory(String pParentNameHistory) {
        this.pParentNameHistory = pParentNameHistory;
    }

    public String getpParentCode() {
        return pParentCode;
    }

    public void setpParentCode(String pParentCode) {
        this.pParentCode = pParentCode;
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

    public Boolean getTempSave() {
        return tempSave;
    }

    public void setTempSave(Boolean tempSave) {
        this.tempSave = tempSave;
    }
}
