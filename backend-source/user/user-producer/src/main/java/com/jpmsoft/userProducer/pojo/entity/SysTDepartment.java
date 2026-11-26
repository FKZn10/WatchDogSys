package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("SYS_T_Department")
public class SysTDepartment {

    @TableId(type = IdType.INPUT)
    private String tdId;
    private String tdName;
    private String tdCode;
    private String tdParentid;
    private Integer tdSort;
    private String tdSolution;
    private String toId;

    private String tdType;

    public String getTdType() {
        return tdType;
    }

    public void setTdType(String tdType) {
        this.tdType = tdType;
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

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }
}
