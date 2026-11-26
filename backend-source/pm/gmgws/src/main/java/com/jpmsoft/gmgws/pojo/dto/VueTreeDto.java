package com.jpmsoft.gmgws.pojo.dto;

import java.io.Serializable;
import java.util.List;

public class VueTreeDto implements Serializable {

    private static final long serialVersionUID = -8305855897212928148L;
    private String key;
    private String title;
    private String value;
    private String pid;
    private String dCode;
    private Integer dSort;
    private String dRemark;
    private String dStatus;
    private String dPath;
    private String dType;
    private String dMdmNo;
    private String dSolution;
    private String dProjectAuth;
    private String dCompanyId;
    private String dCompanyName;
    private List<VueTreeDto> children;


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getdCode() {
        return dCode;
    }

    public void setdCode(String dCode) {
        this.dCode = dCode;
    }

    public Integer getdSort() {
        return dSort;
    }

    public void setdSort(Integer dSort) {
        this.dSort = dSort;
    }

    public String getdRemark() {
        return dRemark;
    }

    public void setdRemark(String dRemark) {
        this.dRemark = dRemark;
    }

    public String getdStatus() {
        return dStatus;
    }

    public void setdStatus(String dStatus) {
        this.dStatus = dStatus;
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

    public String getdProjectAuth() {
        return dProjectAuth;
    }

    public void setdProjectAuth(String dProjectAuth) {
        this.dProjectAuth = dProjectAuth;
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

    public List<VueTreeDto> getChildren() {
        return children;
    }

    public void setChildren(List<VueTreeDto> children) {
        this.children = children;
    }
}
