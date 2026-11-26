package com.jpmsoft.projectProducer.pojo.dto.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 资金计划表
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/16 11:55
 */
@Data
public class ProBGDTO extends BaseFileDto implements Serializable {

    private static final long serialVersionUID = 3491200971457490429L;
    private String id;

    @JsonProperty("code")
    private String code;
    @JsonProperty("type")
    private String type;
    @JsonProperty("pId")
    private String pId;
    @JsonProperty("pName")
    private String pName;
    @JsonProperty("conId")
    private String conId;
    @JsonProperty("conName")
    private String conName;
    @JsonProperty("BL")
    private BigDecimal BL;
    @JsonProperty("Money")
    private BigDecimal Money;
    @JsonProperty("TrueMoney")
    private BigDecimal TrueMoney;
    @JsonProperty("TrueBL")
    private BigDecimal TrueBL;

    private BigDecimal finalConfirmMoney;

}