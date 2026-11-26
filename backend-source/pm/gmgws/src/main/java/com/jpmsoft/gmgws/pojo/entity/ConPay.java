package com.jpmsoft.gmgws.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("con_pay")
public class ConPay {
    @TableField(value = "Pay_Id")
    private String payId;

    @TableField(value = "P_Id")
    private String pId;

    @TableField(value = "Con_Id")
    private String conId;

    @TableField(value = "Pay_Type")
    private String payType;

    @TableField(value = "Pay_Code")
    private String payCode;

    @TableField(value = "Pay_Number")
    private Integer payNumber;

    @TableField(value = "Pay_Done_FBFX")
    private BigDecimal payDoneFbfx;

    @TableField(value = "Pay_Done_CSXM")
    private BigDecimal payDoneCsxm;

    @TableField(value = "Pay_Done_QTXM")
    private BigDecimal payDoneQtxm;

    @TableField(value = "Pay_Done_GF")
    private BigDecimal payDoneGf;

    @TableField(value = "Pay_Done_SJ")
    private BigDecimal payDoneSj;

    @TableField(value = "Pay_Done_QTJK")
    private BigDecimal payDoneQtjk;

    @TableField(value = "Pay_Done")
    private BigDecimal payDone;

    @TableField(value = "Pay_Done_Total_Last")
    private BigDecimal payDoneTotalLast;

    @TableField(value = "Pay_Done_Total")
    private BigDecimal payDoneTotal;

    @TableField(value = "Pay_Ratio")
    private Float payRatio;

    @TableField(value = "Pay_Deduct_YFK")
    private BigDecimal payDeductYfk;

    @TableField(value = "Pay_Deduct_ZBJ")
    private BigDecimal payDeductZbj;

    @TableField(value = "Pay_Deduct_AQ")
    private BigDecimal payDeductAq;

    @TableField(value = "Pay_Deduct_QT")
    private BigDecimal payDeductQt;

    @TableField(value = "Pay_Change")
    private BigDecimal payChange;

    @TableField(value = "Pay_Unpaid_Total")
    private BigDecimal payUnpaidTotal;

    @TableField(value = "Pay_Payable")
    private BigDecimal payPayable;

    @TableField(value = "Pay_Apply")
    private BigDecimal payApply;

    @TableField(value = "Pay_Approval")
    private BigDecimal payApproval;

    @TableField(value = "Pay_Total_Last")
    private BigDecimal payTotalLast;

    @TableField(value = "Pay_Total_Last_P")
    private Float payTotalLastP;

    @TableField(value = "Pay_Total")
    private BigDecimal payTotal;

    @TableField(value = "Pay_Total_P")
    private Float payTotalP;

    @TableField(value = "Pay_Invoice")
    private BigDecimal payInvoice;

    @TableField(value = "Pay_Invoice_Total_Last")
    private BigDecimal payInvoiceTotalLast;

    @TableField(value = "Pay_Invoice_Total")
    private BigDecimal payInvoiceTotal;

    @TableField(value = "Pay_Apg")
    private Integer payApg;

    @TableField(value = "Pay_Apg_Due_Date")
    private Date payApgDueDate;

    @TableField(value = "Pay_Pb")
    private Integer payPb;

    @TableField(value = "Pay_Pb_Due_Date")
    private Date payPbDueDate;

    @TableField(value = "Pay_Remark")
    private String payRemark;

    @TableField(value = "Pay_Image_Progress")
    private String payImageProgress;

    @TableField(value = "Pay_Create_Uid")
    private String payCreateUid;

    @TableField(value = "Pay_Create_Uname")
    private String payCreateUname;

    @TableField(value = "Pay_Create_Time")
    private Date payCreateTime;

    @TableField(value = "Pay_State")
    private Integer payState;

    @TableField(value = "Pay_State_Time")
    private Date payStateTime;

    @TableField(value = "Pay_Payee")
    private String payPayee;

    @TableField(value = "Pay_Payee_CA_Name")
    private String payPayeeCaName;

    @TableField(value = "Pay_Payee_CA")
    private String payPayeeCa;

    @TableField(value = "Pay_Payee_Bank")
    private String payPayeeBank;

    @TableField(value = "Pay_Payee_Bank_Name")
    private String payPayeeBankName;

