package com.jpmsoft.projectProducer.pojo.dto.fundPlan;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProgressContractPlanDTO {
    private String pId;

    private String pName;

    private String conId;

    private String conName;

    private BigDecimal conMoneyTotal;

    private String conCode;

    private String pddPayType;

    private String pddContant;

    private Date pddContantDate;

    private BigDecimal pddMoneySum;



}
