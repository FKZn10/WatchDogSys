package com.jpmsoft.userProducer.pojo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.userProducer.pojo.entity.SysRole;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysRoleSourceVo extends SysRole {
    @JsonProperty("pId")
    private String pId;
    @JsonProperty("pName")
    private String pName;
    private String sourceType;
    @JsonProperty("ppName")
    private String ppName;
    @JsonProperty("pdName")
    private String pdName;
}
