package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 接口请求体
 * @Author: 李文涛 aaron
 * @date: 2024/11/19 10:38
 */
@Data
@TableName("Sys_Interface_Request")
public class SysInterfaceRequest {

    @TableId(type = IdType.INPUT)
    private String id;
    private String ifId;
    private String reqName;
    private String reqField;
    private String reqType;
    private Integer reqSort;
    private String reqContent;
}
