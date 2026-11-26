package com.jpmsoft.projectProducer.pojo.entity.integrity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 功能预警流程记录
 */
@Data
@TableName("Functional_Integrity_Warning")
public class FunctionalIntegrityWarning {
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
     * 预警触发字段
     */
    private String triggerField;

    /**
     * 触发预警值
     */
    private String triggerFieldValue;

    /**
     * 所属模版（弃用）
     */
    @Deprecated
    private String tCode;

    /**
     * 所属表id
     */
    private String warningForm;

    /**
     * 情况描述
     */
    private String situationDescription;

    /**
     * 相关文件
     */
    private String relatedDocuments;

    /**
     * 预警审核结果
     */
    private String auditResults;

    /**
     * 预警指标关联id
     */
    private String wriId;

    /**
     * 所属模版
     */
    private String businessCode;
}