package com.jpmsoft.userProducer.pojo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.userProducer.pojo.entity.SysUser;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 李文涛 aaron
 * @date: 2022/9/16 10:22
 */
@Data
public class UserPDPVo extends SysUser implements Serializable {

    private static final long serialVersionUID = 4169404291768753385L;

    @JsonProperty("pName")
    private String pName;
    @JsonProperty("pCode")
    private String pCode;
    @JsonProperty("pProjectStage")
    private String pProjectStage;

    private String pupId;
    private String pdId;
    private String pdName;
    private String pdCode;
    private String ppName;
    private String ppCode;
    private String ppId;

}
