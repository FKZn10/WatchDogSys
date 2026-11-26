package com.jpmsoft.common.model.response.InvestmentControl;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 月完成情况
 */
@Data
public class InvCompleteMonthCountInvResponse implements Serializable {

    private static final long serialVersionUID = 4398987753048002443L;

    /**
     * 单位Id
     */
    private String competentDepartmentId;

    /**
     * 单位名称
     */
    private String competentDepartmentName;

    /**
     * 投资情况--考核项目数
     */
    private long checkProjectNum;

    /**
     * 投资情况--考核投资额(万元)
     */
    private BigDecimal checkInv = BigDecimal.ZERO;

    /**
     * 投资情况--计划累计投资额(万元)
     */
    private BigDecimal planCumulativeInv = BigDecimal.ZERO;

    /**
     * 投资情况--当月投资额(万元)
     */
    private BigDecimal currentMonthInv = BigDecimal.ZERO;

    /**
     * 投资情况--累计投资额(万元)
     */
    private BigDecimal cumulativeInv = BigDecimal.ZERO;

    /**
     * 投资情况--投资完成率
     */
    private String invCompletePercent = "0.00%";

    /**
     * 投资情况--“投资计划完成率”<100%的项目数
     */
    private long shortProjectNum;

    /**
     * 投资情况--投资贡献度
     */
    private String invContributionPercent = "0.00%";

    /**
     * 投资情况--投资短板率
     */
    private String invShortPercent = "0.00%";

    /**
     * 投资情况--投资计划完成率
     */
    private String invPlanCompletePercent = "0.00%";




}
