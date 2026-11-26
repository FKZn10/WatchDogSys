package com.jpmsoft.jpbpm.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("Sys_Split_Table_Dtl")
public class SysSplitTableDtl {
    @TableId(type = IdType.INPUT)
    public String id;
    public String stId;
    public String tName;
    public String tCode;
    public String tableName;
    public String tableCode;
    public String stdSort;
    public Date createTime;
    public String createUid;
    public String createUname;
}
