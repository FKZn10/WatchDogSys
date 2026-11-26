package com.jpmsoft.projectProducer.pojo.vo.contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/4/18 15:38
 */
@Data
public class ConTypeStatisticsVO implements Serializable {

    private String conType;
    private Integer total;
    @JsonProperty("pId")
    private String pId;
}
