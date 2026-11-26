package com.jpmsoft.projectProducer.pojo.vo.contract;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 城建施工合同支付安全文明措施费
 *
 * @Author: 李文涛 aaron
 * @date: 2025/1/17 9:58
 */
@Data
public class ContractSecurityMoneyVO implements Serializable {


    private static final long serialVersionUID = 51423429047659168L;
    /**
     * 施工合同安全文明措施费
     */
    private BigDecimal conScMoney;

    /**
     * 支付 本次安全文明措施费汇总
     */
    private BigDecimal paySecurityThisSum;
}
