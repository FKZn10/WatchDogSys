package com.jpmsoft.userProducer.pojo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 登录日志
 * Created By:李文涛 aaron
 * Created Time:2022/6/15 10:23
 */
@Data
public class SysLoginLogVo implements Serializable {


    private static final long serialVersionUID = 7765233683269017727L;

    private String sllId;
    @JsonProperty("uId")
    private String uId;
    private String sllIp;
    private String sllLoginType;
    private Date sllLoginTime;
    @JsonProperty("uName")
    private String uName;
    @JsonProperty("uAccount")
    private String uAccount;
    
}
