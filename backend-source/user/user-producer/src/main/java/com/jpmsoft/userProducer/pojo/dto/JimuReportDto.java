package com.jpmsoft.userProducer.pojo.dto;

import com.jpmsoft.userProducer.pojo.entity.*;

import java.io.Serializable;
import java.util.List;

public class JimuReportDto  implements Serializable {

    List<JimuReport> JimuReport;
    List<JimuReportDataSource> JimuReportDataSource;
    List<JimuReportDb> JimuReportDb;
    List<JimuReportDbField> JimuReportDbField;
    List<JimuReportDbParam> JimuReportDbParam;
    List<JimuDict> JimuDict;
    List<JimuDictItem> JimuDictItem;

    public List<com.jpmsoft.userProducer.pojo.entity.JimuReport> getJimuReport() {
        return JimuReport;
    }

    public void setJimuReport(List<com.jpmsoft.userProducer.pojo.entity.JimuReport> jimuReport) {
        JimuReport = jimuReport;
    }

    public List<com.jpmsoft.userProducer.pojo.entity.JimuReportDataSource> getJimuReportDataSource() {
        return JimuReportDataSource;
    }

    public void setJimuReportDataSource(List<com.jpmsoft.userProducer.pojo.entity.JimuReportDataSource> jimuReportDataSource) {
        JimuReportDataSource = jimuReportDataSource;
    }

    public List<com.jpmsoft.userProducer.pojo.entity.JimuReportDb> getJimuReportDb() {
        return JimuReportDb;
    }

    public void setJimuReportDb(List<com.jpmsoft.userProducer.pojo.entity.JimuReportDb> jimuReportDb) {
        JimuReportDb = jimuReportDb;
    }

    public List<com.jpmsoft.userProducer.pojo.entity.JimuReportDbField> getJimuReportDbField() {
        return JimuReportDbField;
    }

    public void setJimuReportDbField(List<com.jpmsoft.userProducer.pojo.entity.JimuReportDbField> jimuReportDbField) {
        JimuReportDbField = jimuReportDbField;
    }

    public List<com.jpmsoft.userProducer.pojo.entity.JimuReportDbParam> getJimuReportDbParam() {
        return JimuReportDbParam;
    }

    public void setJimuReportDbParam(List<com.jpmsoft.userProducer.pojo.entity.JimuReportDbParam> jimuReportDbParam) {
        JimuReportDbParam = jimuReportDbParam;
    }

    public List<com.jpmsoft.userProducer.pojo.entity.JimuDict> getJimuDict() {
        return JimuDict;
    }

    public void setJimuDict(List<com.jpmsoft.userProducer.pojo.entity.JimuDict> jimuDict) {
        JimuDict = jimuDict;
    }

    public List<com.jpmsoft.userProducer.pojo.entity.JimuDictItem> getJimuDictItem() {
        return JimuDictItem;
    }

    public void setJimuDictItem(List<com.jpmsoft.userProducer.pojo.entity.JimuDictItem> jimuDictItem) {
        JimuDictItem = jimuDictItem;
    }
}
