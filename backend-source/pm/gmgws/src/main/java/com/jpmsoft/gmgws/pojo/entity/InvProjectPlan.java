package com.jpmsoft.gmgws.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("Inv_Project_Plan")
public class InvProjectPlan {
    private String ippId; // 主键ID
    @TableField(value = "Ifr_Id")
    private String ifrId;
    @TableField(value = "P_Id")
    private String pId;
    @TableField(value = "Ipp_Construction_Scale")
    private String ippConstructionScale;
    @TableField(value = "Ipp_P_Type")
    private String ippPType;
    @TableField(value = "Ipp_Accumulated_Funds")
    private BigDecimal ippAccumulatedFunds;
    @TableField(value = "Ipp_Funds_Arranged")
    private BigDecimal ippFundsArranged;
    @TableField(value = "Ipp_Annual_Accum_Fund")
    private BigDecimal ippAnnualAccumFund;
    @TableField(value = "Ipp_Release_Funds")
    private BigDecimal ippReleaseFunds;
    @TableField(value = "Ipp_Annual_Release_Funds")
    private BigDecimal ippAnnualReleaseFunds;
    @TableField(value = "Ipp_Doc_Number")
    private String ippDocNumber;
    @TableField(value = "Ipp_File")
    private String ippFile;
    @TableField(value = "Ipp_Create_Uid")
    private String ippCreateUid;
    @TableField(value = "Ipp_Create_Uname")
    private String ippCreateUname;
    @TableField(value = "Ipp_Year")
    private String ippYear;
    @TableField(value = "Ipp_Create_Time")
    private Date ippCreateTime;
    @TableField(value = "Ipp_Investor")
    private String ippInvestor;
    @TableField(value = "Ipp_Build_Content")
    private String ippBuildContent;
    @TableField(value = "Ipp_Build_Nature")
    private String ippBuildNature;
    @TableField(value = "Ipp_Build_Period")
    private Integer ippBuildPeriod;
    @TableField(value = "Ipp_Plan_Inv")
    private BigDecimal ippPlanInv;
    
    @TableField(value = "Ipp_Progress_Last_Year")
    private String ippProgressLastYear;
    @TableField(value = "Ipp_Municipal_Funds")
    private BigDecimal ippMunicipalFunds;
    @TableField(value = "Ipp_County_Funds")
    private BigDecimal ippCountyFunds;
    @TableField(value = "Ipp_Enterprise_Funds")
    private BigDecimal ippEnterpriseFunds;
    @TableField(value = "Ipp_Bank_Loans")
    private BigDecimal ippBankLoans;
    @TableField(value = "Ipp_Other_Funds")
    private BigDecimal ippOtherFunds;
    @TableField(value = "Ipp_Now_Year_Progress")
    private String ippNowYearProgress;
    @TableField(value = "Ipp_Land_Area")
    private String ippLandArea;
    @TableField(value = "Ipp_New_Land_Area")
    private String ippNewLandArea;
    @TableField(value = "Ipp_Construction_Site")
    private String ippConstructionSite;
    @TableField(value = "Ipp_Major_Projects")
    private String ippMajorProjects;
    @TableField(value = "Ipp_Special_Plan")
    private String ippSpecialPlan;
    @TableField(value = "Ipp_Liable_Company")
    private String ippLiableCompany;
    @TableField(value = "Ipp_Liable_Man")
    private String ippLiableMan;
    @TableField(value = "Ipp_Remarks")
    private String ippRemarks;
    @TableField(value = "P_Name")
    private String pName;
    @TableField(value = "P_Code")
    private String pCode;
    @TableField(value = "P_Budget_Unit")
    private String pBudgetUnit;
    @TableField(value = "Ipp_Remaining_Amount")
    private BigDecimal ippRemainingAmount;
    @TableField(value = "Ipp_Budget_Item_Name")
    private String ippBudgetItemName;
    @TableField(value = "Ipp_Ndrc_Code")
    private String ippNdrcCode;
    @TableField(value = "Ipp_Plan_Doc_No")
    private String ippPlanDocNo;
    @TableField(value = "Ipp_Source_Funds")
    private String ippSourceFunds;
    @TableField(value = "Ipp_Release_Type")
    private String ippReleaseType;
    @TableField(value = "Te_Id")
    private String teId;
}
