package com.jpmsoft.jpbpm.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("Sys_Ji_Mu_Report")
public class SysJiMuReport {
    @TableId(type = IdType.INPUT)
    public  String  id;
    public  String  fileId;
    public  String  tCode;
    public Date createTime;
    public  String  jmId;

}
