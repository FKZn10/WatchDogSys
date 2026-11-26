package com.jpmsoft.projectProducer.pojo.dto.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ConfigRelationDTO {
    private String id;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private String crName;
    @JsonProperty("cId")
    private String cId;
    private String crCode1;
    private String crCode2;
    private String crCode3;
    private String crCode4;
    @JsonProperty("dTId")
    private String dTId;
    @JsonProperty("dId")
    private String dId;
    @JsonProperty("crDCode1")
    private String crDCode1;
    @JsonProperty("crDCode2")
    private String crDCode2;
}
