package com.jpmsoft.userProducer.pojo.dto;

import java.io.Serializable;

public class SelectTableDto implements Serializable {

    private static final long serialVersionUID = -7571244590440888559L;
    private String tableName;
    private String tableCode;
    private String tablePk;
    private String valueStr;
    private String owner;
    private Integer start;
    private Integer size;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getTableCode() {
        return tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTablePk() {
        return tablePk;
    }

    public void setTablePk(String tablePk) {
        this.tablePk = tablePk;
    }

    public String getValueStr() {
        return valueStr;
    }

    public void setValueStr(String valueStr) {
        this.valueStr = valueStr;
    }
}
