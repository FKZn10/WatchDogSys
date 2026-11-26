package com.jpmsoft.common.model.request.MeEnter;

import com.jpmsoft.common.model.BaseFileRequest;

import java.util.Date;
import java.util.List;

/**
 * 见证送检
 */

public class MeWitnessInspectionRequest extends BaseFileRequest {

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

    private String wiPictureIds;
    private String conName;
    private String pName;
    private String wiSamplingSupervisorIds;
    private String conPartyBId;
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

    public String getWiPictureIds() {
        return wiPictureIds;
    }

    public void setWiPictureIds(String wiPictureIds) {
        this.wiPictureIds = wiPictureIds;
    }

    public String getWiSamplingSupervisorIds() {
        return wiSamplingSupervisorIds;
    }

    public void setWiSamplingSupervisorIds(String wiSamplingSupervisorIds) {
        this.wiSamplingSupervisorIds = wiSamplingSupervisorIds;
    }

    public List<MeWitnessDtlRequest> getWitnessDtls() {
        return witnessDtls;
    }

    public void setWitnessDtls(List<MeWitnessDtlRequest> witnessDtls) {
        this.witnessDtls = witnessDtls;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getConPartyBId() {
        return conPartyBId;
    }

    public void setConPartyBId(String conPartyBId) {
        this.conPartyBId = conPartyBId;
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
}
