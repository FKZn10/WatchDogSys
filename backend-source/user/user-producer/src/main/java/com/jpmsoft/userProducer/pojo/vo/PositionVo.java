package com.jpmsoft.userProducer.pojo.vo;

import java.io.Serializable;

public class PositionVo implements Serializable {
    private static final long serialVersionUID = -8354686507766796799L;
    public String pId;
    public String pName;
    public String code;
    public String dId;
    public String dName;

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getdId() {
        return dId;
    }

    public void setdId(String dId) {
        this.dId = dId;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }
}
