package com.jpmsoft.projectProducer.pojo.entity.siteform;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 下发单位表
 */
@Data
@TableName("Doc_Distribution_Unit")
public class DocDistributionUnit {
    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
    private String dduId;

    /**
     * 关联主表ID
     */
    private String mainTableId;

    /**
     * 主表类型
     */
    private String mainTableType;

    /**
     * 下发单位
     */
    private String unit;

    /**
     * 是否电子版
     */
    private String isElectronic;

    /**
     * 排序号
     */
    private Integer sortOrder;

    /**
     * 接收人is
     */
    private String recipient;

    /**
     * 批次id
     */
    private String batchId;

    /**
     * 单位类型
     */
    private String unitType;

    /**
     * 下发时间
     */
    private Date releaseTime;

    /**
     * 下发人员id
     */
    private String issuedPersonnelId;

    /**
     * 下发人员
     */
    private String issuedPersonnel;

    /**
     * 审批状态
     */
    private Integer state;

    /**
     * 审批时间
     */
    @TableField("stateTime")
    private String statetime;

    /**
     * 下发单位名称
     */
    @TableField("unitName")
    private String unitname;

    /**
     * 接收人名称
     */
    private String recipientName;
}