package com.jpmsoft.userProducer.pojo.vo;

import java.io.Serializable;
import java.util.List;

public class VueTDepartmentTreeVo implements Serializable {
    private static final long serialVersionUID = -4652462234841901256L;
    // 节点id
    public String key;
    // 节点名称
    public String title;
    // 值(vue赋值要有)
    public String value;
    // 上级结点id
    public String pid;
    public String tdId;
    public String tdName;
    public String tdCode;
    public String tdParentid;
    public Integer tdSort;
    public String tdSolution;

    public String tdType;
    // 子集
    public List<VueTDepartmentTreeVo> children;

    public String getTdType() {
        return tdType;
    }

    public void setTdType(String tdType) {
        this.tdType = tdType;
    }

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

    public String getTdId() {
        return tdId;
    }

    public void setTdId(String tdId) {
        this.tdId = tdId;
    }

    public String getTdName() {
        return tdName;
    }

    public void setTdName(String tdName) {
        this.tdName = tdName;
    }

    public String getTdCode() {
        return tdCode;
    }

    public void setTdCode(String tdCode) {
        this.tdCode = tdCode;
    }

    public String getTdParentid() {
        return tdParentid;
    }

    public void setTdParentid(String tdParentid) {
        this.tdParentid = tdParentid;
    }

    public Integer getTdSort() {
        return tdSort;
    }

    public void setTdSort(Integer tdSort) {
        this.tdSort = tdSort;
    }

    public String getTdSolution() {
        return tdSolution;
    }

    public void setTdSolution(String tdSolution) {
        this.tdSolution = tdSolution;
    }

    public List<VueTDepartmentTreeVo> getChildren() {
        return children;
    }

    public void setChildren(List<VueTDepartmentTreeVo> children) {
        this.children = children;
    }
}
