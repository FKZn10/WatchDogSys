package com.jpmsoft.projectConsumer.pojo.dto.contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: 李文涛 aaron
 * @date: 2022/11/11 10:21
 */
@Data
public class ConDurationAdjustDto extends BaseFileDto {

    private static final long serialVersionUID = 5712238644470092838L;
    @JsonProperty("pId")
    private String pId;
    private String conId;
    private String daSupervisor;
    private String daType;
    private Float daAdjust;
    private String daRmark;
    private String daReason;
    private String conBId;


    private String conBName;
    @JsonProperty("pName")
    private String pName;
    private String conName;
    private BigDecimal conMoneyTotal;
    private String conPartyAId;
    private String conPartyBId;
    private String conPartyAName;
    private String conPartyBName;
    private String daSupervisorName;


}
