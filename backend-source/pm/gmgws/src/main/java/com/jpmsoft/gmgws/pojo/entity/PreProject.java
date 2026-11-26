package com.jpmsoft.gmgws.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("Pre_Project")
public class PreProject {
    @TableField(value = "P_ID")
    private String pId;

    @TableField(value = "PR_ID")
    private String prId;

    @TableField(value = "P_Code")
    private String pCode;

    @TableField(value = "P_Parent_ID")
    private String pParentId;

    @TableField(value = "P_Name")
    private String pName;

    @TableField(value = "P_Project_Abbreviation")
    private String pProjectAbbreviation;

    @TableField(value = "P_Original_Name")
    private String pOriginalName;

    @TableField(value = "P_Name_Initials")
    private String pNameInitials;

    @TableField(value = "P_Project_Code")
    private String pProjectCode;

    @TableField(value = "P_Address")
    private String pAddress;

    @TableField(value = "P_Region")
    private String pRegion;

    @TableField(value = "P_Construction_Area")
    private Float pConstructionArea;

    @TableField(value = "P_Longitude")
    private Float pLongitude;

    @TableField(value = "P_Latitude")
    private Float pLatitude;

    @TableField(value = "P_Overview")
    private String pOverview;

    @TableField(value = "P_Volume_Rate")
    private Float pVolumeRate;

    @TableField(value = "P_Land_Area")
    private Float pLandArea;

    @TableField(value = "P_Seat")
    private Integer pSeat;

    @TableField(value = "P_Number_Of_Elevators")
    private Integer pNumberOfElevators;

    @TableField(value = "P_Max_Height")
    private Float pMaxHeight;

    @TableField(value = "P_Parking_Number")
    private Integer pParkingNumber;

    @TableField(value = "P_Buildings_Number")
    private Integer pBuildingsNumber;

    @TableField(value = "P_Design_Life")
    private Integer pDesignLife;

    @TableField(value = "P_Super_Structure")
    private String pSuperStructure;

    @TableField(value = "P_Basic_Structure")
    private String pBasicStructure;

    @TableField(value = "P_Ground_Floors")
    private Integer pGroundFloors;

    @TableField(value = "P_Under_Ground_Floors")
    private Integer pUnderGroundFloors;

    @TableField(value = "P_Is_Gas")
    private String pIsGas;

    @TableField(value = "P_Basement_Area")
    private Float pBasementArea;

    @TableField(value = "P_Is_Air_Defense")
    private String pIsAirDefense;

    @TableField(value = "P_Air_Defense_Area")
    private Float pAirDefenseArea;

    @TableField(value = "P_Type")
    private String pType;

    @TableField(value = "P_Major_Type")
    private String pMajorType;

    @TableField(value = "P_Construction_Unit")
    private String pConstructionUnit;

    @TableField(value = "P_Competent_Department")
    private String pCompetentDepartment;

    @TableField(value = "P_Project_Director")
    private String pProjectDirector;

    @TableField(value = "P_Project_Stage")
    private String pProjectStage;

    @TableField(value = "P_Project_Status")
    private String pProjectStatus;

    @TableField(value = "P_PLan_Start_Date")
    private Date pPlanStartDate;

    @TableField(value = "P_Plan_Complete_Date")
    private Date pPlanCompleteDate;

    @TableField(value = "P_Total_Duration")
    private Integer pTotalDuration;

    @TableField(value = "P_Actual_Start_Time")
    private Date pActualStartTime;

    @TableField(value = "P_Actual_Complete_Date")
    private Date pActualCompleteDate;

    @TableField(value = "P_Actual_Total_Duration")
    private String pActualTotalDuration;

    @TableField(value = "P_Intervention_Date")
    private Date pInterventionDate;

    @TableField(value = "P_Intervention_Year")
    private Integer pInterventionYear;

    @TableField(value = "P_Receipt_Time")
    private Date pReceiptTime;

    @TableField(value = "P_Acceptance_Record_Date")
    private Date pAcceptanceRecordDate;

    @TableField(value = "P_Handover_Date")
    private Date pHandoverDate;

    @TableField(value = "P_Is_Project_Summary")
    private String pIsProjectSummary;

