package com.jpmsoft.common.model.response.Contract;

import com.jpmsoft.common.model.ProjectCommonRes;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 签证计量备案
 */
public class ConVisaRecordResponse extends ProjectCommonRes {

    private String vrId;
    private String conId;
    private String vrCode;
    private Date vrTime;
    private String vrPlace;
    private String vrContent;
    private String vrParticipant;
    private String vrRemark;
    private String vrCreateUid;
    private String vrCreateUname;
    private Date vrCreateTime;
    private Integer vrState;
    private Date vrStateTime;
    private String vrPic;
    private String vrClId;
    private String pId;
    private BigDecimal vrAmountInvolved;
    private BigDecimal vrEstimatedAmount;
    private String vrFollowUnit;
    private String vrDesignUnit;
    private String vrSupUnit;
    private String vrProposer;
    private String vrPosition;
    private String vrInfluenceTime;
    private String vrVisaCategory;
    private String vrScope;
    private String vrReason;
    private String vrFileContact;
    private String vrFileEstimate;
    private String enisId;
    private String vrName;
    private String vrCid;
    private String vrDid;
    private String vrCnature;

    /****************************************/

    private String vrCname;
    private String vrDname;
    private String enisName;
    private String  enisCode;
    private String conName;
    //工程联系单
    private String clCode;
    private String clTitle;
    private String clLocation;
    private String conCode;
    private String conMoneyTotal;
    private String conPartyBId;
    private String conPartyBName;
    private String conPartyBNameHistory;
    //单位简称
    private String conPartyBAbbName;



    public String getVrId() {
        return vrId;
    }

