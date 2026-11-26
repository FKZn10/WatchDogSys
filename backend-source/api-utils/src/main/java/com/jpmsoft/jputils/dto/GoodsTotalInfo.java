package com.jpmsoft.jputils.dto;

import lombok.Data;

/**
 * @author yb.Sun
 * @date 2025/1/17 10:46
 */
@Data
public class GoodsTotalInfo {

    /**
     * 名称
     */
    private String name;

    /**
     * 规格
     */
    private String spec;

    /**
     * 单位
     */
    private String unit;

    /**
     * 数量
     */
    private String amount;

    /**
     * 单价
     */
    private String priceUnit;

    /**
     * 金额
     */
    private String priceSum;

    /**
     * 税率
     */
    private String taxRate;

    /**
     * 税额
     */
    private String taxSum;

    /**
     * 原始税率字符串，例如【不征收】
     */
    private String orgTaxRate;
}
