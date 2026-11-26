package com.jpmsoft.common.model.response.SecInfo;

import com.jpmsoft.common.model.request.SecInfo.SecPublicityCpyDto;
import com.jpmsoft.common.model.requestAndResponse.SecCommonModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 中标结果公示
 */
public class SecPublicityResultsResponse extends SecCommonModel implements Serializable {

    private static final long serialVersionUID = 541820157462515789L;

    private String prId;
    private String secId;
    private Date prStartTime;
    private Date prEndTime;
    private String prTenderee;
    private String prTendereeTelephone;
    private String prTendereeAddress;
    private String prBidWinner;
    private BigDecimal prBidAmount;
    private String prResultRegistration;
    private String prArmorApplication;
    private Date prCreateTime;
    private String prCreateUid;
    private String prCreateUname;
    private Integer prState;
    private Date prStateTime;
    private BigDecimal prRate;
    private String prOtherValuation;
    private String prPm;
    private Integer prBiddingPeriod;
    private String prBidWinning;
    private String prTitle;
    private Date prTime;
    private String prContentFile;

    private List<SecPublicityCpyDto> secPublicityCpys;
    private String prTendereeName;
    private String prBidWinnerName;
    private String pName;
    private String secOrganizeForm;
    private String secBiddingType;
    private Date secStartTime;
    private Integer secBiddingCycle;
    private String secPlace;
    private String tendereeName;
    private String pCompetentDepartmentName;
    private String prTendereeNameHistory;
    private String prBidWinnerNameHistory;
    private List<SecProjectResponse> secProjects;
    //单位简称
    private String prTendereeAbbName;
    private String prBidWinAbbName;
    private String tendereeAbbName;
    /*
     * 项目规模
     */
    private String projectCategory;
    /*
     * 发起单位
     */
    private String projectInitiatingUnit;
    private String projectOwner;

    public String getProjectOwner() {
        return projectOwner;
    }

    public void setProjectOwner(String projectOwner) {
        this.projectOwner = projectOwner;
    }

    public String getPrId() {
        return prId;
    }

