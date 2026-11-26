package com.jpmsoft.common.model.request.Contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 多项目监理合同项目信息
 *
 * @author tianhelin
 * @date 2025/9/8
 */
@Data
public class ConContractMultiProjectRequest {
    private String id;
    private String ccmId;
    @JsonProperty("pId")
    private String pId;
    @JsonProperty("pName")
    private String pName;
    private BigDecimal conAmount;
    private String location;
    private Integer duration;
    private String conId;
}
