package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("jimu_report_db")
public class JimuReportDb {
    @TableId(type = IdType.INPUT)
    private String id;
    private String jimuReportId;
    private String createBy;
    private String updateBy;
    private Date createTime;
    private Date updateTime;
    private String dbCode;
    private String dbChName;
    private String dbType;
    private String dbTableName;
    private String dbDynSql;
    private String dbKey;
    private String tbDbKey;
    private String tbDbTableName;
    private String javaType;
    private String javaValue;
    private String apiUrl;
    private String apiMethod;
    private String isList;
    private String isPage;
    private String dbSource;
    private String dbSourceType;
    private String jsonData;
    private String apiConvert;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJimuReportId() {
        return jimuReportId;
    }

    public void setJimuReportId(String jimuReportId) {
        this.jimuReportId = jimuReportId;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDbCode() {
        return dbCode;
    }

    public void setDbCode(String dbCode) {
        this.dbCode = dbCode;
    }

    public String getDbChName() {
        return dbChName;
    }

    public void setDbChName(String dbChName) {
        this.dbChName = dbChName;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getDbTableName() {
        return dbTableName;
    }

    public void setDbTableName(String dbTableName) {
        this.dbTableName = dbTableName;
    }

    public String getDbDynSql() {
        return dbDynSql;
    }

    public void setDbDynSql(String dbDynSql) {
        this.dbDynSql = dbDynSql;
    }

    public String getDbKey() {
        return dbKey;
    }

    public void setDbKey(String dbKey) {
        this.dbKey = dbKey;
    }

    public String getTbDbKey() {
        return tbDbKey;
    }

    public void setTbDbKey(String tbDbKey) {
        this.tbDbKey = tbDbKey;
    }

    public String getTbDbTableName() {
        return tbDbTableName;
    }

    public void setTbDbTableName(String tbDbTableName) {
        this.tbDbTableName = tbDbTableName;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public String getJavaValue() {
        return javaValue;
    }

    public void setJavaValue(String javaValue) {
        this.javaValue = javaValue;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getApiMethod() {
        return apiMethod;
    }

    public void setApiMethod(String apiMethod) {
        this.apiMethod = apiMethod;
    }

    public String getIsList() {
        return isList;
    }

    public void setIsList(String isList) {
        this.isList = isList;
    }

    public String getIsPage() {
        return isPage;
    }

    public void setIsPage(String isPage) {
        this.isPage = isPage;
    }

    public String getDbSource() {
        return dbSource;
    }

    public void setDbSource(String dbSource) {
        this.dbSource = dbSource;
    }

    public String getDbSourceType() {
        return dbSourceType;
    }

    public void setDbSourceType(String dbSourceType) {
        this.dbSourceType = dbSourceType;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    public String getApiConvert() {
        return apiConvert;
    }

    public void setApiConvert(String apiConvert) {
        this.apiConvert = apiConvert;
    }
}
