package com.jpmsoft.projectConsumer.pojo.dto.fundPlan;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 资金拨付申请清单
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/16 17:14
 */
@Data
public class FundAppropriationDtlDto extends BaseModeRequest implements Serializable {

    private static final long serialVersionUID = -6607645512222295928L;

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
    private String fadFileIds;
}
