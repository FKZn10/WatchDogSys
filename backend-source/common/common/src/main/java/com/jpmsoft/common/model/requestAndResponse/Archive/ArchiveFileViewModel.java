package com.jpmsoft.common.model.requestAndResponse.Archive;

import com.jpmsoft.common.model.BaseModeRequest;

import java.util.Date;

/**
 * 文件查看记录
 */

public class ArchiveFileViewModel extends BaseModeRequest {

    private String afvId;
    private String afId;
    private String afvUid;
    private String afvUname;
    private Date afvTime;

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

    public String getAfvId() {
        return afvId;
    }

    public void setAfvId(String afvId) {
        this.afvId = afvId;
    }

    public String getAfId() {
        return afId;
    }

    public void setAfId(String afId) {
        this.afId = afId;
    }

    public String getAfvUid() {
        return afvUid;
    }

    public void setAfvUid(String afvUid) {
        this.afvUid = afvUid;
    }

    public String getAfvUname() {
        return afvUname;
    }

    public void setAfvUname(String afvUname) {
        this.afvUname = afvUname;
    }

    public Date getAfvTime() {
        return afvTime;
    }

    public void setAfvTime(Date afvTime) {
        this.afvTime = afvTime;
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
}
