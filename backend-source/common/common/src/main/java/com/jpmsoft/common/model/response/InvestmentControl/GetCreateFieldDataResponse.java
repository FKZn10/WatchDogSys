package com.jpmsoft.common.model.response.InvestmentControl;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2024/4/1 15:38
 * @Description:
 * @Version: 1.0
 */
@Data
public class GetCreateFieldDataResponse implements Serializable {

    private static final long serialVersionUID = 4057506543163734799L;

    /**
     * 当月是否开工: 0-否；1-是
     */
    private String currentMonthStart;

    /**
     * currentMonthStart 字段是否可编辑：true-是；false-否
     */
    private boolean currentMonthStartEnable;

    /**
     * 累计是否开工: 0-否；1-是
     */
    private String cumulativeStart;

    /**
     * cumulativeStart 字段是否可编辑：true-是；false-否
     */
    private boolean cumulativeStartEnable;

    /**
     * 计划开工年月
     */
    @JsonFormat(pattern = "yyyy-MM", timezone = "GMT+8")
    private Date planStartTime;

    /**
     * 实际开工年月
     */
    @JsonFormat(pattern = "yyyy-MM", timezone = "GMT+8")
    private Date actualStartTime;

    /**
     * planStartTime 字段是否可编辑：true-是；false-否
     */
    private boolean planStartTimeEnable;

    /**
     * 开工计划完成情况：01-滞后未完成；02-滞后完成；03-正常完成；04-提前完成
     */
    private String planCompleteResult;

    /**
     * planCompleteResult 字段是否可编辑：true-是；false-否
     */
    private boolean planCompleteResultEnable;

    /**
     * 当月是否完工: 0-否；1-是
     */
    private String currentMonthComplete;

    /**
     * currentMonthComplete 字段是否可编辑：true-是；false-否
     */
    private boolean currentMonthCompleteEnable;

    /**
     * 累计是否开完工: 0-否；1-是
     */
    private String cumulativeComplete;

    /**
     * cumulativeComplete 字段是否可编辑：true-是；false-否
     */
    private boolean cumulativeCompleteEnable;

    /**
     * 计划完工年月
     */
    @JsonFormat(pattern = "yyyy-MM", timezone = "GMT+8")
    private Date planEndTime;

    /**
     * 实际完工年月
     */
    @JsonFormat(pattern = "yyyy-MM", timezone = "GMT+8")
    private Date actualEndTime;

    /**
     * planEndTime 字段是否可编辑：true-是；false-否
     */
    private boolean planEndTimeEnable;

    /**
     * 完工计划完成情况：01-滞后未完成；02-滞后完成；03-正常完成；04-提前完成
     */
    private String completeResult;

    /**
     * completeResult 字段是否可编辑：true-是；false-否
     */
    private boolean completeResultEnable;

    /**
     * 上月的年度累计完成投资额(万元)
     */
    private BigDecimal icmAnnualInvestment;

    /**
     * 年度计划投资额(万元)
     */
    private BigDecimal ipvPlannedInvestment;

    /**
     * X月年度累计计划投资(万元)
     */
    private BigDecimal monthCumulativeInv;


    /**
     * 年度资金计划：当年是否新开工
     */
    private Boolean ipvNewly;

    /**
     * 年度资金计划：当年是否完工
     */
    private Boolean ipvCompleted;
}
