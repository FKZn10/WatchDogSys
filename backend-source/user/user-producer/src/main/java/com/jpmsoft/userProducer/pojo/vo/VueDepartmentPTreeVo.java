package com.jpmsoft.userProducer.pojo.vo;

import java.io.Serializable;
import java.util.List;

public class VueDepartmentPTreeVo implements Serializable {
    private static final long serialVersionUID = 9120197381505467981L;
    //节点id
    public String key;
    //节点名称
    public String title;
    //值(vue赋值要有)
    public String value;
    //上级结点id
    public String pid;
    public String pdId;
    public String pId;
    public String pdName;
    public String pdCode;
    public String pdStatus;
    public String pdParentid;
    public String pdPath;
    public String pdType;
    public String pdMdmNo;
    public String pdRemark;
    public Integer pdSort;
    public String pdSolution;
    //子集
    public List<VueDepartmentPTreeVo> children;

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

    public String getPdId() {
        return pdId;
    }

    public void setPdId(String pdId) {
        this.pdId = pdId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getPdName() {
        return pdName;
    }

    public void setPdName(String pdName) {
        this.pdName = pdName;
    }

    public String getPdCode() {
        return pdCode;
    }

    public void setPdCode(String pdCode) {
        this.pdCode = pdCode;
    }

    public String getPdStatus() {
        return pdStatus;
    }

    public void setPdStatus(String pdStatus) {
        this.pdStatus = pdStatus;
    }

    public String getPdParentid() {
        return pdParentid;
    }

    public void setPdParentid(String pdParentid) {
        this.pdParentid = pdParentid;
    }

    public String getPdPath() {
        return pdPath;
    }

    public void setPdPath(String pdPath) {
        this.pdPath = pdPath;
    }

    public String getPdType() {
        return pdType;
    }

    public void setPdType(String pdType) {
        this.pdType = pdType;
    }

    public String getPdMdmNo() {
        return pdMdmNo;
    }

    public void setPdMdmNo(String pdMdmNo) {
        this.pdMdmNo = pdMdmNo;
    }

    public String getPdRemark() {
        return pdRemark;
    }

    public void setPdRemark(String pdRemark) {
        this.pdRemark = pdRemark;
    }

    public Integer getPdSort() {
        return pdSort;
    }

    public void setPdSort(Integer pdSort) {
        this.pdSort = pdSort;
    }

    public String getPdSolution() {
        return pdSolution;
    }

    public void setPdSolution(String pdSolution) {
        this.pdSolution = pdSolution;
    }

    public List<VueDepartmentPTreeVo> getChildren() {
        return children;
    }

    public void setChildren(List<VueDepartmentPTreeVo> children) {
        this.children = children;
    }
}
