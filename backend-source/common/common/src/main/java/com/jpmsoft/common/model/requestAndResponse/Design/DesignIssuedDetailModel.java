package com.jpmsoft.common.model.requestAndResponse.Design;

import java.util.Date;

public class DesignIssuedDetailModel {

    private String id; // 主键

    private String didUnitId; //单位id

    private String didUnitName; // 单位名称

    private String didUid;  // 用户id

    private String didUname; // 用户名称

    private Integer didPaperNum;  // 纸质价数


    private Integer didElectron;  //电子版

    private Integer didNumber;   // 序号

    // 01 - 参见单位   02  -  其他单位
    private String didType;

    private String diId;

    private Date createTime;

    // 标识是否下发代办 03 - 下发  其他不下发，此字段不存入数据库
    private String didIsIssuedTask;

    public String getDidIsIssuedTask() {
        return didIsIssuedTask;
    }

    public void setDidIsIssuedTask(String didIsIssuedTask) {
        this.didIsIssuedTask = didIsIssuedTask;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDidUname() {
        return didUname;
    }

    public void setDidUname(String didUname) {
        this.didUname = didUname;
    }

    public String getDiId() {
        return diId;
    }
    public void setDiId(String diId) {
        this.diId = diId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDidUnitId() {
        return didUnitId;
    }

    public void setDidUnitId(String didUnitId) {
        this.didUnitId = didUnitId;
    }

    public String getDidUnitName() {
        return didUnitName;
    }

    public void setDidUnitName(String didUnitName) {
        this.didUnitName = didUnitName;
    }

    public String getDidUid() {
        return didUid;
    }

    public void setDidUid(String didUid) {
        this.didUid = didUid;
    }

    public Integer getDidPaperNum() {
        return didPaperNum;
    }

    public void setDidPaperNum(Integer didPaperNum) {
        this.didPaperNum = didPaperNum;
    }

    public Integer getDidElectron() {
        return didElectron;
    }

    public void setDidElectron(Integer didElectron) {
        this.didElectron = didElectron;
    }

    public Integer getDidNumber() {
        return didNumber;
    }

    public void setDidNumber(Integer didNumber) {
        this.didNumber = didNumber;
    }

    public String getDidType() {
        return didType;
    }

    public void setDidType(String didType) {
        this.didType = didType;
    }
}
