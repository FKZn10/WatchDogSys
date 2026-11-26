package com.jpmsoft.jpbpm.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("Sys_Task_Massage")
public class sysTaskMassage {
    public String id;
    public String taskId;
    public String  uIds;
    public String readType;
    public Date readTime;
}
