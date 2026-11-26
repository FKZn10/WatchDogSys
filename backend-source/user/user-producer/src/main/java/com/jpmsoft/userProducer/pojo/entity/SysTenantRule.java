package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 租户权限规则表
 *
 * @Author: 李文涛 aaron
 * @date: 2023/8/28 16:26
 */
@Data
@TableName("Sys_Tenant_Rule")
public class SysTenantRule {

    @TableId(type = IdType.INPUT)
    private String id;
    private String teId;
    private Date trDate;
    private Integer trMaxProject;
    private Integer trMaxUser;
    private Date createTime;
}
