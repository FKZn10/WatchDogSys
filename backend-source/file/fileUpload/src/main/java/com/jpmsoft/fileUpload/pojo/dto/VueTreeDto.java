package com.jpmsoft.fileUpload.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class VueTreeDto implements Serializable {

    private static final long serialVersionUID = -8305855897212928148L;
    private String key;
    private String title;
    private String value;
    @JsonProperty("dStatus")
    private String dStatus;
    private List<VueTreeDto> children;
}
