package com.jpmsoft.projectProducer.pojo.dto.qualitySafety;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/10/25 11:03
 */
@Data
public class ProSafetyLedgerDTO implements Serializable {


    /**
     * 报告份数
     */
    private Integer qsPlanTaskCount;
    /**
     * 问题数量
     */
    private Integer qsRecordCount = 0;
    /**
     * 整改数量
     */
    private Integer qsFinishCount = 0;
    /**
     * 闭合率
     */
    private BigDecimal qsFinishRate;

    private List<ProSafetyLedgerDtlDTO> data;
}
