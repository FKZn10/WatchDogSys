package com.jpmsoft.common.model.response.MeEnter;

import com.jpmsoft.common.model.ProjectCommonRes;
import com.jpmsoft.common.model.request.MeEnter.MeWitnessDtlRequest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 见证送检
 */

public class MeWitnessInspectionResponse extends ProjectCommonRes {
    private String wiId;
    private String eadId;
    private String pId;
    private String conId;
    private Integer wiFrequency;
    private String wiUnitId;
    private String wiUnitContacts;
    private String wiUnitContactsTel;
    private String wiDeliveryman;
    private String wiDeliverymanTel;
    private String wiWitness;
    private String wiWitnessTel;
    private String wiOrderNo;
    private String wiSamplingMethod;
    private Date wiSamplingStartDate;
    private Date wiSamplingEndDate;
    private Date wiStartDate;
    private Date wiEndDate;
    private String wiEnsureSample;
    private String wiPicture;
    private String wiSamplingSupervisor;
    private String wiCreateUid;
    private String wiCreateUname;
    private Date wiCreateTime;
    private Integer wiState;
    private Date wiStateTime;
    private String eaId;
    private String wiCode;
    private String wiDetectionCode;

    private String conName;
    private String wiUnitName;
    private String metName;
    private String eadBrandName;
    private String eadStandardType;
    private Float eadCount;
    private String eadSupplyOrderNo;
    private String conPartyBId;
    private String conPartyBName;
    private String eaCode;
    private String wiUnitNameHistory;
    private String conPartyBNameHistory;
    //单位简称
    private String wiUnitAbbName;
    private String conPartyBAbbName;
    private BigDecimal wdAmountTotal;

    private List<MeWitnessDtlRequest> witnessDtls;

    public String getWiId() {
        return wiId;
    }

    public void setWiId(String wiId) {
        this.wiId = wiId;
    }

    public String getEadId() {
        return eadId;
    }

