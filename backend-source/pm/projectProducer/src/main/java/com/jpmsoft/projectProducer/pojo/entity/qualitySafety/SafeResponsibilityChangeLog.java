package com.jpmsoft.projectProducer.pojo.entity.qualitySafety;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 安全责任体系变更台账
 */
@Data
@TableName("safe_responsibility_change_log")
public class SafeResponsibilityChangeLog {
    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
    private String id;

    /**
     * 安全责任体系ID
     */
    private String systemId;

    /**
     * 对象类型：SYSTEM、LEVEL、POST、DUTY
     */
    private String targetType;

    /**
     * 对象ID
     */
    private String targetId;

    /**
     * 对象名称
     */
    private String targetName;

    /**
     * 操作类型：ADD、UPDATE、DELETE、COPY、RESET
     */
    private String operationType;

    /**
     * 变更前JSON快照
     */
    private String beforeContent;

    /**
     * 变更后JSON快照
     */
    private String afterContent;

    /**
     * 变更摘要
     */
    private String changeDescription;

    /**
     * 操作人ID
     */
    private String operatorUid;

    /**
     * 操作人姓名
     */
    private String operatorUname;

    /**
     * 操作时间
     */
    private Date operationTime;

    /**
     * 批次号
     */
    private String batchNo;
}