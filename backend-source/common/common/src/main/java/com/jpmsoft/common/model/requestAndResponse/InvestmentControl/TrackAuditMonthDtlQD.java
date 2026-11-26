package com.jpmsoft.common.model.requestAndResponse.InvestmentControl;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 跟踪审计月报台账清单
 */
@Data
public class TrackAuditMonthDtlQD {
    private String monthDate;
    private BigDecimal completeValue;
    private BigDecimal totleValue;
    private BigDecimal totleRate;
}