    public void setVrId(String vrId) {
        this.vrId = vrId;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getVrCode() {
        return vrCode;
    }

    public void setVrCode(String vrCode) {
        this.vrCode = vrCode;
    }

    public Date getVrTime() {
        return vrTime;
    }

    public void setVrTime(Date vrTime) {
        this.vrTime = vrTime;
    }

    public String getVrPlace() {
        return vrPlace;
    }

    public void setVrPlace(String vrPlace) {
        this.vrPlace = vrPlace;
    }

    public String getVrContent() {
        return vrContent;
    }

    public void setVrContent(String vrContent) {
        this.vrContent = vrContent;
    }

    public String getVrParticipant() {
        return vrParticipant;
    }

    public void setVrParticipant(String vrParticipant) {
        this.vrParticipant = vrParticipant;
    }

    public String getVrRemark() {
        return vrRemark;
    }

    public void setVrRemark(String vrRemark) {
        this.vrRemark = vrRemark;
    }

    public String getVrCreateUid() {
        return vrCreateUid;
    }

    public void setVrCreateUid(String vrCreateUid) {
        this.vrCreateUid = vrCreateUid;
    }

    public String getVrCreateUname() {
        return vrCreateUname;
    }

    public void setVrCreateUname(String vrCreateUname) {
        this.vrCreateUname = vrCreateUname;
    }

    public Date getVrCreateTime() {
        return vrCreateTime;
    }

    public void setVrCreateTime(Date vrCreateTime) {
        this.vrCreateTime = vrCreateTime;
    }

    public Integer getVrState() {
        return vrState;
    }

    public void setVrState(Integer vrState) {
        this.vrState = vrState;
    }

    public Date getVrStateTime() {
        return vrStateTime;
    }

    public void setVrStateTime(Date vrStateTime) {
        this.vrStateTime = vrStateTime;
    }

    public String getVrPic() {
        return vrPic;
    }

    public void setVrPic(String vrPic) {
        this.vrPic = vrPic;
    }

    public String getVrClId() {
        return vrClId;
    }

    public void setVrClId(String vrClId) {
        this.vrClId = vrClId;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getClCode() {
        return clCode;
    }

    public void setClCode(String clCode) {
        this.clCode = clCode;
    }

    public String getClTitle() {
        return clTitle;
    }

    public void setClTitle(String clTitle) {
        this.clTitle = clTitle;
    }

    public String getClLocation() {
        return clLocation;
    }

    public void setClLocation(String clLocation) {
        this.clLocation = clLocation;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public BigDecimal getVrAmountInvolved() {
        return vrAmountInvolved;
    }

    public void setVrAmountInvolved(BigDecimal vrAmountInvolved) {
        this.vrAmountInvolved = vrAmountInvolved;
    }

    public BigDecimal getVrEstimatedAmount() {
        return vrEstimatedAmount;
    }

    public void setVrEstimatedAmount(BigDecimal vrEstimatedAmount) {
        this.vrEstimatedAmount = vrEstimatedAmount;
    }

    public String getVrFollowUnit() {
        return vrFollowUnit;
    }

    public void setVrFollowUnit(String vrFollowUnit) {
        this.vrFollowUnit = vrFollowUnit;
    }

    public String getVrDesignUnit() {
        return vrDesignUnit;
    }

    public void setVrDesignUnit(String vrDesignUnit) {
        this.vrDesignUnit = vrDesignUnit;
    }

    public String getVrSupUnit() {
        return vrSupUnit;
    }

    public void setVrSupUnit(String vrSupUnit) {
        this.vrSupUnit = vrSupUnit;
    }

    public String getVrProposer() {
        return vrProposer;
    }

    public void setVrProposer(String vrProposer) {
        this.vrProposer = vrProposer;
    }

    public String getVrPosition() {
        return vrPosition;
    }

    public void setVrPosition(String vrPosition) {
        this.vrPosition = vrPosition;
    }

    public String getVrInfluenceTime() {
        return vrInfluenceTime;
    }

    public void setVrInfluenceTime(String vrInfluenceTime) {
        this.vrInfluenceTime = vrInfluenceTime;
    }

    public String getVrVisaCategory() {
        return vrVisaCategory;
    }

    public void setVrVisaCategory(String vrVisaCategory) {
        this.vrVisaCategory = vrVisaCategory;
    }

    public String getVrScope() {
        return vrScope;
    }

    public void setVrScope(String vrScope) {
        this.vrScope = vrScope;
    }

    public String getVrReason() {
        return vrReason;
    }

    public void setVrReason(String vrReason) {
        this.vrReason = vrReason;
    }

    public String getVrFileContact() {
        return vrFileContact;
    }

    public void setVrFileContact(String vrFileContact) {
        this.vrFileContact = vrFileContact;
    }

    public String getVrFileEstimate() {
        return vrFileEstimate;
    }

    public void setVrFileEstimate(String vrFileEstimate) {
        this.vrFileEstimate = vrFileEstimate;
    }

    public String getVrName() {
        return vrName;
    }

    public void setVrName(String vrName) {
        this.vrName = vrName;
    }

    public String getVrCid() {
        return vrCid;
    }

    public void setVrCid(String vrCid) {
        this.vrCid = vrCid;
    }

    public String getVrDid() {
        return vrDid;
    }

    public void setVrDid(String vrDid) {
        this.vrDid = vrDid;
    }

    public String getVrCnature() {
        return vrCnature;
    }

    public void setVrCnature(String vrCnature) {
        this.vrCnature = vrCnature;
    }

    public String getVrCname() {
        return vrCname;
    }

    public void setVrCname(String vrCname) {
        this.vrCname = vrCname;
    }

    public String getVrDname() {
        return vrDname;
    }

    public void setVrDname(String vrDname) {
        this.vrDname = vrDname;
    }

    public String getEnisId() {
        return enisId;
    }

    public void setEnisId(String enisId) {
        this.enisId = enisId;
    }

    public String getEnisName() {
        return enisName;
    }

    public void setEnisName(String enisName) {
        this.enisName = enisName;
    }

    public String getEnisCode() {
        return enisCode;
    }

    public void setEnisCode(String enisCode) {
        this.enisCode = enisCode;
    }

    public String getConCode() {
        return conCode;
    }

    public void setConCode(String conCode) {
        this.conCode = conCode;
    }

    public String getConMoneyTotal() {
        return conMoneyTotal;
    }

    public void setConMoneyTotal(String conMoneyTotal) {
        this.conMoneyTotal = conMoneyTotal;
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


    public String getConPartyBNameHistory() {
        return conPartyBNameHistory;
    }

    public void setConPartyBNameHistory(String conPartyBNameHistory) {
        this.conPartyBNameHistory = conPartyBNameHistory;
    }

    public String getConPartyBAbbName() {
        return conPartyBAbbName;
    }

    public void setConPartyBAbbName(String conPartyBAbbName) {
        this.conPartyBAbbName = conPartyBAbbName;
    }
}
