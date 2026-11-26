package com.jpmsoft.userProducer.pojo.dto;

import java.io.Serializable;

public class TDepartmentDto implements Serializable {

    private static final long serialVersionUID = -6265396793828803816L;
    private String tdId;
    private String tdName;
    private String tdCode;
    private String tdParentId;
    private String tdSort;
    private String tdSolution;
    private String type;
    private String newtdId;
    private String newtdSort;
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

    public String getTdParentId() {
        return tdParentId;
    }

    public void setTdParentId(String tdParentId) {
        this.tdParentId = tdParentId;
    }

    public String getTdSort() {
        return tdSort;
    }

    public void setTdSort(String tdSort) {
        this.tdSort = tdSort;
    }

    public String getTdSolution() {
        return tdSolution;
    }

    public void setTdSolution(String tdSolution) {
        this.tdSolution = tdSolution;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNewtdId() {
        return newtdId;
    }

    public void setNewtdId(String newtdId) {
        this.newtdId = newtdId;
    }

    public String getNewtdSort() {
        return newtdSort;
    }

    public void setNewtdSort(String newtdSort) {
        this.newtdSort = newtdSort;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }
}
