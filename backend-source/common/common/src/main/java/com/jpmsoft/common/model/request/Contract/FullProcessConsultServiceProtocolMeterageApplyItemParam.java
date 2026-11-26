package com.jpmsoft.common.model.request.Contract;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FullProcessConsultServiceProtocolMeterageApplyItemParam {

    private String id;

    // 计量报审id
    private String fullProcessConsultServiceProtocolMeterageApplyId;

    // 合同价（元）
    private BigDecimal contractAmount;

    // 支付条款
    private String payTerms;

    // 批复概算金额（元）
    private BigDecimal budgetApproveAmount;

    // 协议内容
    private String content;

    // 截止上期已支付金额
    private BigDecimal lastTotalPayAmount;

    // 本期计划支付金额
    private BigDecimal thisStagePlanPayAmount;

    // 累计应支付金额
    private BigDecimal totalShouldPayAmount;

    // 累计支付比例
    private BigDecimal totalPayRate;

    // 本期完成工作量
    private String thisStageFinishWork;

    private String paymentDescribe;
}
