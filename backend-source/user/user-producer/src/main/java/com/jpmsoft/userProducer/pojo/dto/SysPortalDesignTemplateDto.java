package com.jpmsoft.userProducer.pojo.dto;

import com.jpmsoft.common.model.BaseModeRequest;

import java.io.Serializable;
import java.util.Date;

public class SysPortalDesignTemplateDto extends BaseModeRequest implements Serializable {

    private static final long serialVersionUID = 8015646572418076250L;
    public String id;
    public String pdtName;
    public String pdtType;
    public String pdtDescribe;
    public String pdtState;
    public String pdtCode;
    public String pdtContent;
    public String teId;
    public String pdtCreateId;
    public String pdtCreateNmae;
    public Date pdtCreateTime;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPdtName() {
        return pdtName;
    }

    public void setPdtName(String pdtName) {
        this.pdtName = pdtName;
    }

    public String getPdtType() {
        return pdtType;
    }

    public void setPdtType(String pdtType) {
        this.pdtType = pdtType;
    }

    public String getPdtDescribe() {
        return pdtDescribe;
    }

    public void setPdtDescribe(String pdtDescribe) {
        this.pdtDescribe = pdtDescribe;
    }

    public String getPdtState() {
        return pdtState;
    }

    public void setPdtState(String pdtState) {
        this.pdtState = pdtState;
    }

    public String getPdtCode() {
        return pdtCode;
    }

    public void setPdtCode(String pdtCode) {
        this.pdtCode = pdtCode;
    }

    public String getPdtContent() {
        return pdtContent;
    }

    public void setPdtContent(String pdtContent) {
        this.pdtContent = pdtContent;
    }

    @Override
    public String getTeId() {
        return teId;
    }

    @Override
    public void setTeId(String teId) {
        this.teId = teId;
    }

    public String getPdtCreateId() {
        return pdtCreateId;
    }

    public void setPdtCreateId(String pdtCreateId) {
        this.pdtCreateId = pdtCreateId;
    }

    public String getPdtCreateNmae() {
        return pdtCreateNmae;
    }

    public void setPdtCreateNmae(String pdtCreateNmae) {
        this.pdtCreateNmae = pdtCreateNmae;
    }

    public Date getPdtCreateTime() {
        return pdtCreateTime;
    }

    public void setPdtCreateTime(Date pdtCreateTime) {
        this.pdtCreateTime = pdtCreateTime;
    }
}
