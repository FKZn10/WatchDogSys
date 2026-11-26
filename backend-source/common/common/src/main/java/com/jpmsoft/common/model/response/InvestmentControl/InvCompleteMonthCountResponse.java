package com.jpmsoft.common.model.response.InvestmentControl;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jpmsoft.common.model.ProjectCommonRes;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 月完成情况
 */
@Data
public class InvCompleteMonthCountResponse implements Serializable {

    private static final long serialVersionUID = 4398987753048002436L;

    /**
     * 开工情况--考核开工数
     */
    private long checkStartNum;

    /**
     * 开工情况--计划累计开工数
     */
    private long planCumulativeStartNum;

    /**
     * 开工情况--当月开工数
     */
    private long currentMonthStartNum;

    /**
     * 开工情况--累计开工数
     */
    private long cumulativeStartNum;

    /**
     *开工情况--开工率
     */
    private String startPercent = "0.00%";

    /**
     *开工情况--开工计划完成率
     */
    private String startPlanCompletePercent = "0.00%";

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
     * 投资情况--投资短板率
     */
    private String invShortPercent = "0.00%";

    /**
     * 投资情况--投资计划完成率
     */
    private String invPlanCompletePercent = "0.00%";

    /**
     * 完工情况--考核完工数
     */
    private long checkEndNum;

    /**
     * 完工情况--计划累计完工数
     */
    private long planCumulativeEndNum;

    /**
     * 完工情况--当月完工数
     */
    private long currentMonthEndNum;

    /**
     * 完工情况--累计完工数
     */
    private long cumulativeEndNum;

    /**
     *完工情况--完工率
     */
    private String endPercent = "0.00%";

    /**
     *完工情况--完工计划完成率
     */
    private String endPlanCompletePercent = "0.00%";


}
