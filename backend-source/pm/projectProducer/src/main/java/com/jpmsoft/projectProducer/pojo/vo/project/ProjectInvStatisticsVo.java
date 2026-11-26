package com.jpmsoft.projectProducer.pojo.vo.project;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 统计工程报告投资金额
 *
 * @author: 李文涛 aaron
 * @date: 2022/7/11 16:32
 */
@Data
public class ProjectInvStatisticsVo implements Serializable {

    private static final long serialVersionUID = 6183473101036241445L;

    private BigDecimal pInvTotal = BigDecimal.ZERO;
    private BigDecimal ipvDoneLastYearTotal = BigDecimal.ZERO;
    private BigDecimal ipvPlannedInvTotal = BigDecimal.ZERO;
    private BigDecimal prCompleteInvTotal = BigDecimal.ZERO;
    private BigDecimal prYearInvTotal = BigDecimal.ZERO;
    private BigDecimal prMonthInvTotal = BigDecimal.ZERO;
}
