package com.jpmsoft.projectProducer.pojo.dto.other;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserPositionPDTO_CJ {
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
}
