package com.jpmsoft.jpbpm.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.jpbpm.constants.BpmMsg;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 代办统计
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/26 11:04
 */
@Data
public class AgencyStatisticsDto implements Serializable {

    @JsonProperty("uId")
    private String uId;
    @JsonProperty("tCodes")
    @NotEmpty(message = BpmMsg.tCodeIsNukk)
    private List<String> tCodes;
}
