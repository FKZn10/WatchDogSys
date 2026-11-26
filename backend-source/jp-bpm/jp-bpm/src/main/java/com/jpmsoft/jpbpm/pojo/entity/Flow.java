package com.jpmsoft.jpbpm.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("SYS_Flow")
public class Flow {

    @TableId(type = IdType.INPUT)
    public String flowid;
    public String flowname;
    public String flowxml;
    public String flowclient;
    public String flowproject;
    public String flowsolution;
    public String flowcompany;
    public Date flowcreatetime;
    public String flowcreateuId;
    public String flowcreateuName;
    public String flowlastedituId;
    public String flowlastedituName;
    public Date flowlastedittime;
    public String flowlasteditreason;
    public Integer flowversionnum;
    public String flowenable;
    public String tCode;
    public String tId;
    private String teId;
    private String flowRecordRule;
}
