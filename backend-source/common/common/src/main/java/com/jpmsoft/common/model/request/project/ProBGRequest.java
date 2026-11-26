package com.jpmsoft.common.model.request.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.base.FileFields;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;
import com.jpmsoft.common.model.request.pay.ConFundNoDto;
import com.jpmsoft.common.model.request.pay.PayFieldDtlDto;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConPayWitnessDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 支付申请
 */
@Data
public class ProBGRequest extends BaseFileRequest {
    @ProcessFields(type = ProcessFieldEnum.id)
    private String payId;
    @JsonProperty("pId")
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
    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String payCreateUid;
    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String payCreateUname;
    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date payCreateTime;
    @ProcessFields(type = ProcessFieldEnum.state)
    private Integer payState;
    @ProcessFields(type = ProcessFieldEnum.stateTime)
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
    private String payPaymentNode;

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
    //支付类别 合同 项目 政府缴费
    private String payCategory;
    //费用类别
    private String payExpenseType;
    private BigDecimal payFarmersExpenditure;
    private String payCategoryPayment;
    private String payInvoiceNo;
    private BigDecimal payChangeAmt;
    private String payBase;
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
    private String payFaCode;
    private String payPaymentName;
    private String payPaymentNature;
    private Integer payIsWages;
    private BigDecimal payBalance;
    //合同付款条款
    @FileFields(index = 1)
    private String payContractTerms;
    @FileFields(index = 2)
    private String payPromiseFile;
    @FileFields(index = 3)
    private String payBillFile;
    private String payExpenseCategory;
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
    @FileFields(index = 4)
    private String payApgFile;
    @FileFields(index = 5)
    private String payPbFile;

    //合同付款条款
    @FileFields(index = 1, type = "payContractTermsIds")
    private String payContractTermsIds;
    @FileFields(index = 2, type = "payPromiseFileIds")
    private String payPromiseFileIds;
    @FileFields(index = 3, type = "payBillFileIds")
    private String payBillFileIds;
    @FileFields(index = 4, type = "payApgFileIds")
    private String payApgFileIds;
    @FileFields(index = 5, type = "payPbFileIds")
    private String payPbFileIds;
    private String conName;
    private String conCode;
    @JsonProperty("pName")
    private String pName;
    private String conType;
    private String conPartyBName;
    private Boolean thisProject;
    private List<String> conMoneyTotals;
    private List<String> payCreateTimes;
    private List<String> payApplys;
    private List<String> payNumbers;
    //申请单位
    private String payApplicantName;
    private String payPayeeName;
    private String payDraweeName;
    @Deprecated
    private List<PayFieldDtlDto> payFieldDtls;
    private List<ConFundNoDto> conFundNos;
    private BigDecimal payFundPlanSurplus;
    private BigDecimal payProjectPaymentSurplus;

    /**
     * 概算清单ID
     * 筛选包含概算子项的合同支付
     */
    private String bddId;
    private String conIdSup;
    private BigDecimal paySecurityCosts;
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
    private List<String> atIds;

    private List<ConPayWitnessDTO> conPayWitnesses;

    private String code;

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }
}
