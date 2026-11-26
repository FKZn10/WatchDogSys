package com.jpmsoft.projectProducer.pojo.request.evaluate.qualitySafety;

import com.jpmsoft.projectProducer.pojo.request.evaluate.BaseEvaluateRequest;
import lombok.Data;

import java.util.Date;

@Data
public class QualitySafetyEvaluateRequest extends BaseEvaluateRequest {
    private String projectId;
    private String peCompanyId;
    private Integer evaluationYear;
    private Integer evaluationMonth;
    private String qsType;
    private Date evaluateStartDate;
    private Date evaluateEndDate;
}
