package com.jpmsoft.userProducer.pojo.dto;

import com.jpmsoft.common.model.BaseModeRequest;

import java.io.Serializable;
import java.util.Date;

public class SysPortalDesignDto extends BaseModeRequest implements Serializable {
    private static final long serialVersionUID = 8830984300189726100L;
    public String id;
    public String pdName;
    public String pdType;
    public String pdDescribe;
    public String pdState;
    public String pdCode;
    public String pdContent;
    public String teId;
    public String pdCreateId;
    public String pdCreateNmae;
    public Date pdCreateTime;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPdName() {
        return pdName;
    }

    public void setPdName(String pdName) {
        this.pdName = pdName;
    }

    public String getPdType() {
        return pdType;
    }

    public void setPdType(String pdType) {
        this.pdType = pdType;
    }

    public String getPdDescribe() {
        return pdDescribe;
    }

    public void setPdDescribe(String pdDescribe) {
        this.pdDescribe = pdDescribe;
    }

    public String getPdState() {
        return pdState;
    }

    public void setPdState(String pdState) {
        this.pdState = pdState;
    }

    public String getPdCode() {
        return pdCode;
    }

    public void setPdCode(String pdCode) {
        this.pdCode = pdCode;
    }

    public String getPdContent() {
        return pdContent;
    }

    public void setPdContent(String pdContent) {
        this.pdContent = pdContent;
    }

    @Override
    public String getTeId() {
        return teId;
    }

    @Override
    public void setTeId(String teId) {
        this.teId = teId;
    }

    public String getPdCreateId() {
        return pdCreateId;
    }

    public void setPdCreateId(String pdCreateId) {
        this.pdCreateId = pdCreateId;
    }

    public String getPdCreateNmae() {
        return pdCreateNmae;
    }

    public void setPdCreateNmae(String pdCreateNmae) {
        this.pdCreateNmae = pdCreateNmae;
    }

    public Date getPdCreateTime() {
        return pdCreateTime;
    }

    public void setPdCreateTime(Date pdCreateTime) {
        this.pdCreateTime = pdCreateTime;
    }
}
