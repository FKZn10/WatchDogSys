package com.jpmsoft.common.model.requestAndResponse.SecInfo;

import com.jpmsoft.common.model.BaseFileRequest;
import com.jpmsoft.common.model.response.SecInfo.SecProjectResponse;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 过多投标人淘汰
 */
public class SecBidderEliminationModel extends BaseFileRequest {

    private String sbeId;
    private String secId;
    private Integer sbeNumberBidders;
    private Integer sbeNumberQualified;
    private Integer sbeNumberBidLinks;
    private String sbeBidSummaryFile;
    private String sbeNumberFinalists;
    private String sbeCreateUid;
    private String sbeCreateUname;
    private Date sbeCreateTime;
    private Integer sbeState;
    private Date sbeStateTime;

    private String sbeBidSummaryFileIds;
    private String sbeNumberFinalistsIds;
    private String secName;
    private String secType;
    private BigDecimal nAmount;
    private String nScope;
    private Date secStartTime;
    private Date secEndTime;
    private List<SecProjectResponse> secProjects;
    private List<String> secStartTimes;
    private List<String> nAmounts;
    private List<String> sbeBidders;
    private List<String> sbeQualifieds;
    private List<String> sbeBidLinks;
    private List<String> sbeCreateTimes;



    public String getSbeId() {
        return sbeId;
    }

    public void setSbeId(String sbeId) {
        this.sbeId = sbeId;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public Integer getSbeNumberBidders() {
        return sbeNumberBidders;
    }

    public void setSbeNumberBidders(Integer sbeNumberBidders) {
        this.sbeNumberBidders = sbeNumberBidders;
    }

    public Integer getSbeNumberQualified() {
        return sbeNumberQualified;
    }

    public void setSbeNumberQualified(Integer sbeNumberQualified) {
        this.sbeNumberQualified = sbeNumberQualified;
    }

    public Integer getSbeNumberBidLinks() {
        return sbeNumberBidLinks;
    }

    public void setSbeNumberBidLinks(Integer sbeNumberBidLinks) {
        this.sbeNumberBidLinks = sbeNumberBidLinks;
    }

    public String getSbeBidSummaryFile() {
        return sbeBidSummaryFile;
    }

    public void setSbeBidSummaryFile(String sbeBidSummaryFile) {
        this.sbeBidSummaryFile = sbeBidSummaryFile;
    }

    public String getSbeNumberFinalists() {
        return sbeNumberFinalists;
    }

    public void setSbeNumberFinalists(String sbeNumberFinalists) {
        this.sbeNumberFinalists = sbeNumberFinalists;
    }

    public String getSbeCreateUid() {
        return sbeCreateUid;
    }

    public void setSbeCreateUid(String sbeCreateUid) {
        this.sbeCreateUid = sbeCreateUid;
    }

    public String getSbeCreateUname() {
        return sbeCreateUname;
    }

    public void setSbeCreateUname(String sbeCreateUname) {
        this.sbeCreateUname = sbeCreateUname;
    }

    public Date getSbeCreateTime() {
        return sbeCreateTime;
    }

    public void setSbeCreateTime(Date sbeCreateTime) {
        this.sbeCreateTime = sbeCreateTime;
    }

    public Integer getSbeState() {
        return sbeState;
    }

    public void setSbeState(Integer sbeState) {
        this.sbeState = sbeState;
    }

    public Date getSbeStateTime() {
        return sbeStateTime;
    }

    public void setSbeStateTime(Date sbeStateTime) {
        this.sbeStateTime = sbeStateTime;
    }

    public String getSbeBidSummaryFileIds() {
        return sbeBidSummaryFileIds;
    }

    public void setSbeBidSummaryFileIds(String sbeBidSummaryFileIds) {
        this.sbeBidSummaryFileIds = sbeBidSummaryFileIds;
    }

    public String getSbeNumberFinalistsIds() {
        return sbeNumberFinalistsIds;
    }

    public void setSbeNumberFinalistsIds(String sbeNumberFinalistsIds) {
        this.sbeNumberFinalistsIds = sbeNumberFinalistsIds;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public List<SecProjectResponse> getSecProjects() {
        return secProjects;
    }

    public void setSecProjects(List<SecProjectResponse> secProjects) {
        this.secProjects = secProjects;
    }

    public BigDecimal getnAmount() {
        return nAmount;
    }

    public void setnAmount(BigDecimal nAmount) {
        this.nAmount = nAmount;
    }

    public String getnScope() {
        return nScope;
    }

    public void setnScope(String nScope) {
        this.nScope = nScope;
    }

    public Date getSecStartTime() {
        return secStartTime;
    }

    public void setSecStartTime(Date secStartTime) {
        this.secStartTime = secStartTime;
    }

    public Date getSecEndTime() {
        return secEndTime;
    }

    public void setSecEndTime(Date secEndTime) {
        this.secEndTime = secEndTime;
    }

    public String getSecType() {
        return secType;
    }

    public void setSecType(String secType) {
        this.secType = secType;
    }

    public List<String> getSecStartTimes() {
        return secStartTimes;
    }

    public void setSecStartTimes(List<String> secStartTimes) {
        this.secStartTimes = secStartTimes;
    }

    public List<String> getnAmounts() {
        return nAmounts;
    }

    public void setnAmounts(List<String> nAmounts) {
        this.nAmounts = nAmounts;
    }

    public List<String> getSbeBidders() {
        return sbeBidders;
    }

    public void setSbeBidders(List<String> sbeBidders) {
        this.sbeBidders = sbeBidders;
    }

    public List<String> getSbeQualifieds() {
        return sbeQualifieds;
    }

    public void setSbeQualifieds(List<String> sbeQualifieds) {
        this.sbeQualifieds = sbeQualifieds;
    }

    public List<String> getSbeBidLinks() {
        return sbeBidLinks;
    }

    public void setSbeBidLinks(List<String> sbeBidLinks) {
        this.sbeBidLinks = sbeBidLinks;
    }

    public List<String> getSbeCreateTimes() {
        return sbeCreateTimes;
    }

    public void setSbeCreateTimes(List<String> sbeCreateTimes) {
        this.sbeCreateTimes = sbeCreateTimes;
    }
}
