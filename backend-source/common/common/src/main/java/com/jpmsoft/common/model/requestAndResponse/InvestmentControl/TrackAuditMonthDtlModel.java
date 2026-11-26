package com.jpmsoft.common.model.requestAndResponse.InvestmentControl;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 跟踪审计月报台账
 */
@Data
public class TrackAuditMonthDtlModel {
    private String pId;
    private String trackCId;
    private String trackCName;
    private String conId;
    private String conName;
    private String sgName;
    private Date planStartTime;
    private Date planEndTime;
    private BigDecimal conTotalAmount;
    private List<TrackAuditMonthDtlQD> trackAuditMonthDtlQD;
}