    @TableField(value = "Pay_Payee_WA_Bank_Num")
    private String payPayeeWaBankNum;

    @TableField(value = "Pay_Drawee")
    private String payDrawee;

    @TableField(value = "Pay_Drawee_CA_Name")
    private String payDraweeCaName;

    @TableField(value = "Pay_Drawee_CA")
    private String payDraweeCa;

    @TableField(value = "Pay_Drawee_Bank")
    private String payDraweeBank;

    @TableField(value = "Pay_Drawee_Bank_Name")
    private String payDraweeBankName;

    @TableField(value = "Pay_Drawee_WA_Bank_Num")
    private String payDraweeWaBankNum;

    @TableField(value = "D_Id")
    private String dId;

    @TableField(value = "Pay_Prepayment_Balance")
    private BigDecimal payPrepaymentBalance;

    @TableField(value = "Pay_Contract_Balance")
    private BigDecimal payContractBalance;

    @TableField(value = "Pay_Done_Labor_Cost")
    private BigDecimal payDoneLaborCost;

    @TableField(value = "Pay_Labor_Cost_Payable")
    private BigDecimal payLaborCostPayable;

    @TableField(value = "Pay_Other_Expenses_Payable")
    private BigDecimal payOtherExpensesPayable;

    @TableField(value = "Pay_Approved_Settlement_Price")
    private BigDecimal payApprovedSettlementPrice;

    @TableField(value = "Pay_Supervision_Fee")
    private BigDecimal paySupervisionFee;

    @TableField(value = "Pay_Penalty_Payable")
    private BigDecimal payPenaltyPayable;

    @TableField(value = "Pay_Contacts")
    private String payContacts;

    @TableField(value = "Pay_Contact_Number")
    private String payContactNumber;

    @TableField(value = "Pay_Deduct_Fine")
    private BigDecimal payDeductFine;

    @TableField(value = "Pay_Method")
    private String payMethod;

    @TableField(value = "Pay_Type_Detailed")
    private String payTypeDetailed;

    @TableField(value = "Pay_Application_Date")
    private Date payApplicationDate;

    @TableField(value = "Pay_Payee_Ca_Name_Wages")
    private String payPayeeCaNameWages;

    @TableField(value = "Pay_Payee_Bank_Wages")
    private String payPayeeBankWages;

    @TableField(value = "Pay_Payee_Ca_Wages")
    private String payPayeeCaWages;

    @TableField(value = "Pay_Applicant_Id")
    private String payApplicantId;

    @TableField(value = "Pay_Application_Department_Id")
    private String payApplicationDepartmentId;

    @TableField(value = "Pay_Done_Sup")
    private BigDecimal payDoneSup;

    @TableField(value = "Pay_Done_Owner")
    private BigDecimal payDoneOwner;
    

    @TableField(value = "Pay_Done_Labor_Cost_Sup")
    private BigDecimal payDoneLaborCostSup;

    @TableField(value = "Pay_Done_Labor_Cost_Owner")
    private BigDecimal payDoneLaborCostOwner;

    @TableField(value = "Pay_Done_QTJK_Sup")
    private BigDecimal payDoneQtjkSup;

    @TableField(value = "Pay_Done_QTJK_Owner")
    private BigDecimal payDoneQtjkOwner;

    @TableField(value = "Pay_Done_Total_Sup")
    private BigDecimal payDoneTotalSup;

    @TableField(value = "Pay_Done_Total_Owner")
    private BigDecimal payDoneTotalOwner;

    @TableField(value = "Pay_Deduct_ZBJ_Sup")
    private BigDecimal payDeductZbjSup;

    @TableField(value = "Pay_Deduct_ZBJ_Owner")
    private BigDecimal payDeductZbjOwner;

    @TableField(value = "Pay_Deduct_YFK_Sup")
    private BigDecimal payDeductYfkSup;

    @TableField(value = "Pay_Deduct_YFK_Owner")
    private BigDecimal payDeductYfkOwner;

    @TableField(value = "Pay_Pre_Payment_Balance_Sup")
    private BigDecimal payPrePaymentBalanceSup;

    @TableField(value = "Pay_Pre_Payment_Balance_Owner")
    private BigDecimal payPrePaymentBalanceOwner;

    @TableField(value = "Pay_Apply_Sup")
    private BigDecimal payApplySup;

    @TableField(value = "Pay_Apply_Owner")
    private BigDecimal payApplyOwner;

