package com.jpmsoft.projectProducer.pojo.entity.peInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * 履约评价表
 */
@TableName("Pe_Form")
public class PeForm {
    @TableId(type = IdType.INPUT)
    private String fId;
    private String fName;
    private String fType;
    private Integer fVersion;
    private String fCreateUid;
    private String fCreateUname;
    private Date fCreateTime;
    private String fState;
    private String fRemark;
    private String teId;
    public String getfId() {
        return fId;
    }

    public void setfId(String fId) {
        this.fId = fId;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getfType() {
        return fType;
    }

    public void setfType(String fType) {
        this.fType = fType;
    }

    public Integer getfVersion() {
        return fVersion;
    }

    public void setfVersion(Integer fVersion) {
        this.fVersion = fVersion;
    }

    public String getfCreateUid() {
        return fCreateUid;
    }

    public void setfCreateUid(String fCreateUid) {
        this.fCreateUid = fCreateUid;
    }

    public String getfCreateUname() {
        return fCreateUname;
    }

    public void setfCreateUname(String fCreateUname) {
        this.fCreateUname = fCreateUname;
    }

    public Date getfCreateTime() {
        return fCreateTime;
    }

    public void setfCreateTime(Date fCreateTime) {
        this.fCreateTime = fCreateTime;
    }

    public String getfState() {
        return fState;
    }

    public void setfState(String fState) {
        this.fState = fState;
    }

    public String getfRemark() {
        return fRemark;
    }

    public void setfRemark(String fRemark) {
        this.fRemark = fRemark;
    }

    public String getTeId() {
        return teId;
    }

    public void setTeId(String teId) {
        this.teId = teId;
    }
}
