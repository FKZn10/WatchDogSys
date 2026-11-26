package com.jpmsoft.projectProducer.pojo.entity.actualMeasurement;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 图纸项实体类
 */
@Data
@TableName("Drawing_Item")
public class DrawingItem   {

    /**
     * 主键ID
     */
    @TableId(value = "Di_ID", type = IdType.INPUT)
    private String diId;

    /**
     * 项目ID
     */
    @TableField("P_Id")
    private String pId;

    /**
     * 区域分组
     */
    @TableField("Di_Region_Group")
    private String diRegionGroup;

    /**
     * 区域名称
     */
    @TableField("Di_Region_Name")
    private String diRegionName;

    /**
     * 区域编码
     */
    @TableField("Di_Region_Code")
    private String diRegionCode;

    /**
     * 图纸
     */
    @TableField("Di_Drawing")
    private String diDrawing;

    /**
     * 序号
     */
    @TableField("Di_Order")
    private Integer diOrder;

    /**
     * 创建人ID
     */
    @TableField("Create_Uid")
    private String createUid;

    /**
     * 创建人姓名
     */
    @TableField("Create_Uname")
    private String createUname;

    /**
     * 创建时间
     */
    @TableField("Create_Time")
    private Date createTime;

    /**
     * 状态
     */
    @TableField("State")
    private Integer state;

    /**
     * 审批时间
     */
    @TableField("State_Time")
    private Date stateTime;
}