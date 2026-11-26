package com.jpmsoft.projectProducer.pojo.vo.investment;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 概算树 合同汇总金额 支付汇总金额
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/26 15:01
 */
@Data
public class BudgetConPayVo implements Serializable {

    private String bddId;
    private String bddParentId;
    private String bddName;
    private BigDecimal bddPrice = BigDecimal.ZERO;
    private BigDecimal bddTotal = BigDecimal.ZERO;
    private BigDecimal conMoneyTotal = BigDecimal.ZERO;
    private BigDecimal conPayTotal = BigDecimal.ZERO;
    private Integer bddOrder;
    //合同金额是否超出概算合价
    private boolean conOut;
    //支付金额是否超出合同金额
    private boolean payOut;
    private List<BudgetConPayVo> childNodes;
}
