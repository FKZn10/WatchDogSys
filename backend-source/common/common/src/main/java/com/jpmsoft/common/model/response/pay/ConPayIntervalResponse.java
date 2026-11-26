package com.jpmsoft.common.model.response.pay;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author jjz
 */
@Data
public class ConPayIntervalResponse {

    // 最小支付年份
    private Integer minNper;


    // 对应年份及对应的支付金额
    private Map<String, BigDecimal> nperPayData;
}
