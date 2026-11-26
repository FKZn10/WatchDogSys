package com.jpmsoft.common.model.requestAndResponse.Archive;

import com.jpmsoft.common.model.ProjectCommonRes;

import java.util.Date;
import java.util.List;

public class ArchiveStatisticsModel extends ProjectCommonRes {

    private String afId;
    private String apId;
    private Integer afOrder;
    private String afCode;
    private String afName;
    private String afType;
    private Integer afPageNumber;
    private Float afFileSize;
    private Integer afDpi;
    private String afCreateUid;
    private String afCreateUname;
    private Date afCreateTime;
    private String afFileId;
    private String afAnnexId;
    private Integer afState;
    private Integer apState;
    private Integer afFrom;
    private String adCode;
    private String adName;
    private String adType;
    private String apTitle;
    private String apAuthor;
    private String apKeyword;
    private String apPosition;
    private String apDocumentNo;
    private Date apWrittenDate;
    private String apIssueCompany;
    private Date apApprovalDate;
    private Date apReceiptTime;

    private String adId;

    private String tempName;
    private List<String> afFileSizes;
    private Date pConstructionStart;
    private Date pConstructionEnd;

    private String fileInputVersion;
    private String fileCreateUid;
    private String fileCreateUname;
    private List<String> pConstructionStarts;
    private List<String> apApprovalDates;
    private List<String> apReceiptTimes;
    private List<String> pConstructionEnds;
    private List<String> afCreateTimes;

    /**
     * 检索字段
     */
    private String retrieval;

    private String pUseUnitName;

    public String getpUseUnitName() {
        return pUseUnitName;
    }

    public void setpUseUnitName(String pUseUnitName) {
        this.pUseUnitName = pUseUnitName;
    }

    public String getAfId() {
        return afId;
    }

    public void setAfId(String afId) {
        this.afId = afId;
    }

    public String getApId() {
        return apId;
    }

    public void setApId(String apId) {
        this.apId = apId;
    }

    public Integer getAfOrder() {
        return afOrder;
    }

    public void setAfOrder(Integer afOrder) {
        this.afOrder = afOrder;
    }

    public String getAfCode() {
        return afCode;
    }

    public void setAfCode(String afCode) {
        this.afCode = afCode;
    }

    public String getAfName() {
        return afName;
    }

    public void setAfName(String afName) {
        this.afName = afName;
    }

    public String getAfType() {
        return afType;
    }

    public void setAfType(String afType) {
        this.afType = afType;
    }

    public Integer getAfPageNumber() {
        return afPageNumber;
    }

    public void setAfPageNumber(Integer afPageNumber) {
        this.afPageNumber = afPageNumber;
    }

    public Float getAfFileSize() {
        return afFileSize;
    }

    public void setAfFileSize(Float afFileSize) {
        this.afFileSize = afFileSize;
    }

    public Integer getAfDpi() {
        return afDpi;
    }

    public void setAfDpi(Integer afDpi) {
        this.afDpi = afDpi;
    }

    public String getAfCreateUid() {
        return afCreateUid;
    }

    public void setAfCreateUid(String afCreateUid) {
        this.afCreateUid = afCreateUid;
    }

    public String getAfCreateUname() {
        return afCreateUname;
    }

    public void setAfCreateUname(String afCreateUname) {
        this.afCreateUname = afCreateUname;
    }

    public Date getAfCreateTime() {
        return afCreateTime;
    }

    public void setAfCreateTime(Date afCreateTime) {
        this.afCreateTime = afCreateTime;
    }

    public String getAfFileId() {
        return afFileId;
    }

    public void setAfFileId(String afFileId) {
        this.afFileId = afFileId;
    }

    public String getAfAnnexId() {
        return afAnnexId;
    }

    public void setAfAnnexId(String afAnnexId) {
        this.afAnnexId = afAnnexId;
    }

    public Integer getAfState() {
        return afState;
    }

    public void setAfState(Integer afState) {
        this.afState = afState;
    }

    public Integer getApState() {
        return apState;
    }

    public void setApState(Integer apState) {
        this.apState = apState;
    }

    public Integer getAfFrom() {
        return afFrom;
    }

    public void setAfFrom(Integer afFrom) {
        this.afFrom = afFrom;
    }

    public String getAdCode() {
        return adCode;
    }

