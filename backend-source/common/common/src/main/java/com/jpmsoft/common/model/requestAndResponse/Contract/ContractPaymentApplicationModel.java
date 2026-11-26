package com.jpmsoft.common.model.requestAndResponse.Contract;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 合同计量计价支付申请清单
 */
@Data
public class ContractPaymentApplicationModel {
    private String id;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private String serverItem;
    private String serviceType;
    private BigDecimal contractPrice;
    private String payRule;
    private BigDecimal approvedEstimateAmount;
    private BigDecimal amountAsOfPrevious;
    private BigDecimal currentPlannedPaymentAmount;
    private BigDecimal cumulativeAmountPayable;
    private BigDecimal cumulativeContractPaymentRatio;
    private String contractMeasurementValuationId;
    private String requestDescription;
}
