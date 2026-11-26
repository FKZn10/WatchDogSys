package com.jpmsoft.common.model.response.SecInfo;

import com.jpmsoft.common.model.BaseFileRequest;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecOpenResultCandidateModel;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecOpenResultListModel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 开标结果
 */
public class SecOpenResultResponse extends BaseFileRequest {

    private String orId;
    private String secId;
    private Integer orEmergency;
    private Date orEndDate;
    private Date orOpenDate;
    private String orRemark;
    private String orProcurementAgency;
    private String orProcurementAgencyName;
    private String orPlaceBidOpening;
    private String orOpeningDocuments;
    private String orCreateUid;
    private String orCreateUname;
    private Date orCreateTime;
    private Integer orState;
    private Date orStateTime;
    private String orBulletinPlatform;
    private String orCandidateOne;
    private String orCandidateTwo;
    private String orCandidateThree;
    private Integer orApplyNum;
    private Integer orValidQuote;
    private BigDecimal orQuoteOne;
    private BigDecimal orQuoteTwo;
    private BigDecimal orQuoteThree;
    private Date orBulletinStart;
    private Date orBulletinEnd;
    private Date orBulletinResStart;
    private Date orBulletinResEnd;
    private String orBidMethod;
    private Date orBidStart;
    private Date orBidEnd;
    private String orPmOne;
    private String orPmTwo;
    private String orPmThree;
    private Integer orBidNum;
    //是否联合体投标 1、是 0、否
    private Integer orCoalitionBid;

    private String secName;
    private String pName;
    private String secType;
    private String secOrganizeForm;
    private String secBiddingType;
    private Date secStartTime;
    private Integer secBiddingCycle;
    private String secPlace;

    private String tendereeName;
    private BigDecimal dAmount;
    private String  pCompetentDepartmentName;
    private List<SecProjectResponse> secProjects;
    private List<SecOpenResultListModel> secOpenResultLists;
    //开评标结果候选人
    private List<SecOpenResultCandidateModel> secOpenResultCandidateList;
    //单位简称
    private String tendereeAbbName;
    private String secAgencyName;



    public String getOrId() {
        return orId;
    }

