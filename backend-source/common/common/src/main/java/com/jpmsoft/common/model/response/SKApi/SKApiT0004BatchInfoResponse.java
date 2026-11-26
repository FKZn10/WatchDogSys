package com.jpmsoft.common.model.response.SKApi;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 批次信息
 */
@Data
public class SKApiT0004BatchInfoResponse {

    /**
     * 外部系统单据编号
     */
    private String billsPayOrderNo;

    /**
     * 业务状态
     */
    private Integer payStatus;

    /**
     * 业务状态名称
     */
    private String payStatusName;

    /**
     * 失败原因
     */
    private String failReasons;

    /**
     * 实际付款日期
     */
    private String relPaymentDate;

    /**
     * 付款金额
     */
    private BigDecimal applyAmount;

    /**
     * 已付金额
     */
    private BigDecimal payAmount;

    /**
     * 在途金额
     */
    private BigDecimal tranistAmount;

    /**
     * 未付金额
     */
    private BigDecimal unAcceptAmount;

    /**
     * 退款金额
     */
    private BigDecimal refundAmount;
}
