package com.jpmsoft.common.model.request.SecInfo;

import com.jpmsoft.common.model.BaseFileRequest;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 评标报告
 */

public class SecEvalReportRequest extends BaseFileRequest {

    private String erId;
    private String secId;
    private String erMethod;
    private Date erDate;
    private String erPlace;
    private String erCreateUid;
    private String erCreateUname;
    private Date erCreateTime;
    private Integer erState;
    private Date erStateTime;
    private BigDecimal erRate;
    private String erOtherValuation;
    private String erDeterminType;
    private BigDecimal erBidWinningPrice;
    private String erArmorApplication;
    private String erCandidateUnit;

    public String getErId() {
        return erId;
    }

    public void setErId(String erId) {
        this.erId = erId;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public String getErMethod() {
        return erMethod;
    }

    public void setErMethod(String erMethod) {
        this.erMethod = erMethod;
    }

    public Date getErDate() {
        return erDate;
    }

    public void setErDate(Date erDate) {
        this.erDate = erDate;
    }

    public String getErPlace() {
        return erPlace;
    }

    public void setErPlace(String erPlace) {
        this.erPlace = erPlace;
    }

    public String getErCreateUid() {
        return erCreateUid;
    }

    public void setErCreateUid(String erCreateUid) {
        this.erCreateUid = erCreateUid;
    }

    public String getErCreateUname() {
        return erCreateUname;
    }

    public void setErCreateUname(String erCreateUname) {
        this.erCreateUname = erCreateUname;
    }

    public Date getErCreateTime() {
        return erCreateTime;
    }

    public void setErCreateTime(Date erCreateTime) {
        this.erCreateTime = erCreateTime;
    }

    public Integer getErState() {
        return erState;
    }

    public void setErState(Integer erState) {
        this.erState = erState;
    }

    public Date getErStateTime() {
        return erStateTime;
    }

    public void setErStateTime(Date erStateTime) {
        this.erStateTime = erStateTime;
    }

    public BigDecimal getErRate() {
        return erRate;
    }

    public void setErRate(BigDecimal erRate) {
        this.erRate = erRate;
    }

    public String getErOtherValuation() {
        return erOtherValuation;
    }

    public void setErOtherValuation(String erOtherValuation) {
        this.erOtherValuation = erOtherValuation;
    }


    public String getErDeterminType() {
        return erDeterminType;
    }

    public void setErDeterminType(String erDeterminType) {
        this.erDeterminType = erDeterminType;
    }

    public BigDecimal getErBidWinningPrice() {
        return erBidWinningPrice;
    }

    public void setErBidWinningPrice(BigDecimal erBidWinningPrice) {
        this.erBidWinningPrice = erBidWinningPrice;
    }

    public String getErArmorApplication() {
        return erArmorApplication;
    }

    public void setErArmorApplication(String erArmorApplication) {
        this.erArmorApplication = erArmorApplication;
    }

    public String getErCandidateUnit() {
        return erCandidateUnit;
    }

    public void setErCandidateUnit(String erCandidateUnit) {
        this.erCandidateUnit = erCandidateUnit;
    }
}
