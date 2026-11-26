package com.jpmsoft.common.model.request.projectOutline;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseFileRequest;
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
public class OutlineProjectManageRequest extends BaseFileRequest {
    private String id;
    @JsonProperty("pId")
    private String pId;
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
    List<OutlineProjectLibRequest> outlineProjectLibList;

    /**
     * 项目管理大纲体系-关联业务功能
     */
    List<OutlineProjectTempRequest> outlineProjectTempList;
    /**
     * 项目管理大纲体系风险指标
     */
    List<OutlineProjectRiskRequest> outlineProjectRiskList;
    /**
     * 项目管理大纲体系节点数据源配置
     */
    List<OutlineProjecDatasourceRequest> outlineProjecDatasourceList;

    private String osmName;
    private List<String> ids;
    @JsonProperty("pIds")
    private List<String> pIds;
    @JsonProperty("pNameList")
    private List<String> pNameList;
    @JsonProperty("pName")
    private String pName;
    private List<String> stateList;
}
