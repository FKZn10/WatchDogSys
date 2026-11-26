package com.jpmsoft.common.model.response.SKApi;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 申请单信息
 */
@Data
public class SKApiT0004ApplyInfoResponse {

    /**
     * 申请单号
     */
    private String orderNo;

    /**
     * 申请单状态
     */
    private Integer payStatus;

    /**
     * 申请单状态名称
     */
    private String payStatusName;

    /**
     * 付款金额
     */
    private BigDecimal payAmount;

    /**
     * 付款成功金额
     */
    private BigDecimal paySucAmount;

    /**
     * 退款金额
     */
    private BigDecimal refundAount;

    /**
     * 扣款金额
     */
    private BigDecimal deductionAmount;

    /**
     * 汇率
     */
    private BigDecimal exchangeRate;

    /**
     * 失败原因
     */
    private String failureReason;

    /**
     * 支付摘要
     */
    private String payUse;

    /**
     * 信用证号
     */
    private String locCode;

    /**
     * 到单承付单号
     */
    private String arrivalApplyNo;

    /**
     * 付款账号
     */
    private String payAccountNo;

    /**
     * 付款账号名称
     */
    private String payAccountName;

    /**
     * 付款单位编号
     */
    private String payOrgCode;

    /**
     * 付款单位名称
     */
    private String payOrgName;

    /**
     * 实际付款日期
     */
    private String relPaymentDate;

    /**
     * 付款类型
     */
    private Integer payType;

    /**
     * 当前待办人名称
     */
    private String currentUserName;

    /**
     * 收款记录列表
     */
    private List<SKApiT0004PaymentRecInfoResponse> paymentRecList;
}
