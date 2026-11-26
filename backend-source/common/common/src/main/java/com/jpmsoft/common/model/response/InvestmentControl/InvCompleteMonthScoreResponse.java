package com.jpmsoft.common.model.response.InvestmentControl;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 月完成情况
 */
@Data
public class InvCompleteMonthScoreResponse implements Serializable {

    private static final long serialVersionUID = 4398987753048002442L;

    /**
     * 单位Id
     */
    private String competentDepartmentId;

    /**
     * 单位名称
     */
    private String competentDepartmentName;

    /**
     * 开工综合得分
     */
    private BigDecimal startComprehensiveScore = BigDecimal.ZERO;

    /**
     * 投资综合得分
     */
    private BigDecimal invComprehensiveScore = BigDecimal.ZERO;

    /**
     * 完工综合得分
     */
    private BigDecimal endComprehensiveScore = BigDecimal.ZERO;

    /**
     * 总分
     */
    private BigDecimal totalScore = BigDecimal.ZERO;

    /**
     * 排名
     */
    private int rank;

}
