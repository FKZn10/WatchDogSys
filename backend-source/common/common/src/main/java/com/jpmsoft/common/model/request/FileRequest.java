package com.jpmsoft.common.model.request;

import com.jpmsoft.common.model.BaseModeRequest;

import java.util.Date;

public class FileRequest extends BaseModeRequest {

    //文档id
    public String  fileId;

    public String  tId;
    //文档标题
    public String  fileTitle;
    //文档编码
    public String  fileCode;
    //word文档主键
    public String  fileKeyWord;
    //
    public String  fileEmergencyLevel;

    public String  fileDescription;

    public Integer  fileFlowStatus;

    public Date  fileCreateDate;

    public String  fileCreateUser;

    public String  fileCreateUserName;

    public String  fileCreateCompanyId;

    public String  fileProject;

    public String  fileSectionId;

    public String  fileContract;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String gettId() {
        return tId;
    }

    public void settId(String tId) {
        this.tId = tId;
    }

    public String getFileTitle() {
        return fileTitle;
    }

    public void setFileTitle(String fileTitle) {
        this.fileTitle = fileTitle;
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }

    public String getFileKeyWord() {
        return fileKeyWord;
    }

    public void setFileKeyWord(String fileKeyWord) {
        this.fileKeyWord = fileKeyWord;
    }

    public String getFileEmergencyLevel() {
        return fileEmergencyLevel;
    }

    public void setFileEmergencyLevel(String fileEmergencyLevel) {
        this.fileEmergencyLevel = fileEmergencyLevel;
    }

    public String getFileDescription() {
        return fileDescription;
    }

    public void setFileDescription(String fileDescription) {
        this.fileDescription = fileDescription;
    }

    public Integer getFileFlowStatus() {
        return fileFlowStatus;
    }

    public void setFileFlowStatus(Integer fileFlowStatus) {
        this.fileFlowStatus = fileFlowStatus;
    }

    public Date getFileCreateDate() {
        return fileCreateDate;
    }

    public void setFileCreateDate(Date fileCreateDate) {
        this.fileCreateDate = fileCreateDate;
    }

    public String getFileCreateUser() {
        return fileCreateUser;
    }

    public void setFileCreateUser(String fileCreateUser) {
        this.fileCreateUser = fileCreateUser;
    }

    public String getFileCreateUserName() {
        return fileCreateUserName;
    }

    public void setFileCreateUserName(String fileCreateUserName) {
        this.fileCreateUserName = fileCreateUserName;
    }

    public String getFileCreateCompanyId() {
        return fileCreateCompanyId;
    }

    public void setFileCreateCompanyId(String fileCreateCompanyId) {
        this.fileCreateCompanyId = fileCreateCompanyId;
    }

    public String getFileProject() {
        return fileProject;
    }

    public void setFileProject(String fileProject) {
        this.fileProject = fileProject;
    }

    public String getFileSectionId() {
        return fileSectionId;
    }

    public void setFileSectionId(String fileSectionId) {
        this.fileSectionId = fileSectionId;
    }

    public String getFileContract() {
        return fileContract;
    }

    public void setFileContract(String fileContract) {
        this.fileContract = fileContract;
    }
}
