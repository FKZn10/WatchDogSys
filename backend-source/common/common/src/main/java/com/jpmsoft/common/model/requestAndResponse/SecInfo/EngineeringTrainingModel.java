package com.jpmsoft.common.model.requestAndResponse.SecInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class EngineeringTrainingModel extends BaseFileDto {
    private static final long serialVersionUID = -53533486009549126L;
    private String etType;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date etTime;
    private String etCompanyName;
    private Integer etPersonNum;
    private String etContent;
    private Integer etIsInvolvedCost;
    private String etForm;
    private String etTeacherLaborCost;
    private BigDecimal etTotal;
    private String etFile;
    private String etFileIds;
}
