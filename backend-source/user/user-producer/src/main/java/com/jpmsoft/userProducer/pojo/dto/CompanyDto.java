package com.jpmsoft.userProducer.pojo.dto;

import com.jpmsoft.common.model.BaseModeRequest;

import java.io.Serializable;
import java.util.List;

public class CompanyDto extends BaseModeRequest implements Serializable {

    private static final long serialVersionUID = -7004661294828945107L;
    public String cId;
    public String cName;
    public String cCode;
    public String cState;
    public String cType;
    public String cCreditcode;
    public String cUnitlegalperson;
    public String cContactperson;
    public String cContactpersontel;
    public String cWebsite;
    public String cMdmNo;
    public String cRemark;
    public String cSolution;
    public String cCreateuId;
    public String cCreateuName;
    private String cBusinessLicense;
    private String cQualification;
    private String cCredentials;
    private String cAbbreviation;

    private List<String> payNames;

    /**
     * 是否仅展示部门单位
     */
    private boolean filterDepCpy;
    /**
     * 查询排除的单位类型
     */
    private List<String> excludeType;
    /**
     * 包含的单位类型
     */
    private List<String> containType;
    private String conId;

    private String topModule;

    private String pdCode;

    private String pId;

    private String pdType;

    private String excludeCIdStr;

    private String includeCIdStr;

    public String getPdCode() {
        return pdCode;
    }

    public void setPdCode(String pdCode) {
        this.pdCode = pdCode;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getPdType() {
        return pdType;
    }

    public void setPdType(String pdType) {
        this.pdType = pdType;
    }

    public String getExcludeCIdStr() {
        return excludeCIdStr;
    }

    public void setExcludeCIdStr(String excludeCIdStr) {
        this.excludeCIdStr = excludeCIdStr;
    }

    public String getIncludeCIdStr() {
        return includeCIdStr;
    }

    public void setIncludeCIdStr(String includeCIdStr) {
        this.includeCIdStr = includeCIdStr;
    }

    public String getTopModule() {
        return topModule;
    }

    public void setTopModule(String topModule) {
        this.topModule = topModule;
    }

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

    public String getcState() {
        return cState;
    }

    public List<String> getPayNames() {
        return payNames;
    }

    public void setPayNames(List<String> payNames) {
        this.payNames = payNames;
    }

    public void setcState(String cState) {
        this.cState = cState;
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

    public String getcAbbreviation() {
        return cAbbreviation;
    }

    public void setcAbbreviation(String cAbbreviation) {
        this.cAbbreviation = cAbbreviation;
    }

    public boolean isFilterDepCpy() {
        return filterDepCpy;
    }

    public void setFilterDepCpy(boolean filterDepCpy) {
        this.filterDepCpy = filterDepCpy;
    }

    public List<String> getExcludeType() {
        return excludeType;
    }

    public void setExcludeType(List<String> excludeType) {
        this.excludeType = excludeType;
    }

    public List<String> getContainType() {
        return containType;
    }

    public void setContainType(List<String> containType) {
        this.containType = containType;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }
}
