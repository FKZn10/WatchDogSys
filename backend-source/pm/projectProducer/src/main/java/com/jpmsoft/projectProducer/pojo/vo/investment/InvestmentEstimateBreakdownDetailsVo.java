package com.jpmsoft.projectProducer.pojo.vo.investment;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author yb.Sun
 * @date 2024/10/25 17:01
 */
@Data
public class InvestmentEstimateBreakdownDetailsVo {
    private String id;
    //投资估算分解ID
    private String isbId;
    //费用类型名称
    private String isbdFeeTypeName;
    //类目
    private String isbdType;
    //标段类型
    private String isbdSectionType;
    //标段名称/费用名称
    private String isbdSectionName;
    //金额
    private Double isbdAmount;
    //备注
    private String isbdRemark;
    private Integer actionDisplay;
    private BigDecimal sort;

    private String changeType;

    // 本级节点
    private String levelCode;

    // 这个应该要加一个上级节点

    private String parentId;


    // 工程量
    private Integer workMeasure;

    // 单位
    private String unit;

}
