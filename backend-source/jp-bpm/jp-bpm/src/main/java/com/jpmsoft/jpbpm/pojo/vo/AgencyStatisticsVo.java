package com.jpmsoft.jpbpm.pojo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 代办统计
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/26 11:14
 */
@Data
public class AgencyStatisticsVo implements Serializable {

    @JsonProperty("tCode")
    private String tCode;
    private Integer total;
}
