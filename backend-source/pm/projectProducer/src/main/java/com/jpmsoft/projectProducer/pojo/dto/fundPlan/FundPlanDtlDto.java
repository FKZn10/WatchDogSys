package com.jpmsoft.projectProducer.pojo.dto.fundPlan;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.ProjectCommonRes;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 资金计划清单表
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/16 11:59
 */
@Data
public class FundPlanDtlDto extends ProjectCommonRes implements Serializable {

    private static final long serialVersionUID = -3902738378782918424L;

    private String fpdId;
    private String fpId;
    @JsonProperty("pId")
    private String pId;
    private String conId;
    private BigDecimal fpdInvestmentCompleted;
    private BigDecimal fpdInvestmentPlan;
    private BigDecimal fpdInvestmentTotal;
    private BigDecimal fpdPayTotal;
    private Float fpdPayRatioTotal;
    private BigDecimal fpdDemand;
    private BigDecimal fpdDemandTotal;
    private Float fpdDemandRatioTotal;
    private BigDecimal fpdDemandFinal;
    private Float fpdDemandRatioTotalFinal;
    private BigDecimal fpdDemandTotalFinal;
    private Integer fpdSort;
    private String fpdFile;
    private String fpdFileIds;

    private String fpdSkCname;
    private BigDecimal fpdMonthAmt;
    private String fpdSecConId;
    private String fpdFeesName;
    private BigDecimal fpdFeesMoney;
    private String fpdPayeeId;

    private String fpdType;

}
