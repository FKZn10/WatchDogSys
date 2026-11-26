package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 招标采购申请
 *
 * @Author: 李文涛 aaron
 * @date: 2022/12/13 16:16
 */
@Data
@TableName("Sec_Procurement_Apply")
public class SecProcurementApply extends BaseEntity {

    private String secId;
    private String spaValuationMethod;
    private BigDecimal spaAmount;
    private String spaRemarks;
    private String spaOtherPricing;
}
