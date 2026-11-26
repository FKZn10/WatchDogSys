package com.jpmsoft.jpbpm.pojo.dto;

import com.jpmsoft.common.model.BaseModeRequest;

import java.util.Date;

public class SysProcessInvalidationRecordDto extends BaseModeRequest {
    public String id;
    public String fileId;
    public String invalidExplanation;
    public String operator;
    public String operatorName;
    public Date operatorTime;
    public String fileDoc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getInvalidExplanation() {
        return invalidExplanation;
    }

    public void setInvalidExplanation(String invalidExplanation) {
        this.invalidExplanation = invalidExplanation;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Date getOperatorTime() {
        return operatorTime;
    }

    public void setOperatorTime(Date operatorTime) {
        this.operatorTime = operatorTime;
    }

    public String getFileDoc() {
        return fileDoc;
    }

    public void setFileDoc(String fileDoc) {
        this.fileDoc = fileDoc;
    }
}
