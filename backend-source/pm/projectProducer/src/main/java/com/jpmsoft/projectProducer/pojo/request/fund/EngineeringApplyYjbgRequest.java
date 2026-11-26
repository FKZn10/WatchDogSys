package com.jpmsoft.projectProducer.pojo.request.fund;

import lombok.Data;

import java.math.BigDecimal;

@Data
/*
    工程用款申请参数
 */
public class EngineeringApplyYjbgRequest {
    //文号名称
    private String whmc;
    //文号余额
    private BigDecimal whye;
    //文号金额
    private BigDecimal whje;
    //本期占用
    private BigDecimal bqzy;
}
