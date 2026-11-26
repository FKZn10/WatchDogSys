package com.jpmsoft.common.model.request.pay;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ConPayPathDtlRequest {
    /**
     * 主键
     */
    private String id;

    /**
     * 支付ID
     */
    private String payId;

    /**
     * 支付路径
     */
    private String paymentPath;

    /**
     * 文号
     */
    private String documentNo;

    /**
     * 金额
     */
    private BigDecimal amount;
}