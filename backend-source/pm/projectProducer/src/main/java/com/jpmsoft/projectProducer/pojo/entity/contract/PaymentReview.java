package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/*
付款请审
 */
@Data
@TableName("Payment_Review")
public class PaymentReview {

    private String 	id;

    private String 	pId;
    private String 	prCode;
    private String 	applyUnit;
    private String 	createUname;
    private String 	conId;
    private Date stateTime;
    private String 	applyContent;
    private String 	createUid;
    private String 	applyType;
    private Date 	createTime;
    private Integer state;

    private String cId;

    private BigDecimal bringTrialMoney;

    private BigDecimal firstTrialMoney;

    private BigDecimal reviseReduceMoney;
}
