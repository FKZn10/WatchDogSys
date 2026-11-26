package com.jpmsoft.projectProducer.pojo.entity.investment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 结算申请清单
 */
@Data
@TableName("inv_settlement_apply_dtl")
public class InvSettlementApplyDtl {
    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
    private String id;

    /**
     * 创建人id
     */
    private String createUid;

    /**
     * 创建人名称
     */
    private String createUname;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 审批状态
     */
    private Integer state;

    /**
     * 审批时间
     */
    private Date stateTime;

    /**
     * 主表id
     */
    private String isaId;

    /**
     * 施工确认单id
     */
    private String completionConfirmId;

    /**
     * 结算金额
     */
    private BigDecimal isadAmountTotal;

    /**
     * 结算报价附件
     */
    private String isadReportFile;

    /**
     * 审定金额
     */
    private BigDecimal isadApprovedAmt;

    /**
     * 核增核减金额
     */
    private BigDecimal isadIncreaseDecrease;

    /**
     * 审定报告
     */
    private String isadAuditReport;

    /**
     * 审计单位备注
     */
    private String isadAuditRemark;

    /**
     * 修缮单
     */
    private String mrId;

    /**
     * 备注
     */
    private String isadRemark;
}