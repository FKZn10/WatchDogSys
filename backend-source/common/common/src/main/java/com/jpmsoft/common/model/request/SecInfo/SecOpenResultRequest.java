package com.jpmsoft.common.model.request.SecInfo;

import com.jpmsoft.common.model.BaseFileRequest;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecOpenResultCandidateModel;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecOpenResultListModel;
import com.jpmsoft.common.model.response.SecInfo.SecProjectResponse;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 开标结果
 */
public class SecOpenResultRequest extends BaseFileRequest {

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

    private String orOpeningDocumentsIds;
    private String secName;
    private String pName;
    private String secBiddingType;
    private List<String> orOpenDates;
    private List<String> orCreateTimes;
    private List<String> orBulletinStarts;
    private List<String> orBulletinEnds;
    private List<String>  orApplyNums;
    private List<String>  orValidQuotes;
    private List<String>  orQuoteOnes;
    private List<String>  orQuoteTwos;
    private List<String>  orQuoteThrees;
    private List<String>  orBulletinResStarts;
    private List<String>  orBulletinResEnds;
    private List<String>  secBiddingCycles;
    private List<SecOpenResultListModel> secOpenResultLists;
    //开评标结果候选人
    private List<SecOpenResultCandidateModel> secOpenResultCandidateList;

    private List<SecProjectResponse> secProjects;

    public List<SecProjectResponse> getSecProjects() {
        return secProjects;
    }

    public void setSecProjects(List<SecProjectResponse> secProjects) {
        this.secProjects = secProjects;
    }

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

    public String getOrOpeningDocumentsIds() {
        return orOpeningDocumentsIds;
    }

    public void setOrOpeningDocumentsIds(String orOpeningDocumentsIds) {
        this.orOpeningDocumentsIds = orOpeningDocumentsIds;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getSecBiddingType() {
        return secBiddingType;
    }

    public void setSecBiddingType(String secBiddingType) {
        this.secBiddingType = secBiddingType;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public List<String> getOrOpenDates() {
        return orOpenDates;
    }

    public void setOrOpenDates(List<String> orOpenDates) {
        this.orOpenDates = orOpenDates;
    }

    public List<String> getOrCreateTimes() {
        return orCreateTimes;
    }

    public void setOrCreateTimes(List<String> orCreateTimes) {
        this.orCreateTimes = orCreateTimes;
    }


    public List<String> getOrBulletinStarts() {
        return orBulletinStarts;
    }

    public void setOrBulletinStarts(List<String> orBulletinStarts) {
        this.orBulletinStarts = orBulletinStarts;
    }

    public List<String> getOrBulletinEnds() {
        return orBulletinEnds;
    }

    public void setOrBulletinEnds(List<String> orBulletinEnds) {
        this.orBulletinEnds = orBulletinEnds;
    }

    public List<String> getOrApplyNums() {
        return orApplyNums;
    }

    public void setOrApplyNums(List<String> orApplyNums) {
        this.orApplyNums = orApplyNums;
    }

    public List<String> getOrValidQuotes() {
        return orValidQuotes;
    }

    public void setOrValidQuotes(List<String> orValidQuotes) {
        this.orValidQuotes = orValidQuotes;
    }

    public List<String> getOrQuoteOnes() {
        return orQuoteOnes;
    }

    public void setOrQuoteOnes(List<String> orQuoteOnes) {
        this.orQuoteOnes = orQuoteOnes;
    }

    public List<String> getOrQuoteTwos() {
        return orQuoteTwos;
    }

    public void setOrQuoteTwos(List<String> orQuoteTwos) {
        this.orQuoteTwos = orQuoteTwos;
    }

    public List<String> getOrQuoteThrees() {
        return orQuoteThrees;
    }

    public void setOrQuoteThrees(List<String> orQuoteThrees) {
        this.orQuoteThrees = orQuoteThrees;
    }

    public List<String> getOrBulletinResStarts() {
        return orBulletinResStarts;
    }

    public void setOrBulletinResStarts(List<String> orBulletinResStarts) {
        this.orBulletinResStarts = orBulletinResStarts;
    }

    public List<String> getOrBulletinResEnds() {
        return orBulletinResEnds;
    }

    public void setOrBulletinResEnds(List<String> orBulletinResEnds) {
        this.orBulletinResEnds = orBulletinResEnds;
    }

    public List<String> getSecBiddingCycles() {
        return secBiddingCycles;
    }

    public void setSecBiddingCycles(List<String> secBiddingCycles) {
        this.secBiddingCycles = secBiddingCycles;
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
}
