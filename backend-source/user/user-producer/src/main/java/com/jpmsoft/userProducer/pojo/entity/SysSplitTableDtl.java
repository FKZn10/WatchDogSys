package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * 流程分表配置详情
 *
 * @Author: 李文涛 aaron
 * @date: 2025/2/7 10:02
 */
@Data
@TableName("Sys_Split_Table_Dtl ")
public class SysSplitTableDtl {

    @TableId(type = IdType.INPUT)
    public String id;
    public String stId;
    @JsonProperty("tName")
    public String tName;
    @JsonProperty("tCode")
    public String tCode;
    public String tableName;
    public String tableCode;
    public Integer stdSort;
    public Date createTime;
    public String createUid;
    public String createUname;
}
