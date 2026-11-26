package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 多项目合同支付项目信息
 *
 * @author tianhelin
 * @date 2025/9/11
 */
@Data
@TableName("Con_Pay_Multi_Project")
public class ConPayMultiProject {
    @TableId(type = IdType.INPUT)
    private String id;
    private String cpmId;
    private String pId;
    private BigDecimal conAmount;
    private String location;
    private Integer duration;
    private String conId;
    private BigDecimal paidAmount;
    private BigDecimal paidRatio;
    private BigDecimal currentPayAmount;
    private BigDecimal changeVisaPaidAmount;
    private BigDecimal visaTempFundRatio;
    private BigDecimal safetyFeePaidAmount;
    private BigDecimal workerWagePaidAmount;
    private BigDecimal workerWageAmount;
    private BigDecimal projectAmount;
    private BigDecimal projectPaidAmount;
    private String payMultiFile;
    private String payId;
}