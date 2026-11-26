package com.jpmsoft.projectProducer.pojo.vo.investment;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: 李文涛 aaron
 * @date: 2022/10/21 10:34
 */
@Data
public class InvSettlementSecondStatisticsVo implements Serializable {

    private static final long serialVersionUID = -1547441932223344911L;
    private BigDecimal ssApprovedAmtSum;
    private BigDecimal saApprovedAmtSum;

}
