package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/1/3 14:16
 */
@Data
@TableName("Sys_Split_Table")
public class SysSplitTable {
    @TableId(type = IdType.ASSIGN_UUID)
    public String id;
    @JsonProperty("tCode")
    public String tCode;
    public String stTable;
    public String stName;
    public String stRemark;
    public String stCode;
    public Date createTime;
    public String createUid;
    public String createUname;
}
