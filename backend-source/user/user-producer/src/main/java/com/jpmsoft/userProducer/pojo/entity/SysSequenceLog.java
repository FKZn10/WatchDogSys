package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 编码日志
 *
 * @Author: 李文涛 aaron
 * @date: 2023/5/15 9:37
 */
@Data
@TableName("Sys_Sequence_Log")
public class SysSequenceLog {

    @TableId(type = IdType.INPUT)
    private String id;
    private String sslCode;
    private String sslBody;
    private String sslRule;
    private Date sslTime;
}
