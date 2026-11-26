package com.jpmsoft.projectProducer.pojo.vo.fundPlan;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.ProjectCommonRes;
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
public class FundPlanVo extends ProjectCommonRes implements Serializable {

    private static final long serialVersionUID = -4185007128411129341L;

    private String id;
    private String createUname;
    private String createUid;
    private Date createTime;
    private Integer state;
    private Date stateTime;
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
    private String fpCnature;
    private BigDecimal fpBqPayTotal;
    private String fpBz;
    private String fpType;
    private BigDecimal fpApproveBudget;


    private BigDecimal fpPreviousPayment; //截止上期支付金额
    private BigDecimal fpPreviousPaymentRatio; //截止上期累计支付比例

    private BigDecimal fpPmonthlyPaymentRatio; //当月支付比例

    @JsonProperty("cName")
    private String cName;
    @JsonProperty("cAbbName")
    private String cAbbName;
    @JsonProperty("dName")
    private String dName;
    @JsonProperty("pApproveBudget")
    private BigDecimal pApproveBudget;
    private List<FundPlanDtlVo> fundPlanDtls;
    private List<FundPlanDtlVo> fundPlanDtls2;
    //项目建设管理费信息
    private List<FundPlanDtlVo> fundPlanDtls3;
}
