package com.jpmsoft.projectProducer.pojo.vo.fundPlan;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 计算汇总查询资金计划
 *
 * @author: 李文涛 aaron
 * @date: 2022/9/2 8:57
 */
@Data
public class ConAppropriationStatisticsVo implements Serializable {
    /**
     * 前期已完成投资
     */
    private BigDecimal invCompleted = BigDecimal.ZERO;
    /**
     * 前期已付资金
     */
    private BigDecimal payTotal = BigDecimal.ZERO;
    /**
     * 本期需求资金
     */
    private BigDecimal demand = BigDecimal.ZERO;

}
