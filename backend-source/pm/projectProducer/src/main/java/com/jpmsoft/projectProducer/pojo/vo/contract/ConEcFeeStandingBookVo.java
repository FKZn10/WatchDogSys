package com.jpmsoft.projectProducer.pojo.vo.contract;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: 李文涛 aaron
 * @date: 2022/12/28 8:59
 */
@Data
public class ConEcFeeStandingBookVo implements Serializable {


    private BigDecimal ecApplyMoneySum;
    private BigDecimal ecfApplyMoneySum;
    private Integer ecfDurationSum;
    private Object data;
}
