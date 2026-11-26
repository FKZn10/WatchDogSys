package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("Sys_Tool_Data_Backup")
public class SysToolDataBackup {
    @TableId(type = IdType.INPUT)
    public String id;
    public Date stdbTime;
    public String stdbTitle;
    public Integer stdbTableCount;
    public Integer stdbDataCount;
    public String stdbOperator;
    public String stdbOperatorId;
    public Boolean stdbBackupStatus;
    public Boolean stdbDeleteStatus;
    public Boolean stdReductionStatus;

    public String stdbTableCode;
    public String stdDeleteUid;
    public String stdDeleteUname;
    public String stdReductionUid;
    public String stdReductionUname;
}
