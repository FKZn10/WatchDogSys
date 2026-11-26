package com.jpmsoft.projectProducer.pojo.entity.contract;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付申请
 */
@Data
@TableName("Con_Pay")
public class ConPay {

    @TableId(type = IdType.INPUT)
    private String payId;
    private String pId;
    private String conId;
    private String payType;
    private String payCode;
    private Integer payNumber;
    private BigDecimal payDoneFbfx;
    private BigDecimal payDoneCsxm;
    private BigDecimal payDoneQtxm;
    private BigDecimal payDoneGf;
    private BigDecimal payDoneSj;
    private BigDecimal payDoneQtjk;
    private BigDecimal payDone;
    private BigDecimal payDoneTotalLast;
    private BigDecimal payDoneTotal;
    private Float payRatio;
    private BigDecimal payDeductYfk;
    private BigDecimal payDeductZbj;
    private BigDecimal payDeductAq;
    private BigDecimal payDeductQt;
    private BigDecimal payChange;
    private BigDecimal payUnpaidTotal;
    private BigDecimal payPayable;
    private BigDecimal payApply;
    private BigDecimal payApproval;
    private BigDecimal payTotalLast;
    private Float payTotalLastP;
    private BigDecimal payTotal;
    private Float payTotalP;
    private BigDecimal payInvoice;
    private BigDecimal payInvoiceTotalLast;
    private BigDecimal payInvoiceTotal;
    private Integer payApg;
    private Date payApgDueDate;
    private Integer payPb;
    private Date payPbDueDate;
    private String payRemark;
    private String payImageProgress;
    private String payCreateUid;
    private String payCreateUname;
    private Date payCreateTime;

    private String payKeyWords;

    private String payReferenceNumber;

    private Integer payMaintenance;
    private String payLetterStatus;
    private Integer payState;
    private Date payStateTime;
    private String payPayee;
    private String payPayeeCaName;
    private String payPayeeCa;
    private String payPayeeBank;
    private String payPayeeBankName;
    private String payPayeeWaBankNum;
    private String payDrawee;
    private String payDraweeCaName;
    private String payDraweeCa;
    private String payDraweeBank;
    private String payDraweeBankName;
    private String payDraweeWaBankNum;
    private String dId;

    private String payApplicationType;

    //预付款余额
    private BigDecimal payPrepaymentBalance;
    //合同余款
    private BigDecimal payContractBalance;
    //其中人工费
    private BigDecimal payDoneLaborCost;
    //本期应付进度款人工费
    private BigDecimal payLaborCostPayable;
    //至本期应付工料机费及其他费用
    private BigDecimal payOtherExpensesPayable;
    //审定结算价
    private BigDecimal payApprovedSettlementPrice;
    //本期应付监理费
    private BigDecimal paySupervisionFee;
    //本期应付罚款
    private BigDecimal payPenaltyPayable;
    //【付款】联系人
    private String payContacts;
    //【付款】联系电话
    private String payContactNumber;
    //【扣回】本期应扣罚款
    private BigDecimal payDeductFine;
    //付款方式
    private String payMethod;
    //详细类型
    private String payTypeDetailed;
    //申请日期
    private Date payApplicationDate;
    //账户名称(工人工资账号)
    private String payPayeeCaNameWages;
    //开户行(工人工资账号)
    private String payPayeeBankWages;
    //收款账号(工人工资账号)
    private String payPayeeCaWages;
    //申请单位
    private String payApplicantId;
    //申请部门
    private String payApplicationDepartmentId;
    //本期完成工程造价(监理审核)
    private BigDecimal payDoneSup;
    //业主审核
    private BigDecimal payDoneOwner;
    //其中人工费(监理审核)
    private BigDecimal payDoneLaborCostSup;
    //业主审核
    private BigDecimal payDoneLaborCostOwner;
    //其中工料机费及其它费用（监理审核）
    private BigDecimal payDoneQtjkSup;
    //业主审核
    private BigDecimal payDoneQtjkOwner;
    //至本期累计已完成工程造价（监理审核）
    private BigDecimal payDoneTotalSup;
    //业主审核
    private BigDecimal payDoneTotalOwner;
    //本期应扣保留金（监理审核）
    private BigDecimal payDeductZbjSup;
    //业主审核
    private BigDecimal payDeductZbjOwner;
    //本期应扣预付款（监理审核）
    private BigDecimal payDeductYfkSup;
    //业主审核
    private BigDecimal payDeductYfkOwner;
    //预付款余额（监理审核）
    private BigDecimal payPrePaymentBalanceSup;
    //业主审核
    private BigDecimal payPrePaymentBalanceOwner;
    //本期应付进度款（监理审核）
    private BigDecimal payApplySup;
    //业主审核
    private BigDecimal payApplyOwner;
    //其中本期应付进度款人工费（监理审核）
    private BigDecimal payLaborCostPayableSup;
    //业主审核
    private BigDecimal payLaborCostPayableOwner;
    //其中本期应付进度款工料机费及其他费用（监理审核）
    private BigDecimal payOtherExpensesPayableSup;
    //业主审核
    private BigDecimal payOtherExpensesPayableOwner;
    //合同付款条款
    private String payContractTerms;
    //是否历史合同
    private String payIsHistory;
    //【支付】分包单位金额
    private BigDecimal paySubcontractingAmount;
    //【支付】管理费金额
    private BigDecimal payManagementExpense;
    //【支付】其中零星支出金额
    private BigDecimal paySporadicExpenditure;

