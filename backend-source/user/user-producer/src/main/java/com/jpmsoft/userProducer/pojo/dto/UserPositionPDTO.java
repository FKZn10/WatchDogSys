package com.jpmsoft.userProducer.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/3/18 13:38
 */
@Data
public class UserPositionPDTO {

    private String pupId;
    private String pdId;
    private String pdCode;
    private String ppId;
    private String ppName;
    private String ppCode;
    @JsonProperty("uId")
    private String uId;
    @JsonProperty("uName")
    private String uName;
    @JsonProperty("uMtel")
    private String uMtel;
    @JsonProperty("cName")
    private String cName;

    private String ownerUnit;

    private String ownerUnitName;

    private String pdName;
}
