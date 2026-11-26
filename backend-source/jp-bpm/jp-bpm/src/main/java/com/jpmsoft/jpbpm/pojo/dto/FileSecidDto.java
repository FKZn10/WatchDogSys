package com.jpmsoft.jpbpm.pojo.dto;

import java.util.List;


public class FileSecidDto {
    private String Secid;
    private List<String> secIds;
    private String pId;
    private String conId;
    private List<String> Code;

    private Object nowobj;
    public String year;

    public List<String> getSecIds() {
        return secIds;
    }

    public void setSecIds(List<String> secIds) {
        this.secIds = secIds;
    }

    public Object getNowobj() {
        return nowobj;
    }

    public void setNowobj(Object nowobj) {
        this.nowobj = nowobj;
    }

    public String getSecid() {
        return Secid;
    }

    public void setSecid(String secid) {
        Secid = secid;
    }

    public List<String> getCode() {
        return Code;
    }

    public void setCode(List<String> code) {
        Code = code;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
