package com.jpmsoft.projectConsumer.pojo.dto.oa;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 年度计划清单
 *
 * @Author: 李文涛 aaron
 * @date: 2022/12/2 15:01
 */
@Data
public class OaAnnualPlanDtlDto implements Serializable {

    private static final long serialVersionUID = 5842831396595528429L;
    private String id;
    private String apId;
    @JsonProperty("pId")
    private String pId;
    private String apdType;
    private Integer apdSort;
    private String apdCheckType;
}
