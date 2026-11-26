package com.jpmsoft.jpbpm.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("SYS_Process_Invalidation_Record")
public class SysProcessInvalidationRecord {
    @TableId(type = IdType.INPUT)
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
