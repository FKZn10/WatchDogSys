package com.jpmsoft.projectProducer.pojo.dto.oa;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 年度计划变更清单
 *
 * @Author: 李文涛 aaron
 * @date: 2022/12/2 15:01
 */
@Data
public class OaAnnualPlanChangeDtlDto implements Serializable {

    private static final long serialVersionUID = -6157304908019819780L;
    private String id;
    private String apcId;
    @JsonProperty("pId")
    private String pId;
    private String apdType;
    private Integer apdSort;
    private String apdCheckType;

}
