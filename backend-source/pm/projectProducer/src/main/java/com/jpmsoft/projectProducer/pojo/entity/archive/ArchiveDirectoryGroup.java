package com.jpmsoft.projectProducer.pojo.entity.archive;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 档案目录权限 -组和用户
 *
 * @author: 李文涛 aaron
 * @date: 2022/11/9 17:13
 */
@Data
@TableName("Archive_Directory_Group")
public class ArchiveDirectoryGroup {

    @TableId(type = IdType.INPUT)
    private String id;
    private String adId;
    private String adgName;
    private String adgType;
    private String adgGroupId;
    private String adgSecrecyLevel;
    /**
     * 1 选择Sys_Role
     * 2 自定义角色
     */
    private String adgRoleType;
    private String adgRole;
    private String adgPower;
    private Date createTime;
    private String teId;
}
