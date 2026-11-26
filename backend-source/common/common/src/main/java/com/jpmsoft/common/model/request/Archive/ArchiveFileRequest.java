package com.jpmsoft.common.model.request.Archive;

import java.util.Date;

/**
 * 归档文件信息
 */
public class ArchiveFileRequest {
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
    /**
     * 手动归档：0   人为上传
     * 自动归档：1   拷贝文件
     */
    private Integer afFrom;

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

    public Integer getAfFrom() {
        return afFrom;
    }

    public void setAfFrom(Integer afFrom) {
        this.afFrom = afFrom;
    }
}
