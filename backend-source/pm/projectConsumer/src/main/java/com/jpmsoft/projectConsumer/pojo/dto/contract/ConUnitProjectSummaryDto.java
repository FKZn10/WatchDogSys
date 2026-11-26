package com.jpmsoft.projectConsumer.pojo.dto.contract;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 单位工程汇总表
 *
 * @author: 李文涛 aaron
 * @date: 2022/10/25 13:56
 */
@Data
public class ConUnitProjectSummaryDto implements Serializable {

    private static final long serialVersionUID = -2605914836125736210L;
    private String id;
    private String boqdId;
    private String meaId;
    private BigDecimal upsSubItemAmt;
    private BigDecimal upsUnitPriceAmt;
    private BigDecimal upsTotalPriceAmt;
    private BigDecimal upsStepAmt;
    private BigDecimal upsOtherItemAmt;
    private BigDecimal upsTax;
    private BigDecimal upsFee;
    private BigDecimal upsOtherAmount;
    private BigDecimal upsCompleted;
    private Float upsTaxesRatio;
    private Float upsFeesRatio;
    private Float upsRatio;
    private Float upsOtherRatio;
    private String upsFormula;
    private String upsRemark;
    private Date createTime;
    private String createUid;
    private String createUname;
}
