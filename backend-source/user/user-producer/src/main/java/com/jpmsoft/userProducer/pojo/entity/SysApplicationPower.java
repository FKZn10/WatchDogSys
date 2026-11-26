package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 应用菜单表
 *
 * @Author: 李文涛 aaron
 * @date: 2023/3/7 9:21
 */
@Data
@TableName("SYS_Application_Power")
public class SysApplicationPower {

    @TableId(type = IdType.INPUT)
    private String apId;
    private String appId;
    private String powerId;
}
