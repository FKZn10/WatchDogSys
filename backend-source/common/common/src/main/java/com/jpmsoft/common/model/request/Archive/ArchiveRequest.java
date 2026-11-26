package com.jpmsoft.common.model.request.Archive;

import com.jpmsoft.common.model.BaseModeRequest;

import java.util.Date;
import java.util.List;

/**
 * 项目档案归档信息
 */

public class ArchiveRequest extends BaseModeRequest {

    private String apId;
    private String adId;
    private String pId;
    private String apPersonUid;
    private String apPersonUname;
    private Integer apState;
    private String apTpyeCode;
    private String apReceiptNo;
    private Date apReceiptTime;
    private String apDocumentNo;
    private String apAuthor;
    private String apTitle;
    private Date apWrittenDate;
    private Integer apPageNumber;
    private Integer apCopies;
    private Integer apA4PaperNum;
    private String apPosition;
    private String apKeyword;
    private String apRemark;
    private String apDistribute;
    private String apEntityDirection;
    private String apDescCreateUid;
    private String apDescCreateUname;
    private Date apDescCreateTime;
    private String apEntryMethod;
    private String apSecrecyLevel;
    private Integer apRetentionPeriod;
    private String apTransferRemark;
    private String apTransferUid;
    private String apTransferUname;
    private String apReceiverUid;
    private String apReceiverUname;
    private Integer apTransferState;
    private Date apTransferTime;
    private String apTransferCreateUid;
    private String apTransferCreateUname;
    private Date apTransferCreateTime;
    private Date apApprovalDate;
    private String apIssueCompany;

    /***********************************************/
    private String pName;
    private String fileCode;

    private List<ArchiveFileRequest> archiveFiles;

    public String getApId() {
        return apId;
    }

    public void setApId(String apId) {
        this.apId = apId;
    }

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getApPersonUid() {
        return apPersonUid;
    }

    public void setApPersonUid(String apPersonUid) {
        this.apPersonUid = apPersonUid;
    }

    public String getApPersonUname() {
        return apPersonUname;
    }

    public void setApPersonUname(String apPersonUname) {
        this.apPersonUname = apPersonUname;
    }

    public Integer getApState() {
        return apState;
    }

    public void setApState(Integer apState) {
        this.apState = apState;
    }

    public String getApTpyeCode() {
        return apTpyeCode;
    }

    public void setApTpyeCode(String apTpyeCode) {
        this.apTpyeCode = apTpyeCode;
    }

    public String getApReceiptNo() {
        return apReceiptNo;
    }

    public void setApReceiptNo(String apReceiptNo) {
        this.apReceiptNo = apReceiptNo;
    }

    public Date getApReceiptTime() {
        return apReceiptTime;
    }

    public void setApReceiptTime(Date apReceiptTime) {
        this.apReceiptTime = apReceiptTime;
    }

    public String getApDocumentNo() {
        return apDocumentNo;
    }

    public void setApDocumentNo(String apDocumentNo) {
        this.apDocumentNo = apDocumentNo;
    }

    public String getApAuthor() {
        return apAuthor;
    }

    public void setApAuthor(String apAuthor) {
        this.apAuthor = apAuthor;
    }

    public String getApTitle() {
        return apTitle;
    }

    public void setApTitle(String apTitle) {
        this.apTitle = apTitle;
    }

    public Date getApWrittenDate() {
        return apWrittenDate;
    }

    public void setApWrittenDate(Date apWrittenDate) {
        this.apWrittenDate = apWrittenDate;
    }

    public Integer getApPageNumber() {
        return apPageNumber;
    }

    public void setApPageNumber(Integer apPageNumber) {
        this.apPageNumber = apPageNumber;
    }

    public Integer getApCopies() {
        return apCopies;
    }

    public void setApCopies(Integer apCopies) {
        this.apCopies = apCopies;
    }

    public Integer getApA4PaperNum() {
        return apA4PaperNum;
    }

    public void setApA4PaperNum(Integer apA4PaperNum) {
        this.apA4PaperNum = apA4PaperNum;
    }

    public String getApPosition() {
        return apPosition;
    }

    public void setApPosition(String apPosition) {
        this.apPosition = apPosition;
    }

    public String getApKeyword() {
        return apKeyword;
    }

