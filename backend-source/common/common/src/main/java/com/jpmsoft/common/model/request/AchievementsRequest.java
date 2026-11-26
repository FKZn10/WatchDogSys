package com.jpmsoft.common.model.request;

import com.jpmsoft.common.model.BaseModeRequest;

import java.util.List;

public class AchievementsRequest extends BaseModeRequest {

    private String fileName;
    private String pName;
    private String cpdName;
    private String fileCreateUName;
    private List<String>  cplIds;
    private String pId;

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getCpdName() {
        return cpdName;
    }

    public void setCpdName(String cpdName) {
        this.cpdName = cpdName;
    }

    public String getFileCreateUName() {
        return fileCreateUName;
    }

    public void setFileCreateUName(String fileCreateUName) {
        this.fileCreateUName = fileCreateUName;
    }

    public List<String> getCplIds() {
        return cplIds;
    }

    public void setCplIds(List<String> cplIds) {
        this.cplIds = cplIds;
    }
}
