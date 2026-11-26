package com.jpmsoft.projectProducer.pojo.vo.contract;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 合同已审批设计变更、现场签证的价款明细，供表单计算累计金额和继承历史余额。
 */
@Data
public class ContractConEcHistoryVO {

    /**
     * 子表记录ID。
     */
    private String ecId;

    /**
     * 合同ID。
     */
    private String conId;

    /**
     * 主流程ID：设计变更为uuid，现场签证为Svis_Id。
     */
    private String flowId;

    /**
     * 设计变更主流程编号，用于关联完工确认的changeCode；现场签证不返回此编号。
     */
    private String changeCode;

    /**
     * 流程类型：01设计变更，02现场签证。
     */
    private String ecChangeType;

    /**
     * 申请金额，正负方向由ecApplyMoneyType确定。
     */
    private BigDecimal ecApplyMoney;

    /**
     * 运算符号：0减少，1增加。
     */
    private Integer ecApplyMoneyType;

    /**
     * 主流程审批状态。
     */
    private Integer ecState;

    /**
     * 主流程审批时间，不读取可能为空的子表审批时间。
     */
    private Date ecStateTime;

    /**
     * 已保存的累计调整金额。
     */
    private BigDecimal ecTotalMoneyC;

    /**
     * 已保存的合同可用金额，0为有效余额。
     */
    private BigDecimal contractAvailableMoney;

    /**
     * 已保存的概算可用金额，允许为负数。
     */
    private BigDecimal estimateAvailableMoney;
}
