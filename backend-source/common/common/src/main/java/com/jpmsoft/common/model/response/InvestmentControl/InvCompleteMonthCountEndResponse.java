package com.jpmsoft.common.model.response.InvestmentControl;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 月完成情况
 */
@Data
public class InvCompleteMonthCountEndResponse implements Serializable {

    private static final long serialVersionUID = 4398987753048002446L;

    /**
     * 单位Id
     */
    private String competentDepartmentId;

    /**
     * 单位名称
     */
    private String competentDepartmentName;

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
