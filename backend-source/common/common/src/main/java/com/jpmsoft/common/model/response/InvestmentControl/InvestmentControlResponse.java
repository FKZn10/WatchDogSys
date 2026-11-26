package com.jpmsoft.common.model.response.InvestmentControl;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

//投资控制实时监控
@Data
public class InvestmentControlResponse {
    @JsonProperty("pId")
    private String pId;

    @JsonProperty("pName")
    public String pName;
    @JsonProperty("cId")
    private String cId;
    @JsonProperty("cName")
    private String cName;
    @JsonProperty("dId")
    private String dId;
    @JsonProperty("dName")
    private String dName;

    //概算总投资（万元）
    private BigDecimal pBudgetInvestmentAmount = BigDecimal.ZERO;

    //估算总投资
    private BigDecimal pApproveBudget= BigDecimal.ZERO;

    //总投资
    private BigDecimal allInvestment= BigDecimal.ZERO;

    //实际支付总额
    private BigDecimal actualPaymentAmt= BigDecimal.ZERO;

    //风险提示
    private String riskStatement;

    private Integer pBudgetInvestmentCount;



}
