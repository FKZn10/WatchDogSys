package com.jpmsoft.projectProducer.pojo.dto.contract;

import com.jpmsoft.common.model.ProjectCommonRes;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ConPayApplyDto extends ProjectCommonRes implements Serializable {
    private static final long serialVersionUID = -7313984485563206798L;
    public String cpaId;
    public String pId;
    public String conId;
    public String conPartyAId;
    public String conPartyAName;
    public String cpaType;
    public String cpaSources;
    public Date cpaApplyDate;
    public String cpaApplyUid;
    public BigDecimal cpaApprovedAmount;
    public BigDecimal cpaPaymentAmount;
    public String cpaPaymentProportion;
    public String cpaRemarks;
    public Date cpaCreateTime;
    public String cpaForeign;


    public String conName;
    public String cpaApplyUname;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCpaId() {
        return cpaId;
    }

    public void setCpaId(String cpaId) {
        this.cpaId = cpaId;
    }

    @Override
    public String getpId() {
        return pId;
    }

    @Override
    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getCpaType() {
        return cpaType;
    }

    public void setCpaType(String cpaType) {
        this.cpaType = cpaType;
    }

    public String getCpaSources() {
        return cpaSources;
    }

    public void setCpaSources(String cpaSources) {
        this.cpaSources = cpaSources;
    }

    public Date getCpaApplyDate() {
        return cpaApplyDate;
    }

    public void setCpaApplyDate(Date cpaApplyDate) {
        this.cpaApplyDate = cpaApplyDate;
    }

    public String getCpaApplyUid() {
        return cpaApplyUid;
    }

    public void setCpaApplyUid(String cpaApplyUid) {
        this.cpaApplyUid = cpaApplyUid;
    }

    public BigDecimal getCpaApprovedAmount() {
        return cpaApprovedAmount;
    }

    public void setCpaApprovedAmount(BigDecimal cpaApprovedAmount) {
        this.cpaApprovedAmount = cpaApprovedAmount;
    }

    public BigDecimal getCpaPaymentAmount() {
        return cpaPaymentAmount;
    }

    public void setCpaPaymentAmount(BigDecimal cpaPaymentAmount) {
        this.cpaPaymentAmount = cpaPaymentAmount;
    }

    public String getCpaPaymentProportion() {
        return cpaPaymentProportion;
    }

    public void setCpaPaymentProportion(String cpaPaymentProportion) {
        this.cpaPaymentProportion = cpaPaymentProportion;
    }

    public String getCpaRemarks() {
        return cpaRemarks;
    }

    public void setCpaRemarks(String cpaRemarks) {
        this.cpaRemarks = cpaRemarks;
    }

    public Date getCpaCreateTime() {
        return cpaCreateTime;
    }

    public void setCpaCreateTime(Date cpaCreateTime) {
        this.cpaCreateTime = cpaCreateTime;
    }

    public String getCpaForeign() {
        return cpaForeign;
    }

    public void setCpaForeign(String cpaForeign) {
        this.cpaForeign = cpaForeign;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getCpaApplyUname() {
        return cpaApplyUname;
    }

    public void setCpaApplyUname(String cpaApplyUname) {
        this.cpaApplyUname = cpaApplyUname;
    }

    public String getConPartyAId() {
        return conPartyAId;
    }

    public void setConPartyAId(String conPartyAId) {
        this.conPartyAId = conPartyAId;
    }

    public String getConPartyAName() {
        return conPartyAName;
    }

    public void setConPartyAName(String conPartyAName) {
        this.conPartyAName = conPartyAName;
    }
}
