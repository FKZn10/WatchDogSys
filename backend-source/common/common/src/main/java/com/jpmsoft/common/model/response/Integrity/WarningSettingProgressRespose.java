package com.jpmsoft.common.model.response.Integrity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * 预警提醒设置
 */
@Data
public class WarningSettingProgressRespose {
    private String id;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    @JsonProperty("tCode")
    private String tCode;
    private String alertSettings;
    private String modelCode;
}
