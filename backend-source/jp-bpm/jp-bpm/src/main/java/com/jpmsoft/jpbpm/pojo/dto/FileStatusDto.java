package com.jpmsoft.jpbpm.pojo.dto;

public class FileStatusDto {
    public  String fileid;
    public Integer filestatus;
    public String invalidExplanation;
    public String tCode;
    public String year;

    public String getFileid() {
        return fileid;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid;
    }

    public Integer getFilestatus() {
        return filestatus;
    }

    public void setFilestatus(Integer filestatus) {
        this.filestatus = filestatus;
    }

    public String gettCode() {
        return tCode;
    }

    public void settCode(String tCode) {
        this.tCode = tCode;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getInvalidExplanation() {
        return invalidExplanation;
    }

    public void setInvalidExplanation(String invalidExplanation) {
        this.invalidExplanation = invalidExplanation;
    }
}
