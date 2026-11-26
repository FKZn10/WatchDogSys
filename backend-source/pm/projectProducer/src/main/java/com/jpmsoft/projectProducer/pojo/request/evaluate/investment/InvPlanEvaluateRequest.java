package com.jpmsoft.projectProducer.pojo.request.evaluate.investment;

import com.jpmsoft.projectProducer.pojo.request.evaluate.BaseEvaluateRequest;
import lombok.Data;

import java.util.Date;

@Data
public class InvPlanEvaluateRequest extends BaseEvaluateRequest {
    private Integer evaluationMonth;
    private Date evaluateEndDate;
}