    @TableField(value = "P_Use_Unit")
    private String pUseUnit;

    @TableField(value = "P_Sources_Funds")
    private String pSourcesFunds;

    @TableField(value = "P_Calculate_Roughly_Amount")
    private BigDecimal pCalculateRoughlyAmount;

    @TableField(value = "P_Estimate_Amount")
    private BigDecimal pEstimateAmount;

    @TableField(value = "P_Approve_Budget")
    private BigDecimal pApproveBudget;

    @TableField(value = "P_Approve_Budget_Code")
    private String pApproveBudgetCode;

    @TableField(value = "P_Approve_Budget_Date")
    private Date pApproveBudgetDate;

    @TableField(value = "P_Final_Accounts")
    private BigDecimal pFinalAccounts;

    @TableField(value = "P_Final_Accounts_Report_Date")
    private Date pFinalAccountsReportDate;

    @TableField(value = "P_Construction_Project_Planning_Permit_Code")
    private String pConstructionProjectPlanningPermitCode;

    @TableField(value = "P_Construction_Land_Planning_PermitCode")
    private String pConstructionLandPlanningPermitCode;

    @TableField(value = "P_Opinions_On_Site_Selection_For_Construction_Projects_Code")
    private String pOpinionsOnSiteSelectionForConstructionProjectsCode;

    @TableField(value = "P_Land_Use_Right_Assignment_Contract_Code")
    private String pLandUseRightAssignmentContractCode;

    @TableField(value = "P_Feasibility_Study_Approval_Code")
    private String pFeasibilityStudyApprovalCode;

    @TableField(value = "P_EIA_Approval_Code")
    private String pEiaApprovalCode;

    @TableField(value = "P_Is_Agent_Construction")
    private String pIsAgentConstruction;

    @TableField(value = "P_Agent_Construction_Unit")
    private String pAgentConstructionUnit;

    @TableField(value = "P_Design_Grade")
    private String pDesignGrade;

    @TableField(value = "P_Is_Prefabricated")
    private String pIsPrefabricated;

    @TableField(value = "P_Four_New_Technologies")
    private String pFourNewTechnologies;

    @TableField(value = "P_BIM_Technology")
    private String pBimTechnology;

    @TableField(value = "P_Is_EPC")
    private String pIsEpc;

    @TableField(value = "P_Is_Sponge_City")
    private String pIsSpongeCity;

    @TableField(value = "P_Is_Regular_Mode")
    private String pIsRegularMode;

    @TableField(value = "P_Is_Design_And_Construction_Integration")
    private String pIsDesignAndConstructionIntegration;

    @TableField(value = "P_Design_And_Construction_Integration_Unit")
    private String pDesignAndConstructionIntegrationUnit;

    @TableField(value = "P_Is_Whole_Process_Management")
    private String pIsWholeProcessManagement;

    @TableField(value = "P_Whole_Process_Management_Unit")
    private String pWholeProcessManagementUnit;

    @TableField(value = "P_Construction_Cooperation")
    private String pConstructionCooperation;

    @TableField(value = "P_Status")
    private String pStatus;

    @TableField(value = "P_Create_User_ID")
    private String pCreateUserId;

    @TableField(value = "P_Create_User_Name")
    private String pCreateUserName;

    @TableField(value = "P_Create_Time")
    private Date pCreateTime;

    @TableField(value = "P_Update_User_ID")
    private String pUpdateUserId;

    @TableField(value = "P_Update_Time")
    private Date pUpdateTime;

    @TableField(value = "P_Serial_Number")
    private String pSerialNumber;

    @TableField(value = "deleted")
    private Boolean deleted;

    @TableField(value = "P_Assignment_No")
    private String pAssignmentNo;

    @TableField(value = "P_Raise_Funds")
    private BigDecimal pRaiseFunds;

    @TableField(value = "P_Introduction")
    private String pIntroduction;

    @TableField(value = "P_Land_Warrant")
    private String pLandWarrant;

    @TableField(value = "P_Parking_Number_Un")
    private Integer pParkingNumberUn;

    @TableField(value = "P_Parking_Number_On")
    private Integer pParkingNumberOn;

