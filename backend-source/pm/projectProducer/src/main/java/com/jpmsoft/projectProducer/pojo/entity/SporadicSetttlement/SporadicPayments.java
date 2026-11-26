package com.jpmsoft.projectProducer.pojo.entity.SporadicSetttlement;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("sporadic_payments")
public class SporadicPayments {


    @TableId
    private String id;

    @TableField("cretate_Uid")
    private String cretateUid;

    @TableField("create_Uname")
    private String createUname;

    @TableField("create_Time")
    private Date createTime;

    @TableField("state")
    private Integer state;

    @TableField("state_Time")
    private Date stateTime;

    /**
     * 工程名称
     */
    @TableField("sp_p_name")
    private String spPName;

    /**
     * 零星维修支付流水号
     */
    @TableField("sp_p_number")
    private String spPNumber;

    /**
     * 付款金额（元）
     */
    @TableField("sp_p_paymentamount")
    private BigDecimal spPPaymentamount;

    /**
     * 填报日期
     */
    @TableField("sp_p_fillindata")
    private Date spPFillindata;

    /**
     * 申请单位id
     */
    @TableField("sp_p_applyunitid")
    private String spPApplyunitid;

    /**
     * 申请单位名称
     */
    @TableField("sp_p_applyunitname")
    private String spPApplyunitname;

    /**
     * 支付时间
     */
    @TableField("sp_p_paymentdata")
    private Date spPPaymentdata;

    /**
     * 区域负责人id
     */
    @TableField("sp_p_areamanager_uid")
    private String spPAreamanagerUid;

    @TableField("sp_p_pay_date")
    private Date spPPayDate;
}
