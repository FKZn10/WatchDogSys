package com.jpmsoft.userProducer.pojo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * 系统版本更新记录
 */
@TableName("SYS_Version")
public class SysVersion {

    @TableId(type = IdType.INPUT)
    private String vId;
    private String vName;
    private String vCode;
    private Date vDate;
    private String vContent;
    private String vStep;
    private String vInvolve;
    private String vPackage;
    private String vSql;
    private String vRequirements;
    private String vTestreport;
    private String vStepdoc;
    private String vOtherdoc;
    private String vCreateUid;
    private String vCreateUname;
    private Date vCreateTime;
    private Integer vState;


    public String getvId() {
        return vId;
    }

    public void setvId(String vId) {
        this.vId = vId;
    }

    public String getvName() {
        return vName;
    }

    public void setvName(String vName) {
        this.vName = vName;
    }

    public String getvCode() {
        return vCode;
    }

    public void setvCode(String vCode) {
        this.vCode = vCode;
    }

    public Date getvDate() {
        return vDate;
    }

    public void setvDate(Date vDate) {
        this.vDate = vDate;
    }

    public String getvContent() {
        return vContent;
    }

    public void setvContent(String vContent) {
        this.vContent = vContent;
    }

    public String getvStep() {
        return vStep;
    }

    public void setvStep(String vStep) {
        this.vStep = vStep;
    }

    public String getvInvolve() {
        return vInvolve;
    }

    public void setvInvolve(String vInvolve) {
        this.vInvolve = vInvolve;
    }

    public String getvPackage() {
        return vPackage;
    }

    public void setvPackage(String vPackage) {
        this.vPackage = vPackage;
    }

    public String getvSql() {
        return vSql;
    }

    public void setvSql(String vSql) {
        this.vSql = vSql;
    }

    public String getvRequirements() {
        return vRequirements;
    }

    public void setvRequirements(String vRequirements) {
        this.vRequirements = vRequirements;
    }

    public String getvTestreport() {
        return vTestreport;
    }

    public void setvTestreport(String vTestreport) {
        this.vTestreport = vTestreport;
    }

    public String getvStepdoc() {
        return vStepdoc;
    }

    public void setvStepdoc(String vStepdoc) {
        this.vStepdoc = vStepdoc;
    }

    public String getvOtherdoc() {
        return vOtherdoc;
    }

    public void setvOtherdoc(String vOtherdoc) {
        this.vOtherdoc = vOtherdoc;
    }

    public String getvCreateUid() {
        return vCreateUid;
    }

    public void setvCreateUid(String vCreateUid) {
        this.vCreateUid = vCreateUid;
    }

    public String getvCreateUname() {
        return vCreateUname;
    }

    public void setvCreateUname(String vCreateUname) {
        this.vCreateUname = vCreateUname;
    }

    public Date getvCreateTime() {
        return vCreateTime;
    }

    public void setvCreateTime(Date vCreateTime) {
        this.vCreateTime = vCreateTime;
    }

    public Integer getvState() {
        return vState;
    }

    public void setvState(Integer vState) {
        this.vState = vState;
    }
}
