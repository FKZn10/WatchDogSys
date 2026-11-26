package com.jpmsoft.common.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BasePageEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 政府投资工程项目 进展情况统计表 接收
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProgressStatisticsRequest extends BasePageEntity {
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
     * 形象进度
     */
    private String imageProgress;

    private Integer month;
    private String year;
}
