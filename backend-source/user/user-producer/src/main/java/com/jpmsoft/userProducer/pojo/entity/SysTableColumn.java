package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("Sys_TableColumn")
public class SysTableColumn {
    @TableId(type = IdType.INPUT)
    public String columnId;
    public String tableId;
    public String columnName;
    public String columnCode;
    public String columnType;
    public String columnRemark;
    public String columnNull;
    public String columnPk;
    public Integer columnLength;
    public String columnCsharptype;
    public String columnCsharpcode;
    public String columnFormvisible;
    public String columnFormtype;
    public Date columnTime;
    public Integer columnNumber;
    public String columnJavaSmallHump;
    private String tableCode;
    /**
     * 是否是附件
     */
    private Boolean columnFile;
    /**
     * 1 普通字段
     * 2 链表字段
     * 3 编码字段
     */
    private Integer filterType;
    /**
     * 关联的表编码或者字典编码
     */
    private String assocTableCode;
    /**
     * 关联为表编码时 其导入导出对应的字段编码
     */
    private String assocColumnCode;
    /**
     * 关联为表编码时其关联表的主键ID编码
     */
    private String assocColumnPk;
    /**
     *关联为表编码时 额外导出字段编码
     */
    private String assocShowCode;
    /**
     * 关联为表编码时 额外导出字段名称
     */
    private String assocShowName;
    /**
     * 导入时 是否唯一校验
     */
    private Boolean columnOnly;
    /**
     * 导入时 是否导入列
     */
    private Boolean columnExp;
    /**
     * 导出时 是否导出列
     */
    private Boolean columnImp;

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
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

    public String getColumnRemark() {
        return columnRemark;
    }

    public void setColumnRemark(String columnRemark) {
        this.columnRemark = columnRemark;
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

    public Integer getColumnLength() {
        return columnLength;
    }

    public void setColumnLength(Integer columnLength) {
        this.columnLength = columnLength;
    }

    public String getColumnCsharptype() {
        return columnCsharptype;
    }

    public void setColumnCsharptype(String columnCsharptype) {
        this.columnCsharptype = columnCsharptype;
    }

    public String getColumnCsharpcode() {
        return columnCsharpcode;
    }

    public void setColumnCsharpcode(String columnCsharpcode) {
        this.columnCsharpcode = columnCsharpcode;
    }

    public String getColumnFormvisible() {
        return columnFormvisible;
    }

    public void setColumnFormvisible(String columnFormvisible) {
        this.columnFormvisible = columnFormvisible;
    }

    public String getColumnFormtype() {
        return columnFormtype;
    }

    public void setColumnFormtype(String columnFormtype) {
        this.columnFormtype = columnFormtype;
    }

    public Date getColumnTime() {
        return columnTime;
    }

    public void setColumnTime(Date columnTime) {
        this.columnTime = columnTime;
    }

    public Integer getColumnNumber() {
        return columnNumber;
    }

    public void setColumnNumber(Integer columnNumber) {
        this.columnNumber = columnNumber;
    }

    public String getColumnJavaSmallHump() {
        return columnJavaSmallHump;
    }

    public void setColumnJavaSmallHump(String columnJavaSmallHump) {
        this.columnJavaSmallHump = columnJavaSmallHump;
    }

    public String getTableCode() {
        return tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public Boolean getColumnFile() {
        return columnFile;
    }

    public void setColumnFile(Boolean columnFile) {
        this.columnFile = columnFile;
    }

    public Integer getFilterType() {
        return filterType;
    }

    public void setFilterType(Integer filterType) {
        this.filterType = filterType;
    }

    public String getAssocTableCode() {
        return assocTableCode;
    }

    public void setAssocTableCode(String assocTableCode) {
        this.assocTableCode = assocTableCode;
    }

    public String getAssocColumnCode() {
        return assocColumnCode;
    }

    public void setAssocColumnCode(String assocColumnCode) {
        this.assocColumnCode = assocColumnCode;
    }

    public String getAssocColumnPk() {
        return assocColumnPk;
    }

    public void setAssocColumnPk(String assocColumnPk) {
        this.assocColumnPk = assocColumnPk;
    }

    public String getAssocShowCode() {
        return assocShowCode;
    }

    public void setAssocShowCode(String assocShowCode) {
        this.assocShowCode = assocShowCode;
    }

    public String getAssocShowName() {
        return assocShowName;
    }

    public void setAssocShowName(String assocShowName) {
        this.assocShowName = assocShowName;
    }

    public Boolean getColumnOnly() {
        return columnOnly;
    }

    public void setColumnOnly(Boolean columnOnly) {
        this.columnOnly = columnOnly;
    }

    public Boolean getColumnExp() {
        return columnExp;
    }

    public void setColumnExp(Boolean columnExp) {
        this.columnExp = columnExp;
    }

    public Boolean getColumnImp() {
        return columnImp;
    }

    public void setColumnImp(Boolean columnImp) {
        this.columnImp = columnImp;
    }
}
