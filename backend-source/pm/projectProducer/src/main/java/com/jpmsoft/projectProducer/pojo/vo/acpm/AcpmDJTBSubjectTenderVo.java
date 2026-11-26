package com.jpmsoft.projectProducer.pojo.vo.acpm;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 投标主体
 * Created By:李文涛 aaron
 * Created Time:2022/6/23 15:57
 */
@Data
public class AcpmDJTBSubjectTenderVo implements Serializable {

    private static final long serialVersionUID = -1048881544285932308L;

    private String dasId;
    private String daId;
    @JsonProperty("cId")
    private String cId;
    private String dasSort;

    @JsonProperty("cName")
    private String cName;
    @JsonProperty("cAbbName")
    private String cAbbName;

}
