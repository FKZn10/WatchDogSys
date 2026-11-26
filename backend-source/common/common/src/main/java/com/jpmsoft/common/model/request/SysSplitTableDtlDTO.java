package com.jpmsoft.common.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 流程分表配置详情
 *
 * @Author: 李文涛 aaron
 * @date: 2025/2/7 10:02
 */
@Data
public class SysSplitTableDtlDTO extends BaseModeRequest implements Serializable {


    private static final long serialVersionUID = -7997892927256553569L;
    private String id;
    private String stId;
    @JsonProperty("tName")
    private String tName;
    @JsonProperty("tCode")
    private String tCode;
    private String tableName;
    private String tableCode;
    private Integer stdSort;
    private Date createTime;
    private String createUid;
    private String createUname;

    private String stCode;
}
