package com.jpmsoft.common.model.request.Supervisor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

@Data
public class PunishRequest extends BaseFileRequest {
    @JsonProperty("pName")
    private String pName;
    @JsonProperty("conName")
    private String conName;
    @JsonProperty("cName")
    private String cName;
    @JsonProperty("punishType")
    private String punishType;
}
