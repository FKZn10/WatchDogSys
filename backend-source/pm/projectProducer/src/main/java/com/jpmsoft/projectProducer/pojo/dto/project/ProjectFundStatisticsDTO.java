package com.jpmsoft.projectProducer.pojo.dto.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * 项目资金统计列表
 *
 * @Author: 李文涛 aaron
 * @date: 2023/5/24 11:09
 */
@Data
public class ProjectFundStatisticsDTO extends BaseModeRequest implements Serializable {
    private static final long serialVersionUID = -4985511377390773949L;

    private String year;
    @JsonProperty("pId")
    private String pId;
    @JsonProperty("pParentId")
    private String pParentId;
    @JsonProperty("pName")
    private String pName;
    @JsonProperty("pCode")
    private String pCode;
    @JsonProperty("pUseUnit")
    private String pUseUnit;
    @JsonProperty("pUseUnitName")
    private String pUseUnitName;
    @JsonProperty("pConstructionUnit")
    private String pConstructionUnit;
    @JsonProperty("pConstructionUnitName")
    private String pConstructionUnitName;
    @JsonProperty("pProjectStage")
    private String pProjectStage;
    @JsonProperty("pProjectStatus")
    private String pProjectStatus;
    /**
     * 1 父项目
     * 2 子项目
     * 空 全部
     */
    private Integer parentProject;
}
