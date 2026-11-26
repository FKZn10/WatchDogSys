package com.jpmsoft.projectProducer.pojo.entity.investment;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author yb.Sun    估算详情表
 * @date 2024/10/25 15:41
 */
@Data
@TableName("Investment_Estimate_Breakdown_Details")
public class InvestmentEstimateBreakdownDetails {
    private String id;
    //投资估算分解ID
    private String isbId;
    //估算详情表id
    private String ieDetailId;
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
    private Integer actionDisplay;
    private BigDecimal sort;

    private String changeType;

    // 工程量
    private BigDecimal workMeasure;

    // 单位
    private String unit;

    // 序号
    private String levelCode;

    // 这个应该要加一个上级节点

    private String parentId;

    private String unitPrice;

}
