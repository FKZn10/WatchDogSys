package com.jpmsoft.common.model.response.MeEnter;

import com.jpmsoft.common.model.ProjectCommonRes;

import java.util.Date;

/**
 * 材料设备进场报审
 */

public class MeEnterApplyResponse extends ProjectCommonRes {

    private String eaId;
    private String pId;
    private String conId;
    private String eaCode;
    private Date eaDate;
    private String eaSupervisorId;
    private String eaDesignId;
    private String eaBrandApply;
    private String eaPurchaseContract;
    private String eaProjectLocation;
    private String eaScCategory;
    private Integer eaWitness;
    private Integer eaProjectTeam;
    private String eaCreateUid;
    private String eaCreateUname;
    private Date eaCreateTime;
    private Integer eaState;
    private Date eaStateTime;
    private String eaFilePc;
    private String eaFilePtr;
    private String eaFileNmc;
    private String eaFileInvoice;
    private String eaFileCd;
    private String eaFileMas;
    private String eaFileAp;
    private Date eaAcceptTime;
    private String eaAcceptorId;
    private String eaAcceptorName;

    private String conName;
    private String eaPurchaseContractName;
    private String eaSupervisorName;
    private String eaDesignName;
    private String conPartyBId;
    private String conPartyBName;
    private String wiId;
    private Integer irResult;
    private String eiId;
    private String eiCode;
    private String wiOrderNo;

    private String eaSupervisorNameHistory;
    private String eaDesignNameHistory;
    private String conPartyBNameHistory;
    //单位简称
    private String eaSupervisorAbbName;
    private String eaDesignAbbName;
    private String conPartyBAbbName;

    public String getEaId() {
        return eaId;
    }

