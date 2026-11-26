package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 发票签收
 *
 * @Author: 李文涛 aaron
 * @date: 2024/3/28 15:07
 */
@Data
@TableName("Con_Invoice_Sign")
public class ConInvoiceSign extends BaseEntity {

    @TableField(value = "P_Id")
    private String pId;
    @TableField(value = "Con_Id")
    private String conId;
    @TableField(value = "Is_Issuing_Unit")
    private String isIssuingUnit;
    @TableField(value = "Is_Collect_Name")
    private String isCollectName;
    @TableField(value = "Is_File")
    private String isFile;
    @TableField(value = "Is_Time")
    private Date isTime;
    @TableField(value = "Is_Payment_Amt")
    private BigDecimal isPaymentAmt;
    @TableField(value = "Is_Payment_Rate")
    private BigDecimal isPaymentRate;
    @TableField(value = "Te_Id")
    private String teId;
    @TableField(value = "Is_Year")
    private Integer isYear;
    @TableField(value = "Is_Actual_Amt")
    private BigDecimal isActualAmt;
}
