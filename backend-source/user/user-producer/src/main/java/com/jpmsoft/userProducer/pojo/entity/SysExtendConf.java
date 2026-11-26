package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 文档模板扩展配置
 *
 * @Author: 李文涛 aaron
 * @date: 2023/5/10 11:11
 */
@Data
@TableName("Sys_Extend_Conf")
public class SysExtendConf {

    @TableId(type = IdType.INPUT)
    private String id;
    @JsonProperty("tId")
    private String tId;
    private String ecName;
    private String ecRemark;
    private Integer ecSort;
    private Boolean ecDefValue;
    private String ecPrompt;
}
