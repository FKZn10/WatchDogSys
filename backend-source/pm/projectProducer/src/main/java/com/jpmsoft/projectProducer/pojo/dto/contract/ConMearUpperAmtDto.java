package com.jpmsoft.projectProducer.pojo.dto.contract;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 上一次该清单计量
 *
 * @author: 李文涛 aaron
 * @date: 2022/7/26 10:31
 */
@Data
public class ConMearUpperAmtDto implements Serializable {

    private static final long serialVersionUID = -2341664073844944914L;

    private Float mearUpperAmount = 0F;
    private BigDecimal mearUpperTotal = BigDecimal.ZERO;
    private Float mearCumulativeAmount = 0F;
    private BigDecimal mearCumulativeTotal = BigDecimal.ZERO;
}
