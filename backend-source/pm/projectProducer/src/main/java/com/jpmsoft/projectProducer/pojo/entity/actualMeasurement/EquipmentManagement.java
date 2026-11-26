package com.jpmsoft.projectProducer.pojo.entity.actualMeasurement;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 设备管理实体类
 * Equipment_Management
 */
@Data
@TableName("Equipment_Management")
public class EquipmentManagement implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 设备ID
     */
    @TableId(value = "EM_Id", type = IdType.INPUT)
    private String emId;

    /**
     * 设备名称
     */
    @TableField("EM_Name")
    private String emName;

    /**
     * 蓝牙名称
     */
    @TableField("EM_Blue_Name")
    private String emBlueName;

    /**
     * APP-notify服务编号
     */
    @TableField("EM_App_Notify_Service_Code")
    private String emAppNotifyServiceCode;

    /**
     * APP-notify特征编号
     */
    @TableField("EM_App_Notify_Charac_Code")
    private String emAppNotifyCharacCode;

    /**
     * 小程序notify服务编号
     */
    @TableField("EM_Mini_Notify_Service_Code")
    private String emMiniNotifyServiceCode;

    /**
     * 小程序notify特征编号
     */
    @TableField("EM_Mini_Notify_Charac_Code")
    private String emMiniNotifyCharacCode;

    /**
     * 数据发送次数
     */
    @TableField("Em_Data_Send_Times")
    private Integer emDataSendTimes;

    /**
     * 数据类型
     */
    @TableField("EM_Data_Type")
    private String emDataType;

    /**
     * 启用质量
     */
    @TableField("EM_Quality")
    private String emQuality;

    /**
     * 采样指令
     */
    @TableField("EM_Sampling")
    private String emSampling;

    /**
     * 指令回复
     */
    @TableField("EM_Reply")
    private String emReply;

    /**
     * APP-write服务编号
     */
    @TableField("EM_App_Write_Service_Code")
    private String emAppWriteServiceCode;

    /**
     * APP-write特征编号
     */
    @TableField("EM_App_Write_Charac_Code")
    private String emAppWriteCharacCode;

    /**
     * 小程序-write服务编号
     */
    @TableField("EM_Mini_Write_Service_Code")
    private String emMiniWriteServiceCode;

    /**
     * 小程序-write特征编号
     */
    @TableField("EM_Mini_Write_Charac_Code")
    private String emMiniWriteCharacCode;

    /**
     * 创建用户ID
     */
    @TableField("Create_Uid")
    private String createUid;

    /**
     * 创建用户名称
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