    public void setApKeyword(String apKeyword) {
        this.apKeyword = apKeyword;
    }

    public String getApRemark() {
        return apRemark;
    }

    public void setApRemark(String apRemark) {
        this.apRemark = apRemark;
    }

    public String getApDistribute() {
        return apDistribute;
    }

    public void setApDistribute(String apDistribute) {
        this.apDistribute = apDistribute;
    }

    public String getApEntityDirection() {
        return apEntityDirection;
    }

    public void setApEntityDirection(String apEntityDirection) {
        this.apEntityDirection = apEntityDirection;
    }

    public String getApDescCreateUid() {
        return apDescCreateUid;
    }

    public void setApDescCreateUid(String apDescCreateUid) {
        this.apDescCreateUid = apDescCreateUid;
    }

    public String getApDescCreateUname() {
        return apDescCreateUname;
    }

    public void setApDescCreateUname(String apDescCreateUname) {
        this.apDescCreateUname = apDescCreateUname;
    }

    public Date getApDescCreateTime() {
        return apDescCreateTime;
    }

    public void setApDescCreateTime(Date apDescCreateTime) {
        this.apDescCreateTime = apDescCreateTime;
    }

    public String getApEntryMethod() {
        return apEntryMethod;
    }

    public void setApEntryMethod(String apEntryMethod) {
        this.apEntryMethod = apEntryMethod;
    }

    public String getApSecrecyLevel() {
        return apSecrecyLevel;
    }

    public void setApSecrecyLevel(String apSecrecyLevel) {
        this.apSecrecyLevel = apSecrecyLevel;
    }

    public Integer getApRetentionPeriod() {
        return apRetentionPeriod;
    }

    public void setApRetentionPeriod(Integer apRetentionPeriod) {
        this.apRetentionPeriod = apRetentionPeriod;
    }

    public String getApTransferRemark() {
        return apTransferRemark;
    }

    public void setApTransferRemark(String apTransferRemark) {
        this.apTransferRemark = apTransferRemark;
    }

    public String getApTransferUid() {
        return apTransferUid;
    }

    public void setApTransferUid(String apTransferUid) {
        this.apTransferUid = apTransferUid;
    }

    public String getApTransferUname() {
        return apTransferUname;
    }

    public void setApTransferUname(String apTransferUname) {
        this.apTransferUname = apTransferUname;
    }

    public String getApReceiverUid() {
        return apReceiverUid;
    }

    public void setApReceiverUid(String apReceiverUid) {
        this.apReceiverUid = apReceiverUid;
    }

    public String getApReceiverUname() {
        return apReceiverUname;
    }

    public void setApReceiverUname(String apReceiverUname) {
        this.apReceiverUname = apReceiverUname;
    }

    public Integer getApTransferState() {
        return apTransferState;
    }

    public void setApTransferState(Integer apTransferState) {
        this.apTransferState = apTransferState;
    }

    public Date getApTransferTime() {
        return apTransferTime;
    }

    public void setApTransferTime(Date apTransferTime) {
        this.apTransferTime = apTransferTime;
    }

    public String getApTransferCreateUid() {
        return apTransferCreateUid;
    }

    public void setApTransferCreateUid(String apTransferCreateUid) {
        this.apTransferCreateUid = apTransferCreateUid;
    }

    public String getApTransferCreateUname() {
        return apTransferCreateUname;
    }

    public void setApTransferCreateUname(String apTransferCreateUname) {
        this.apTransferCreateUname = apTransferCreateUname;
    }

    public Date getApTransferCreateTime() {
        return apTransferCreateTime;
    }

    public void setApTransferCreateTime(Date apTransferCreateTime) {
        this.apTransferCreateTime = apTransferCreateTime;
    }


    public Date getApApprovalDate() {
        return apApprovalDate;
    }

    public void setApApprovalDate(Date apApprovalDate) {
        this.apApprovalDate = apApprovalDate;
    }

    public String getApIssueCompany() {
        return apIssueCompany;
    }

    public void setApIssueCompany(String apIssueCompany) {
        this.apIssueCompany = apIssueCompany;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public List<ArchiveFileRequest> getArchiveFiles() {
        return archiveFiles;
    }

    public void setArchiveFiles(List<ArchiveFileRequest> archiveFiles) {
        this.archiveFiles = archiveFiles;
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }
}