    @TableField(value = "P_Item_Time")
    private Date pItemTime;

    @TableField(value = "P_Pre_Item_Time")
    private Date pPreItemTime;

    @TableField(value = "P_Pre_Item_Number")
    private String pPreItemNumber;

    @TableField(value = "P_Item_Number")
    private String pItemNumber;

    @TableField(value = "P_Site_Area")
    private Float pSiteArea;

    @TableField(value = "P_Cm_Mode")
    private String pCmMode;

    @TableField(value = "P_Assignment_File")
    private String pAssignmentFile;

    @TableField(value = "P_Name_History")
    private String pNameHistory;

    @TableField(value = "P_Committee_Approva_Document")
    private String pCommitteeApprovaDocument;

    @TableField(value = "P_City_Leader_Meeting_Minutes")
    private String pCityLeaderMeetingMinutes;

    @TableField(value = "P_City_Leader_Instructions")
    private String pCityLeaderInstructions;

    @TableField(value = "P_Other")
    private String pOther;

    @TableField(value = "P_Nature")
    private String pNature;

    @TableField(value = "P_Reported_Amount")
    private BigDecimal pReportedAmount;

    @TableField(value = "P_Approva_Document")
    private String pApprovaDocument;

    @TableField(value = "P_Leader_Meeting_Minutes")
    private String pLeaderMeetingMinutes;

    @TableField(value = "P_Leader_Instructions")
    private String pLeaderInstructions;

    @TableField(value = "P_Project_Director_Tel")
    private String pProjectDirectorTel;

    @TableField(value = "P_Construction_Cost")
    private BigDecimal pConstructionCost;

    @TableField(value = "P_Contract_Price")
    private BigDecimal pContractPrice;

    @TableField(value = "P_Purchase_Price")
    private BigDecimal pPurchasePrice;

    @TableField(value = "P_Estimate_File")
    private String pEstimateFile;

    @TableField(value = "P_Scale_Describe")
    private String pScaleDescribe;

    @TableField(value = "P_Bim")
    private Integer pBim;

    @TableField(value = "P_Smart_Site")
    private Integer pSmartSite;

    @TableField(value = "P_Management_System")
    private Integer pManagementSystem;

    @TableField(value = "P_New_Technology")
    private Integer pNewTechnology;

    @TableField(value = "P_New_Material")
    private Integer pNewMaterial;

    @TableField(value = "P_New_Craft")
    private Integer pNewCraft;

    @TableField(value = "P_Save_Energy")
    private Integer pSaveEnergy;

    @TableField(value = "P_Save_Land")
    private Integer pSaveLand;

    @TableField(value = "P_Save_Water")
    private Integer pSaveWater;

    @TableField(value = "P_Save_Material")
    private Integer pSaveMaterial;

    @TableField(value = "P_Digital_Construction")
    private Integer pDigitalConstruction;

    @TableField(value = "P_Green_Construction")
    private String pGreenConstruction;

    @TableField(value = "P_Centralized_Construction")
    private Integer pCentralizedConstruction;

    @TableField(value = "P_Contracting_Mode")
    private String pContractingMode;

    @TableField(value = "P_Process_Consulting")
    private Integer pProcessConsulting;

    @TableField(value = "P_Engineer_Responsible")
    private Integer pEngineerResponsible;

    @TableField(value = "P_Is_Project_Approval")
    private Integer pIsProjectApproval;

    @TableField(value = "P_Building_Scale")
    private Float pBuildingScale;

    @TableField(value = "P_Building_Unit")
    private String pBuildingUnit;

    @TableField(value = "State")
    private Integer state;

    @TableField(value = "State_Time")
    private Date stateTime;

    @TableField(value = "P_Supervise_Type")
    private String pSuperviseType;

    @TableField(value = "P_Group_Type")
    private String pGroupType;

    @TableField(value = "P_Remark_Planned")
    private String pRemarkPlanned;

    @TableField(value = "P_Plan_Number")
    private String pPlanNumber;

    @TableField(value = "P_Cons_Release")
    private Date pConsRelease;

    @TableField(value = "P_Imp_Company")
    private String pImpCompany;

