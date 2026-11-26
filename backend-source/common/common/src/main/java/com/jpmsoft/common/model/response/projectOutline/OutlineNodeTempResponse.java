package com.jpmsoft.common.model.response.projectOutline;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * 体系模板-关联业务功能
 *
 * @author tianhelin
 * @date 2025/6/17
 */
@Data
public class OutlineNodeTempResponse {
    private String id;
    /**
     * 体系模板-目录节点ID
     */
    private String oslId;
    @JsonProperty("tCode")
    private String tCode;
    @JsonProperty("tName")
    private String tName;
    private Integer odtSort;
    private Date createTime;
    private String createUid;
    private String createUname;
    private String preType;
}
