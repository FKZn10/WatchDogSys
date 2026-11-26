package com.jpmsoft.projectProducer.pojo.dto.fundPlan;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.ProjectCommonRes;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProgressDeclarationMatchDTO   extends ProjectCommonRes {

    private String id;
    private String conId;

    private String  pddProgressNodeId; //进度节点Id,

    private String  pddPayType;    //支付条件类型

    private String paymentMethod; //支付方式

    private String paymentBasis; //支付基础

    private BigDecimal paymentTo; //支付到%
    private String conProgressType; // 合同进度类型

    private String  paymentConditionValue;  //支付条件值

    private BigDecimal pddStoichiometric;  //计量值

    private BigDecimal paymentRatio;//支付比例

    private BigDecimal pddProgress;  //


    @JsonProperty("cId")
    private String cId;

    @JsonProperty("conName")
    private String conName;

    @JsonProperty("pName")
    private String pName;

    //竣工日期
    private Date completionDate;
    @JsonProperty("cppId")
    private String cppId;

    private String conCode;

    private String conPartyBName;

    private String conPartyAAgent;

    private String faApprovalUnitName;

}
