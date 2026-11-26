package com.jpmsoft.common.model.requestAndResponse.Project;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目注册资金来源
 */
public class ProSourcesFundsRegModel {

    private String fsId;
    private String prId;
    private String fsCode;
    private Date fsCreateTime;
    private Float fsScale;
    private BigDecimal fsMoney;
    private String fsRemark;
    private Integer fsSort;

    public String getFsId() {
        return fsId;
    }

    public void setFsId(String fsId) {
        this.fsId = fsId;
    }

    public String getPrId() {
        return prId;
    }

    public void setPrId(String prId) {
        this.prId = prId;
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
}
