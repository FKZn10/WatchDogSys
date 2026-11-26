package com.jpmsoft.common.model.response.InvestmentControl;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 月完成情况
 */
@Data
public class InvCompleteMonthCountStartResponse implements Serializable {

    private static final long serialVersionUID = 4398987753048002444L;

    /**
     * 单位Id
     */
    private String competentDepartmentId;

    /**
     * 单位名称
     */
    private String competentDepartmentName;

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
     * 开工情况--开工贡献度
     */
    private String startContributionPercent = "0.00%";

    /**
     *开工情况--开工计划完成率
     */
    private String startPlanCompletePercent = "0.00%";




}
