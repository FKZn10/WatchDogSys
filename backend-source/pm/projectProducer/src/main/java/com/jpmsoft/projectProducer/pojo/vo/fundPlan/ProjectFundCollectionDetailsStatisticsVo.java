package com.jpmsoft.projectProducer.pojo.vo.fundPlan;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 资金拨付收款台账
 * @author: 李文涛 aaron
 * @date: 2022/10/17 16:41
 */
@Data
public class ProjectFundCollectionDetailsStatisticsVo implements Serializable {

    private BigDecimal fcdMoneySum = BigDecimal.ZERO;
    private BigDecimal faDemandSum = BigDecimal.ZERO;
    private BigDecimal balanceAmtSum = BigDecimal.ZERO;
    private BigDecimal payApplySum = BigDecimal.ZERO;
    private BigDecimal payBalanceAmtSum = BigDecimal.ZERO;
    private List<ProjectFundCollectionDetailsVo> data;

}
