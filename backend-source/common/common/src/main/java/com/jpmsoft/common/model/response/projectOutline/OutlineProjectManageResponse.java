package com.jpmsoft.common.model.response.projectOutline;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseFileResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * 项目管理大纲体系管理
 *
 * @author tianhelin
 * @date 2025/6/13
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OutlineProjectManageResponse extends BaseFileResponse {
    private String id;
    @JsonProperty("pId")
    private String pId;
    @JsonProperty("pName")
    private String pName;
    /**
     * 体系模板管理id
     */
    private String osmId;
    private Date createTime;
    private String createUid;
    private String createUname;
    private Integer state;
    private Date stateTime;
    private Boolean opmStatus;
    private Date updateTime;
    private String updateUid;
    private String updateUname;
    private String sortRules;
    private String opmCode;
    /**
     * 落款单位
     */
    private String opmUnit;
    /**
     * 项目管理大纲体系目录节点
     */
    List<OutlineProjectLibResponse> outlineProjectLibList;

    /**
     * 项目管理大纲体系目录节点平铺
     */
    List<OutlineProjectLibResponse> outlineProjectLibListFlow;
    /**
     * 项目管理大纲体系-关联业务功能
     */
    List<OutlineProjectTempResponse> outlineProjectTempList;
    /**
     * 项目管理大纲体系风险指标
     */
    List<OutlineProjectRiskResponse> outlineProjectRiskList;
    /**
     * 项目管理大纲体系节点数据源配置
     */
    List<OutlineProjecDatasourceResponse> outlineProjecDatasourceList;

    private String osmName;
    private String osmProjectType;
    private String osmDepartmentId;

    private Integer riskWarning;
    private Integer riskFinished;
    private Integer riskRate;
}
