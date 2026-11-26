package com.jpmsoft.common.model.response.MeEnter;


import com.jpmsoft.common.model.ProjectCommonRes;

import java.util.Date;

/**
 * 进场验货
 */
public class MeEnterInspectionResponse extends ProjectCommonRes {

    private String eiId;
    private String eaId;
    private String pId;
    private String conId;
    private String eiSupervisorId;
    private String eiDesignId;
    private String eiContractorId;
    private String eiSupplierId;
    private String eiAcceptPic;
    private Date eiAcceptTime;
    private String eiAcceptorId;
    private String eiAcceptorName;
    private String eiCreateUid;
    private String eiCreateUname;
    private Date eiCreateTime;
    private Integer eiState;
    private Date eiStateTime;
    private String eiCode;

    /***********************************/
    private String conName;
    private String conPartyBId;
    private String conPartyBName;
    private String eiSupervisorName;
    private String eiDesignName;
    private String eiContractorName;
    private String eiSupplierName;
    private String metName;
    private String eadStandardType;
    private String eadSupplyOrderNo;
    private String eadBrandName;
    private String eadCount;
    private String eaCode;
    private String conPartyBNameHistory;
    private String eiSupervisorNameHistory;
    private String eiDesignNameHistory;
    private String eiContractorNameHistory;
    private String eiSupplierNameHistory;
    //单位简称
    private String conPartyBAbbName;
    private String eiSupervisorAbbName;
    private String eiDesignAbbName;
    private String eiContractorAbbName;
    private String eiSupplierAbbName;


    public String getEiId() {
        return eiId;
    }

    public void setEiId(String eiId) {
        this.eiId = eiId;
    }

    public String getEaId() {
        return eaId;
    }

