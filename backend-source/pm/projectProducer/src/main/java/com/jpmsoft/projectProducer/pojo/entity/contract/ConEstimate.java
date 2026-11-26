package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 合同概算表
 */
@Data
@TableName("Con_Estimate")
public class ConEstimate {

    @TableId(type = IdType.INPUT)
    private String ceId;
    private String ConId;
    private String ceCost;
    private String ceCostName;
    private String ceMainCost;
    private String ceMainCostName;
    private String ceCompany;
    private String ceQuantities;
    private BigDecimal ceUnitPrice;
    private BigDecimal ceInvestment;
    private String ceRemarks;
    private BigDecimal ceTotalExpenses;
    private Integer ceExpenseType;
    private String ceStageType;
}
