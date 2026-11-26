package com.jpmsoft.projectProducer.pojo.vo.contract;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 合同计量计价信息 视图对象
 *
 * @author tianhelin
 * @date 2025/10/21
 */
@Data
public class ConMeasurePriceInfoVo {
    /**
     * 当前应该是第几期
     */
    private Integer currentCmpPeriod;

    /**
     * 是否允许提交
     */
    private Boolean allowSubmit;

    /**
     * 当前累计工程费
     */
    private BigDecimal currentCmpAccumulatedAmount;

    /**
     * 最新一条累计工程费合计
     */
    private BigDecimal latestCmpAccumulatedAmount;
}