    public void setEaId(String eaId) {
        this.eaId = eaId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getEiSupervisorId() {
        return eiSupervisorId;
    }

    public void setEiSupervisorId(String eiSupervisorId) {
        this.eiSupervisorId = eiSupervisorId;
    }

    public String getEiDesignId() {
        return eiDesignId;
    }

    public void setEiDesignId(String eiDesignId) {
        this.eiDesignId = eiDesignId;
    }

    public String getEiContractorId() {
        return eiContractorId;
    }

    public void setEiContractorId(String eiContractorId) {
        this.eiContractorId = eiContractorId;
    }

    public String getEiSupplierId() {
        return eiSupplierId;
    }

    public void setEiSupplierId(String eiSupplierId) {
        this.eiSupplierId = eiSupplierId;
    }

    public String getEiAcceptPic() {
        return eiAcceptPic;
    }

    public void setEiAcceptPic(String eiAcceptPic) {
        this.eiAcceptPic = eiAcceptPic;
    }

    public Date getEiAcceptTime() {
        return eiAcceptTime;
    }

    public void setEiAcceptTime(Date eiAcceptTime) {
        this.eiAcceptTime = eiAcceptTime;
    }

    public String getEiAcceptorId() {
        return eiAcceptorId;
    }

    public void setEiAcceptorId(String eiAcceptorId) {
        this.eiAcceptorId = eiAcceptorId;
    }

    public String getEiAcceptorName() {
        return eiAcceptorName;
    }

    public void setEiAcceptorName(String eiAcceptorName) {
        this.eiAcceptorName = eiAcceptorName;
    }

    public String getEiCreateUid() {
        return eiCreateUid;
    }

    public void setEiCreateUid(String eiCreateUid) {
        this.eiCreateUid = eiCreateUid;
    }

    public String getEiCreateUname() {
        return eiCreateUname;
    }

    public void setEiCreateUname(String eiCreateUname) {
        this.eiCreateUname = eiCreateUname;
    }

    public Date getEiCreateTime() {
        return eiCreateTime;
    }

    public void setEiCreateTime(Date eiCreateTime) {
        this.eiCreateTime = eiCreateTime;
    }

    public Integer getEiState() {
        return eiState;
    }

    public void setEiState(Integer eiState) {
        this.eiState = eiState;
    }

    public Date getEiStateTime() {
        return eiStateTime;
    }

    public void setEiStateTime(Date eiStateTime) {
        this.eiStateTime = eiStateTime;
    }

    public String getEiCode() {
        return eiCode;
    }

    public void setEiCode(String eiCode) {
        this.eiCode = eiCode;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getConPartyBId() {
        return conPartyBId;
    }

    public void setConPartyBId(String conPartyBId) {
        this.conPartyBId = conPartyBId;
    }

    public String getConPartyBName() {
        return conPartyBName;
    }

    public void setConPartyBName(String conPartyBName) {
        this.conPartyBName = conPartyBName;
    }

    public String getEiSupervisorName() {
        return eiSupervisorName;
    }

    public void setEiSupervisorName(String eiSupervisorName) {
        this.eiSupervisorName = eiSupervisorName;
    }

    public String getEiDesignName() {
        return eiDesignName;
    }

    public void setEiDesignName(String eiDesignName) {
        this.eiDesignName = eiDesignName;
    }

    public String getEiContractorName() {
        return eiContractorName;
    }

    public void setEiContractorName(String eiContractorName) {
        this.eiContractorName = eiContractorName;
    }

    public String getEiSupplierName() {
        return eiSupplierName;
    }

    public void setEiSupplierName(String eiSupplierName) {
        this.eiSupplierName = eiSupplierName;
    }

    public String getMetName() {
        return metName;
    }

    public void setMetName(String metName) {
        this.metName = metName;
    }

    public String getEadStandardType() {
        return eadStandardType;
    }

    public void setEadStandardType(String eadStandardType) {
        this.eadStandardType = eadStandardType;
    }

    public String getEadSupplyOrderNo() {
        return eadSupplyOrderNo;
    }

    public void setEadSupplyOrderNo(String eadSupplyOrderNo) {
        this.eadSupplyOrderNo = eadSupplyOrderNo;
    }

    public String getEadBrandName() {
        return eadBrandName;
    }

    public void setEadBrandName(String eadBrandName) {
        this.eadBrandName = eadBrandName;
    }

    public String getEadCount() {
        return eadCount;
    }

    public void setEadCount(String eadCount) {
        this.eadCount = eadCount;
    }

    public String getEaCode() {
        return eaCode;
    }

    public void setEaCode(String eaCode) {
        this.eaCode = eaCode;
    }

    public String getConPartyBNameHistory() {
        return conPartyBNameHistory;
    }

    public void setConPartyBNameHistory(String conPartyBNameHistory) {
        this.conPartyBNameHistory = conPartyBNameHistory;
    }

    public String getEiSupervisorNameHistory() {
        return eiSupervisorNameHistory;
    }

    public void setEiSupervisorNameHistory(String eiSupervisorNameHistory) {
        this.eiSupervisorNameHistory = eiSupervisorNameHistory;
    }

    public String getEiDesignNameHistory() {
        return eiDesignNameHistory;
    }

    public void setEiDesignNameHistory(String eiDesignNameHistory) {
        this.eiDesignNameHistory = eiDesignNameHistory;
    }

    public String getEiContractorNameHistory() {
        return eiContractorNameHistory;
    }

    public void setEiContractorNameHistory(String eiContractorNameHistory) {
        this.eiContractorNameHistory = eiContractorNameHistory;
    }

    public String getEiSupplierNameHistory() {
        return eiSupplierNameHistory;
    }

    public void setEiSupplierNameHistory(String eiSupplierNameHistory) {
        this.eiSupplierNameHistory = eiSupplierNameHistory;
    }

    public String getConPartyBAbbName() {
        return conPartyBAbbName;
    }

    public void setConPartyBAbbName(String conPartyBAbbName) {
        this.conPartyBAbbName = conPartyBAbbName;
    }

    public String getEiSupervisorAbbName() {
        return eiSupervisorAbbName;
    }

    public void setEiSupervisorAbbName(String eiSupervisorAbbName) {
        this.eiSupervisorAbbName = eiSupervisorAbbName;
    }

    public String getEiDesignAbbName() {
        return eiDesignAbbName;
    }

    public void setEiDesignAbbName(String eiDesignAbbName) {
        this.eiDesignAbbName = eiDesignAbbName;
    }

    public String getEiContractorAbbName() {
        return eiContractorAbbName;
    }

    public void setEiContractorAbbName(String eiContractorAbbName) {
        this.eiContractorAbbName = eiContractorAbbName;
    }

    public String getEiSupplierAbbName() {
        return eiSupplierAbbName;
    }

    public void setEiSupplierAbbName(String eiSupplierAbbName) {
        this.eiSupplierAbbName = eiSupplierAbbName;
    }
}
