package com.jpmsoft.common.model.response;


import java.util.Date;

public class FileResponse {

    private String uid;
    private String name;
    private String status = "done";
    private String url;
    private String openUrl;
    private String relationid;
    private Date uploadTime;
    private Long fileSize;
    private String fileSuffix;
    //上传时录入信息
    private String fileInputName;
    private String fileInputCode;
    private Date fileInputTime;
    private String fileInputVersion;

    public String getRelationid() {
        return relationid;
    }

    public void setRelationid(String relationid) {
        this.relationid = relationid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOpenUrl() {
        return openUrl;
    }

    public void setOpenUrl(String openUrl) {
        this.openUrl = openUrl;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileSuffix() {
        return fileSuffix;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }

    public String getFileInputName() {
        return fileInputName;
    }

    public void setFileInputName(String fileInputName) {
        this.fileInputName = fileInputName;
    }

    public String getFileInputCode() {
        return fileInputCode;
    }

    public void setFileInputCode(String fileInputCode) {
        this.fileInputCode = fileInputCode;
    }

    public Date getFileInputTime() {
        return fileInputTime;
    }

    public void setFileInputTime(Date fileInputTime) {
        this.fileInputTime = fileInputTime;
    }

    public String getFileInputVersion() {
        return fileInputVersion;
    }

    public void setFileInputVersion(String fileInputVersion) {
        this.fileInputVersion = fileInputVersion;
    }
}
