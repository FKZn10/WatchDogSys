package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 合同价款及支付信息
 */
@Data
@TableName("contract_price_payment_info")
public class ContractPricePaymentInfo {
    @TableId(type = IdType.INPUT)
    private String id;
    private String conId;
    private String secId;
    /**
     * 服务项
     */
    private String serviceItem;

    /**
     * 服务类型
     */
    private String serviceType;

    /**
     * 是否分包
     */
    private String subcontracting;

    /**
     * 价格形式
     */
    private String priceType;

    /**
     * 结算价计算
     */
    private String settlementText;

    /**
     * 支付条款
     */
    private String priceText;

    /**
     * 签约合同价格
     */
    private BigDecimal price;

    /**
     * 变更类型
     */
    private String changeType;

    /**
     * 变更内容
     */
    private String changeContent;

    /**
     * 排序号
     */
    private Integer sortNum;
}