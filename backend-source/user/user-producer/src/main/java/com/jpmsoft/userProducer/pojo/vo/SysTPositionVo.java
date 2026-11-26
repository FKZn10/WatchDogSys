package com.jpmsoft.userProducer.pojo.vo;

import java.io.Serializable;

public class SysTPositionVo implements Serializable {

    private static final long serialVersionUID = 6767123522455308243L;
    private String tpId;
    private String tdId;
    private String tpName;
    private String tpCode;
    private String tpRemark;
    private Integer tpSort;
    private String tpStatus;
    private String tdName;

    public String getTpId() {
        return tpId;
    }

    public void setTpId(String tpId) {
        this.tpId = tpId;
    }

    public String getTdId() {
        return tdId;
    }

    public void setTdId(String tdId) {
        this.tdId = tdId;
    }

    public String getTpName() {
        return tpName;
    }

    public void setTpName(String tpName) {
        this.tpName = tpName;
    }

    public String getTpCode() {
        return tpCode;
    }

    public void setTpCode(String tpCode) {
        this.tpCode = tpCode;
    }

    public String getTpRemark() {
        return tpRemark;
    }

    public void setTpRemark(String tpRemark) {
        this.tpRemark = tpRemark;
    }

    public Integer getTpSort() {
        return tpSort;
    }

    public void setTpSort(Integer tpSort) {
        this.tpSort = tpSort;
    }

    public String getTpStatus() {
        return tpStatus;
    }

    public void setTpStatus(String tpStatus) {
        this.tpStatus = tpStatus;
    }

    public String getTdName() {
        return tdName;
    }

    public void setTdName(String tdName) {
        this.tdName = tdName;
    }
}
