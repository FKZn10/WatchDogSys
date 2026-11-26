package com.jpmsoft.projectConsumer.pojo.dto.acpm;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 投标主体
 * Created By:李文涛 aaron
 * Created Time:2022/6/23 15:57
 */
@Data
public class AcpmDJTBSubjectTenderDto implements Serializable {

    private static final long serialVersionUID = -5257780286600541865L;

    private String dasId;
    private String daId;
    @JsonProperty("cId")
    private String cId;
    private String dasSort;
}
