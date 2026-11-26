package com.jpmsoft.projectProducer.pojo.vo.contract;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 漕湖结算查询合同带出值
 * 合同金额，变更签证金额、清标金额、已收工程款
 *
 * @Author: 李文涛 aaron
 * @date: 2024/12/31 12:37
 */
@Data
public class ChContractSettlementVO implements Serializable {

    private static final long serialVersionUID = -2240713642734091449L;
    private BigDecimal conMoneyTotal = BigDecimal.ZERO;//合同金额
    private BigDecimal ecfApplyMoneyTotal = BigDecimal.ZERO;//变更金额
    private BigDecimal payApprovalTotal = BigDecimal.ZERO;//已支付金额
    private BigDecimal ecfApplyClearMoney = BigDecimal.ZERO;//清标金额
}
