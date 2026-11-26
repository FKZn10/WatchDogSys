package com.jpmsoft.userProducer.pojo.vo;

import java.io.Serializable;
import java.util.Date;

public class TableInfoVo implements Serializable {
    private static final long serialVersionUID = -8291956922388056960L;
    private  String  owner;
    private  String tableName;
    private  String tableId;
    private  String tableDescribe;
    private Date tableCreatetime;
    private  String tableCode;
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getTableDescribe() {
        return tableDescribe;
    }

    public void setTableDescribe(String tableDescribe) {
        this.tableDescribe = tableDescribe;
    }

    public Date getTableCreatetime() {
        return tableCreatetime;
    }

    public void setTableCreatetime(Date tableCreatetime) {
        this.tableCreatetime = tableCreatetime;
    }

    public String getTableCode() {
        return tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }
}
