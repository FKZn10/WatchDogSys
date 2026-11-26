package com.jpmsoft.userProducer.pojo.dto;

import java.io.Serializable;

public class ExlTableColumnDto implements Serializable {
    private static final long serialVersionUID = -1042014801518136040L;
    private  String tableId;
    private  String tablename;
    private String columnCode;
    private String columnType;
    private String columnName;
    private String columnNull;
    private String columnPk;
    private String dataLength;
    private String dataType;
    private Integer dataPrecision;
    private Integer dataScale;
    private String tableCode;
    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public String getColumnCode() {
        return columnCode;
    }

    public void setColumnCode(String columnCode) {
        this.columnCode = columnCode;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDataLength() {
        return dataLength;
    }

    public void setDataLength(String dataLength) {
        this.dataLength = dataLength;
    }

    public Integer getDataPrecision() {
        return dataPrecision;
    }

    public void setDataPrecision(Integer dataPrecision) {
        this.dataPrecision = dataPrecision;
    }

    public Integer getDataScale() {
        return dataScale;
    }

    public void setDataScale(Integer dataScale) {
        this.dataScale = dataScale;
    }

    public String getColumnNull() {
        return columnNull;
    }

    public void setColumnNull(String columnNull) {
        this.columnNull = columnNull;
    }

    public String getColumnPk() {
        return columnPk;
    }

    public void setColumnPk(String columnPk) {
        this.columnPk = columnPk;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getTableCode() {
        return tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }
}
