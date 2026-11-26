package com.jpmsoft.common.model.response.projectOutline;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * 项目管理大纲体系-关联业务功能
 *
 * @author tianhelin
 * @date 2025/6/17
 */
@Data
public class OutlineProjectTempResponse {
    private String id;
    /**
     * 项目管理大纲体系目录节点id
     */
    private String oplId;
    @JsonProperty("tCode")
    private String tCode;
    @JsonProperty("tName")
    private String tName;
    private Integer opdSort;
    private Date createTime;
    private String createUid;
    private String createUname;
    private String preType;
    private String oplName;
    private String oplCatalogName;
}
