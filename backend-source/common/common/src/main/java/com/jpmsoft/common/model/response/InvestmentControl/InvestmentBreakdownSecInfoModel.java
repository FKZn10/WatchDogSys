package com.jpmsoft.common.model.response.InvestmentControl;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InvestmentBreakdownSecInfoModel {

    //费用类型名称
    private String isbdFeeTypeName;

    //类目
    private String isbdType;

    //标段类型
    private String isbdSectionType;

    //标段名称/费用名称
    private String isbdSectionName;

    //估算价
    private BigDecimal isbdAmount;

    //投资概算分解-概算价（万元）
    private BigDecimal isbdBudgetAmount;

    //概算表id
    private String ibId;

    //概算详细表id
    private String ibDetailId;

    //估算表id
    private String ieId;

    //估算详细表id
    private String ieDetailId;

    //排序
    private BigDecimal sort;

    //是否是全过程
    private Integer isAllProcessAsk;

    private String isAllProcessMsg;

    private String changeType;

}