    @TableField(value = "Pay_Labor_Cost_Payable_Sup")
    private BigDecimal payLaborCostPayableSup;

    @TableField(value = "Pay_Labor_Cost_Payable_Owner")
    private BigDecimal payLaborCostPayableOwner;

    @TableField(value = "Pay_Other_Expenses_Payable_Sup")
    private BigDecimal payOtherExpensesPayableSup;

    @TableField(value = "Pay_Other_Expenses_Payable_Owner")
    private BigDecimal payOtherExpensesPayableOwner;

    @TableField(value = "Pay_Contract_Terms")
    private String payContractTerms;

    @TableField(value = "Pay_Advance_Payment_Payable")
    private BigDecimal payAdvancePaymentPayable;

    @TableField(value = "Pay_Is_History")
    private Integer payIsHistory;

    @TableField(value = "Pay_Subcontracting_Amount")
    private BigDecimal paySubcontractingAmount;

    @TableField(value = "Pay_Management_Expense")
    private BigDecimal payManagementExpense;

    @TableField(value = "Pay_Sporadic_Expenditure")
    private BigDecimal paySporadicExpenditure;

    @TableField(value = "Pay_Liquidated_Damages")
    private BigDecimal payLiquidatedDamages;

    @TableField(value = "Pay_Liquidated_Damages_Sup")
    private BigDecimal payLiquidatedDamagesSup;

    @TableField(value = "Pay_Liquidated_Damages_Owner")
    private BigDecimal payLiquidatedDamagesOwner;

    @TableField(value = "Pay_Payee_Name_Pro")
    private String payPayeeNamePro;

    @TableField(value = "Pay_Category")
    private String payCategory;

    @TableField(value = "Pay_Expense_Type")
    private String payExpenseType;

    @TableField(value = "Pay_Farmers_Expenditure")
    private BigDecimal payFarmersExpenditure;

    @TableField(value = "Pay_Category_Payment")
    private String payCategoryPayment;

    @TableField(value = "Pay_Invoice_No")
    private String payInvoiceNo;

    @TableField(value = "Pay_Change_Amt")
    private BigDecimal payChangeAmt;

    @TableField(value = "Pay_Base")
    private String payBase;

    @TableField(value = "Pay_Fund_No")
    private String payFundNo;

    @TableField(value = "Pay_Repair_One")
    private String payRepairOne;

    @TableField(value = "Pay_Repair_Two")
    private String payRepairTwo;

    @TableField(value = "Pay_Repair_Three")
    private String payRepairThree;

    @TableField(value = "Pay_Advance_Total")
    private BigDecimal payAdvanceTotal;

    @TableField(value = "Pay_Actual_Pay_Total")
    private BigDecimal payActualPayTotal;

    @TableField(value = "Pay_Actual_Pay_Total_P")
    private BigDecimal payActualPayTotalP;

    @TableField(value = "Pay_Default_Total")
    private BigDecimal payDefaultTotal;

    @TableField(value = "Pay_Settlement_Price_Buckle")
    private BigDecimal paySettlementPriceBuckle;

    @TableField(value = "Pay_Approva_Change_Total")
    private BigDecimal payApprovaChangeTotal;

    @TableField(value = "Pay_Deduct_ZBJ_P")
    private BigDecimal payDeductZbjP;

    @TableField(value = "Pay_This_Default_Total")
    private BigDecimal payThisDefaultTotal;

    @TableField(value = "Pay_This_Change_Amount")
    private BigDecimal payThisChangeAmount;

    @TableField(value = "Pay_Change_Pay_Total")
    private BigDecimal payChangePayTotal;

    @TableField(value = "Pay_Change_Pay_Total_L")
    private BigDecimal payChangePayTotalL;

    @TableField(value = "Pay_Change_Pay_Total_T")
    private BigDecimal payChangePayTotalT;

    @TableField(value = "Pay_Unfinished_Work")
    private BigDecimal payUnfinishedWork;

    @TableField(value = "Pay_Work_Amount_Total")
    private BigDecimal payWorkAmountTotal;

    @TableField(value = "Pay_Deduction")
    private BigDecimal payDeduction;

    @TableField(value = "Pay_Progress_Pay")
    private BigDecimal payProgressPay;

 

    @TableField(value = "Pay_P_Type")
    private String payPType;

