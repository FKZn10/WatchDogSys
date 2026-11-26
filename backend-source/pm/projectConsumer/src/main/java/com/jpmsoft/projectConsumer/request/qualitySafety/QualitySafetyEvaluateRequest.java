package com.jpmsoft.projectConsumer.request.qualitySafety;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jpmsoft.projectConsumer.request.BaseEvaluateRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class QualitySafetyEvaluateRequest extends BaseEvaluateRequest {
    private String projectId;
    private String companyId;
    private Integer evaluationYear;
    private Integer evaluationMonth;
    private String qsType;
    private Date evaluateStartDate;
    private Date evaluateEndDate;
}
