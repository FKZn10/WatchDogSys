package com.jpmsoft.projectProducer.pojo.entity.fund;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 收款收据变化历史
 */
@Data
public class PaymentReceiptHistory {
    private String id;

    /**
     * 收款收据ID
     */
    private String prId;

    /**
     * 来款认领ID
     */
    private String cifId;

    /**
     * 来款认领明细ID
     */
    private String cifdId;

    /**
     * 核销状态
     * 申请中 0
     * 未核销 1
     * 核销成功 2
     * 核销退回 3
     * 已停止 4
     * 核销退回 5
     */
    private Integer verifyState;

    /**
     * 核销金额
     */
    private BigDecimal amount;

    /**
     * 核销时间
     */
    private Date createTime;
}