    public void setEadId(String eadId) {
        this.eadId = eadId;
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

    public Integer getWiFrequency() {
        return wiFrequency;
    }

    public void setWiFrequency(Integer wiFrequency) {
        this.wiFrequency = wiFrequency;
    }

    public String getWiUnitId() {
        return wiUnitId;
    }

    public void setWiUnitId(String wiUnitId) {
        this.wiUnitId = wiUnitId;
    }

    public String getWiUnitContacts() {
        return wiUnitContacts;
    }

    public void setWiUnitContacts(String wiUnitContacts) {
        this.wiUnitContacts = wiUnitContacts;
    }

    public String getWiUnitContactsTel() {
        return wiUnitContactsTel;
    }

    public void setWiUnitContactsTel(String wiUnitContactsTel) {
        this.wiUnitContactsTel = wiUnitContactsTel;
    }

    public String getWiDeliveryman() {
        return wiDeliveryman;
    }

    public void setWiDeliveryman(String wiDeliveryman) {
        this.wiDeliveryman = wiDeliveryman;
    }

    public String getWiDeliverymanTel() {
        return wiDeliverymanTel;
    }

    public void setWiDeliverymanTel(String wiDeliverymanTel) {
        this.wiDeliverymanTel = wiDeliverymanTel;
    }

    public String getWiWitness() {
        return wiWitness;
    }

    public void setWiWitness(String wiWitness) {
        this.wiWitness = wiWitness;
    }

    public String getWiWitnessTel() {
        return wiWitnessTel;
    }

    public void setWiWitnessTel(String wiWitnessTel) {
        this.wiWitnessTel = wiWitnessTel;
    }

    public String getWiOrderNo() {
        return wiOrderNo;
    }

    public void setWiOrderNo(String wiOrderNo) {
        this.wiOrderNo = wiOrderNo;
    }

    public String getWiSamplingMethod() {
        return wiSamplingMethod;
    }

    public void setWiSamplingMethod(String wiSamplingMethod) {
        this.wiSamplingMethod = wiSamplingMethod;
    }

    public Date getWiSamplingStartDate() {
        return wiSamplingStartDate;
    }

    public void setWiSamplingStartDate(Date wiSamplingStartDate) {
        this.wiSamplingStartDate = wiSamplingStartDate;
    }

    public Date getWiSamplingEndDate() {
        return wiSamplingEndDate;
    }

    public void setWiSamplingEndDate(Date wiSamplingEndDate) {
        this.wiSamplingEndDate = wiSamplingEndDate;
    }

    public Date getWiStartDate() {
        return wiStartDate;
    }

    public void setWiStartDate(Date wiStartDate) {
        this.wiStartDate = wiStartDate;
    }

    public Date getWiEndDate() {
        return wiEndDate;
    }

    public void setWiEndDate(Date wiEndDate) {
        this.wiEndDate = wiEndDate;
    }

    public String getWiEnsureSample() {
        return wiEnsureSample;
    }

    public void setWiEnsureSample(String wiEnsureSample) {
        this.wiEnsureSample = wiEnsureSample;
    }

    public String getWiPicture() {
        return wiPicture;
    }

    public void setWiPicture(String wiPicture) {
        this.wiPicture = wiPicture;
    }

    public String getWiSamplingSupervisor() {
        return wiSamplingSupervisor;
    }

    public void setWiSamplingSupervisor(String wiSamplingSupervisor) {
        this.wiSamplingSupervisor = wiSamplingSupervisor;
    }

    public String getWiCreateUid() {
        return wiCreateUid;
    }

    public void setWiCreateUid(String wiCreateUid) {
        this.wiCreateUid = wiCreateUid;
    }

    public String getWiCreateUname() {
        return wiCreateUname;
    }

    public void setWiCreateUname(String wiCreateUname) {
        this.wiCreateUname = wiCreateUname;
    }

    public Date getWiCreateTime() {
        return wiCreateTime;
    }

    public void setWiCreateTime(Date wiCreateTime) {
        this.wiCreateTime = wiCreateTime;
    }

    public Integer getWiState() {
        return wiState;
    }

    public void setWiState(Integer wiState) {
        this.wiState = wiState;
    }

    public Date getWiStateTime() {
        return wiStateTime;
    }

    public void setWiStateTime(Date wiStateTime) {
        this.wiStateTime = wiStateTime;
    }

    public String getEaId() {
        return eaId;
    }

    public void setEaId(String eaId) {
        this.eaId = eaId;
    }

    public String getWiCode() {
        return wiCode;
    }

    public void setWiCode(String wiCode) {
        this.wiCode = wiCode;
    }

    public String getWiDetectionCode() {
        return wiDetectionCode;
    }

    public void setWiDetectionCode(String wiDetectionCode) {
        this.wiDetectionCode = wiDetectionCode;
    }

    public List<MeWitnessDtlRequest> getWitnessDtls() {
        return witnessDtls;
    }

    public void setWitnessDtls(List<MeWitnessDtlRequest> witnessDtls) {
        this.witnessDtls = witnessDtls;
    }

    public String getMetName() {
        return metName;
    }

    public void setMetName(String metName) {
        this.metName = metName;
    }

    public String getEadBrandName() {
        return eadBrandName;
    }

    public void setEadBrandName(String eadBrandName) {
        this.eadBrandName = eadBrandName;
    }

    public String getEadStandardType() {
        return eadStandardType;
    }

    public void setEadStandardType(String eadStandardType) {
        this.eadStandardType = eadStandardType;
    }

    public Float getEadCount() {
        return eadCount;
    }

    public void setEadCount(Float eadCount) {
        this.eadCount = eadCount;
    }

    public String getEadSupplyOrderNo() {
        return eadSupplyOrderNo;
    }

    public void setEadSupplyOrderNo(String eadSupplyOrderNo) {
        this.eadSupplyOrderNo = eadSupplyOrderNo;
    }

    public String getWiUnitName() {
        return wiUnitName;
    }

    public void setWiUnitName(String wiUnitName) {
        this.wiUnitName = wiUnitName;
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

    public String getEaCode() {
        return eaCode;
    }

    public void setEaCode(String eaCode) {
        this.eaCode = eaCode;
    }

    public String getConPartyBName() {
        return conPartyBName;
    }

    public void setConPartyBName(String conPartyBName) {
        this.conPartyBName = conPartyBName;
    }

    public String getWiUnitNameHistory() {
        return wiUnitNameHistory;
    }

    public void setWiUnitNameHistory(String wiUnitNameHistory) {
        this.wiUnitNameHistory = wiUnitNameHistory;
    }

    public String getConPartyBNameHistory() {
        return conPartyBNameHistory;
    }

    public void setConPartyBNameHistory(String conPartyBNameHistory) {
        this.conPartyBNameHistory = conPartyBNameHistory;
    }

    public String getWiUnitAbbName() {
        return wiUnitAbbName;
    }

    public void setWiUnitAbbName(String wiUnitAbbName) {
        this.wiUnitAbbName = wiUnitAbbName;
    }

    public String getConPartyBAbbName() {
        return conPartyBAbbName;
    }

    public void setConPartyBAbbName(String conPartyBAbbName) {
        this.conPartyBAbbName = conPartyBAbbName;
    }

    public BigDecimal getWdAmountTotal() {
        return wdAmountTotal;
    }

    public void setWdAmountTotal(BigDecimal wdAmountTotal) {
        this.wdAmountTotal = wdAmountTotal;
    }
}
