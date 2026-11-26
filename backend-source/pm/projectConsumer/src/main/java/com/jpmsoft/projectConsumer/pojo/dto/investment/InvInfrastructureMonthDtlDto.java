package com.jpmsoft.projectConsumer.pojo.dto.investment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 基建投资月报清单
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/5 9:02
 */
@Data
public class InvInfrastructureMonthDtlDto implements Serializable {

    private static final long serialVersionUID = -4100536406237738824L;

    private String id;
    private String imId;
    @JsonProperty("pId")
    private String pId;
    private BigDecimal imdSurplusFunds;
    private BigDecimal imdCollectCountry;
    private BigDecimal imdCollectSchool;
    private BigDecimal imdCollectGovernment;
    private BigDecimal imdCollectDonate;
    private BigDecimal imdCollectOther;
    private BigDecimal imdPayCountry;
    private BigDecimal imdPaySchool;
    private BigDecimal imdPayGovernment;
    private BigDecimal imdPayDonate;
    private BigDecimal imdPayOther;
    private BigDecimal imdOtherFunds;
    private BigDecimal imdReturnFunds;
    private BigDecimal imdYearBalance;
    private BigDecimal imdPlanFunds;
    private String imdRemark;
    private Integer imdSort;
    private String imdSchoolName;
    private BigDecimal imdCollectTotal;
    private BigDecimal imdPayTotal;
}
