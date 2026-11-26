package com.jpmsoft.common.model.requestAndResponse.InvestmentControl;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 跟踪审计月报
 */
@Data
public class TrackAuditMonth {
    private String tamId;
    private String pId;
    private String pName;
    private String trackCId;
    private String trackCName;
    private String conId;
    private String conName;
    private String sgName;
    private Date planStartTime;
    private Date planEndTime;
    private BigDecimal conTotalAmount;
    private Date monthDate;
    private BigDecimal completeValue;
    private BigDecimal totleValueNotThis;

    private BigDecimal totleValue;
    private BigDecimal totleRate;
    private Integer state;
    private Date stateTime;
    private Integer dataStatus;
}
