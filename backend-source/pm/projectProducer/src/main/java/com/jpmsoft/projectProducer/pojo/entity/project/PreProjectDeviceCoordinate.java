package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("pre_project_device_coordinate")
public class PreProjectDeviceCoordinate {

    /**
     * 主键uuid
     */
    @TableId(value = "uuid",type = IdType.ASSIGN_UUID)
    private String uuid;

    /**
     * 项目id
     */
    @TableField(value = "project_id")
    private String projectId;

    /**
     * 项目名称
     */
    @TableField(value = "project_name")
    private String projectName;


    /**
     * x点坐标
     */
    @TableField(value = "point_x")
    private String pointX;

    /**
     * y点坐标
     */
    @TableField(value = "point_y")
    private String pointY;

    /**
     * 被标记的图片标识
     */
    @TableField(value = "picture")
    private String picture;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 创建人id
     */
    @TableField(value = "create_user_id")
    private String createUserId;

    /**
     * 创建人名称
     */
    @TableField(value = "create_username")
    private String createUsername;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 更新人id
     */
    @TableField(value = "update_user_id")
    private String updateUserId;

    /**
     * 更新人名称
     */
    @TableField(value = "update_username")
    private String updateUsername;

    /**
     * 点顺序
     */
    @TableField(value = "point_order")
    private Integer pointOrder;

    /**
     * 项目编码
     */
    @TableField(exist = false)
    private String projectCode;

}
