package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data
@TableName("sys_portal_index_component")
public class SysPortalIndexComponent {

     /**
      * 组件ID
      */
     @TableId("ic_Id")
     private String icId;

     /**
      * 关联主表SysPortalIndexConfig 的 ID
      */
     @TableField("Index_Config_Id")
     private String indexConfigId;

    /*
     * 组件状态 是否启用
     * 01 启用
     * 02 停用 默认
     */
    @TableField("Component_State")
    private String componentState;


    /**
     * 角色的id
     * 角色表SYS_Role 中的ID
     */
     @TableField("Role_Id")
    private String roleId;

}