    @TableField(value = "Pay_Workers_Wages_P")
    private BigDecimal payWorkersWagesP;

    @TableField(value = "Pay_Deduction_Work")
    private BigDecimal payDeductionWork;

    @TableField(value = "Pay_Deduction_Work_P")
    private BigDecimal payDeductionWorkP;

    @TableField(value = "Pay_Fund_Plan_Surplus")
    private BigDecimal payFundPlanSurplus;

    @TableField(value = "Pay_Project_Payment_Surplus")
    private BigDecimal payProjectPaymentSurplus;

    @TableField(value = "Mea_Id")
    private String meaId;

    @TableField(value = "Con_Id_Sup")
    private String conIdSup;

    @TableField(value = "Pay_Security_Costs")
    private BigDecimal paySecurityCosts;

    @TableField(value = "Pay_Fa_Code")
    private String payFaCode;

    @TableField(value = "Pay_Payment_Name")
    private String payPaymentName;

    @TableField(value = "Pay_Payment_Nature")
    private String payPaymentNature;

    @TableField(value = "Pay_Is_Wages")
    private Integer payIsWages;

    @TableField(value = "Pay_Promise_File")
    private String payPromiseFile;

    @TableField(value = "Pay_Balance")
    private BigDecimal payBalance;

    @TableField(value = "Pay_Bill_File")
    private String payBillFile;

    @TableField(value = "Pay_Expense_Category")
    private String payExpenseCategory;

    @TableField(value = "Pay_Payment_Node")
    private String payPaymentNode;

    @TableField(value = "Pay_Acceptance_Settlement")
    private Integer payAcceptanceSettlement;

    @TableField(value = "Pay_Capital_Account")
    private String payCapitalAccount;

    @TableField(value = "Pay_Capital_Bank")
    private String payCapitalBank;

    @TableField(value = "Pay_Capital_Name")
    private String payCapitalName;

    @TableField(value = "Pay_Deduct_QT_Sup")
    private BigDecimal payDeductQtSup;

    @TableField(value = "Pay_Deduct_QT_Owner")
    private BigDecimal payDeductQtOwner;

    @TableField(value = "Pay_Deduct_ZBJ_Last")
    private BigDecimal payDeductZbjLast;

    @TableField(value = "Pay_Deduct_ZBJ_This")
    private BigDecimal payDeductZbjThis;

    @TableField(value = "Pay_Deduct_Fine_Last")
    private BigDecimal payDeductFineLast;

    @TableField(value = "Pay_Deduct_Fine_This")
    private BigDecimal payDeductFineThis;

    @TableField(value = "Pay_Deduct_YFK_Last")
    private BigDecimal payDeductYfkLast;

    @TableField(value = "Pay_Deduct_YFK_This")
    private BigDecimal payDeductYfkThis;

    @TableField(value = "Pay_Deduct_QT_Last")
    private BigDecimal payDeductQtLast;

    @TableField(value = "Pay_Deduct_QT_This")
    private BigDecimal payDeductQtThis;

    @TableField(value = "Pay_Bonus_Last")
    private BigDecimal payBonusLast;

    @TableField(value = "Pay_Bonus")
    private BigDecimal payBonus;

    @TableField(value = "Pay_Bonus_This")
    private BigDecimal payBonusThis;

    @TableField(value = "Pay_Change_Pay_Total_This")
    private BigDecimal payChangePayTotalThis;

    @TableField(value = "Pay_Payable_Last")
    private BigDecimal payPayableLast;

    @TableField(value = "Pay_Payable_This")
    private BigDecimal payPayableThis;

    @TableField(value = "Te_Id")
    private String teId;

    @TableField(value = "Cpp_Id")
    private String cppId;

    @TableField(value = "Pay_Schedule_Rate")
    private Float payScheduleRate;

    @TableField(value = "Pay_Change_Rate")
    private Float payChangeRate;

    @TableField(value = "Pay_Farmers_Period")
    private BigDecimal payFarmersPeriod;

    @TableField(value = "Pay_Farmers_Total")
    private BigDecimal payFarmersTotal;

    @TableField(value = "Ctd_Id")
    private String ctdId;

    @TableField(value = "Ctd_Code")
    private String ctdCode;

    @TableField(value = "Pay_Cnature")
    private String payCnature;

