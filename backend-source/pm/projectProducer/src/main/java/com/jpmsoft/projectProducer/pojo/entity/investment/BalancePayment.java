package com.jpmsoft.projectProducer.pojo.entity.investment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("Balance_Payment")
public class BalancePayment {

    /**
     * 主键ID
     */
    @TableId(type = IdType.INPUT)
    private String id;

    /**
     * 创建人UID
     */
    private String createUid;

    /**
     * 创建人姓名
     */
    private String createUname;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 状态更新时间
     */
    private Date stateTime;

    /**
     * 项目ID
     */
    private String pId;

    /**
     * 合同ID
     */
    private String conId;

    /**
     * 申请日期
     */
    private Date bpApplicationDate;

    /**
     * 支付计划ID
     */
    private String cppId;

    /**
     * 支付情况说明
     */
    private String bpPaymentSituation;

    /**
     * 应付金额
     */
    private BigDecimal bpPayable;

    /**
     * 实际申请金额
     */
    private BigDecimal bpActualApplication;

    /**
     * 扣款原因
     */
    private String bpReasonDeduction;

    /**
     * 备注
     */
    private String bpRemark;

    /**
     * 合同总金额
     */
    private BigDecimal bpConMoneyTotal;

    /**
     * 审核金额
     */
    private BigDecimal bpAuditAmount;
}
