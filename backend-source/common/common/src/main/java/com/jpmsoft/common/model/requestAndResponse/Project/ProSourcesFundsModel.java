package com.jpmsoft.common.model.requestAndResponse.Project;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目资金来源
 */
public class ProSourcesFundsModel {

    private String fsId;
    private String pId;
    private String fsCode;
    private Date fsCreateTime;
    private Float fsScale;
    private BigDecimal fsMoney;
    private String fsRemark;
    private Integer fsSort;
    private  String fsCostCode;

    public String getFsId() {
        return fsId;
    }

    public void setFsId(String fsId) {
        this.fsId = fsId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getFsCode() {
        return fsCode;
    }

    public void setFsCode(String fsCode) {
        this.fsCode = fsCode;
    }

    public Date getFsCreateTime() {
        return fsCreateTime;
    }

    public void setFsCreateTime(Date fsCreateTime) {
        this.fsCreateTime = fsCreateTime;
    }

    public Float getFsScale() {
        return fsScale;
    }

    public void setFsScale(Float fsScale) {
        this.fsScale = fsScale;
    }

    public BigDecimal getFsMoney() {
        return fsMoney;
    }

    public void setFsMoney(BigDecimal fsMoney) {
        this.fsMoney = fsMoney;
    }

    public String getFsRemark() {
        return fsRemark;
    }

    public void setFsRemark(String fsRemark) {
        this.fsRemark = fsRemark;
    }

    public Integer getFsSort() {
        return fsSort;
    }

    public void setFsSort(Integer fsSort) {
        this.fsSort = fsSort;
    }

    public String getFsCostCode() {
        return fsCostCode;
    }

    public void setFsCostCode(String fsCostCode) {
        this.fsCostCode = fsCostCode;
    }
}
