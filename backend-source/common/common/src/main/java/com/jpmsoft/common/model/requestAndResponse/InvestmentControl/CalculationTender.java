package com.jpmsoft.common.model.requestAndResponse.InvestmentControl;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 标底测算
 */
@Data
public class CalculationTender {
    private String pId;
    private String secId;
    private BigDecimal zbysj;
    private BigDecimal zlje;
    private BigDecimal gsj;
}