    //【支付】其中本期应扣违约金
    private BigDecimal payLiquidatedDamages;
    //【支付】其中本期应扣违约金
    private BigDecimal payLiquidatedDamagesSup;
    //【支付】其中本期应扣违约金
    private BigDecimal payLiquidatedDamagesOwner;
    //【收款】单位名称（无合同支付时手填）
    private String payPayeeNamePro;
    //支付类别  01 合同支付 02 项目支付 03政府缴费
    private String payCategory;
    //费用类别
    private String payExpenseType;
    private BigDecimal payFarmersExpenditure;
    /**
     * 累计农民工工资金额。
     */
    @TableField("pay_farmers_expenditure_total")
    private BigDecimal payFarmersExpenditureTotal;
    private String payCategoryPayment;
    private String payInvoiceNo;
    private BigDecimal payChangeAmt;
    private String payBase;
    private String payFundNo;

    private String payRepairOne;
    private String payRepairTwo;
    private String payRepairThree;
    private BigDecimal payAdvanceTotal;
    private BigDecimal payActualPayTotal;
    private BigDecimal payActualPayTotalP;
    private BigDecimal payDefaultTotal;
    private BigDecimal paySettlementPriceBuckle;

    private BigDecimal payApprovaChangeTotal;
    private BigDecimal payWorkAmountTotal;
    @TableField(value = "payDeduct_ZBJ_P")
    private BigDecimal payDeductZbjP;
    private BigDecimal payThisDefaultTotal;
    private BigDecimal payThisChangeAmount;
    private BigDecimal payChangePayTotal;
    private BigDecimal payChangePayTotalL;
    private BigDecimal payChangePayTotalT;
    private String payUnfinishedWork;
    private BigDecimal payProgressPay;
    private BigDecimal payDeduction;
    private String payPType;
    private BigDecimal payWorkersWagesP;
    private BigDecimal payDeductionWork;
    private BigDecimal payDeductionWorkP;
    private BigDecimal payFundPlanSurplus;
    private BigDecimal payProjectPaymentSurplus;
    private String meaId;
    private String conIdSup;
    private BigDecimal paySecurityCosts;
    private String payPaymentName;
    private String payPaymentNature;

    private Integer payIsWages;
    private BigDecimal payBalance;
    private String payPromiseFile;
    private String payBillFile;
    private String payExpenseCategory;
    private String payPaymentNode;
    private String payAcceptanceSettlement;
    private String payCapitalAccount;
    private String payCapitalBank;
    private String payCapitalName;
    private BigDecimal payDeductQtSup;
    private BigDecimal payDeductQtOwner;


    private BigDecimal payDeductZbjLast;
    private BigDecimal payDeductZbjThis;
    private BigDecimal payDeductFineLast;
    private BigDecimal payDeductFineThis;
    private BigDecimal payDeductYfkLast;
    private BigDecimal payDeductYfkThis;
    private BigDecimal payDeductQtLast;
    private BigDecimal payDeductQtThis;
    private BigDecimal payBonusLast;
    private BigDecimal payBonus;
    private BigDecimal payBonusThis;
    private BigDecimal payChangePayTotalThis;
    private BigDecimal payPayableLast;
    private BigDecimal payPayableThis;
    private String teId;
    private String cppId;
    private Float payScheduleRate;
    private Float payChangeRate;
    private BigDecimal payFarmersPeriod;
    private BigDecimal payFarmersTotal;
    private String ctdId;
    private String ctdCode;


