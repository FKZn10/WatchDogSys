package com.jpmsoft.projectProducer.pojo.entity.actualMeasurement;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 图纸管理目录实体类
 */
@Data
@TableName("Drawing_Management_Catalogue")
public class DrawingManagementCatalogue {

    /**
     * 主键ID
     */
    @TableId(value = "Dm_Id", type = IdType.INPUT)
    private String dmId;

    /**
     * 项目ID
     */
    @TableField("P_Id")
    private String pId;

    /**
     * 区域名称
     */
    @TableField("Dm_Region_Name")
    private String dmRegionName;

    /**
     * 父级ID
     */
    @TableField("Dm_Parent_Id")
    private String dmParentId;

    /**
     * 序号
     */
    @TableField("Dm_Order")
    private Integer dmOrder;

    /**
     * 区域编码
     */
    @TableField("Dm_Code")
    private String dmCode;

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
     * 图纸文件
     */
    @TableField("Dm_Drawing")
    private String dmDrawing;

    /**
     * 是否上传图纸  01 未上传 02 已上传
     */
    @TableField("Has_Drawing")
    private String hasDrawing;

    /**
     * 是否删除  01 未删除 02 已删除
     */
    @TableField("del_Flag")
    private String delFlag;

    /**
     * 租户id
     */
    @TableField("te_Id")
    private String teId;
}
