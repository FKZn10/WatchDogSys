package com.jpmsoft.projectProducer.pojo.dto.contract;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/9/25 15:39
 */
@Data
public class PayFundAllocationDTO  implements Serializable {

    private static final long serialVersionUID = 2233574071571864225L;

    private String conPartyBName;
    private String pName;
    private String conName;
    private String conType;
    private BigDecimal conMoneyTotal=BigDecimal.ZERO;
    private BigDecimal conMoneyTentative=BigDecimal.ZERO;
    private String conPartyAAgent;
    private BigDecimal faApprovedAmt=BigDecimal.ZERO;
    private BigDecimal asAuditedAmount=BigDecimal.ZERO;
    private BigDecimal fadActualPaymentSum=BigDecimal.ZERO;
    private String conDepartment;
    private boolean firstAllocation=true;

    /**
     * 本次拨付金额
     */
    private BigDecimal bcPayAmt=BigDecimal.ZERO;
}
