package com.jpmsoft.projectProducer.pojo.entity.fundPlan;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 资金拨付申请清单
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/16 17:14
 */
@Data
@TableName("Fund_Appropriation_Dtl")
public class FundAppropriationDtl {

    @TableId(type = IdType.INPUT)
    private String fadId;
    private String faId;
    @JsonProperty("pId")
    private String pId;
    private String conId;
    private BigDecimal fadInvestmentCompleted;
    private BigDecimal fadInvestmentActual;
    private BigDecimal fadInvestmentTotal;
    private BigDecimal fadPayTotal;
    private Float fadPayRatioTotal;
    private BigDecimal fadDemand;
    private BigDecimal fadDemandAppr;
    private BigDecimal fadDemandTotal;
    private Float fadDemandRatioTotal;
    private Integer fadSort;
    private String fadFile;
}
