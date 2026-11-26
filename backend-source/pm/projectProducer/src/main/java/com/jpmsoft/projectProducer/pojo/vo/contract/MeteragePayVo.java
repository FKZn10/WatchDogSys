package com.jpmsoft.projectProducer.pojo.vo.contract;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MeteragePayVo {
    private String id;

    // 协议内容
    private String content;

    // 合同价
    private BigDecimal contractAmount;

    // 支付条款
    private String payTerms;

    // 概算批复金额
    private BigDecimal budgetApproveAmount;

    // 截止上期已支付金额
    private BigDecimal lastTotalPayAmount;

}
