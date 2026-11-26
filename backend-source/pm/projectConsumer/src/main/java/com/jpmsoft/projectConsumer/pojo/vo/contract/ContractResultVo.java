package com.jpmsoft.projectConsumer.pojo.vo.contract;

import com.jpmsoft.projectConsumer.pojo.entity.contract.ContractPaymentPlanTemplateDl;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ContractResultVo {

    private String id;
    private String contractType;

    private String contractTypeQuery;

    private String contractName;
    private BigDecimal contractAmountBelow;

    private BigDecimal contractAmountUpper;

    private String balanceCondition;

    private String balanceTimeSun;

    private String balanceTime;

    private Integer page;

    private Integer size;


    //结点
    private Integer paymentOrder;  //支付次序
    private String nodeName;  //节点名称
    private String paymentConditionType;  //支付条件类型
    private String paymentConditionPrice;  //支付条件值
    private String paymentWay;  //支付方式
    private String paymentFoundation; //支付基础
    private Integer paymentProportion;   //支付情况   比例
    private String rmk;  //备注
    private String templateId;  //模板id   主表id

    private BigDecimal paymentAmt;  //支付金额

    private List<ContractPaymentPlanTemplateDl> dataDl;

    private BigDecimal contrActAmt;

}
