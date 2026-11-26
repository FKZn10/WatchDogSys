package com.jpmsoft.common.model.response.Contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 多项目合同支付项目信息
 *
 * @author tianhelin
 * @date 2025/9/11
 */
@Data
public class ConPayMultiProjectResponse {
    private String id;
    private String cpmId;
    @JsonProperty("pId")
    private String pId;
    @JsonProperty("pName")
    private String pName;
    private BigDecimal conAmount;
    private String location;
    private Integer duration;
    private String conId;
    private String conName;
    private String conCode;
    private BigDecimal paidAmount;
    private BigDecimal paidRatio;
    private BigDecimal currentPayAmount;
    private BigDecimal changeVisaPaidAmount;
    private BigDecimal visaTempFundRatio;
    private BigDecimal safetyFeePaidAmount;
    private BigDecimal workerWagePaidAmount;
    private BigDecimal workerWageAmount;
    private BigDecimal projectAmount;
    private BigDecimal projectPaidAmount;
    private String payMultiFile;
    private String payId;
    private String payCode;
}
