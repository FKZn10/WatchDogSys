package com.jpmsoft.userProducer.pojo.dto;

import com.jpmsoft.common.model.BaseModeRequest;
import com.jpmsoft.userProducer.constants.BaseMsg;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

public class CompanyNameDto extends BaseModeRequest implements Serializable {

    private static final long serialVersionUID = 3031644197285635318L;
    @NotBlank(message = BaseMsg.cnIdIsNull)
    public String cnId;
    public String cId;
    public String cnOldname;
    public String cnNewname;
    public String cnRemark;
    public String cnFile;
    public Date cnCreatetime;
    public String cnCreateuId;
    public String cnCreateuName;
    // 内部用户：1 外部用户：0
    public String cnCode;


    public String getCnId() {
        return cnId;
    }

    public void setCnId(String cnId) {
        this.cnId = cnId;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getCnOldname() {
        return cnOldname;
    }

    public void setCnOldname(String cnOldname) {
        this.cnOldname = cnOldname;
    }

    public String getCnNewname() {
        return cnNewname;
    }

    public void setCnNewname(String cnNewname) {
        this.cnNewname = cnNewname;
    }

    public String getCnRemark() {
        return cnRemark;
    }

    public void setCnRemark(String cnRemark) {
        this.cnRemark = cnRemark;
    }

    public String getCnFile() {
        return cnFile;
    }

    public void setCnFile(String cnFile) {
        this.cnFile = cnFile;
    }

    public Date getCnCreatetime() {
        return cnCreatetime;
    }

    public void setCnCreatetime(Date cnCreatetime) {
        this.cnCreatetime = cnCreatetime;
    }

    public String getCnCreateuId() {
        return cnCreateuId;
    }

    public void setCnCreateuId(String cnCreateuId) {
        this.cnCreateuId = cnCreateuId;
    }

    public String getCnCreateuName() {
        return cnCreateuName;
    }

    public void setCnCreateuName(String cnCreateuName) {
        this.cnCreateuName = cnCreateuName;
    }

    public String getCnCode() {
        return cnCode;
    }

    public void setCnCode(String cnCode) {
        this.cnCode = cnCode;
    }
}