    @TableField(value = "P_Imp_State")
    private String pImpState;

    @TableField(value = "P_Apply_Tilte")
    private String pApplyTilte;

    @TableField(value = "P_Inv_Total")
    private BigDecimal pInvTotal;

    @TableField(value = "P_Inv_Caliber")
    private String pInvCaliber;

    @TableField(value = "P_Inv_Tilte")
    private String pInvTilte;

    @TableField(value = "P_Inv_Code")
    private String pInvCode;

    @TableField(value = "P_Inv_Date")
    private Date pInvDate;

    @TableField(value = "P_Inv_File")
    private String pInvFile;

    @TableField(value = "P_Pass_Video")
    private String pPassVideo;

    @TableField(value = "P_Build_Model")
    private String pBuildModel;

    @TableField(value = "P_Intervention_End_Year")
    private Integer pInterventionEndYear;

    @TableField(value = "P_Each_District")
    private String pEachDistrict;

    @TableField(value = "P_Approval_Unit")
    private String pApprovalUnit;

    @TableField(value = "P_Construction_Start")
    private Date pConstructionStart;

    @TableField(value = "P_Construction_End")
    private Date pConstructionEnd;

    @TableField(value = "P_Check_Type")
    private String pCheckType;

    @TableField(value = "P_Progress_Situation")
    private String pProgressSituation;

    @TableField(value = "P_Record_Number")
    private String pRecordNumber;

    @TableField(value = "P_Category")
    private String pCategory;

    @TableField(value = "P_Economic_File")
    private String pEconomicFile;

    @TableField(value = "P_Proposal_File")
    private String pProposalFile;

    @TableField(value = "P_Report_File")
    private String pReportFile;

    @TableField(value = "P_Inv_Category")
    private String pInvCategory;

    @TableField(value = "P_Advance_Fund")
    private Integer pAdvanceFund;

    @TableField(value = "P_Corporate_Risk")
    private Integer pCorporateRisk;

    @TableField(value = "P_Safety_Filing")
    private String pSafetyFiling;

    @TableField(value = "Cpl_Id")
    private String cplId;

    @TableField(value = "P_Apply_Name")
    private String pApplyName;

    @TableField(value = "P_Fiscal_Budget")
    private BigDecimal pFiscalBudget;

    @TableField(value = "Te_Id")
    private String teId;

    @TableField(value = "P_Campus")
    private String pCampus;

    @TableField(value = "P_Audit_Unit")
    private String pAuditUnit;

    @TableField(value = "P_Cost_Unit")
    private String pCostUnit;

    @TableField(value = "P_Opinion_File")
    private String pOpinionFile;

    @TableField(value = "P_Plan_File")
    private String pPlanFile;

    @TableField(value = "P_Feasibility_File")
    private String pFeasibilityFile;

    @TableField(value = "P_Approve_File")
    private String pApproveFile;

    @TableField(value = "P_Sbgjgzf_Amt")
    private BigDecimal pSbgjgzfAmt;

    @TableField(value = "P_Gcjsqt_Amt")
    private BigDecimal pGcjsqtAmt;

    @TableField(value = "P_Yb_Amt")
    private BigDecimal pYbAmt;

    @TableField(value = "P_Construction_File")
    private String pConstructionFile;

    @TableField(value = "P_Sbgjgzf_File")
    private String pSbgjgzfFile;

    @TableField(value = "P_Gcjsqt_File")
    private String pGcjsqtFile;

    @TableField(value = "P_Yb_File")
    private String pYbFile;

    @TableField(value = "P_Rate")
    private Float pRate;

    @TableField(value = "P_Weight")
    private Float pWeight;

    @TableField(value = "fgld")
    private String fgld;

    @TableField(value = "qzgclxtze")
    private BigDecimal qzgclxtze;

    @TableField(value = "qzcqlxtze")
    private BigDecimal qzcqlxtze;

    @TableField(value = "qzgcgstze")
    private BigDecimal qzgcgstze;

    @TableField(value = "qzcqgstze")
    private BigDecimal qzcqgstze;

    @TableField(value = "xxdz")
    private String xxdz;

    @TableField(value = "bdksrq")
    private String bdksrq;

