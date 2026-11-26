package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 本次开通应用
 *
 * @Author: 李文涛 aaron
 * @date: 2023/3/7 15:11
 */
@Data
@TableName("SYS_Pay_Application")
public class SysPayApplication {

    @TableId(type = IdType.INPUT)
    private String paId;
    private String payId;
    private String appId;
}
