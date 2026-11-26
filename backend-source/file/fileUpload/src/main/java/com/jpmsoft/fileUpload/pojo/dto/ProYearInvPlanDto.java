package com.jpmsoft.fileUpload.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 项目年度资金计划累计
 */
@Data
public class ProYearInvPlanDto implements Serializable {

    private static final long serialVersionUID = -4927111697209086935L;

    private String ippYear;
    private String pCode;
    private BigDecimal funds;

}
