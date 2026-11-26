package com.jpmsoft.projectProducer.pojo.vo.contract;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: 李文涛 aaron
 * @date: 2022/12/27 9:12
 */
@Data
public class ConPayGroupByContractVo implements Serializable {

    private static final long serialVersionUID = 5056141833955614190L;
    private BigDecimal payApplySum;
    private BigDecimal payWorkAmountTotalSum;
    private BigDecimal payUnfinishedWorkSum;
    private BigDecimal payLiquidatedDamagesSum;
    private BigDecimal payDeductQtSum;
    private BigDecimal payApprovaChangeTotalSum;
    private BigDecimal payTotalLastSum;
    private BigDecimal payTotalSum;
    private BigDecimal payDoneSum;
    private Object data;
}
