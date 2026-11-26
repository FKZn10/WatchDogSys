package com.jpmsoft.fileUpload.pojo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/5 10:58
 */
@Data
public class InvInfrastructureMonthDtlVo implements Serializable {

    private static final long serialVersionUID = -6398321798717601082L;
    private String id;
    private String imdSchoolName;
    @JsonProperty("pId")
    private String pId;
    @JsonProperty("pName")
    private String pName;
    @JsonProperty("pCode")
    private String pCode;
    private BigDecimal imdSurplusFunds;
    private BigDecimal imdCollectCountry;
    private BigDecimal imdCollectSchool;
    private BigDecimal imdCollectGovernment;
    private BigDecimal imdCollectDonate;
    private BigDecimal imdCollectOther;
    private BigDecimal imdOtherFunds;
    private BigDecimal imdPayCountry;
    private BigDecimal imdPaySchool;
    private BigDecimal imdPayGovernment;
    private BigDecimal imdPayDonate;
    private BigDecimal imdPayOther;
    private BigDecimal imdReturnFunds;
    private BigDecimal imdYearBalance;
    private BigDecimal imdPlanFunds;
    private BigDecimal imdCollectTotal;
    private BigDecimal imdPayTotal;
    private String imdRemark;
    private Integer imdSort;
}
