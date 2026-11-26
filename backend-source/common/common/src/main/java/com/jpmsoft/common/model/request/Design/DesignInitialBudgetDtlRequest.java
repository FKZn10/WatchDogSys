package com.jpmsoft.common.model.request.Design;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DesignInitialBudgetDtlRequest {

    private String dibdId;

    //初步设计表id
    private String diId;

    //概算主表id
    private String bdId;

    //概算明细id
    private String bddId;

    //项目及费用名称
    private String bddName;

    //合价（投资）
    private BigDecimal bddTotal;


}
