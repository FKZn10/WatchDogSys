package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 多项目合同支付
 *
 * @author tianhelin
 * @date 2025/9/10
 */
@Data
@TableName("Con_Pay_Multi")
public class ConPayMulti {
    @TableId(type = IdType.INPUT)
    private String id;
    private String ccmId;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private String name;
    private String code;
    private String paymentType;
    private String paymentName;
    private String payeeUnit;
    private Date payTime;
    private Integer payPeriod;
    private Integer payTotalPeriods;
    private BigDecimal contractAmount;
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
    private String payBasis;
    @TableField("document_preparationdata")
    private Date documentPreparationdata;
}