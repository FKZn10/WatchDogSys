package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("SYS.ALL_ALL_TABLES")
public class TableInfo {
    @TableId(type = IdType.INPUT)
    private String owner;
    private String tableName;
    private String tableCode;
    private String tableId;
    private String tableDescribe;
    private Date tableCreatetime;
    private String tabelRemark;

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

    public String getTableCode() {
        return tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public Date getTableCreatetime() {
        return tableCreatetime;
    }

    public void setTableCreatetime(Date tableCreatetime) {
        this.tableCreatetime = tableCreatetime;
    }

    public String getTabelRemark() {
        return tabelRemark;
    }

    public void setTabelRemark(String tabelRemark) {
        this.tabelRemark = tabelRemark;
    }
}
