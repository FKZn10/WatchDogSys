package com.jpmsoft.fileUpload.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 登录日志
 * Created By:李文涛 aaron
 * Created Time:2022/6/15 10:23
 */
@Data
public class SysLoginLogDto extends BaseModeRequest implements Serializable {


    private static final long serialVersionUID = 2144518472444057355L;

    private String sllId;
    @JsonProperty("uId")
    private String uId;

    @JsonProperty("sllIp")
    private String sllIp;

    @JsonProperty("sllLoginType")
    private String sllLoginType;

    @JsonProperty("sllLoginTime")
    private Date sllLoginTime;
    @JsonProperty("uName")
    private String uName;
    @JsonProperty("uAccount")
    private String uAccount;
    @JsonProperty("sllAction")
    private String sllAction;

    @JsonProperty("sllLoginTimes")
    private List<String> sllLoginTimes;
}