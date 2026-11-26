package com.jpmsoft.projectProducer.pojo.entity.investment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 月进度计划
 */
@Data
@TableName("Inv_Monthly_Schedule")
public class InvMonthlySchedule {

    @TableId(type = IdType.INPUT)
    private String imsId;
    private String ipvId;
    private Integer imsMonth;
    private BigDecimal imsPlannedInv;
    private Float imsAnnualProportion;
    private BigDecimal imsCumulativeInv;
    private Float imsCumulativeInvProportion;
    private BigDecimal imsPlannedPay;
    private Float imsPayProportion;
    private BigDecimal imsCumulativePay;
    private Float imsCumulativePayProportion;
    private String imsImgProgress;
    private Integer imsYear;

}
