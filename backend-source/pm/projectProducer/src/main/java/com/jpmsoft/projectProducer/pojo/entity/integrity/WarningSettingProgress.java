package com.jpmsoft.projectProducer.pojo.entity.integrity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 预警提醒设置
 */
@Data
@TableName("Warning_Setting_Progress")
public class WarningSettingProgress {
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
     * 所属模板
     */
    private String tCode;

    /**
     * 预警设置
     */
    private String alertSettings;

    /**
     * 所属模块
     */
    private String modelCode;
}