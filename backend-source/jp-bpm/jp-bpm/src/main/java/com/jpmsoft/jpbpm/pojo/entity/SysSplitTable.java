package com.jpmsoft.jpbpm.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("Sys_Split_Table")
public class SysSplitTable {
    @TableId(type = IdType.INPUT)
    public String  id;
    public String  tCode;
    public String  stTable;
    public String  stName;
    public String  stRemark;
    public String  stCode;
    public Date createTime;
    public String  createUid;
    public String  createUname;
}
