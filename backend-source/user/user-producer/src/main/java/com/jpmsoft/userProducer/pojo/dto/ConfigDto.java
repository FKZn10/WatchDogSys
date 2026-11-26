package com.jpmsoft.userProducer.pojo.dto;

import com.jpmsoft.common.model.BaseModeRequest;
import com.jpmsoft.userProducer.constants.BaseMsg;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

public class ConfigDto extends BaseModeRequest implements Serializable {

    private static final long serialVersionUID = -527569777951721789L;
    @NotBlank(message = BaseMsg.configIdIsNull)
    private String conId;
    private String conCode;
    private String conName;
    private Date conCreatetime;
    private String conDescription;
    private String conValue;
    private String conCreateuId;
    private String conCreateuName;
    private Integer conType;
    private String conCategory;
    private String fileIds;
    /**
     * 启停状态 1 启用 0 停用
     */
    private Integer conStatus;

    private Integer conMold;

    public Integer getConType() {
        return conType;
    }

    public void setConType(Integer conType) {
        this.conType = conType;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getConCode() {
        return conCode;
    }

    public void setConCode(String conCode) {
        this.conCode = conCode;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public Date getConCreatetime() {
        return conCreatetime;
    }

    public void setConCreatetime(Date conCreatetime) {
        this.conCreatetime = conCreatetime;
    }

    public String getConDescription() {
        return conDescription;
    }

    public void setConDescription(String conDescription) {
        this.conDescription = conDescription;
    }

    public String getConValue() {
        return conValue;
    }

    public void setConValue(String conValue) {
        this.conValue = conValue;
    }

    public String getConCreateuId() {
        return conCreateuId;
    }

    public void setConCreateuId(String conCreateuId) {
        this.conCreateuId = conCreateuId;
    }

    public String getConCreateuName() {
        return conCreateuName;
    }

    public void setConCreateuName(String conCreateuName) {
        this.conCreateuName = conCreateuName;
    }


    public String getConCategory() {
        return conCategory;
    }

    public void setConCategory(String conCategory) {
        this.conCategory = conCategory;
    }

    public String getFileIds() {
        return fileIds;
    }

    public void setFileIds(String fileIds) {
        this.fileIds = fileIds;
    }

    public Integer getConStatus() {
        return conStatus;
    }

    public void setConStatus(Integer conStatus) {
        this.conStatus = conStatus;
    }

    public Integer getConMold() {
        return conMold;
    }

    public void setConMold(Integer conMold) {
        this.conMold = conMold;
    }
}
