package com.jpmsoft.projectProducer.pojo.vo.leaderBoard;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class AnnualMaintenanceBudgetVo {
    //修缮项目申报审批个数
    private Integer repairApproval;
    //修缮项目申报完成个数
    private Integer repairApprovalFinish;
    //施工完成确认审批个数
    private Integer constructionCompletionConfirmApproval;
    //施工完成确认完成个数
    private Integer constructionCompletionConfirmFinish;
    //结算送审审批个数
    private Integer settlementSubmissionApproval;
    //结算送审完成个数
    private Integer settlementSubmissionFinish;
    //款项支付审批个数
    private Integer suspendingPaymentApproval;
    //款项支付完成个数
    private Integer suspendingPaymentFinish;
    //计划预算金额总额
    private BigDecimal planBudgetTotal;
    //预算金额实用总额
    private BigDecimal budgetTotal;
    //计划预算金额剩余总额
    private BigDecimal planBudgetTotalSurplus;
    //计划支付金额总额
    private BigDecimal planPayTotal;
    //支付金额实付总额
    private BigDecimal payTotal;
    //计划支付金额剩余总额
    private BigDecimal planPayTotalSurplus;
    //预算金额使用占比: 预算金额实用金额/计算预算金额总额
    private BigDecimal budgetTotalUse;
    //支付金额使用占比:支付金额实付总额/计划支付金额总额
    private BigDecimal payTotalUse;
    // 质保金未退回金额
    private BigDecimal guaranteeAmountNotReturned;
    // 质保金未退回个数
    private Integer guaranteeNotReturnedCount;
    private List<AnnualMaintenanceBudgetListVo> annualMaintenanceBudgetList;
}