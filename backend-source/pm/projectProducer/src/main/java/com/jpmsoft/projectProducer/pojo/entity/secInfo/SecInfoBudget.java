package com.jpmsoft.projectProducer.pojo.entity.secInfo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 工程概算
 */
@Data
@TableName("Sec_Info_Budget")
public class SecInfoBudget {
    @TableId(type = IdType.INPUT)
    private String sibId;
    //标段id
    private String secId;
    //项目费用
    private String sibCost;
    //项目费用名称
    private String sibCostName;
    //主要项目及费用
    private String sibMainCost;
    //主要项目及费用名称
    private String sibMainCostName;
    //单位
    private String sibCompany;
    //工程量
    private String sibQuantities;
    //单价
    private BigDecimal sibUnitPrice;
    //投资
    private BigDecimal sibInvestment;
    private String sibRemarks;
    //合计
    private BigDecimal sibTotalExpenses;
    //费用类型（1：主要，0：次要）
    private Integer sibExpenseType;
    //阶段类型（01、概算 02、估算 03、匡算）
    private String sibStageType;
}
