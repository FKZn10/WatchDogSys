package com.jpmsoft.common.model.response.SKApi;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 司库接口响应体数据T0004 实体类
 */
@Data
public class SKApiBodyDataT0004Response {
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
     * 实际支付日期
     */
    private String relPaymentDate;

    /**
     * 单据受理类型
     */
    private String acceptType;

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
     * 批次列表
     */
    private List<SKApiT0004BatchInfoResponse> batchList;

    /**
     * 申请单列表
     */
    private List<SKApiT0004ApplyInfoResponse> applyList;
}
