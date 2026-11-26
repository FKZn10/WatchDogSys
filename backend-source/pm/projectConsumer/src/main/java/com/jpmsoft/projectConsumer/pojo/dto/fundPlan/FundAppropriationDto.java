package com.jpmsoft.projectConsumer.pojo.dto.fundPlan;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 资金拨付申请
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/16 17:11
 */
@Data

public class FundAppropriationDto extends BaseFileDto implements Serializable {

    private static final long serialVersionUID = -6959173308685808184L;

    private String fpId;
    @JsonProperty("dId")
    private String dId;
    @JsonProperty("cId")
    private String cId;
    private String faCode;
    private String faPayBasis;
    private String faPayExplain;
    private BigDecimal faInvestmentCompleted;
    private BigDecimal faInvestmentActual;
    private BigDecimal faInvestmentTotal;
    private BigDecimal faPayTotal;
    private Float faPayTotalRatio;
    private Float faDemandRatioTotal;
    private BigDecimal faReceived;
    private BigDecimal faDemand;
    private BigDecimal faDemandTotal;
    private Date faDate;
    private Integer faYear;
    private Integer faMonth;

    @JsonProperty("pName")
    private String pName;
    private List<FundAppropriationDtlDto> fundAppropriationDtls;
}
