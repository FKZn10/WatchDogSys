package com.jpmsoft.common.model.request.InvestmentControl;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

//投资控制实时监控
@Data
public class InvestmentControlRequest {
    @JsonProperty("pId")
    private String pId;

    @JsonProperty("pName")
    public String pName;
    @JsonProperty("cId")
    private String cId;
    @JsonProperty("cName")
    private String cName;

    //概算总投资（万元）
    private BigDecimal pBudgetInvestmentAmount;

    //估算总投资
    private BigDecimal pApproveBudget;

    //总投资
    private BigDecimal allInvestment;

    //实际支付总额
    private BigDecimal actualPaymentAmt;

    //风险提示
    private String riskStatement;

    private Integer page;

    private Integer size;


    @JsonProperty("pNameList")
    private List<String> pNameList;
    @JsonProperty("cNameList")
    private List<String> cNameList;
    @JsonProperty("pApproveBudgetList")
    private List<BigDecimal> pApproveBudgetList;
    @JsonProperty("pBudgetInvestmentAmountList")
    private List<BigDecimal> pBudgetInvestmentAmountList;
    @JsonProperty("allInvestmentList")
    private List<BigDecimal> allInvestmentList;
    @JsonProperty("actualPaymentAmtList")
    private List<BigDecimal> actualPaymentAmtList;


}
