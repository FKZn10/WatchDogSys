package com.jpmsoft.common.model.response.pay;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.ProjectCommonRes;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConPayWitnessDTO;
import com.jpmsoft.common.model.response.Claim.PcAwardTicketResponse;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 支付申请
 */
@Data
public class ConPayResponse extends ProjectCommonRes {

    private String payId;
    private String conId;
    private String payType;
    private String payCode;
    private String payLetterStatus;

    private Date payLetterDate;
    private String payKeyWords;

    private String payReferenceNumber;

    private Integer payMaintenance;
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
    @JsonProperty("dId")
    private String dId;
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
    private BigDecimal payDoneOwner;
    //其中人工费(监理审核)
    private BigDecimal payDoneLaborCostSup;
    private BigDecimal payDoneLaborCostOwner;
    //其中工料机费及其它费用（监理审核）
    private BigDecimal payDoneQtjkSup;
    private BigDecimal payDoneQtjkOwner;
    //至本期累计已完成工程造价（监理审核）
    private BigDecimal payDoneTotalSup;
    private BigDecimal payDoneTotalOwner;
    //本期应扣保留金（监理审核）
    private BigDecimal payDeductZbjSup;
    private BigDecimal payDeductZbjOwner;
    //本期应扣预付款（监理审核）
    private BigDecimal payDeductYfkSup;
    private BigDecimal payDeductYfkOwner;
    //预付款余额（监理审核）
    private BigDecimal payPrePaymentBalanceSup;
    private BigDecimal payPrePaymentBalanceOwner;
    //本期应付进度款（监理审核）
    private BigDecimal payApplySup;
    private BigDecimal payApplyOwner;
    //其中本期应付进度款人工费（监理审核）
    private BigDecimal payLaborCostPayableSup;
    private BigDecimal payLaborCostPayableOwner;
    //其中本期应付进度款工料机费及其他费用（监理审核）
    private BigDecimal payOtherExpensesPayableSup;
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
    private BigDecimal payFarmersExpenditure;
    private String payFundNo;

    private String payRepairOne;
    private String payRepairTwo;
    private String payRepairThree;
    private String payRepairOneName;
    private String payRepairTwoName;
    private String payRepairThreeName;
    private BigDecimal payAdvanceTotal;
    private BigDecimal payActualPayTotal;
    private BigDecimal payActualPayTotalP;
    private BigDecimal payDefaultTotal;
    private BigDecimal paySettlementPriceBuckle;
    private BigDecimal payApprovaChangeTotal;
    private BigDecimal payWorkAmountTotal;
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
    private String meaId;

    //【支付】其中本期应扣违约金
    private BigDecimal payLiquidatedDamages;
    //【支付】其中本期应扣违约金
    private BigDecimal payLiquidatedDamagesSup;
    //【支付】其中本期应扣违约金
    private BigDecimal payLiquidatedDamagesOwner;
    //【收款】单位名称（无合同支付时手填）
    private String payPayeeNamePro;
    //支付类别 合同 项目 政府缴费
    private String payCategory;
    //费用类别
    private String payExpenseType;
    private BigDecimal payFundPlanSurplus;
    private BigDecimal payProjectPaymentSurplus;
    private String payFaCode;
    private String payPaymentName;
    private String payPaymentNature;
    private Integer payIsWages;
    private BigDecimal payBalance;
    private String payPromiseFile;
    private String payBillFile;
    private String payExpenseCategory;
    private String meaCode;
    private String conName;
    private String payPayeeName;
    private String payPayeeName1;
    private String payDraweeName;
    @JsonProperty("dName")
    private String dName;
    private String conCode;
    private String conState;
    private String conMoneyTotal;
    private String conMoneyTentative;
    private String secId;
    //申请单位
    private String payApplicantName;
    //申请部门
    private String payApplicationDepartmentName;
    private BigDecimal conAcMoney;
    private String conPartyBName;
    private String conPartyBNameHistory;
    private String conPartyAName;
    private String conPartyAId;
    private String conDepartment;
    private String conDepName;
    private String conType;
    private String payPayeeNameHistory;
    private String payDraweeNameHistory;
    private String payApplicantNameHistory;
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
    private BigDecimal payChangePayTotalThis= BigDecimal.ZERO;;
    private BigDecimal payPayableLast;
    private BigDecimal payPayableThis;
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
    private String payIsPayee;
    private String payNewPayeeBank;
    private String payNewPayeeCa;
    /**
     * 履约保函有效期
     */
    private Date perfValidity;
    /**
     * 预付款保函有效期
     */
    private Date prePayValidity;
    /**
     * 履约保函是否办退
     */
    private Boolean perfRefund;
    /**
     * 预付款保函是否办退
     */
    private Boolean prePayRefund;

