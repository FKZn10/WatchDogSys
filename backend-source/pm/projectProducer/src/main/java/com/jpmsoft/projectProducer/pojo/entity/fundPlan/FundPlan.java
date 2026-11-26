package com.jpmsoft.projectProducer.pojo.entity.fundPlan;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 资金计划表
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/16 11:55
 */
@Data
@TableName("Fund_Plan")
public class FundPlan extends BaseEntity {

    @JsonProperty("pId")
    private String pId;
    @JsonProperty("dId")
    private String dId;
    @JsonProperty("cId")
    private String cId;
    private String fpCode;
    private String fpProgress;
    private BigDecimal fpInvestmentCompleted;
    private BigDecimal fpInvestmentPlan;
    private BigDecimal fpInvestmentTotal;
    private BigDecimal fpPayTotal;
    private Float fpPayTotalRatio;
    private BigDecimal fpReceived;
    private BigDecimal fpDemand;
    private Float fpDemandRatioTotal;
    private BigDecimal fpDemandTotal;
    private BigDecimal fpBookBalance;
    private BigDecimal fpGap;
    private Date fpDate;
    private Integer fpYear;
    private Integer fpMonth;
    private BigDecimal fpDemandFinal;
    private Float fpDemandRatioTotalFinal;
    private BigDecimal fpDemandTotalFinal;
    private String teId;

    private BigDecimal fpPreviousPayment; //截止上期支付金额
    private BigDecimal fpPreviousPaymentRatio; //截止上期累计支付比例

    private BigDecimal fpPmonthlyPaymentRatio; //当月支付比例
    private String fpCnature;
    private BigDecimal fpBqPayTotal;
    private String fpBz;
    private String fpType;
    private BigDecimal fpApproveBudget;
}
