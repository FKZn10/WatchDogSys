package com.jpmsoft.projectProducer.pojo.vo.project;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 投资估算概算分解，概算金额，项目已支付金额
 * 限制招标计划，合同，支付的发起
 *
 * @Author: 李文涛 aaron
 * @date: 2025/1/23 9:53
 */
@Data
public class ProjectCjGsGsFenJieVO {

    /**
     * 是否有投资估算分解
     */
    private int estimateBreakdown;
    /**
     * 是否有投资概算分解
     */
    private int budgetBreakdown;
    /**
     * 项目下已支付金额
     */
    private BigDecimal payAmt;
    /**
     * 概算金额
     */
    private BigDecimal budgetAmt;
}
