package com.jpmsoft.projectProducer.pojo.request.fund;

import com.jpmsoft.common.model.BasePageEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EngineeringFundApplicationRequest extends BasePageEntity {
    private String id;
    private String pId;
    private String projectId;
    private String projectName;
    private BigDecimal efaApplyAmount;
}
