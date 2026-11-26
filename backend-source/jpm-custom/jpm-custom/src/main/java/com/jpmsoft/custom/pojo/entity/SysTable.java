package com.jpmsoft.custom.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;


@Data
@Accessors(chain = true)
@TableName("Sys_Table")
public class SysTable {
    @TableId(type = IdType.INPUT)
    public String tableId;
    public String tableName;
    public String tableCode;
    public String tableRemark;
    public Date tableCreatetime;
    public String tableCreateuser;
    public String tableCreateusername;
    public String tableStats;
    public String tablePk;
    public String tableClassname;
    public String tableDb;
    public String tableType;
}
