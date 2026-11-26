package com.jpmsoft.userProducer.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询用户项目组织机构已经岗位信息
 *
 * @author: 李文涛 aaron
 * @date: 2022/9/16 10:26
 */
@Data
public class UserPDPDto extends BaseModeRequest implements Serializable {

    @JsonProperty("pName")
    private String pName;
    @JsonProperty("pCode")
    private String pCode;
    private String pdId;
    @JsonProperty("uId")
    private String uId;
    @JsonProperty("uName")
    private String uName;
    @JsonProperty("uAccount")
    private String uAccount;
    private String pdName;
    private String pdCode;
    private String ppName;
    private String ppCode;

    private String roleName;
}
