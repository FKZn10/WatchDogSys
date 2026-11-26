package com.jpmsoft.projectConsumer.pojo.dto.fundPlan;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 资金计划表
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/16 11:55
 */
@Data
public class FundPlanDto extends BaseFileDto implements Serializable {

    private static final long serialVersionUID = 3491200971457490429L;

    @JsonProperty("pName")
    private String pName;
    @JsonProperty("dId")
    private String dId;
    @JsonProperty("cId")
    private String cId;
    @JsonProperty("cName")
    private String cName;
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
    private String fpCnature;
    private BigDecimal fpBqPayTotal;
    private String fpBz;
    private String fpType;
    private BigDecimal fpApproveBudget;
    private BigDecimal fpPreviousPayment; //截止上期支付金额
    private BigDecimal fpPreviousPaymentRatio; //截止上期累计支付比例

    private BigDecimal fpPmonthlyPaymentRatio; //当月支付比例

    private List<FundPlanDtlDto> fundPlanDtls;

    private List<FundPlanDtlDto> fundPlanDtls2;
    //项目建设管理费信息
    private List<FundPlanDtlDto> fundPlanDtls3;
}
