package com.jpmsoft.userProducer.pojo.dto;


import com.jpmsoft.common.model.BaseFileRequest;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统版本更新记录
 */
public class SysVersionDto extends BaseFileRequest  implements Serializable {

    private static final long serialVersionUID = 3594223161489414709L;
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


    private String vPackageIds;
    private String vSqlIds;
    private String vRequirementsIds;
    private String vTestreportIds;
    private String vStepdocIds;
    private String vOtherdocIds;

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

    public String getvPackageIds() {
        return vPackageIds;
    }

    public void setvPackageIds(String vPackageIds) {
        this.vPackageIds = vPackageIds;
    }

    public String getvSqlIds() {
        return vSqlIds;
    }

    public void setvSqlIds(String vSqlIds) {
        this.vSqlIds = vSqlIds;
    }

    public String getvRequirementsIds() {
        return vRequirementsIds;
    }

    public void setvRequirementsIds(String vRequirementsIds) {
        this.vRequirementsIds = vRequirementsIds;
    }

    public String getvTestreportIds() {
        return vTestreportIds;
    }

    public void setvTestreportIds(String vTestreportIds) {
        this.vTestreportIds = vTestreportIds;
    }

    public String getvStepdocIds() {
        return vStepdocIds;
    }

    public void setvStepdocIds(String vStepdocIds) {
        this.vStepdocIds = vStepdocIds;
    }

    public String getvOtherdocIds() {
        return vOtherdocIds;
    }

    public void setvOtherdocIds(String vOtherdocIds) {
        this.vOtherdocIds = vOtherdocIds;
    }
}
