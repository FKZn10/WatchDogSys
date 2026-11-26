package com.jpmsoft.fileUpload.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
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
