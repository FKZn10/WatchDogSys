package com.jpmsoft.common.model.request.SecInfo;

import com.jpmsoft.common.model.BaseFileRequest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 中标结果公示
 */
public class SecPublicityResultsRequest extends BaseFileRequest {

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

    private String prContentFileIds;
    private List<SecPublicityCpyDto> secPublicityCpys;
    private String prResultRegistrationIds;
    private String prArmorApplicationIds;
    private String secName;
    private String prTendereeName;
    private String prBidWinnerName;
    private List<String> prBidAmounts;
    private List<String> prCreateTimes;
    private List<String> prStartTimes;
    private List<String> prEndTimes;
    private String pName;
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

    public String getPrContentFileIds() {
        return prContentFileIds;
    }

    public void setPrContentFileIds(String prContentFileIds) {
        this.prContentFileIds = prContentFileIds;
    }

    public List<SecPublicityCpyDto> getSecPublicityCpys() {
        return secPublicityCpys;
    }

    public void setSecPublicityCpys(List<SecPublicityCpyDto> secPublicityCpys) {
        this.secPublicityCpys = secPublicityCpys;
    }

    public String getPrResultRegistrationIds() {
        return prResultRegistrationIds;
    }

    public void setPrResultRegistrationIds(String prResultRegistrationIds) {
        this.prResultRegistrationIds = prResultRegistrationIds;
    }

    public String getPrArmorApplicationIds() {
        return prArmorApplicationIds;
    }

    public void setPrArmorApplicationIds(String prArmorApplicationIds) {
        this.prArmorApplicationIds = prArmorApplicationIds;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
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

    public List<String> getPrBidAmounts() {
        return prBidAmounts;
    }

    public void setPrBidAmounts(List<String> prBidAmounts) {
        this.prBidAmounts = prBidAmounts;
    }

    public List<String> getPrCreateTimes() {
        return prCreateTimes;
    }

    public void setPrCreateTimes(List<String> prCreateTimes) {
        this.prCreateTimes = prCreateTimes;
    }

    public List<String> getPrStartTimes() {
        return prStartTimes;
    }

    public void setPrStartTimes(List<String> prStartTimes) {
        this.prStartTimes = prStartTimes;
    }

    public List<String> getPrEndTimes() {
        return prEndTimes;
    }

    public void setPrEndTimes(List<String> prEndTimes) {
        this.prEndTimes = prEndTimes;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
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