package com.jpmsoft.jpbpm.pojo.dto;

import java.util.List;

public class ApprovalRecordAuthorityDto {
    public  String fileId;
    public List<String> tCode;
    public String secId;
    public String pId;
    public String conId;
    public String year;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public List<String> gettCode() {
        return tCode;
    }

    public void settCode(List<String> tCode) {
        this.tCode = tCode;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}

