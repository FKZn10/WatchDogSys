package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 缴费信息
 *
 * @Author: 李文涛 aaron
 * @date: 2023/3/7 14:28
 */
@Data
@TableName("SYS_Tenant_Pay")
public class SysTenantPay {

    @TableId(type = IdType.INPUT)
    private String payId;
    private String teId;
    private String payType;
    private Date payStartTime;
    private Date payExpirationTime;
    private Integer payMaximumUser;
    private Integer payMaximumProject;
    private BigDecimal payAmountPayable;
    private Float payDiscount;
    private BigDecimal payAmountActual;
    private BigDecimal payAmountCarryOver;
    private Date payDate;
    private String payWay;
    private String payVoucher;
    private String payStatus;
    private Date payCarryOverTime;
    private BigDecimal payCarryOverBalance;
    private Date payRefundTime;
    private BigDecimal payAmountRefund;

    private String createUid;
    private String createUname;
    private Date createTime;
    private Date stateTime;
    private Integer state;

}
