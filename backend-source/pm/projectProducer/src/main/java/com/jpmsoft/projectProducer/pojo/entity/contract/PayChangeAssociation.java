package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 支付变更关联表
 */
@Data
@TableName("Pay_Change_Association")
public class PayChangeAssociation {
    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
    private String id;

    /**
     * 支付id
     */
    private String payId;

    /**
     * 变更费用id
     */
    private String ecfId;

    /**
     * 费用批准金额
     */
    private BigDecimal ecfMoney;

    /**
     * 支付比例
     */
    private Integer payRatio;

    /**
     * 本期支付金额
     */
    private BigDecimal payMoney;
}