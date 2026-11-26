package com.jpmsoft.projectProducer.pojo.entity.integrity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("Warning_Reminder_Indicators")
public class WarningReminderIndicators {
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
     *
     */
    private Date stateTime;

    /**
     * 预警指标
     */
    private String wriIndicator;

    /**
     * 指标描述
     */
    private String indicatorDescription;

    /**
     * 针对问题
     */
    private String regardingIssue;

    /**
     * 预警提示等级
     */
    private String wriLevel;
}