    /**
     * 合同乙方单位联系人
     */
    @JsonProperty("cContactperson")
    private String cContactperson;

    /**
     * 合同乙方单位联系人电话
     */
    @JsonProperty("cContactpersontel")
    private String cContactpersontel;
    /**
     * 合同余款
     */
    private BigDecimal conBalance;
    private String payCategoryPayment;
    private String payInvoiceNo;
    private BigDecimal payChangeAmt;
    private String payBase;
    private String cppId;
    private String cppClause;
    //单位简称
    private String payPayeeAbbName;
    private String payDraweeAbbName;
    private String payApplicantAbbName;
    private String conPartyBAbbName;
    private String conPricingMethod;
    private String conOtherValuation;
    private String conIdSup;
    private String conCategory;
    private List<Map<String, Object>> conList;
    @Deprecated
    private List<PayFieldDtlVo> payFieldDtls;
    private List<ConFundNoVo> conFundNos;
    private BigDecimal paySecurityCosts;
    private List<PcAwardTicketResponse> pcAwardTickets;
    private List<ConPayWitnessDTO> conPayWitnesses;
    private List<Object> conPayFundsDetails;
    // 预付款支付比例
    private String conAcPayRate;
    // 进度款支付比例
    private String conProgressRatio;

    // 跟审审核金额
    private BigDecimal payGsAmt;

    //苏相支付
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
    private BigDecimal payDeductScThis = BigDecimal.ZERO;;
    /**
     * 【支付】应扣安全文明措施费_本期发生
     */
    private BigDecimal payDeductSc;
    private String payIsSecurity;
    private BigDecimal paySecurityCost;
    private BigDecimal paySecurityThis;
    private BigDecimal paySecuritySum;

    // 发票附件
    private String invoiceFileRelationId;

    // 是否已上传发票附件，1是，其他否
    private Integer uploadedInvoice;

    private  String ccdId;
    private String ccdBusiness;
    private String ccdPlan;
    private String cId;
    private String ccdAmt;
    private String ccdRemark;

    private String conPartyBId;

    private String cName;

    private String contractAmount;

    private String conPartyAAgentId;

    private String conPartyAAgent;

    private BigDecimal settlementamount;

    private BigDecimal payApplySum;

    private BigDecimal conMoneyTotalSum;

    private String payApplicationType;

    private String cppSort;

    private String cppName;

    private BigDecimal appropriateAmt;

    private String cppConditiontype;

    private String cppConditionValuel;

    private String cppMode;

    private String cppBasics;

    private String cppConJdlx;

    private String cppRatioPayTo;

    private String cppMoney;
    private BigDecimal cpafApplicationSum;

    private Boolean canPush;

    // 付款总共期数
    private Integer PayNumberSum;

    // 工程款金额
    private BigDecimal PayAomount;

    // 暂列金是否符合要求 默认符合要求
    private String conForm = "true";

    private Float payProportion;
    //经费账户户名
    private String payFundAccount;
    // 经费账户账号
    private String payFundAccountNum;
    // '是否已处理：0-未处理，1-已处理'
    private Integer isProcessed;

    private String payNumberStr;
    private Integer payYear;
    private Integer payMonth;

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

    private String conTerm;

    // 质保金总额
    private BigDecimal payRefundSum;

    /**
     * 合同支付类型（标识）
     * 1：旧功能，2：9-2-4合同支付，3：9-2-5其他费用支付，4：9-1-5资金支付
     */
    private String contractPayType;

    private BigDecimal cmpActualPayment;
    private Integer cmpPeriod;
    private BigDecimal gfpdBalancePayAmount;
    private BigDecimal gfpdExpectedPayAmount;
    private BigDecimal gfpdTotalPayAmount;

    /**
     * 支付状态
     */
    private String paymentStatus;

    private List<ConPayPathDtlResponse> conPayPathDtls;
}