package com.jpmsoft.common.model.response.SecInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.requestAndResponse.SecCommonModel;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecEvalReportDtlModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 评标报告
 */

public class SecEvalReportResponse extends SecCommonModel implements Serializable {

    private static final long serialVersionUID = -9161317136782728097L;
    private String erId;
    private String secId;
    private String secName;
    private String erMethod;
    private Date erDate;
    private String erPlace;
    private String erCreateUid;
    private String erCreateUname;
    private Date erCreateTime;
    private Integer erState;
    private Date erStateTime;
    private String erRemark;
    private String erDeterminType;
    private BigDecimal erBidWinningPrice;
    private BigDecimal erRate;
    private String erOtherValuation;
    private String erArmorApplication;
    private String erCandidateUnit;
    @JsonProperty("pId")
    private String pId;
    @JsonProperty("pName")
    private String pName;
    private Date erOpenBidTime;
    private Double erPriceAndRate;
    private BigDecimal erTenderSumLimit;
    private BigDecimal erBiddingControlRate;
    private String erType;
    private BigDecimal erConversionControlPrice;

    private String erCandidateName;
    private List<SecProjectResponse> secProjects;
    //单位简称
    private String erCandiAbbName;

    private List<SecEvalReportDtlModel> secEvalReportDtls;

    @JsonProperty("pCategory")
    private String pCategory;
    @JsonProperty("pBuildModel")
    private String pBuildModel;

    public String getpCategory() {
        return pCategory;
    }

    public void setpCategory(String pCategory) {
        this.pCategory = pCategory;
    }

    public String getpBuildModel() {
        return pBuildModel;
    }

    public void setpBuildModel(String pBuildModel) {
        this.pBuildModel = pBuildModel;
    }

    @Override
    public String getSecName() {
        return secName;
    }

    @Override
    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getErRemark() {
        return erRemark;
    }

    public void setErRemark(String erRemark) {
        this.erRemark = erRemark;
    }

    @Override
    public String getpId() {
        return pId;
    }

    @Override
    public void setpId(String pId) {
        this.pId = pId;
    }

    public Date getErOpenBidTime() {
        return erOpenBidTime;
    }

    public void setErOpenBidTime(Date erOpenBidTime) {
        this.erOpenBidTime = erOpenBidTime;
    }

    public Double getErPriceAndRate() {
        return erPriceAndRate;
    }

    public void setErPriceAndRate(Double erPriceAndRate) {
        this.erPriceAndRate = erPriceAndRate;
    }

    public BigDecimal getErTenderSumLimit() {
        return erTenderSumLimit;
    }

    public void setErTenderSumLimit(BigDecimal erTenderSumLimit) {
        this.erTenderSumLimit = erTenderSumLimit;
    }

    public BigDecimal getErBiddingControlRate() {
        return erBiddingControlRate;
    }

    public void setErBiddingControlRate(BigDecimal erBiddingControlRate) {
        this.erBiddingControlRate = erBiddingControlRate;
    }

    public String getErType() {
        return erType;
    }

    public void setErType(String erType) {
        this.erType = erType;
    }

    public BigDecimal getErConversionControlPrice() {
        return erConversionControlPrice;
    }

    public void setErConversionControlPrice(BigDecimal erConversionControlPrice) {
        this.erConversionControlPrice = erConversionControlPrice;
    }

    public List<SecEvalReportDtlModel> getSecEvalReportDtls() {
        return secEvalReportDtls;
    }

    public void setSecEvalReportDtls(List<SecEvalReportDtlModel> secEvalReportDtls) {
        this.secEvalReportDtls = secEvalReportDtls;
    }

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

    public String getErCandidateName() {
        return erCandidateName;
    }

    public void setErCandidateName(String erCandidateName) {
        this.erCandidateName = erCandidateName;
    }

    public List<SecProjectResponse> getSecProjects() {
        return secProjects;
    }

    public void setSecProjects(List<SecProjectResponse> secProjects) {
        this.secProjects = secProjects;
    }

    public String getErCandiAbbName() {
        return erCandiAbbName;
    }

    public void setErCandiAbbName(String erCandiAbbName) {
        this.erCandiAbbName = erCandiAbbName;
    }
}
