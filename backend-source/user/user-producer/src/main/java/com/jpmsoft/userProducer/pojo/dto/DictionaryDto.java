package com.jpmsoft.userProducer.pojo.dto;

import com.jpmsoft.common.model.BaseModeRequest;
import com.jpmsoft.userProducer.constants.BaseMsg;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class DictionaryDto extends BaseModeRequest implements Serializable {

    private static final long serialVersionUID = 8543667163224038832L;
    private String dId;
    private String dName;
    private String dCode;
    private Integer dSort;
    private String dParentid;
    private String dRemark;
    @NotBlank(message = BaseMsg.noOperationTypeSelected)
    private String type;
    private String dStatus;
    private String teId;
    private Integer dMold;

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

    public Integer getdSort() {
        return dSort;
    }

    public void setdSort(Integer dSort) {
        this.dSort = dSort;
    }

    public String getdParentid() {
        return dParentid;
    }

    public void setdParentid(String dParentid) {
        this.dParentid = dParentid;
    }

    public String getdRemark() {
        return dRemark;
    }

    public void setdRemark(String dRemark) {
        this.dRemark = dRemark;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getdStatus() {
        return dStatus;
    }

    public void setdStatus(String dStatus) {
        this.dStatus = dStatus;
    }

    public String getTeId() {
        return teId;
    }

    public void setTeId(String teId) {
        this.teId = teId;
    }

    public Integer getdMold() {
        return dMold;
    }

    public void setdMold(Integer dMold) {
        this.dMold = dMold;
    }
}
