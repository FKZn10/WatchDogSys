package com.jpmsoft.projectProducer.pojo.vo.contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/3/8 9:22
 */
@Data
public class ContractPartialVO implements Serializable {

    private static final long serialVersionUID = 4442658513213695762L;
    @JsonProperty("pName")
    private String pName;
    @JsonProperty("pCode")
    private String pCode;
    private String conProjectId;
    private Integer conApprovalState;
    private String conName;
    private String conCode;
    private String conType;
    private String conId;
}
