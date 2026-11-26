package com.jpmsoft.custom.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/4/25 9:02
 */
@Data
@TableName("Sys_Form")
public class SysForm {

    @TableId(type = IdType.INPUT)
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
    private String teId;
    private String syFormContentMobile;
}
