package com.jpmsoft.common.model.response.projectOutline;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 业务功能数据源关联
 *
 * @author tianhelin
 * @date 2025/6/26
 */
@Data
public class OutlineDatasourceTempResponse {
    private String id;
    /**
     * 数据源节点ID
     */
    private String odId;
    @JsonProperty("tCode")
    private String tCode;
    @JsonProperty("tName")
    private String tName;
}
