package com.jpmsoft.common.model.response.Contract;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 支付变更关联
 */
@Data
public class PayChangeAssociationResponse {
    private String id;
    private String payId;
    private String ecfId;
    private BigDecimal ecfMoney;
    private Integer payRatio;
    private BigDecimal payMoney;
}
