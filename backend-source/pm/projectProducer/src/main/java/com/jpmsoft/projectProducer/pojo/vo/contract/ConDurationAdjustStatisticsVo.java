package com.jpmsoft.projectProducer.pojo.vo.contract;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/11/11 10:45
 */
@Data
public class ConDurationAdjustStatisticsVo implements Serializable {
    private BigDecimal conMoneyTotalSum = BigDecimal.ZERO;
    private List<ConDurationAdjustVo> data;
}
