package com.jpmsoft.projectConsumer.pojo.dto.riskWarn;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/3/29 13:43
 */
@Data
public class RiskTemplateRelationDTO extends BaseModeRequest implements Serializable {

    private static final long serialVersionUID = -1835406411791931202L;
    private String id;
    private String rpmId;
    @JsonProperty("tId")
    private String tId;
    private String rtrFieldName;
    private String rtrFieldCode;
    private String createUid;
    private String createUname;
    private Date createTime;

    @JsonProperty("tName")
    private String tName;
    @JsonProperty("tCode")
    private String tCode;
}
