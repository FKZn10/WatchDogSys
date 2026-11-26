package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 已开通应用
 *
 * @Author: 李文涛 aaron
 * @date: 2023/3/7 9:41
 */
@Data
@TableName("SYS_TenantApplication")
public class SysTenantApplication {

    @TableId(type = IdType.INPUT)
    private String taId;
    private String appId;
    private String teId;
}
