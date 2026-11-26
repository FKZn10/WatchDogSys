package com.jpmsoft.userProducer.pojo.vo;

import java.io.Serializable;
import java.util.Date;

public class SysPositionVo implements Serializable {

    private static final long serialVersionUID = 7871813191652298900L;
    private String pId;
    private String dId;
    private String pName;
    private String pCode;
    private String pRemark;
    private Integer pSort;
    private String pStatus;
    private String pMdmNo;
    private Date pCreatetime;
    private String pCreateuId;
    private String pCreateuName;
    private String dName;
    public Boolean pDuty;

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getdId() {
        return dId;
    }

    public void setdId(String dId) {
        this.dId = dId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpCode() {
        return pCode;
    }

    public void setpCode(String pCode) {
        this.pCode = pCode;
    }

    public String getpRemark() {
        return pRemark;
    }

    public void setpRemark(String pRemark) {
        this.pRemark = pRemark;
    }

    public Integer getpSort() {
        return pSort;
    }

    public void setpSort(Integer pSort) {
        this.pSort = pSort;
    }

    public String getpStatus() {
        return pStatus;
    }

    public void setpStatus(String pStatus) {
        this.pStatus = pStatus;
    }

    public String getpMdmNo() {
        return pMdmNo;
    }

    public void setpMdmNo(String pMdmNo) {
        this.pMdmNo = pMdmNo;
    }

    public Date getpCreatetime() {
        return pCreatetime;
    }

    public void setpCreatetime(Date pCreatetime) {
        this.pCreatetime = pCreatetime;
    }

    public String getpCreateuId() {
        return pCreateuId;
    }

    public void setpCreateuId(String pCreateuId) {
        this.pCreateuId = pCreateuId;
    }

    public String getpCreateuName() {
        return pCreateuName;
    }

    public void setpCreateuName(String pCreateuName) {
        this.pCreateuName = pCreateuName;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }

    public Boolean getpDuty() {
        return pDuty;
    }

    public void setpDuty(Boolean pDuty) {
        this.pDuty = pDuty;
    }
}
