package com.jpmsoft.projectProducer.pojo.entity.zdyxy;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("inv_settlement_pay")
public class InvSettlementPay implements Serializable {
    private static final long serialVersionUID = 2350943761312243232L;
    /**
     *
     */
    private String id;

    /**
     * 结算申请ID
     */
    private String invSettlementApplyId;

    /**
     * 确认完成ID
     */
    private String confirmCompleteId;

    /**
     * 维修ID
     */
    private String mrId;

    /**
     * 支付金额
     */
    private BigDecimal payAmount;

    /**
     * 质保金支付比例
     */
    private BigDecimal zbjPayRate;

    /**
     * 质保金支付金额
     */
    private BigDecimal zbjPayAmount;

    /**
     * 创建人
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
     * 状态
     */
    private Integer state;

    /**
     * 状态时间
     */
    private Date stateTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 发票附件
     */
    private String invoiceAttachmentFile;

    /**
     * 请款单附件
     */
    private String payAttachmentFile;

    /**
     * 其他附件
     */
     private String otherAttachmentFile;

    /**
     * 年度预算ID
     */
    private String apId;
}