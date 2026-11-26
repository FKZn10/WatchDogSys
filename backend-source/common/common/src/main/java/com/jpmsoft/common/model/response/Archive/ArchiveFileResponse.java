package com.jpmsoft.common.model.response.Archive;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseFileResponse;

import java.util.Date;

/**
 * 归档文件信息
 */
public class ArchiveFileResponse extends BaseFileResponse {

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
    private String afAnnexName;
    private Integer afFrom;
    private String  fileInputVersion;
    private String  fileCreateUid;
    private String  fileCreateUname;
    private String fileMd5Code;
    private String adId;
    private String adName;
    @JsonProperty("pId")
    private String pId;
    @JsonProperty("pName")
    private String pName;
    private Integer apState;

    public String getAdName() {
        return adName;
    }

    public void setAdName(String adName) {
        this.adName = adName;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public Integer getApState() {
        return apState;
    }

    public void setApState(Integer apState) {
        this.apState = apState;
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

    public String getAfAnnexName() {
        return afAnnexName;
    }

    public void setAfAnnexName(String afAnnexName) {
        this.afAnnexName = afAnnexName;
    }

    public Integer getAfFrom() {
        return afFrom;
    }

    public void setAfFrom(Integer afFrom) {
        this.afFrom = afFrom;
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

    public String getFileMd5Code() {
        return fileMd5Code;
    }

    public void setFileMd5Code(String fileMd5Code) {
        this.fileMd5Code = fileMd5Code;
    }

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }
}
