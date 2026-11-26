package com.jpmsoft.projectProducer.pojo.vo.fundPlan;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: 李文涛 aaron
 * @date: 2022/10/18 16:51
 */
@Data
public class StatisticsFundCollectionBatchNoAndAmtVo implements Serializable {
    private int batchNo;
    private BigDecimal receivedAmt;
}
