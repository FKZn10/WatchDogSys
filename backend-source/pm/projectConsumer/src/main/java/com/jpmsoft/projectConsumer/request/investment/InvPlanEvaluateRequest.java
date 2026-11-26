package com.jpmsoft.projectConsumer.request.investment;

import com.jpmsoft.projectConsumer.request.BaseEvaluateRequest;
import lombok.Data;

import java.util.Date;

@Data
public class InvPlanEvaluateRequest extends BaseEvaluateRequest {
    private Integer evaluationMonth;
    private Date  evaluateEndDate;
}
