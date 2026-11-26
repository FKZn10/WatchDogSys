package com.jpmsoft.projectProducer.pojo.vo.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/3/28 14:42
 */
@Data
public class ProjectApproveBudgetRateDTO implements Serializable {

    private static final long serialVersionUID = -154561712192077088L;
    @JsonProperty("pApproveBudget")
    private BigDecimal pApproveBudget;
    private BigDecimal conMoneyTotalSum;
    @JsonProperty("pApproveBudgetRate")
    private BigDecimal pApproveBudgetRate;
}
