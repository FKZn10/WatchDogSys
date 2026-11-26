package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 合同概算子表
 */
@Data
@TableName("Con_Estimate_Maintenance_Dtl")
public class ConEstimateMaintenanceDtl {

    @TableId(type = IdType.INPUT)
    private String cemdId;
    private String CemId;
    private String cemdCost;
    private String cemdCostName;
    private String cemdMainCost;
    private String cemdMainCostName;
    private String cemdCompany;
    private String cemdQuantities;
    private BigDecimal cemdUnitPrice;
    private BigDecimal cemdInvestment;
    private String cemdRemarks;
    private BigDecimal cemdTotalExpenses;
    private Integer cemdExpenseType;
    private String cemdStageType;
}
