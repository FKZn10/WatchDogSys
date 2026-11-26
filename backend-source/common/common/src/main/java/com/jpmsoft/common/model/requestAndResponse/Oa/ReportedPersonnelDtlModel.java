package com.jpmsoft.common.model.requestAndResponse.Oa;

import java.io.Serializable;

/**
 * 项目人员报备详情
 */
public class ReportedPersonnelDtlModel implements Serializable {

    private static final long serialVersionUID = 3312655131739571244L;
    private String rpdId;
    private String rpId;
    private String rpdPdid;
    private String rpdPpid;
    private String rpdUid;
    private String rpdType;
    private String rpdName;
    private String rpdNumber;
    private String rpdTel;
    private Integer rpdSort;
    private String rpdCpy;
    private String rpdQualification;

    private String rpdQualificationIds;
    private String pdName;
    private String ppName;
    private String ppCode;
    private String uName;
    private String uMtel;
    private String uIdcard;
    private String cName;
    private String cAbbName;
    private String pId;
    private String pupId;
    private Boolean rpdAdd;
    private String ownerUnit;
    private String ownerUnitName;

    private String rpdRemark;

    private String oldUid;
    private String oldName;

    public String getOldUid() {
        return oldUid;
    }

    public void setOldUid(String oldUid) {
        this.oldUid = oldUid;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public String getRpdRemark() {
        return rpdRemark;
    }

    public void setRpdRemark(String rpdRemark) {
        this.rpdRemark = rpdRemark;
    }

    public String getOwnerUnitName() {
        return ownerUnitName;
    }

    public void setOwnerUnitName(String ownerUnitName) {
        this.ownerUnitName = ownerUnitName;
    }

    public String getOwnerUnit() {
        return ownerUnit;
    }

    public void setOwnerUnit(String ownerUnit) {
        this.ownerUnit = ownerUnit;
    }

    public String getRpdId() {
        return rpdId;
    }

    public void setRpdId(String rpdId) {
        this.rpdId = rpdId;
    }

    public String getRpId() {
        return rpId;
    }

    public void setRpId(String rpId) {
        this.rpId = rpId;
    }

    public String getRpdPdid() {
        return rpdPdid;
    }

    public void setRpdPdid(String rpdPdid) {
        this.rpdPdid = rpdPdid;
    }

    public String getRpdPpid() {
        return rpdPpid;
    }

    public void setRpdPpid(String rpdPpid) {
        this.rpdPpid = rpdPpid;
    }

    public String getRpdUid() {
        return rpdUid;
    }

    public void setRpdUid(String rpdUid) {
        this.rpdUid = rpdUid;
    }

    public String getRpdType() {
        return rpdType;
    }

    public void setRpdType(String rpdType) {
        this.rpdType = rpdType;
    }

    public String getRpdName() {
        return rpdName;
    }

    public void setRpdName(String rpdName) {
        this.rpdName = rpdName;
    }

    public String getRpdNumber() {
        return rpdNumber;
    }

    public void setRpdNumber(String rpdNumber) {
        this.rpdNumber = rpdNumber;
    }

    public String getRpdTel() {
        return rpdTel;
    }

    public void setRpdTel(String rpdTel) {
        this.rpdTel = rpdTel;
    }

    public Integer getRpdSort() {
        return rpdSort;
    }

    public void setRpdSort(Integer rpdSort) {
        this.rpdSort = rpdSort;
    }

    public String getRpdCpy() {
        return rpdCpy;
    }

    public void setRpdCpy(String rpdCpy) {
        this.rpdCpy = rpdCpy;
    }

    public String getRpdQualification() {
        return rpdQualification;
    }

    public void setRpdQualification(String rpdQualification) {
        this.rpdQualification = rpdQualification;
    }

    public String getRpdQualificationIds() {
        return rpdQualificationIds;
    }

    public void setRpdQualificationIds(String rpdQualificationIds) {
        this.rpdQualificationIds = rpdQualificationIds;
    }

    public String getPdName() {
        return pdName;
    }

    public void setPdName(String pdName) {
        this.pdName = pdName;
    }

    public String getPpName() {
        return ppName;
    }

    public void setPpName(String ppName) {
        this.ppName = ppName;
    }

    public String getPpCode() {
        return ppCode;
    }

    public void setPpCode(String ppCode) {
        this.ppCode = ppCode;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuMtel() {
        return uMtel;
    }

    public void setuMtel(String uMtel) {
        this.uMtel = uMtel;
    }

    public String getuIdcard() {
        return uIdcard;
    }

    public void setuIdcard(String uIdcard) {
        this.uIdcard = uIdcard;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcAbbName() {
        return cAbbName;
    }

    public void setcAbbName(String cAbbName) {
        this.cAbbName = cAbbName;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getPupId() {
        return pupId;
    }

    public void setPupId(String pupId) {
        this.pupId = pupId;
    }

    public Boolean getRpdAdd() {
        return rpdAdd;
    }

    public void setRpdAdd(Boolean rpdAdd) {
        this.rpdAdd = rpdAdd;
    }
}
