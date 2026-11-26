package com.jpmsoft.jpbpm.pojo.dto;

import com.jpmsoft.common.model.BaseModeRequest;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class DepartmentDto extends BaseModeRequest implements Serializable {
    private static final long serialVersionUID = 5878472751294408360L;
    public String dId;
    public String dName;
    public String dCode;
    public String dStatus;
    public String dParentid;


    public String getdId() {
        return dId;
    }

    public void setdId(String dId) {
        this.dId = dId;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }

    public String getdCode() {
        return dCode;
    }

    public void setdCode(String dCode) {
        this.dCode = dCode;
    }

    public String getdStatus() {
        return dStatus;
    }

    public void setdStatus(String dStatus) {
        this.dStatus = dStatus;
    }

    public String getdParentid() {
        return dParentid;
    }

    public void setdParentid(String dParentid) {
        this.dParentid = dParentid;
    }
}
