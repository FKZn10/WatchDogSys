package com.jpmsoft.projectProducer.pojo.vo.qualitySafety;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class QsNodePerson implements Serializable {
     @JsonProperty("uId")
     private String uId;
     @JsonProperty("uName")
     private String uName;
}
