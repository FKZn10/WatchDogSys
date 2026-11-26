package com.jpmsoft.projectConsumer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("Contract_Payment_Plan_Template_Dl")
public class ContractPaymentPlanTemplateDl implements Serializable {
    @TableId(type = IdType.INPUT)
    private String id;
    private Integer paymentOrder;
    private String nodeName;
    private String paymentConditionType;
    private String paymentConditionPrice;
    private String paymentWay;
    private String paymentFoundation;
    private Integer paymentProportion;
    private String rmk;
    private String templateId;

    private BigDecimal paymentAmt;

    private Date createTime;

    private Date updateTime;

    private String balanceCondition;  //结算审计  工程竣工

    private String balanceTimeSun;  // 年月日

    private String balanceTime;  //配套balanceTimeSun的多少
}