    public void setOrId(String orId) {
        this.orId = orId;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public Integer getOrEmergency() {
        return orEmergency;
    }

    public void setOrEmergency(Integer orEmergency) {
        this.orEmergency = orEmergency;
    }

    public Date getOrEndDate() {
        return orEndDate;
    }

    public void setOrEndDate(Date orEndDate) {
        this.orEndDate = orEndDate;
    }

    public Date getOrOpenDate() {
        return orOpenDate;
    }

    public void setOrOpenDate(Date orOpenDate) {
        this.orOpenDate = orOpenDate;
    }

    public String getOrRemark() {
        return orRemark;
    }

    public void setOrRemark(String orRemark) {
        this.orRemark = orRemark;
    }

    public String getOrProcurementAgency() {
        return orProcurementAgency;
    }

    public void setOrProcurementAgency(String orProcurementAgency) {
        this.orProcurementAgency = orProcurementAgency;
    }

    public String getOrProcurementAgencyName() {
        return orProcurementAgencyName;
    }

    public void setOrProcurementAgencyName(String orProcurementAgencyName) {
        this.orProcurementAgencyName = orProcurementAgencyName;
    }

    public String getOrPlaceBidOpening() {
        return orPlaceBidOpening;
    }

    public void setOrPlaceBidOpening(String orPlaceBidOpening) {
        this.orPlaceBidOpening = orPlaceBidOpening;
    }

    public String getOrOpeningDocuments() {
        return orOpeningDocuments;
    }

    public void setOrOpeningDocuments(String orOpeningDocuments) {
        this.orOpeningDocuments = orOpeningDocuments;
    }

    public String getOrCreateUid() {
        return orCreateUid;
    }

    public void setOrCreateUid(String orCreateUid) {
        this.orCreateUid = orCreateUid;
    }

    public String getOrCreateUname() {
        return orCreateUname;
    }

    public void setOrCreateUname(String orCreateUname) {
        this.orCreateUname = orCreateUname;
    }

    public Date getOrCreateTime() {
        return orCreateTime;
    }

    public void setOrCreateTime(Date orCreateTime) {
        this.orCreateTime = orCreateTime;
    }

    public Integer getOrState() {
        return orState;
    }

    public void setOrState(Integer orState) {
        this.orState = orState;
    }

    public Date getOrStateTime() {
        return orStateTime;
    }

    public void setOrStateTime(Date orStateTime) {
        this.orStateTime = orStateTime;
    }

    public String getOrBulletinPlatform() {
        return orBulletinPlatform;
    }

    public void setOrBulletinPlatform(String orBulletinPlatform) {
        this.orBulletinPlatform = orBulletinPlatform;
    }

    public String getOrCandidateOne() {
        return orCandidateOne;
    }

    public void setOrCandidateOne(String orCandidateOne) {
        this.orCandidateOne = orCandidateOne;
    }

    public String getOrCandidateTwo() {
        return orCandidateTwo;
    }

    public void setOrCandidateTwo(String orCandidateTwo) {
        this.orCandidateTwo = orCandidateTwo;
    }

    public String getOrCandidateThree() {
        return orCandidateThree;
    }

    public void setOrCandidateThree(String orCandidateThree) {
        this.orCandidateThree = orCandidateThree;
    }

    public Integer getOrApplyNum() {
        return orApplyNum;
    }

    public void setOrApplyNum(Integer orApplyNum) {
        this.orApplyNum = orApplyNum;
    }

    public Integer getOrValidQuote() {
        return orValidQuote;
    }

    public void setOrValidQuote(Integer orValidQuote) {
        this.orValidQuote = orValidQuote;
    }

    public BigDecimal getOrQuoteOne() {
        return orQuoteOne;
    }

    public void setOrQuoteOne(BigDecimal orQuoteOne) {
        this.orQuoteOne = orQuoteOne;
    }

    public BigDecimal getOrQuoteTwo() {
        return orQuoteTwo;
    }

    public void setOrQuoteTwo(BigDecimal orQuoteTwo) {
        this.orQuoteTwo = orQuoteTwo;
    }

    public BigDecimal getOrQuoteThree() {
        return orQuoteThree;
    }

    public void setOrQuoteThree(BigDecimal orQuoteThree) {
        this.orQuoteThree = orQuoteThree;
    }

    public Date getOrBulletinStart() {
        return orBulletinStart;
    }

    public void setOrBulletinStart(Date orBulletinStart) {
        this.orBulletinStart = orBulletinStart;
    }

    public Date getOrBulletinEnd() {
        return orBulletinEnd;
    }

    public void setOrBulletinEnd(Date orBulletinEnd) {
        this.orBulletinEnd = orBulletinEnd;
    }

    public Date getOrBulletinResStart() {
        return orBulletinResStart;
    }

    public void setOrBulletinResStart(Date orBulletinResStart) {
        this.orBulletinResStart = orBulletinResStart;
    }

    public Date getOrBulletinResEnd() {
        return orBulletinResEnd;
    }

    public void setOrBulletinResEnd(Date orBulletinResEnd) {
        this.orBulletinResEnd = orBulletinResEnd;
    }

    public String getOrBidMethod() {
        return orBidMethod;
    }

    public void setOrBidMethod(String orBidMethod) {
        this.orBidMethod = orBidMethod;
    }

    public Date getOrBidStart() {
        return orBidStart;
    }

    public void setOrBidStart(Date orBidStart) {
        this.orBidStart = orBidStart;
    }

    public Date getOrBidEnd() {
        return orBidEnd;
    }

    public void setOrBidEnd(Date orBidEnd) {
        this.orBidEnd = orBidEnd;
    }

    public String getOrPmOne() {
        return orPmOne;
    }

    public void setOrPmOne(String orPmOne) {
        this.orPmOne = orPmOne;
    }

    public String getOrPmTwo() {
        return orPmTwo;
    }

    public void setOrPmTwo(String orPmTwo) {
        this.orPmTwo = orPmTwo;
    }

    public String getOrPmThree() {
        return orPmThree;
    }

    public void setOrPmThree(String orPmThree) {
        this.orPmThree = orPmThree;
    }

    public Integer getOrBidNum() {
        return orBidNum;
    }

    public void setOrBidNum(Integer orBidNum) {
        this.orBidNum = orBidNum;
    }

    public Integer getOrCoalitionBid() {
        return orCoalitionBid;
    }

    public void setOrCoalitionBid(Integer orCoalitionBid) {
        this.orCoalitionBid = orCoalitionBid;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getSecType() {
        return secType;
    }

    public void setSecType(String secType) {
        this.secType = secType;
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

    public BigDecimal getdAmount() {
        return dAmount;
    }

    public void setdAmount(BigDecimal dAmount) {
        this.dAmount = dAmount;
    }

    public String getpCompetentDepartmentName() {
        return pCompetentDepartmentName;
    }

    public void setpCompetentDepartmentName(String pCompetentDepartmentName) {
        this.pCompetentDepartmentName = pCompetentDepartmentName;
    }

    public List<SecProjectResponse> getSecProjects() {
        return secProjects;
    }

    public void setSecProjects(List<SecProjectResponse> secProjects) {
        this.secProjects = secProjects;
    }

    public List<SecOpenResultListModel> getSecOpenResultLists() {
        return secOpenResultLists;
    }

    public void setSecOpenResultLists(List<SecOpenResultListModel> secOpenResultLists) {
        this.secOpenResultLists = secOpenResultLists;
    }

    public List<SecOpenResultCandidateModel> getSecOpenResultCandidateList() {
        return secOpenResultCandidateList;
    }

    public void setSecOpenResultCandidateList(List<SecOpenResultCandidateModel> secOpenResultCandidateList) {
        this.secOpenResultCandidateList = secOpenResultCandidateList;
    }

    public String getTendereeAbbName() {
        return tendereeAbbName;
    }

    public void setTendereeAbbName(String tendereeAbbName) {
        this.tendereeAbbName = tendereeAbbName;
    }

    public String getSecAgencyName() {
        return secAgencyName;
    }

    public void setSecAgencyName(String secAgencyName) {
        this.secAgencyName = secAgencyName;
    }
}
