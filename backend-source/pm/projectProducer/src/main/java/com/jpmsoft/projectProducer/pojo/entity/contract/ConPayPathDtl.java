package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("Con_Pay_Path_Dtl")
public class ConPayPathDtl {
    /**
     * 主键
     */
    private String id;

    /**
     * 支付ID
     */
    private String payId;

    /**
     * 支付路径
     */
    private String paymentPath;

    /**
     * 文号
     */
    private String documentNo;

    /**
     * 金额
     */
    private BigDecimal amount;
}