    @TableField(value = "bdjsrq")
    private String bdjsrq;

    @TableField(value = "zxm")
    private String zxm;

    @TableField(value = "qzqtlxtzewy")
    private BigDecimal qzqtlxtzewy;

    @TableField(value = "qzqtgstzewy")
    private BigDecimal qzqtgstzewy;

    @TableField(value = "xmfzr")
    private String xmfzr;

    @TableField(value = "is_external_data")
    private Boolean isExternalData;

    @TableField(value = "P_Length")
    private Float pLength;

    @TableField(value = "P_Width")
    private Float pWidth;

    @TableField(value = "total_building_scale")
    private String totalBuildingScale;

    @TableField(value = "P_Contact")
    private String pContact;

    @TableField(value = "P_Contact_Phone")
    private String pContactPhone;

    @TableField(value = "P_Repair_Project")
    private String pRepairProject;

    @TableField(value = "P_Project_Basis")
    private String pProjectBasis;

    @TableField(value = "P_Content")
    private String pContent;

    @TableField(value = "P_Funds_Sources")
    private String pFundsSources;

    @TableField(value = "P_Sec_Type")
    private String pSecType;

    @TableField(value = "P_Initial_Approval_Number")
    private String pInitialApprovalNumber;

    @TableField(value = "P_Initial_Approval_Amount")
    private BigDecimal pInitialApprovalAmount;

    @TableField(value = "P_Settlement_Audit_Report_Date")
    private Date pSettlementAuditReportDate;

    @TableField(value = "P_Settlement_Audit_Amount")
    private BigDecimal pSettlementAuditAmount;

    @TableField(value = "P_Address_Detail")
    private String pAddressDetail;

    @TableField(value = "p_auth_type")
    private Integer pAuthType;

    @TableField(value = "Con_Plan_Department")
    private String conPlanDepartment;

    @TableField(value = "Con_Office")
    private String conOffice;

    @TableField(value = "Con_Technology_Department")
    private String conTechnologyDepartment;

    @TableField(value = "Con_PM_Department")
    private String conPMDepartment;

    @TableField(value = "P_Project_Number")
    private String pProjectNumber;

    @TableField(value = "P_Approval_Investment_Amount")
    private BigDecimal pApprovalInvestmentAmount;

    @TableField(value = "P_Can_Yan_Apply_Unit")
    private String pCanYanApplyUnit;

    @TableField(value = "P_Can_Yan_Approval_Unit")
    private String pCanYanApprovalUnit;

    @TableField(value = "P_Record_Num")
    private String pRecordNum;

    @TableField(value = "P_Record_Date")
    private Date pRecordDate;

    @TableField(value = "P_Record_File_Ids")
    private String pRecordFileIds;

    @TableField(value = "P_Record_Amount")
    private BigDecimal pRecordAmount;

    @TableField(value = "P_Legal_Unit")
    private String pLegalUnit;

    @TableField(value = "P_Record_Issue_Unit")
    private String pRecordIssueUnit;

    @TableField(value = "P_Apply_File_Ids")
    private String pApplyFileIds;

    @TableField(value = "P_Apply_Date")
    private Date pApplyDate;

    @TableField(value = "P_Approval_Amount")
    private BigDecimal pApprovalAmount;

    @TableField(value = "P_Build_Type")
    private String pBuildType;

    @TableField(value = "P_Professional_Type")
    private String pProfessionalType;

    @TableField(value = "P_Transfer_Unit")
    private String pTransferUnit;

    @TableField(value = "P_Project_Manager")
    private Date pProjectManager;

    @TableField(value = "P_Project_Leader")
    private String pProjectLeader;

    @TableField(value = "data_source_type")
    private String dataSourceType;

    @TableField(value = "P_Project_Form_Type")
    private String pProjectFormType;

    @TableField(value = "P_Record_File")
    private String pRecordFile;

    @TableField(value = "P_Apply_File")
    private String pApplyFile;

    @TableField(value = "P_Investment_Constitute")
    private String pInvestmentConstitute;

    @TableField(value = "P_Build_Year")
    private String pBuildYear;

    @TableField(value = "P_Department")
    private String pDepartment;
}
