package com.jpmsoft.common.model.requestAndResponse.SecInfo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author jjz
 * 城建 - 开评标情况报告子表
 */
@Data
public class SecEvalReportDtlModel {

    private String erdId;

    private String erId;

    private String cId;

    private Double erdPriceAndRate;

    private String erdSort;

    private BigDecimal erdPrice;

    private BigDecimal erdRate;
}
