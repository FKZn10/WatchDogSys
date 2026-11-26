package com.jpmsoft.jpbpm.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("SYS_FileActUser")
public class FileActUser {

    @TableId(type = IdType.INPUT)
    public String fcuId;
    public String fcuFileid;
    public String fcuActid;
    public String fcuUserid;
    private String teId;
    public String getFcuId() {
        return fcuId;
    }

    public void setFcuId(String fcuId) {
        this.fcuId = fcuId;
    }

    public String getFcuFileid() {
        return fcuFileid;
    }

    public void setFcuFileid(String fcuFileid) {
        this.fcuFileid = fcuFileid;
    }

    public String getFcuActid() {
        return fcuActid;
    }

    public void setFcuActid(String fcuActid) {
        this.fcuActid = fcuActid;
    }

    public String getFcuUserid() {
        return fcuUserid;
    }

    public void setFcuUserid(String fcuUserid) {
        this.fcuUserid = fcuUserid;
    }

    public String getTeId() {
        return teId;
    }

    public void setTeId(String teId) {
        this.teId = teId;
    }
}
