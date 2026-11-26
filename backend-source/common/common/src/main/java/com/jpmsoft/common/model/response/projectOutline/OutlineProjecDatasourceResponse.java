package com.jpmsoft.common.model.response.projectOutline;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 项目管理大纲体系节点数据源配置
 *
 * @author tianhelin
 * @date 2025/6/13
 */
@Data
public class OutlineProjecDatasourceResponse {
    private String id;
    /**
     * 项目管理大纲体系目录节点id
     */
    private String oplId;
    private String odId;
    private String odName;
    private String odType;
    private String odSource;
    private Boolean odStatus;
    private String odRemark;
    private String odAddress;
    private String odMethod;
    private String odBody;
    private String odDataType;
    private Date createTime;
    private String createUid;
    private String createUname;
    private String odAlias;
    @JsonProperty("tCodeList")
    private List<String> tCodeList;
}
