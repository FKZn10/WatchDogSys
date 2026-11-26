package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 接口响应体
 * @Author: 李文涛 aaron
 * @date: 2024/11/19 10:41
 */
@Data
@TableName("Sys_Interface_Response")
public class SysInterfaceResponse {

    @TableId(type = IdType.INPUT)
    private String id;
    private String ifId;
    private String resName;
    private String resField;
    private String resType;
    private Integer resSort;
    private String resContent;
}
