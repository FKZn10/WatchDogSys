package com.jpmsoft.gmgws.pojo.vo;


import java.io.Serializable;
import java.util.Date;


public class SysUserDepartmentVo implements Serializable {


    private static final long serialVersionUID = -6209612713947414329L;
    private String dId;
    private String dName;
    private String dCode;
    private String dStatus;
    private String dParentid;
    private String dPath;
    private String dType;
    private String dMdmNo;
    private String dSolution;
    private String dRemark;
    private Integer dSort;
    private Date dCreatetime;
    private String dCreateuId;
    private String dCreateuName;
    private String dProjectAuth;
    private Integer dHierarchy;
    private String dCompanyId;
    private String dCompanyName;

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

    public String getdCode() {
        return dCode;
    }

    public void setdCode(String dCode) {
        this.dCode = dCode;
    }

    public String getdStatus() {
        return dStatus;
    }

    public void setdStatus(String dStatus) {
        this.dStatus = dStatus;
    }

    public String getdParentid() {
        return dParentid;
    }

    public void setdParentid(String dParentid) {
        this.dParentid = dParentid;
    }

    public String getdPath() {
        return dPath;
    }

    public void setdPath(String dPath) {
        this.dPath = dPath;
    }

    public String getdType() {
        return dType;
    }

    public void setdType(String dType) {
        this.dType = dType;
    }

    public String getdMdmNo() {
        return dMdmNo;
    }

    public void setdMdmNo(String dMdmNo) {
        this.dMdmNo = dMdmNo;
    }

    public String getdSolution() {
        return dSolution;
    }

    public void setdSolution(String dSolution) {
        this.dSolution = dSolution;
    }

    public String getdRemark() {
        return dRemark;
    }

    public void setdRemark(String dRemark) {
        this.dRemark = dRemark;
    }

    public Integer getdSort() {
        return dSort;
    }

    public void setdSort(Integer dSort) {
        this.dSort = dSort;
    }

    public Date getdCreatetime() {
        return dCreatetime;
    }

    public void setdCreatetime(Date dCreatetime) {
        this.dCreatetime = dCreatetime;
    }

    public String getdCreateuId() {
        return dCreateuId;
    }

    public void setdCreateuId(String dCreateuId) {
        this.dCreateuId = dCreateuId;
    }

    public String getdCreateuName() {
        return dCreateuName;
    }

    public void setdCreateuName(String dCreateuName) {
        this.dCreateuName = dCreateuName;
    }

    public String getdProjectAuth() {
        return dProjectAuth;
    }

    public void setdProjectAuth(String dProjectAuth) {
        this.dProjectAuth = dProjectAuth;
    }

    public Integer getdHierarchy() {
        return dHierarchy;
    }

    public void setdHierarchy(Integer dHierarchy) {
        this.dHierarchy = dHierarchy;
    }

    public String getdCompanyId() {
        return dCompanyId;
    }

    public void setdCompanyId(String dCompanyId) {
        this.dCompanyId = dCompanyId;
    }

    public String getdCompanyName() {
        return dCompanyName;
    }

    public void setdCompanyName(String dCompanyName) {
        this.dCompanyName = dCompanyName;
    }
}
