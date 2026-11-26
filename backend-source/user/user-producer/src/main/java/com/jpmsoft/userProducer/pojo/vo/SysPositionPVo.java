package com.jpmsoft.userProducer.pojo.vo;


import java.io.Serializable;
import java.util.Date;

public class SysPositionPVo implements Serializable {

    private static final long serialVersionUID = -185425261172296804L;
    private String ppId;
    private String pdId;
    private String ppName;
    private String ppCode;
    private String ppRemark;
    private Integer ppSort;
    private String ppStatus;
    private String ppMdmNo;
    private Date ppCreatetime;
    private String ppCreateuId;
    private String ppCreateuName;
    private String pdName;
    private String pId;

    public String getPpId() {
        return ppId;
    }

    public void setPpId(String ppId) {
        this.ppId = ppId;
    }

    public String getPdId() {
        return pdId;
    }

    public void setPdId(String pdId) {
        this.pdId = pdId;
    }

    public String getPpName() {
        return ppName;
    }

    public void setPpName(String ppName) {
        this.ppName = ppName;
    }

    public String getPpCode() {
        return ppCode;
    }

    public void setPpCode(String ppCode) {
        this.ppCode = ppCode;
    }

    public String getPpRemark() {
        return ppRemark;
    }

    public void setPpRemark(String ppRemark) {
        this.ppRemark = ppRemark;
    }

    public Integer getPpSort() {
        return ppSort;
    }

    public void setPpSort(Integer ppSort) {
        this.ppSort = ppSort;
    }

    public String getPpStatus() {
        return ppStatus;
    }

    public void setPpStatus(String ppStatus) {
        this.ppStatus = ppStatus;
    }

    public String getPpMdmNo() {
        return ppMdmNo;
    }

    public void setPpMdmNo(String ppMdmNo) {
        this.ppMdmNo = ppMdmNo;
    }

    public Date getPpCreatetime() {
        return ppCreatetime;
    }

    public void setPpCreatetime(Date ppCreatetime) {
        this.ppCreatetime = ppCreatetime;
    }

    public String getPpCreateuId() {
        return ppCreateuId;
    }

    public void setPpCreateuId(String ppCreateuId) {
        this.ppCreateuId = ppCreateuId;
    }

    public String getPpCreateuName() {
        return ppCreateuName;
    }

    public void setPpCreateuName(String ppCreateuName) {
        this.ppCreateuName = ppCreateuName;
    }

    public String getPdName() {
        return pdName;
    }

    public void setPdName(String pdName) {
        this.pdName = pdName;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }
}
