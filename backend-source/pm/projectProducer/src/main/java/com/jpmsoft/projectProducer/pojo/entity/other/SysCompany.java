package com.jpmsoft.projectProducer.pojo.entity.other;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("SYS_Company")
public class SysCompany {

    @TableId(type = IdType.INPUT)
    private String cId;
    private String cName;
    private String cCode;
    private String cType;
    private String cCreditcode;
    private String cUnitlegalperson;
    private String cContactperson;
    private String cContactpersontel;
    private String cWebsite;
    private String cState;
    private String cMdmNo;
    private String cRemark;
    private String cSolution;
    private Date cCreatetime;
    private String cCreateuId;
    private String cCreateuName;
    private String cBusinessLicense;
    private String cQualification;
    private String cCredentials;
    private String cNameHistory;
    private String cAbbreviation;
    private String teId;

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcCode() {
        return cCode;
    }

    public void setcCode(String cCode) {
        this.cCode = cCode;
    }

    public String getcType() {
        return cType;
    }

    public void setcType(String cType) {
        this.cType = cType;
    }

    public String getcCreditcode() {
        return cCreditcode;
    }

    public void setcCreditcode(String cCreditcode) {
        this.cCreditcode = cCreditcode;
    }

    public String getcUnitlegalperson() {
        return cUnitlegalperson;
    }

    public void setcUnitlegalperson(String cUnitlegalperson) {
        this.cUnitlegalperson = cUnitlegalperson;
    }

    public String getcContactperson() {
        return cContactperson;
    }

    public void setcContactperson(String cContactperson) {
        this.cContactperson = cContactperson;
    }

    public String getcContactpersontel() {
        return cContactpersontel;
    }

    public void setcContactpersontel(String cContactpersontel) {
        this.cContactpersontel = cContactpersontel;
    }

    public String getcWebsite() {
        return cWebsite;
    }

    public void setcWebsite(String cWebsite) {
        this.cWebsite = cWebsite;
    }

    public String getcState() {
        return cState;
    }

    public void setcState(String cState) {
        this.cState = cState;
    }

    public String getcMdmNo() {
        return cMdmNo;
    }

    public void setcMdmNo(String cMdmNo) {
        this.cMdmNo = cMdmNo;
    }

    public String getcRemark() {
        return cRemark;
    }

    public void setcRemark(String cRemark) {
        this.cRemark = cRemark;
    }

    public String getcSolution() {
        return cSolution;
    }

    public void setcSolution(String cSolution) {
        this.cSolution = cSolution;
    }

    public Date getcCreatetime() {
        return cCreatetime;
    }

    public void setcCreatetime(Date cCreatetime) {
        this.cCreatetime = cCreatetime;
    }

    public String getcCreateuId() {
        return cCreateuId;
    }

    public void setcCreateuId(String cCreateuId) {
        this.cCreateuId = cCreateuId;
    }

    public String getcCreateuName() {
        return cCreateuName;
    }

    public void setcCreateuName(String cCreateuName) {
        this.cCreateuName = cCreateuName;
    }

    public String getcBusinessLicense() {
        return cBusinessLicense;
    }

    public void setcBusinessLicense(String cBusinessLicense) {
        this.cBusinessLicense = cBusinessLicense;
    }

    public String getcQualification() {
        return cQualification;
    }

    public void setcQualification(String cQualification) {
        this.cQualification = cQualification;
    }

    public String getcCredentials() {
        return cCredentials;
    }

    public void setcCredentials(String cCredentials) {
        this.cCredentials = cCredentials;
    }

    public String getcNameHistory() {
        return cNameHistory;
    }

    public void setcNameHistory(String cNameHistory) {
        this.cNameHistory = cNameHistory;
    }

    public String getcAbbreviation() {
        return cAbbreviation;
    }

    public void setcAbbreviation(String cAbbreviation) {
        this.cAbbreviation = cAbbreviation;
    }

    public String getTeId() {
        return teId;
    }

    public void setTeId(String teId) {
        this.teId = teId;
    }
}
