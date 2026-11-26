package com.jpmsoft.userProducer.pojo.dto;

import com.jpmsoft.common.model.BaseModeRequest;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SysLoggerInfoDto extends BaseModeRequest implements Serializable {


    private static final long serialVersionUID = 1395985640454314929L;
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
    public String lCreateuName;
    public Object lRequestparameter;
    public Object lContent;
    private String lActionEnd;
    private String lFunModule;
    private String ifId;
    /**
     * 非200响应值
     */
    private boolean unSuccess;
    private List<String> lRequesttimes;


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

    public Object getlRequestparameter() {
        return lRequestparameter;
    }

    public void setlRequestparameter(Object lRequestparameter) {
        this.lRequestparameter = lRequestparameter;
    }

    public Object getlContent() {
        return lContent;
    }

    public void setlContent(Object lContent) {
        this.lContent = lContent;
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

    public boolean isUnSuccess() {
        return unSuccess;
    }

    public void setUnSuccess(boolean unSuccess) {
        this.unSuccess = unSuccess;
    }

    public List<String> getlRequesttimes() {
        return lRequesttimes;
    }

    public void setlRequesttimes(List<String> lRequesttimes) {
        this.lRequesttimes = lRequesttimes;
    }
}