    public void setEaId(String eaId) {
        this.eaId = eaId;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getEaCode() {
        return eaCode;
    }

    public void setEaCode(String eaCode) {
        this.eaCode = eaCode;
    }

    public Date getEaDate() {
        return eaDate;
    }

    public void setEaDate(Date eaDate) {
        this.eaDate = eaDate;
    }

    public String getEaSupervisorId() {
        return eaSupervisorId;
    }

    public void setEaSupervisorId(String eaSupervisorId) {
        this.eaSupervisorId = eaSupervisorId;
    }

    public String getEaDesignId() {
        return eaDesignId;
    }

    public void setEaDesignId(String eaDesignId) {
        this.eaDesignId = eaDesignId;
    }

    public String getEaBrandApply() {
        return eaBrandApply;
    }

    public void setEaBrandApply(String eaBrandApply) {
        this.eaBrandApply = eaBrandApply;
    }

    public String getEaPurchaseContract() {
        return eaPurchaseContract;
    }

    public void setEaPurchaseContract(String eaPurchaseContract) {
        this.eaPurchaseContract = eaPurchaseContract;
    }

    public String getEaProjectLocation() {
        return eaProjectLocation;
    }

    public void setEaProjectLocation(String eaProjectLocation) {
        this.eaProjectLocation = eaProjectLocation;
    }

    public String getEaScCategory() {
        return eaScCategory;
    }

    public void setEaScCategory(String eaScCategory) {
        this.eaScCategory = eaScCategory;
    }

    public Integer getEaWitness() {
        return eaWitness;
    }

    public void setEaWitness(Integer eaWitness) {
        this.eaWitness = eaWitness;
    }

    public Integer getEaProjectTeam() {
        return eaProjectTeam;
    }

    public void setEaProjectTeam(Integer eaProjectTeam) {
        this.eaProjectTeam = eaProjectTeam;
    }

    public String getEaCreateUid() {
        return eaCreateUid;
    }

    public void setEaCreateUid(String eaCreateUid) {
        this.eaCreateUid = eaCreateUid;
    }

    public String getEaCreateUname() {
        return eaCreateUname;
    }

    public void setEaCreateUname(String eaCreateUname) {
        this.eaCreateUname = eaCreateUname;
    }

    public Date getEaCreateTime() {
        return eaCreateTime;
    }

    public void setEaCreateTime(Date eaCreateTime) {
        this.eaCreateTime = eaCreateTime;
    }

    public Integer getEaState() {
        return eaState;
    }

    public void setEaState(Integer eaState) {
        this.eaState = eaState;
    }

    public Date getEaStateTime() {
        return eaStateTime;
    }

    public void setEaStateTime(Date eaStateTime) {
        this.eaStateTime = eaStateTime;
    }

    public String getEaFilePc() {
        return eaFilePc;
    }

    public void setEaFilePc(String eaFilePc) {
        this.eaFilePc = eaFilePc;
    }

    public String getEaFilePtr() {
        return eaFilePtr;
    }

    public void setEaFilePtr(String eaFilePtr) {
        this.eaFilePtr = eaFilePtr;
    }

    public String getEaFileNmc() {
        return eaFileNmc;
    }

    public void setEaFileNmc(String eaFileNmc) {
        this.eaFileNmc = eaFileNmc;
    }

    public String getEaFileInvoice() {
        return eaFileInvoice;
    }

    public void setEaFileInvoice(String eaFileInvoice) {
        this.eaFileInvoice = eaFileInvoice;
    }

    public String getEaFileCd() {
        return eaFileCd;
    }

    public void setEaFileCd(String eaFileCd) {
        this.eaFileCd = eaFileCd;
    }

    public String getEaFileMas() {
        return eaFileMas;
    }

    public void setEaFileMas(String eaFileMas) {
        this.eaFileMas = eaFileMas;
    }

    public String getEaFileAp() {
        return eaFileAp;
    }

    public void setEaFileAp(String eaFileAp) {
        this.eaFileAp = eaFileAp;
    }

    public Date getEaAcceptTime() {
        return eaAcceptTime;
    }

    public void setEaAcceptTime(Date eaAcceptTime) {
        this.eaAcceptTime = eaAcceptTime;
    }

    public String getEaAcceptorId() {
        return eaAcceptorId;
    }

    public void setEaAcceptorId(String eaAcceptorId) {
        this.eaAcceptorId = eaAcceptorId;
    }

    public String getEaAcceptorName() {
        return eaAcceptorName;
    }

    public void setEaAcceptorName(String eaAcceptorName) {
        this.eaAcceptorName = eaAcceptorName;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }


    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getEaSupervisorName() {
        return eaSupervisorName;
    }

    public void setEaSupervisorName(String eaSupervisorName) {
        this.eaSupervisorName = eaSupervisorName;
    }

    public String getEaDesignName() {
        return eaDesignName;
    }

    public void setEaDesignName(String eaDesignName) {
        this.eaDesignName = eaDesignName;
    }

    public String getEaPurchaseContractName() {
        return eaPurchaseContractName;
    }

    public void setEaPurchaseContractName(String eaPurchaseContractName) {
        this.eaPurchaseContractName = eaPurchaseContractName;
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


    public String getWiId() {
        return wiId;
    }

    public void setWiId(String wiId) {
        this.wiId = wiId;
    }

    public Integer getIrResult() {
        return irResult;
    }

    public void setIrResult(Integer irResult) {
        this.irResult = irResult;
    }

    public String getEiId() {
        return eiId;
    }

    public void setEiId(String eiId) {
        this.eiId = eiId;
    }

    public String getEiCode() {
        return eiCode;
    }

    public void setEiCode(String eiCode) {
        this.eiCode = eiCode;
    }

    public String getWiOrderNo() {
        return wiOrderNo;
    }

    public void setWiOrderNo(String wiOrderNo) {
        this.wiOrderNo = wiOrderNo;
    }

    public String getEaSupervisorNameHistory() {
        return eaSupervisorNameHistory;
    }

    public void setEaSupervisorNameHistory(String eaSupervisorNameHistory) {
        this.eaSupervisorNameHistory = eaSupervisorNameHistory;
    }

    public String getEaDesignNameHistory() {
        return eaDesignNameHistory;
    }

    public void setEaDesignNameHistory(String eaDesignNameHistory) {
        this.eaDesignNameHistory = eaDesignNameHistory;
    }

    public String getConPartyBNameHistory() {
        return conPartyBNameHistory;
    }

    public void setConPartyBNameHistory(String conPartyBNameHistory) {
        this.conPartyBNameHistory = conPartyBNameHistory;
    }

    public String getEaSupervisorAbbName() {
        return eaSupervisorAbbName;
    }

    public void setEaSupervisorAbbName(String eaSupervisorAbbName) {
        this.eaSupervisorAbbName = eaSupervisorAbbName;
    }

    public String getEaDesignAbbName() {
        return eaDesignAbbName;
    }

    public void setEaDesignAbbName(String eaDesignAbbName) {
        this.eaDesignAbbName = eaDesignAbbName;
    }

    public String getConPartyBAbbName() {
        return conPartyBAbbName;
    }

    public void setConPartyBAbbName(String conPartyBAbbName) {
        this.conPartyBAbbName = conPartyBAbbName;
    }
}
