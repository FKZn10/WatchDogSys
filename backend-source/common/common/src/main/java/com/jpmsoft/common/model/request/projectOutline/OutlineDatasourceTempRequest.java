package com.jpmsoft.common.model.request.projectOutline;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BasePageEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 业务功能数据源关联
 *
 * @author tianhelin
 * @date 2025/6/26
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OutlineDatasourceTempRequest extends BasePageEntity {
    private String id;
    /**
     * 数据源节点ID
     */
    private String odId;
    @JsonProperty("tCode")
    private String tCode;
    @JsonProperty("tName")
    private String tName;

    // 用于搜索
    private List<String> odIds;
    @JsonProperty("tCodeList")
    private List<String> tCodeList;
}
