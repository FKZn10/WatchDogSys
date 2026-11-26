package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("Sys_Table")
public class SysTable {
    @TableId(type = IdType.INPUT)
    public String tableId;
    public String tableName;
    public String tableCode;
    public String tableRemark;
    public Date tableCreatetime;
    public String tableCreateuser;
    public String tableCreateusername;
    public String tableStats;
    public String tablePk;
    public String tableClassname;
    public String tableDb;
    public String tableType;

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableCode() {
        return tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public String getTableRemark() {
        return tableRemark;
    }

    public void setTableRemark(String tableRemark) {
        this.tableRemark = tableRemark;
    }

    public Date getTableCreatetime() {
        return tableCreatetime;
    }

    public void setTableCreatetime(Date tableCreatetime) {
        this.tableCreatetime = tableCreatetime;
    }

    public String getTableCreateuser() {
        return tableCreateuser;
    }

    public void setTableCreateuser(String tableCreateuser) {
        this.tableCreateuser = tableCreateuser;
    }

    public String getTableCreateusername() {
        return tableCreateusername;
    }

    public void setTableCreateusername(String tableCreateusername) {
        this.tableCreateusername = tableCreateusername;
    }

    public String getTableStats() {
        return tableStats;
    }

    public void setTableStats(String tableStats) {
        this.tableStats = tableStats;
    }

    public String getTablePk() {
        return tablePk;
    }

    public void setTablePk(String tablePk) {
        this.tablePk = tablePk;
    }

    public String getTableClassname() {
        return tableClassname;
    }

    public void setTableClassname(String tableClassname) {
        this.tableClassname = tableClassname;
    }

    public String getTableDb() {
        return tableDb;
    }

    public void setTableDb(String tableDb) {
        this.tableDb = tableDb;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }
}
