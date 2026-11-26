package com.jpmsoft.projectProducer.pojo.vo.contract;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 已申报的清单的总数，已审核的总数，申报金额统计，审核金额统计
 *
 * @Author: 李文涛 aaron
 * @date: 2023/5/10 9:10
 */
@Data
public class ConMeasureApplyAmtVO implements Serializable {

    /**
     * 已申报的清单的总数
     */
    private int declaredNum;
    /**
     * 已审核的总数
     */
    private int examineNum;
    /**
     * 申报金额统计
     */
    private BigDecimal declaredAmt = BigDecimal.ZERO;
    /**
     * 审核金额统计
     */
    private BigDecimal examineAmt = BigDecimal.ZERO;
    /**
     * 本期审定产值统计
     */
    private BigDecimal applyAmt = BigDecimal.ZERO;

    private String meaId;
    private String meaCode;
    private Date meaStartTime;
    private Date meaEndTime;
    /**
     * 超出合同量的数据
     */
    private int outConNum;
}
