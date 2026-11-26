package com.jpmsoft.projectProducer.pojo.vo.contract;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 合同概算汇总
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/26 16:57
 */
@Data
public class ConBudgetSumVo implements Serializable {
    //无概算合同数
    private int nonBudgetCount;
    //无概算合同价
    private BigDecimal nonBudgetAmt = BigDecimal.ZERO;
}
