package com.jpmsoft.projectProducer.pojo.dto.zjt;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BasePageEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ZjtProjectExportDTO extends BasePageEntity {

    @JsonProperty("pCreateTime")
    private List<String> pCreateTime;
    @JsonProperty("pCreateUserName")
    private String pCreateUserName;
    @JsonProperty("pName")
    private String pName;
    @JsonProperty("pProfessionalType")
    private String pProfessionalType;
    @JsonProperty("pType")
    private String pType;
    @JsonProperty("state")
    private String state;
}
