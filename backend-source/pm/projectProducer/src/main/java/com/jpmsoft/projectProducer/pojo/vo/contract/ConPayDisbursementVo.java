package com.jpmsoft.projectProducer.pojo.vo.contract;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
public class ConPayDisbursementVo {

    private String payId;  //id

    private String panyId;  //单位id
    @JsonProperty("pId")
    private String pId;

    @JsonProperty("dId")
    private String dId;

    @JsonProperty("conId")
    private String conId;

    @JsonProperty("cName")
    private String cName;  //单位名(合同乙方)
    @JsonProperty("cId")
    private String cId;


    @JsonProperty("pName")
    private String pName;   //项目名
    private String conName;  //合同名
    private String conType;  //合同类型
    private BigDecimal conMoneyTotal;  //合同总额
    @JsonProperty("pFinal")
    private BigDecimal pFinal;   //决算额

    private BigDecimal asAuditedAmount; //决算审计额
    private BigDecimal payApply;  //本次支付金额
    private BigDecimal payTotal;  //积累拨付(含本次)

    @JsonProperty("dName")
    private String dName;  //主管部门
    private String conPartyAAgent;  //甲方联系人

    private LocalDateTime PayStateTime;

    private LocalDateTime PayCreateTime;

    //本次拨付总额
    private BigDecimal bursementAmtTotal;


    //本次拨付
    private BigDecimal thisAllocationAmt = BigDecimal.ZERO;

    private BigDecimal actualPaymentAmt;   //账面实际已付

    private BigDecimal historicalAmount; //历史未支付

    private BigDecimal actualPaymentRatio;//账面实际支付到比例

    private BigDecimal thisProportionPaymentRatio;   //本次应支付到比例

    private BigDecimal paExamineApprove; //初步审计价

}
