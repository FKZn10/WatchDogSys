package com.jpmsoft.projectProducer.pojo.vo.design;

import com.jpmsoft.common.model.response.Design.DesignInitialResponse;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/10/21 12:05
 */
@Data
public class DesignInitialMoneySumVo implements Serializable {

    private static final long serialVersionUID = 6930792745903774900L;
    private BigDecimal diEstimateSum = BigDecimal.ZERO;
    private BigDecimal diBudgetSum = BigDecimal.ZERO;
    private BigDecimal diControlPriceSum = BigDecimal.ZERO;
    private BigDecimal diEstimatePriceSum = BigDecimal.ZERO;
    private List<DesignInitialResponse> data;
}
