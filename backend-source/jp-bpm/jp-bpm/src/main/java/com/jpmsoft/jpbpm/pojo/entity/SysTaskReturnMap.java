package com.jpmsoft.jpbpm.pojo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("Sys_Task_Return_Map")
public class SysTaskReturnMap {
    @TableId(type = IdType.INPUT)
    public String id;
    public String fileId;
    public String operateAct;
    public String operateActUser;
    public String operateTargetAct;
    public Date operateTime;
    public String taksId;
}
