package com.jpmsoft.common.model.requestAndResponse.SecInfo;

import java.math.BigDecimal;

/**
 * 询价单位
 */

public class SecPriceResultsfCompanyModel {

    private String sprcId;
    private String sprId;
    private String sprcCid;
    private String sprcCname;
    private String sprcContacts;
    private String sprcContactNumber;
    private String sprcRemarks;
    private BigDecimal sprcMoney;
    private String sprcType;
    private String sprcOtherPricing;

    public String getSprcId() {
        return sprcId;
    }

    public void setSprcId(String sprcId) {
        this.sprcId = sprcId;
    }

    public String getSprId() {
        return sprId;
    }

    public void setSprId(String sprId) {
        this.sprId = sprId;
    }

    public String getSprcCid() {
        return sprcCid;
    }

    public void setSprcCid(String sprcCid) {
        this.sprcCid = sprcCid;
    }

    public String getSprcCname() {
        return sprcCname;
    }

    public void setSprcCname(String sprcCname) {
        this.sprcCname = sprcCname;
    }

    public String getSprcContacts() {
        return sprcContacts;
    }

    public void setSprcContacts(String sprcContacts) {
        this.sprcContacts = sprcContacts;
    }

    public String getSprcContactNumber() {
        return sprcContactNumber;
    }

    public void setSprcContactNumber(String sprcContactNumber) {
        this.sprcContactNumber = sprcContactNumber;
    }

    public String getSprcRemarks() {
        return sprcRemarks;
    }

    public void setSprcRemarks(String sprcRemarks) {
        this.sprcRemarks = sprcRemarks;
    }

    public BigDecimal getSprcMoney() {
        return sprcMoney;
    }

    public void setSprcMoney(BigDecimal sprcMoney) {
        this.sprcMoney = sprcMoney;
    }

    public String getSprcType() {
        return sprcType;
    }

    public void setSprcType(String sprcType) {
        this.sprcType = sprcType;
    }

    public String getSprcOtherPricing() {
        return sprcOtherPricing;
    }

    public void setSprcOtherPricing(String sprcOtherPricing) {
        this.sprcOtherPricing = sprcOtherPricing;
    }
}
