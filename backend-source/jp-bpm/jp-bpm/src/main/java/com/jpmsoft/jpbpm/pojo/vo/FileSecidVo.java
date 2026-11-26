package com.jpmsoft.jpbpm.pojo.vo;

public class FileSecidVo {
    public  String typeName;
    public Object fileList;
    public String authority;

    public Integer fileflowstatus;

    public String describe;
    public Integer processingStatus;
    public Integer getFileflowstatus() {
        return fileflowstatus;
    }

    public void setFileflowstatus(Integer fileflowstatus) {
        this.fileflowstatus = fileflowstatus;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Object getFileList() {
        return fileList;
    }

    public void setFileList(Object fileList) {
        this.fileList = fileList;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Integer getProcessingStatus() {
        return processingStatus;
    }

    public void setProcessingStatus(Integer processingStatus) {
        this.processingStatus = processingStatus;
    }
}
