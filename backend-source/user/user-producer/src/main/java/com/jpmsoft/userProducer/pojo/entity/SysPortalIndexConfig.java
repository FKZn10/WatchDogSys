package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sys_portal_index_config")
public class SysPortalIndexConfig {

    @TableId
    private String id;

    @TableField("Create_Uid")
    private String createUid;

    @TableField("Create_Uname")
    private String createUname;

    @TableField("Create_Time")
    private Date createTime;

    @TableField("Update_Time")
    private Date updateTime;

    @TableField("State")
    private Integer state;

     @TableField("State_Time")
     private Date stateTime;

    /**
     * 门户名称
     */
     @TableField("Portal_Name")
     private String portalName;

    /**
     * 门户编码
     */
     @TableField("Portal_Code")
     private String portalCode;

    /**
     * 门户状态
     * 0 禁用
     * 1 启用
     */
     @TableField("Portal_State")
     private String portalState;

    /**
     * 关联SysPortalDesign表的ID
     */
     @TableField("Portal_Design_Id")
     private String portalDesignId;

    /**
     * 系统标识
     */
    @TableField("System_Identifier")
    private String systemIdentifier;
}
