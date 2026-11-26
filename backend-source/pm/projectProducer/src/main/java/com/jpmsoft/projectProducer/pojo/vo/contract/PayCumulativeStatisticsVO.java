package com.jpmsoft.projectProducer.pojo.vo.contract;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/8/10 8:45
 */
@Data
public class PayCumulativeStatisticsVO implements Serializable {

    private BigDecimal payApprovalCount = BigDecimal.ZERO;
    private BigDecimal payApplyCount = BigDecimal.ZERO;
    private BigDecimal payDoneTotalCount = BigDecimal.ZERO;
    private BigDecimal payChangeCount = BigDecimal.ZERO;
    private BigDecimal payThisChangeAmountCount = BigDecimal.ZERO;
    private BigDecimal payPayableLast = BigDecimal.ZERO;
    private BigDecimal payBonusLast = BigDecimal.ZERO;
    private BigDecimal payDeductQtLast = BigDecimal.ZERO;
    private BigDecimal payDeductYfkLast = BigDecimal.ZERO;
    private BigDecimal payDeductFineLast = BigDecimal.ZERO;
    private BigDecimal payDeductZbjLast = BigDecimal.ZERO;
    private BigDecimal payDeductScLast = BigDecimal.ZERO;
    private BigDecimal payApplySupLast = BigDecimal.ZERO;
    private BigDecimal payBonus = BigDecimal.ZERO;
    private BigDecimal payThisChangeAmount = BigDecimal.ZERO;
    private BigDecimal payDeductFine = BigDecimal.ZERO;
    private BigDecimal payFarmersPeriod = BigDecimal.ZERO;
    /**
     * 累计农民工工资金额，按统计范围汇总支付记录的 payFarmersExpenditure。
     */
    private BigDecimal payFarmersExpenditureTotal = BigDecimal.ZERO;
    private Integer batchNo;
    private BigDecimal payDeductYfk = BigDecimal.ZERO;
    private BigDecimal payDeductSc = BigDecimal.ZERO;
    private BigDecimal meaCompleted = BigDecimal.ZERO;
    private BigDecimal meaPeriodAmt = BigDecimal.ZERO;
    private BigDecimal meaCurrentlyAmt = BigDecimal.ZERO;
    private BigDecimal payLiquidatedDamagesCount= BigDecimal.ZERO;
    private BigDecimal paySecurityCosts=BigDecimal.ZERO;
}
