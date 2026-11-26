package com.jpmsoft.projectProducer.pojo.entity.investment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("Preliminary_Audit")
public class PreliminaryAudit {

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
     * 审核单位
     */
    private String paAuditUnit;

    /**
     * 报审日期
     */
    private Date paBringTrialDate;

    /**
     * 审定日期
     */
    private Date paExamineApproveDate;

    /**
     * 审核文号
     */
    private String paAuditDocumentNumber;

    /**
     * 备注
     */
    private String paRemarks;

    /**
     * 事实清单
     */
    private String paFactSheet;

    /**
     * 项目ID
     */
    private String pId;

    /**
     * 合同ID
     */
    private String conId;

    /**
     * 合同金额
     */
    private BigDecimal paContractAmount;

    /**
     * 报审金额
     */
    private BigDecimal paBringTrial;

    /**
     * 审定金额
     */
    private BigDecimal paExamineApprove;

    /**
     * 核减金额
     */
    private BigDecimal paDeductionAmount;

    /**
     * 核减率
     */
    private BigDecimal paReductionRate;
}
