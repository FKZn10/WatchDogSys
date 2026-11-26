package com.jpmsoft.projectProducer.pojo.vo.contract;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: 李文涛 aaron
 * @date: 2022/12/27 9:57
 */
@Data
public class ConEcStandingBookVo implements Serializable {
    private BigDecimal ecApplyMoneySum;
    private BigDecimal ecTotalMoneyCSum;
    private Integer ecDurationSum;
    private Object data;
}
