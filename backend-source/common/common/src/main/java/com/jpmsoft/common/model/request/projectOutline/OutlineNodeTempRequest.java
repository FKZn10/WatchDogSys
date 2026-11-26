package com.jpmsoft.common.model.request.projectOutline;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 体系模板-关联业务功能
 *
 * @author tianhelin
 * @date 2025/6/16
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OutlineNodeTempRequest extends BaseModeRequest {
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
