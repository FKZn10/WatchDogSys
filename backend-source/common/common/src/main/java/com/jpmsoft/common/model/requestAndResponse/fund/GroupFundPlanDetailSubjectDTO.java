package com.jpmsoft.common.model.requestAndResponse.fund;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 资金计划明细-概算关联表
 */
@Data
public class GroupFundPlanDetailSubjectDTO {
    private String id;

    /**
     * 资金计划id
     */
    private String gfpId;

    /**
     * 资金计划明细id
     */
    private String gfpdId;

    /**
     * 概算科目id（ibbdId）
     */
    private String ibbId;

    /**
     * 估算科目id（iebdId）
     */
    private String iebId;

    //======概算估算详情信息=======
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
    //投资概算分解-概算价（万元）
    private Double isbdBudgetAmount;
    //投资概算分解-概算备注
    private String isbdBudgetRemark;
    private Integer actionDisplay;
    private BigDecimal sort;
    //估算详情表id
    private String ieDetailId;
    private String changeType;
    // 工程量
    private Double workMeasure;
    // 单位
    private String unit;
    // 单价
    private String unitPrice;
    // 序号
    private String levelCode;
}