    public void setAdCode(String adCode) {
        this.adCode = adCode;
    }

    public String getAdName() {
        return adName;
    }

    public void setAdName(String adName) {
        this.adName = adName;
    }

    public String getAdType() {
        return adType;
    }

    public void setAdType(String adType) {
        this.adType = adType;
    }

    public String getApTitle() {
        return apTitle;
    }

    public void setApTitle(String apTitle) {
        this.apTitle = apTitle;
    }

    public String getApAuthor() {
        return apAuthor;
    }

    public void setApAuthor(String apAuthor) {
        this.apAuthor = apAuthor;
    }

    public String getApKeyword() {
        return apKeyword;
    }

    public void setApKeyword(String apKeyword) {
        this.apKeyword = apKeyword;
    }

    public String getApPosition() {
        return apPosition;
    }

    public void setApPosition(String apPosition) {
        this.apPosition = apPosition;
    }

    public String getApDocumentNo() {
        return apDocumentNo;
    }

    public void setApDocumentNo(String apDocumentNo) {
        this.apDocumentNo = apDocumentNo;
    }

    public Date getApWrittenDate() {
        return apWrittenDate;
    }

    public void setApWrittenDate(Date apWrittenDate) {
        this.apWrittenDate = apWrittenDate;
    }

    public String getApIssueCompany() {
        return apIssueCompany;
    }

    public void setApIssueCompany(String apIssueCompany) {
        this.apIssueCompany = apIssueCompany;
    }

    public Date getApApprovalDate() {
        return apApprovalDate;
    }

    public void setApApprovalDate(Date apApprovalDate) {
        this.apApprovalDate = apApprovalDate;
    }

    public Date getApReceiptTime() {
        return apReceiptTime;
    }

    public void setApReceiptTime(Date apReceiptTime) {
        this.apReceiptTime = apReceiptTime;
    }

    public List<String> getApApprovalDates() {
        return apApprovalDates;
    }

    public void setApApprovalDates(List<String> apApprovalDates) {
        this.apApprovalDates = apApprovalDates;
    }

    public List<String> getApReceiptTimes() {
        return apReceiptTimes;
    }

    public void setApReceiptTimes(List<String> apReceiptTimes) {
        this.apReceiptTimes = apReceiptTimes;
    }

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public String getTempName() {
        return tempName;
    }

    public void setTempName(String tempName) {
        this.tempName = tempName;
    }

    public List<String> getAfFileSizes() {
        return afFileSizes;
    }

    public void setAfFileSizes(List<String> afFileSizes) {
        this.afFileSizes = afFileSizes;
    }


    public Date getpConstructionStart() {
        return pConstructionStart;
    }

    public void setpConstructionStart(Date pConstructionStart) {
        this.pConstructionStart = pConstructionStart;
    }

    public Date getpConstructionEnd() {
        return pConstructionEnd;
    }

    public void setpConstructionEnd(Date pConstructionEnd) {
        this.pConstructionEnd = pConstructionEnd;
    }

    public String getFileInputVersion() {
        return fileInputVersion;
    }

    public void setFileInputVersion(String fileInputVersion) {
        this.fileInputVersion = fileInputVersion;
    }

    public String getFileCreateUid() {
        return fileCreateUid;
    }

    public void setFileCreateUid(String fileCreateUid) {
        this.fileCreateUid = fileCreateUid;
    }

    public String getFileCreateUname() {
        return fileCreateUname;
    }

    public void setFileCreateUname(String fileCreateUname) {
        this.fileCreateUname = fileCreateUname;
    }

    public List<String> getpConstructionStarts() {
        return pConstructionStarts;
    }

    public void setpConstructionStarts(List<String> pConstructionStarts) {
        this.pConstructionStarts = pConstructionStarts;
    }

    public List<String> getpConstructionEnds() {
        return pConstructionEnds;
    }

    public void setpConstructionEnds(List<String> pConstructionEnds) {
        this.pConstructionEnds = pConstructionEnds;
    }

    public List<String> getAfCreateTimes() {
        return afCreateTimes;
    }

    public void setAfCreateTimes(List<String> afCreateTimes) {
        this.afCreateTimes = afCreateTimes;
    }

    public String getRetrieval() {
        return retrieval;
    }

    public void setRetrieval(String retrieval) {
        this.retrieval = retrieval;
    }
}
