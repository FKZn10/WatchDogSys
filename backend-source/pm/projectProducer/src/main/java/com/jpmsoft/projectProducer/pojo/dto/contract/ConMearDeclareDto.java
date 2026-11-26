package com.jpmsoft.projectProducer.pojo.dto.contract;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 合同计量申报情况
 *
 * @author: 李文涛 aaron
 * @date: 2022/7/26 14:07
 */
@Data
public class ConMearDeclareDto implements Serializable {

    private static final long serialVersionUID = -8382399719180686889L;

    private String mearBoqdCode;

    private Float mearCumulativeAmount;

    private BigDecimal mearCumulativeTotal;

}