    public void setPrId(String prId) {
        this.prId = prId;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public Date getPrStartTime() {
        return prStartTime;
    }

    public void setPrStartTime(Date prStartTime) {
        this.prStartTime = prStartTime;
    }

    public Date getPrEndTime() {
        return prEndTime;
    }

    public void setPrEndTime(Date prEndTime) {
        this.prEndTime = prEndTime;
    }

    public String getPrTenderee() {
        return prTenderee;
    }

    public void setPrTenderee(String prTenderee) {
        this.prTenderee = prTenderee;
    }

    public String getPrTendereeTelephone() {
        return prTendereeTelephone;
    }

    public void setPrTendereeTelephone(String prTendereeTelephone) {
        this.prTendereeTelephone = prTendereeTelephone;
    }

    public String getPrTendereeAddress() {
        return prTendereeAddress;
    }

    public void setPrTendereeAddress(String prTendereeAddress) {
        this.prTendereeAddress = prTendereeAddress;
    }

    public String getPrBidWinner() {
        return prBidWinner;
    }

    public void setPrBidWinner(String prBidWinner) {
        this.prBidWinner = prBidWinner;
    }

    public BigDecimal getPrBidAmount() {
        return prBidAmount;
    }

    public void setPrBidAmount(BigDecimal prBidAmount) {
        this.prBidAmount = prBidAmount;
    }

    public String getPrResultRegistration() {
        return prResultRegistration;
    }

    public void setPrResultRegistration(String prResultRegistration) {
        this.prResultRegistration = prResultRegistration;
    }

    public String getPrArmorApplication() {
        return prArmorApplication;
    }

    public void setPrArmorApplication(String prArmorApplication) {
        this.prArmorApplication = prArmorApplication;
    }

    public Date getPrCreateTime() {
        return prCreateTime;
    }

    public void setPrCreateTime(Date prCreateTime) {
        this.prCreateTime = prCreateTime;
    }

    public String getPrCreateUid() {
        return prCreateUid;
    }

    public void setPrCreateUid(String prCreateUid) {
        this.prCreateUid = prCreateUid;
    }

    public String getPrCreateUname() {
        return prCreateUname;
    }

    public void setPrCreateUname(String prCreateUname) {
        this.prCreateUname = prCreateUname;
    }

    public Integer getPrState() {
        return prState;
    }

    public void setPrState(Integer prState) {
        this.prState = prState;
    }

    public Date getPrStateTime() {
        return prStateTime;
    }

    public void setPrStateTime(Date prStateTime) {
        this.prStateTime = prStateTime;
    }

    public BigDecimal getPrRate() {
        return prRate;
    }

    public void setPrRate(BigDecimal prRate) {
        this.prRate = prRate;
    }

    public String getPrOtherValuation() {
        return prOtherValuation;
    }

    public void setPrOtherValuation(String prOtherValuation) {
        this.prOtherValuation = prOtherValuation;
    }

    public String getPrPm() {
        return prPm;
    }

    public void setPrPm(String prPm) {
        this.prPm = prPm;
    }

    public Integer getPrBiddingPeriod() {
        return prBiddingPeriod;
    }

    public void setPrBiddingPeriod(Integer prBiddingPeriod) {
        this.prBiddingPeriod = prBiddingPeriod;
    }

    public String getPrBidWinning() {
        return prBidWinning;
    }

    public void setPrBidWinning(String prBidWinning) {
        this.prBidWinning = prBidWinning;
    }

    public String getPrTitle() {
        return prTitle;
    }

    public void setPrTitle(String prTitle) {
        this.prTitle = prTitle;
    }

    public Date getPrTime() {
        return prTime;
    }

    public void setPrTime(Date prTime) {
        this.prTime = prTime;
    }

    public String getPrContentFile() {
        return prContentFile;
    }

    public void setPrContentFile(String prContentFile) {
        this.prContentFile = prContentFile;
    }

    public List<SecPublicityCpyDto> getSecPublicityCpys() {
        return secPublicityCpys;
    }

    public void setSecPublicityCpys(List<SecPublicityCpyDto> secPublicityCpys) {
        this.secPublicityCpys = secPublicityCpys;
    }

    public String getPrTendereeName() {
        return prTendereeName;
    }

    public void setPrTendereeName(String prTendereeName) {
        this.prTendereeName = prTendereeName;
    }

    public String getPrBidWinnerName() {
        return prBidWinnerName;
    }

    public void setPrBidWinnerName(String prBidWinnerName) {
        this.prBidWinnerName = prBidWinnerName;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getSecOrganizeForm() {
        return secOrganizeForm;
    }

    public void setSecOrganizeForm(String secOrganizeForm) {
        this.secOrganizeForm = secOrganizeForm;
    }

    public String getSecBiddingType() {
        return secBiddingType;
    }

    public void setSecBiddingType(String secBiddingType) {
        this.secBiddingType = secBiddingType;
    }

    public Date getSecStartTime() {
        return secStartTime;
    }

    public void setSecStartTime(Date secStartTime) {
        this.secStartTime = secStartTime;
    }

    public Integer getSecBiddingCycle() {
        return secBiddingCycle;
    }

    public void setSecBiddingCycle(Integer secBiddingCycle) {
        this.secBiddingCycle = secBiddingCycle;
    }

    public String getSecPlace() {
        return secPlace;
    }

    public void setSecPlace(String secPlace) {
        this.secPlace = secPlace;
    }

    public String getTendereeName() {
        return tendereeName;
    }

    public void setTendereeName(String tendereeName) {
        this.tendereeName = tendereeName;
    }

    public String getpCompetentDepartmentName() {
        return pCompetentDepartmentName;
    }

    public void setpCompetentDepartmentName(String pCompetentDepartmentName) {
        this.pCompetentDepartmentName = pCompetentDepartmentName;
    }

    public String getPrTendereeNameHistory() {
        return prTendereeNameHistory;
    }

    public void setPrTendereeNameHistory(String prTendereeNameHistory) {
        this.prTendereeNameHistory = prTendereeNameHistory;
    }

    public String getPrBidWinnerNameHistory() {
        return prBidWinnerNameHistory;
    }

    public void setPrBidWinnerNameHistory(String prBidWinnerNameHistory) {
        this.prBidWinnerNameHistory = prBidWinnerNameHistory;
    }

    public List<SecProjectResponse> getSecProjects() {
        return secProjects;
    }

    public void setSecProjects(List<SecProjectResponse> secProjects) {
        this.secProjects = secProjects;
    }

    public String getPrTendereeAbbName() {
        return prTendereeAbbName;
    }

    public void setPrTendereeAbbName(String prTendereeAbbName) {
        this.prTendereeAbbName = prTendereeAbbName;
    }

    public String getPrBidWinAbbName() {
        return prBidWinAbbName;
    }

    public void setPrBidWinAbbName(String prBidWinAbbName) {
        this.prBidWinAbbName = prBidWinAbbName;
    }

    public String getTendereeAbbName() {
        return tendereeAbbName;
    }

    public void setTendereeAbbName(String tendereeAbbName) {
        this.tendereeAbbName = tendereeAbbName;
    }

    public String getProjectCategory() {
        return projectCategory;
    }

    public void setProjectCategory(String projectCategory) {
        this.projectCategory = projectCategory;
    }

    public String getProjectInitiatingUnit() {
        return projectInitiatingUnit;
    }

    public void setProjectInitiatingUnit(String projectInitiatingUnit) {
        this.projectInitiatingUnit = projectInitiatingUnit;
    }
}