package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("Sys_Tool_Data_Backup_Qd")
public class SysToolDataBackupQd {
    @TableId(type = IdType.INPUT)
    public String id;
    public String sysId;
    public String tableCode;
    public String tableName;
    public String backupFileAddress;
    public String dataCount;
    public Integer serialNumber;
    public Integer deletedState;
    public Integer restoreState;
    public Date restoreTime;
}
