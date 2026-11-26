package com.jpmsoft.userProducer.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/4/25 9:06
 */
@Data
public class SysFormDTO extends BaseModeRequest implements Serializable {

    private static final long serialVersionUID = -8039372820483189858L;

    private String syId;
    private String syName;
    private String syType;
    private String syFormRemark;
    private String syState;
    private String syVersion;
    private Date syCreateTime;
    private String syCreateId;
    private String syCreateName;
    private Date syUpdateTime;
    private String syUpdateId;
    private String syUpdateName;
    private String syDataSourceType;
    private String syCreateRequestType;
    private String syCreateRequestUrl;
    private String syCreateRequestParameter;
    private String syDetailsRequestType;
    private String syDetailsRequestUrl;
    private String syDetailsRequestParameter;
    private String syDataContent;
    private String syFormContent;
    private String syFormEvents;
    @JsonProperty("tCode")
    private String tCode;
    @JsonProperty("tId")
    private String tId;
    private String syFormContentMobile;
}
