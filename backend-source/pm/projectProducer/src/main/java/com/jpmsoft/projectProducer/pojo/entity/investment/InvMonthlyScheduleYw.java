package com.jpmsoft.projectProducer.pojo.entity.investment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("inv_monthly_schedule_Yw")
public class InvMonthlyScheduleYw {

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
