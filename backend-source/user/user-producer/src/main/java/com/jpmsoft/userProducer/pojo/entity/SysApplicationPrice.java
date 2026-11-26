package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/3/7 17:43
 */
@Data
@TableName("SYS_Application_Price")
public class SysApplicationPrice {
    @TableId(type = IdType.INPUT)
    private String apId;
    private String appId;
    private String apCode;
    private BigDecimal apMoney;
}
