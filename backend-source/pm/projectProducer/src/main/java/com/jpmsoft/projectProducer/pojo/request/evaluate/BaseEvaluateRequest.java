package com.jpmsoft.projectProducer.pojo.request.evaluate;

import lombok.Data;

@Data
public class BaseEvaluateRequest {
    private String projectId;
    private String contractId;
    private String peCompanyId;
    private Integer evaluationYear;
    private Integer evaluationMonth;
    private Integer evaluationQuarter;
}
