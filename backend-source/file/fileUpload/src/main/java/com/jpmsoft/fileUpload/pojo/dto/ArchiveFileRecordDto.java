package com.jpmsoft.fileUpload.pojo.dto;

import java.io.Serializable;
import java.util.List;

public class ArchiveFileRecordDto implements Serializable {

    private static final long serialVersionUID = 2983195090114105305L;

    private String pId;
    private Integer page = 1;
    private Integer size = 10;
    private String fileName;
    private List<String> tCodes;

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<String> gettCodes() {
        return tCodes;
    }

    public void settCodes(List<String> tCodes) {
        this.tCodes = tCodes;
    }
}
