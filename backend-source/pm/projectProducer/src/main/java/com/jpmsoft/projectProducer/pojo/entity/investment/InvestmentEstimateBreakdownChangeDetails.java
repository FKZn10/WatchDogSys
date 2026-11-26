package com.jpmsoft.projectProducer.pojo.entity.investment;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**

 */
@Data
@TableName("Investment_Estimate_Breakdown_change_Details")
public class InvestmentEstimateBreakdownChangeDetails {
    private String id;
    //投资估算、概算分解表id
    private String isbId;
    //投资估算、概算分解变更表id
    private String isbChangeId;
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
    private Double isbdAmount;
    //备注
    private String isbdRemark;
    private Integer actionDisplay;
    private BigDecimal sort;
    //变更类型标识
    private String changeType;
}
