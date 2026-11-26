package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("SYS_LoggerInfo")
public class SysLoggerInfo {

    @TableId(type = IdType.INPUT)
    private String lId;
    private String lClientip;
    private String lServerip;
    private String lUrl;
    private String lRequesttype;
    private String lRequestmode;
    private Integer lResponsetime;
    private String lHttpstatus;
    private Date lRequesttime;
    private String lCreateuId;
    private String lCreateuName;
    private String lRequestparameter;
    private String lContent;
    private String sign;
    private String lActionEnd;
    private String lFunModule;
    private String ifId;
    @TableField(exist = false)
    private String powerName;

    @TableField(exist = false)
    private String interfaceName;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getlId() {
        return lId;
    }

    public void setlId(String lId) {
        this.lId = lId;
    }

    public String getlClientip() {
        return lClientip;
    }

    public void setlClientip(String lClientip) {
        this.lClientip = lClientip;
    }

    public String getlServerip() {
        return lServerip;
    }

    public void setlServerip(String lServerip) {
        this.lServerip = lServerip;
    }

    public String getlUrl() {
        return lUrl;
    }

    public void setlUrl(String lUrl) {
        this.lUrl = lUrl;
    }

    public String getlRequesttype() {
        return lRequesttype;
    }

    public void setlRequesttype(String lRequesttype) {
        this.lRequesttype = lRequesttype;
    }

    public String getlRequestmode() {
        return lRequestmode;
    }

    public void setlRequestmode(String lRequestmode) {
        this.lRequestmode = lRequestmode;
    }

    public Integer getlResponsetime() {
        return lResponsetime;
    }

    public void setlResponsetime(Integer lResponsetime) {
        this.lResponsetime = lResponsetime;
    }

    public String getlHttpstatus() {
        return lHttpstatus;
    }

    public void setlHttpstatus(String lHttpstatus) {
        this.lHttpstatus = lHttpstatus;
    }

    public Date getlRequesttime() {
        return lRequesttime;
    }

    public void setlRequesttime(Date lRequesttime) {
        this.lRequesttime = lRequesttime;
    }

    public String getlCreateuId() {
        return lCreateuId;
    }

    public void setlCreateuId(String lCreateuId) {
        this.lCreateuId = lCreateuId;
    }

    public String getlCreateuName() {
        return lCreateuName;
    }

    public void setlCreateuName(String lCreateuName) {
        this.lCreateuName = lCreateuName;
    }


    public String getlRequestparameter() {
        return lRequestparameter;
    }

    public void setlRequestparameter(String lRequestparameter) {
        this.lRequestparameter = lRequestparameter;
    }

    public String getlContent() {
        return lContent;
    }

    public void setlContent(String lContent) {
        this.lContent = lContent;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getlActionEnd() {
        return lActionEnd;
    }

    public void setlActionEnd(String lActionEnd) {
        this.lActionEnd = lActionEnd;
    }

    public String getlFunModule() {
        return lFunModule;
    }

    public void setlFunModule(String lFunModule) {
        this.lFunModule = lFunModule;
    }

    public String getIfId() {
        return ifId;
    }

    public void setIfId(String ifId) {
        this.ifId = ifId;
    }

    public String getPowerName() {
        return powerName;
    }

    public void setPowerName(String powerName) {
        this.powerName = powerName;
    }
}
