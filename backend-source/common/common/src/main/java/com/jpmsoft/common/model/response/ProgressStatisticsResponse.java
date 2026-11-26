package com.jpmsoft.common.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 政府投资工程项目 进展情况统计表 返回
 */
@Data
public class ProgressStatisticsResponse {
    @JsonProperty("pId")
    private String pId;
    @JsonProperty("pName")
    private String pName;
    /**
     * 所属市县
     */
    private String affiliatedArea;
    /**
     * 项目分类
     */
    @JsonProperty("pType")
    private String pType;
    /**
     * 建设性质
     */
    @JsonProperty("pBuildModel")
    private String pBuildModel;
    /**
     * 建设周期
     */
    @JsonProperty("pTotalDuration")
    private String pTotalDuration;
    /**
     * 建设内容
     */
    @JsonProperty("pScaleDescribe")
    private String pScaleDescribe;
    /**
     * 实际开工时间
     */
    @JsonProperty("pActualStartTime")
    private String pActualStartTime;
    /**
     * 计划完成时间
     */
    @JsonProperty("pPlanCompleteDate")
    private String pPlanCompleteDate;

    @JsonProperty("pCompetentDepartment")
    private String pCompetentDepartment;
    /**
     * 至上季度止累计计划投资
     */
    private BigDecimal plannedForLastQuarter;
    /**
     * 本季度计划投资
     */
    private BigDecimal plannedForThisQuarter;
    /**
     * 至上季度止累计完成投资
     */
    private BigDecimal completedLastQuarter;
    /**
     * 本季度完成投资
     */
    private BigDecimal completedThisQuarter;
    /**
     * 至上季度止累计完成支付
     */
    private BigDecimal payLastQuarter;
    /**
     * 本季度完成支付
     */
    private BigDecimal payThisQuarter;
    /**
     * 形象进度
     */
    private String imageProgress;
    /**
     * 当项目责任目标一季度
     */
    private BigDecimal firstQuarterTarget;
    /**
     * 当项目责任目标二季度
     */
    private BigDecimal secondQuarterTarget;
    /**
     * 当项目责任目标三季度
     */
    private BigDecimal thirdQuarterTarget;
    /**
     * 当项目责任目标四季度
     */
    private BigDecimal fourthQuarterTarget;
}
