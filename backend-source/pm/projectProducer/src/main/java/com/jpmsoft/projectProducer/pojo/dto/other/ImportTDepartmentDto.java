package com.jpmsoft.projectProducer.pojo.dto.other;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 李文涛 aaron
 * @date: 2022/9/29 10:18
 */
@Data
public class ImportTDepartmentDto implements Serializable {

    @JsonProperty("pId")
    private String pId;
    private String toId;
    @JsonProperty("cId")
    private String cId;
}
