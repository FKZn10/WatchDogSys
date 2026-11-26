package com.jpmsoft.projectProducer.pojo.vo.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 项目资金统计列表
 * 金额汇总
 *
 * @Author: 李文涛 aaron
 * @date: 2023/5/24 11:09
 */
@Data
public class ProjectFundStatisticsSumVO implements Serializable {


    private static final long serialVersionUID = 876724347445136923L;

    @JsonProperty("pCalculateRoughlyAmountSum")
    private BigDecimal pCalculateRoughlyAmountSum;
    @JsonProperty("pEstimateAmountSum")
    private BigDecimal pEstimateAmountSum;
    @JsonProperty("pApproveBudgetSum")
    private BigDecimal pApproveBudgetSum;
    @JsonProperty("pFinalAccountsSum")
    private BigDecimal pFinalAccountsSum;
    private BigDecimal secMoneyTotalSum;
    private BigDecimal conMoneyTotalSum;
    //合同支付已付款
    private BigDecimal conPayApprovalTotalSum;
    //合同支付年度已付款
    private BigDecimal conPayYearTotalSum;
    //项目支付已付款（无合同支付）
    private BigDecimal proPayApprovalTotalSum;
    /**
     * 结算
     */
    private BigDecimal saApprovedAmtTotalSum;
    /**
     * 未付款
     * 支付申请Pay_Payable减Pay_Approval
     */
    private BigDecimal unPayAmtTotalSum;

    private List<ProjectFundStatisticsVO> data;
}
