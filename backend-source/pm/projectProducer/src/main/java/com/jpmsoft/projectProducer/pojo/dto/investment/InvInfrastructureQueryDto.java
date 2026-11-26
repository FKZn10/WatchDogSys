package com.jpmsoft.projectProducer.pojo.dto.investment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/5 9:54
 */
@Data
public class InvInfrastructureQueryDto extends BaseModeRequest implements Serializable {

    private static final long serialVersionUID = -8582219681629673368L;
    private String imYear;
    private String imMon;
    private Integer state;
    private String createUname;
    @JsonProperty("pName")
    private String pName;
    @JsonProperty("pId")
    private String pId;
    private String imdSchoolName;
}
