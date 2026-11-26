package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 发票签收-发票信息
 * @Author: 李文涛 aaron
 * @date: 2024/3/28 15:08
 */
@Data
@TableName("Con_Invoice_Info")
public class ConInvoiceInfo {
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    @TableField(value = "Is_id")
    private String isId;
    @TableField(value = "Ci_Type")
    private String ciType;
    @TableField(value = "Ci_Bus_Type")
    private String ciBusType;
    @TableField(value = "Ci_Collect_Amt")
    private BigDecimal ciCollectAmt;
    @TableField(value = "Ci_Tax_Amt")
    private BigDecimal ciTaxAmt;
    @TableField(value = "Ci_Collect_Tax_Amt")
    private BigDecimal ciCollectTaxAmt;
    @TableField(value = "Ci_Tax_Rate")
    private BigDecimal ciTaxRate;
    @TableField(value = "Ci_Code")
    private String ciCode;
    @TableField(value = "Ci_Number")
    private String ciNumber;
    @TableField(value = "Ci_Date")
    private Date ciDate;
    @TableField(value = "Ci_Sort")
    private Integer ciSort;
    @TableField(value = "Ci_Remark")
    private String ciRemark;

}