    @TableField(value = "Pay_Pro_Cname")
    private String payProCname;

    @TableField(value = "Pay_Yk_Cname")
    private String payYkCname;

    @TableField(value = "Pay_Content")
    private String payContent;

    @TableField(value = "Pay_Mold")
    private String payMold;

    @TableField(value = "Pay_Apg_File")
    private String payApgFile;

    @TableField(value = "Pay_Apg_Take_Date")
    private Date payApgTakeDate;

    @TableField(value = "Pay_Pb_File")
    private String payPbFile;

    @TableField(value = "Pay_Pb_Take_Date")
    private Date payPbTakeDate;

    @TableField(value = "Pay_Bh_Reason")
    private String payBhReason;

    @TableField(value = "Pay_Zb_Amt")
    private BigDecimal payZbAmt;

    @TableField(value = "Pay_Qb_Amt")
    private BigDecimal payQbAmt;

    @TableField(value = "Pay_Sdf_Amt")
    private BigDecimal paySdfAmt;

    @TableField(value = "Pay_Bz")
    private String payBz;

    @TableField(value = "is_external_data")
    private Boolean isExternalData;

    @TableField(value = "Pay_Letter_Date")
    private Date payLetterDate;

    @TableField(value = "Pay_Deduction_Work_P1")
    private BigDecimal payDeductionWorkP1;

    @TableField(value = "Pay_Deduction_Work1")
    private BigDecimal payDeductionWork1;

    @TableField(value = "Pay_Payee1")
    private String payPayee1;

    @TableField(value = "Pay_Payee_CA_Name1")
    private String payPayeeCaName1;

    @TableField(value = "Pay_Payee_CA1")
    private String payPayeeCa1;

    @TableField(value = "Pay_Payee_Bank1")
    private String payPayeeBank1;

    @TableField(value = "Pay_P_Type1")
    private String payPType1;

    @TableField(value = "Pay_FarmersExpenditure1")
    private BigDecimal payFarmersExpenditure1;

    @TableField(value = "Pay_Workers_Wages_P1")
    private BigDecimal payWorkersWagesP1;

    @TableField(value = "Pay_Payee_Ca_Name_Wages1")
    private String payPayeeCaNameWages1;

    @TableField(value = "Pay_Payee_Bank_Wages1")
    private String payPayeeBankWages1;

    @TableField(value = "Pay_Payee_Ca_Wages1")
    private String payPayeeCaWages1;

    @TableField(value = "Pay_Gs_Amt")
    private BigDecimal payGsAmt;



    @TableField(value = "Pay_Wallet_Number")
    private String payWalletNumber;

    @TableField(value = "Pay_Digital_Bank")
    private String payDigitalBank;

    @TableField(value = "Pay_Wallet_Name")
    private String payWalletName;

    @TableField(value = "Pay_Other_Amt")
    private BigDecimal payOtherAmt;

    @TableField(value = "Pay_Zj_Plan_Id")
    private String payZjPlanId;

    @TableField(value = "Pay_Zj_Plan_Code")
    private String payZjPlanCode;

    @TableField(value = "Pay_Zj_Amt")
    private BigDecimal payZjAmt;

    @TableField(value = "Pay_Ment_Request_File")
    private String payMentRequestFile;

    @TableField(value = "Pay_Ment_Certificate_File")
    private String payMentCertificateFile;

    @TableField(value = "Pay_Audit_Opinion_File")
    private String payAuditOpinionFile;

    @TableField(value = "Pay_Collection_Statement_File")
    private String payCollectionStatementFile;

    @TableField(value = "Pay_Final_Payment_Statement_File")
    private String payFinalPaymentStatementFile;

    @TableField(value = "Pay_Notice_Of_Award_File")
    private String payNoticeOfAwardFile;

    @TableField(value = "Pay_Contract_File")
    private String payContractFile;

    @TableField(value = "Pay_Deduct_Sc_Last")
    private BigDecimal payDeductScLast;

    @TableField(value = "Pay_Deduct_Sc_This")
    private BigDecimal payDeductScThis;

    @TableField(value = "Pay_Deduct_Sc")
    private BigDecimal payDeductSc;

    @TableField(value = "is_travel_on_business_fee")
    private String isTravelOnBusinessFee;

    @TableField(value = "is_need_supervisor_approve")
    private String isNeedSupervisorApprove;
}
