package com.jpmsoft.projectProducer.pojo.vo.investment;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author yb.Sun
 * @date 2024/11/1 16:14
 */
@Data
public class InvestmentBudgetBreakdownChangeDetailsVo {
    private String id;
    //估算详情表id
    private String ieDetailId;
    //概算详情表id
    private String ibDetailId;
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
    private BigDecimal isbdAmount;
    //备注
    private String isbdRemark;
    //投资概算分解-概算价（万元）
    private BigDecimal isbdBudgetAmount;
    //投资概算分解-概算备注
    private String isbdBudgetRemark;
    private Integer actionDisplay;
    private BigDecimal sort;
    //变更类型标识
    private String changeType;
}
