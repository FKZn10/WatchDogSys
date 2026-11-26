package com.jpmsoft.projectProducer.pojo.vo.contract;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/24 14:30
 */
@Data
public class ContractFundStatisticsSumVO implements Serializable {

    private static final long serialVersionUID = 8336369111562158409L;
    private BigDecimal conMoneyTotalSum;
    private BigDecimal payApprovalTotalSum;
    private BigDecimal payYearTotalSum;
    private BigDecimal unPayAmtTotalSum;
    private BigDecimal payApprovalRateSum;
    private BigDecimal ecApplyMoneySum;
    private BigDecimal ecfApplyMoneySum;
    private BigDecimal payDoneSum;
    private BigDecimal saApprovedAmtTotalSum;
    private BigDecimal saContractorAmountTotalSum;
    private BigDecimal saSupervisorAmountTotalSum;
    private BigDecimal saCostConsultTotalSum;
    private BigDecimal isPaymentAmtTotalSum;
    private BigDecimal isPaymentRateSum;
    private BigDecimal ciCollectTaxAmtTotalSum;
    private BigDecimal isActualAmtTotalSum;
    private List<ContractFundStatisticsVO> data;
}