    private String payCnature;
    private String payProCname;
    private String payYkCname;
    private String payContent;
    private String payMold;
    private Date payPbTakeDate;
    private Date payApgTakeDate;
    private String payBhReason;
    private BigDecimal payZbAmt;
    private BigDecimal payQbAmt;
    private BigDecimal paySdfAmt;
    private String payBz;
    private String payApgFile;
    private String payPbFile;
    private String payFaCode;

    private Date payLetterDate;
    private BigDecimal payGsAmt;

    private BigDecimal payDeductionWorkP1;
    private BigDecimal payDeductionWork1;
    private String payPayee1;
    private String payPayeeCaName1;
    private String payPayeeCa1;
    private String payPayeeBank1;
    private String payPType1;
    private BigDecimal payFarmersExpenditure1;
    private BigDecimal payWorkersWagesP1;
    private String payPayeeCaNameWages1;
    private String payPayeeBankWages1;
    private String payPayeeCaWages1;

    private String payWalletNumber;
    private String payDigitalBank;
    private String payWalletName;
    private BigDecimal payOtherAmt;
    private String payZjPlanId;
    private String payZjPlanCode;
    private BigDecimal payZjAmt;
    /*
    支付申请
     */
    private String payMentRequestFile;

    /*
   监理支付证书
    */
    private String payMentCertificateFile;

    /*
    审计意见
    */
    private String payAuditOpinionFile;

    /*
    收款对账单
    */
    private String payCollectionStatementFile;

    /*
    尾款对账单
    */
    private String payFinalPaymentStatementFile;

    /*
    中标通知书
    */
    private String payNoticeOfAwardFile;

    /*
    合同
    */
    private String payContractFile;
    /**
     * 【支付】应扣安全文明措施费_上期累计
     */
    private BigDecimal payDeductScLast;
    /**
     * 【支付】应扣安全文明措施费_本期累计
     */
    private BigDecimal payDeductScThis;
    /**
     * 【支付】应扣安全文明措施费_本期发生
     */
    private BigDecimal payDeductSc;

    /**
     * 本次质保金金额
     */
    private BigDecimal thisTimeRetentionMoney;

    // 发票附件
    private String invoiceFileRelationId;

    // 是否已上传发票附件，1是，其他否
    private Integer uploadedInvoice;

    private String ccdId;

    private String payIsSecurity;
    private BigDecimal paySecurityCost;
    private BigDecimal paySecurityThis;
    private BigDecimal paySecuritySum;
    private String payIsPayee;
    private String payNewPayeeBank;
    private String payNewPayeeCa;

    // 付款总共期数
    private Integer payNumberSum;
    // 工程款金额
    private BigDecimal payAomount;
    // 签证金额占暂列金比例
    private Float payProportion;

    //经费账户户名
    private String payFundAccount;
    // 经费账户账号
    private String payFundAccountNum;


    // '是否已处理：0-未处理，1-已处理'
    private Integer isProcessed;

    private String payNumberStr;

    private Integer pushsk;

    private Integer isPay;

    private String cpmId;

    private Integer isCashier;

    //收款账户联行号
    private String payPayeeBankCode;
    //新收款账户联行号
    private String payNewPayeeBankCode;
    //农民工工资账户联行号
    private String payPayeeBankCodeWages;

    /**
     * 支付路径
     */
    private String paymentPath;

    private Date warrantyPaymentTime;
    private String warrantyPaymentFile;
    private BigDecimal warrantyPaymentAmount;
    private String warrantyApplicationFile;
    private Date warrantyRefundTime;
    private String warrantyRefundFile;
    private BigDecimal conPayRate;
    private BigDecimal payApplyConIn;
    private BigDecimal payApplyConOut;
    private BigDecimal quantitiesTotal;
    private BigDecimal quantitiesCompleted;
    private BigDecimal quantitiesRate;

    // 累计没期的质保金总额
    private BigDecimal payRefundSum;

    /**
     * 合同支付类型（标识）
     * 1：旧功能，2：9-2-4合同支付，3：9-2-5其他费用支付，4：9-1-5资金支付
     */
    private String contractPayType;

    private String gfpdId;

    /**
     * 经办人
     */
    private String payHandlerId;

    /**
     * 分管领导
     */
    private String payLeaderId;

    /**
     * 支付状态
     */
    private String paymentStatus;
}
