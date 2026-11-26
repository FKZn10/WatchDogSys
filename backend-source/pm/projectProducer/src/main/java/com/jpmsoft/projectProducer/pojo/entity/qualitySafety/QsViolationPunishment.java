package com.jpmsoft.projectProducer.pojo.entity.qualitySafety;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 质量安全违规处罚
 */
@Data
@TableName("Qs_Violation_Punishment")
public class QsViolationPunishment {
    @TableId(type = IdType.INPUT)
    private String vpId;

    /**
     * 项目ID
     */
    private String pId;

    /**
     * 工程ID
     */
    private String secId;

    /**
     * 合同ID
     */
    private String conId;

    /**
     * 被处罚单位ID
     */
    private String vpCId;

    /**
     * 违规类型 JPMsoft_ZLAQWGLX
     */
    private String vpType;

    /**
     * 违规行为原因
     */
    private String vpReason;

    /**
     * 违规处理通知书编号
     */
    private String vpCode;

    /**
     * 处罚主办部门人员
     */
    private String vpUId;

    /**
     * 违约金金额
     */
    private BigDecimal vpLd;

    /**
     * Vp_Warning_Situation
     */
    private String vpWarningSituation;

    /**
     * 被处罚单位签收人ID
     */
    private String vpSignUid;

    /**
     * 被处罚单位签收人姓名
     */
    private String vpSignUname;

    /**
     * 被处罚单位签收日期
     */
    private Date vpSignTime;

    private String vpCreateUid;
    private String vpCreateUname;
    private Date vpCreateTime;
    private Integer vpState;
    private Date vpStateTime;

    /**
     * 备注
     */
    private String vpRemark;

    /**
     * 处罚日期
     */
    private Date vpDate;

    /**
     * 处罚主办部门（部门ID）
     */
    private String vpDId;

    /**
     * 页码编号
     */
    private String vpPageCode;

    /**
     * 处罚主办部门人员姓名
     */
    private String vpUName;
    private String teId;
    /**
     * 问题严重等级
     */
    private String vpLevel;
    private String qstId;

    private BigDecimal vpScore;
}
