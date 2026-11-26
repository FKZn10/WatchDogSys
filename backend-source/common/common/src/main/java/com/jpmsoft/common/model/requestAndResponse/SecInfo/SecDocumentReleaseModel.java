package com.jpmsoft.common.model.requestAndResponse.SecInfo;

import com.jpmsoft.common.model.BaseFileRequest;
import com.jpmsoft.common.model.response.SecInfo.SecProjectResponse;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 招标文件发布
 */
public class SecDocumentReleaseModel extends BaseFileRequest {

    private String sdrId;
    private String secId;
    private BigDecimal sdrStage;
    private Date sdrBidOpenTime;
    private BigDecimal sdrControlPrice;
    private String sdrUrl;
    private String sdrContent;
    private String sdrRemarks;
    private String sdrCreateUid;
    private String sdrCreateUname;
    private Date sdrCreateTime;
    private Integer state;
    private Date stateTime;

    private String secName;
    private String secCode;
    private String sdrBidYearMon;
    private List<String> sdrStages;
    private List<String> sdrBidOpenTimes;
    private List<String> sdrControlPrices;
    private List<String> sdrCreateTimes;
    private List<SecProjectResponse> secProjects;

    public String getSdrId() {
        return sdrId;
    }

    public void setSdrId(String sdrId) {
        this.sdrId = sdrId;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public BigDecimal getSdrStage() {
        return sdrStage;
    }

    public void setSdrStage(BigDecimal sdrStage) {
        this.sdrStage = sdrStage;
    }

    public Date getSdrBidOpenTime() {
        return sdrBidOpenTime;
    }

    public void setSdrBidOpenTime(Date sdrBidOpenTime) {
        this.sdrBidOpenTime = sdrBidOpenTime;
    }

    public BigDecimal getSdrControlPrice() {
        return sdrControlPrice;
    }

    public void setSdrControlPrice(BigDecimal sdrControlPrice) {
        this.sdrControlPrice = sdrControlPrice;
    }

    public String getSdrUrl() {
        return sdrUrl;
    }

    public void setSdrUrl(String sdrUrl) {
        this.sdrUrl = sdrUrl;
    }

    public String getSdrContent() {
        return sdrContent;
    }

    public void setSdrContent(String sdrContent) {
        this.sdrContent = sdrContent;
    }

    public String getSdrRemarks() {
        return sdrRemarks;
    }

    public void setSdrRemarks(String sdrRemarks) {
        this.sdrRemarks = sdrRemarks;
    }

    public String getSdrCreateUid() {
        return sdrCreateUid;
    }

    public void setSdrCreateUid(String sdrCreateUid) {
        this.sdrCreateUid = sdrCreateUid;
    }

    public String getSdrCreateUname() {
        return sdrCreateUname;
    }

    public void setSdrCreateUname(String sdrCreateUname) {
        this.sdrCreateUname = sdrCreateUname;
    }

    public Date getSdrCreateTime() {
        return sdrCreateTime;
    }

    public void setSdrCreateTime(Date sdrCreateTime) {
        this.sdrCreateTime = sdrCreateTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getStateTime() {
        return stateTime;
    }

    public void setStateTime(Date stateTime) {
        this.stateTime = stateTime;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getSecCode() {
        return secCode;
    }

    public void setSecCode(String secCode) {
        this.secCode = secCode;
    }

    public String getSdrBidYearMon() {
        return sdrBidYearMon;
    }

    public void setSdrBidYearMon(String sdrBidYearMon) {
        this.sdrBidYearMon = sdrBidYearMon;
    }

    public List<String> getSdrStages() {
        return sdrStages;
    }

    public void setSdrStages(List<String> sdrStages) {
        this.sdrStages = sdrStages;
    }

    public List<String> getSdrBidOpenTimes() {
        return sdrBidOpenTimes;
    }

    public void setSdrBidOpenTimes(List<String> sdrBidOpenTimes) {
        this.sdrBidOpenTimes = sdrBidOpenTimes;
    }

    public List<String> getSdrControlPrices() {
        return sdrControlPrices;
    }

    public void setSdrControlPrices(List<String> sdrControlPrices) {
        this.sdrControlPrices = sdrControlPrices;
    }

    public List<String> getSdrCreateTimes() {
        return sdrCreateTimes;
    }

    public void setSdrCreateTimes(List<String> sdrCreateTimes) {
        this.sdrCreateTimes = sdrCreateTimes;
    }

    public List<SecProjectResponse> getSecProjects() {
        return secProjects;
    }

    public void setSecProjects(List<SecProjectResponse> secProjects) {
        this.secProjects = secProjects;
    }
}
