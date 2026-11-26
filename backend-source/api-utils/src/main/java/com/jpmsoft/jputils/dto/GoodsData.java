package com.jpmsoft.jputils.dto;

import lombok.Data;

/**
 * @author yb.Sun
 * @date 2025/1/17 10:46
 */
@Data
public class GoodsData {

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
    // 承运人
    private String cyr;
    // 承运日期
    private String cyrq;
    // 航班号
    private String hbh;
    // 目的站
    private String mdz;
    // 起飞时间
    private String qfsj;
    // 始发站
    private String sfz;
    // 座位等级
    private String zwdj;
}
