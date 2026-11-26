package com.jpmsoft.common.model.requestAndResponse.SecInfo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 标段资金来源
 */
public class SecSourcesFundsModel {

    private String ssfId;
    private String secId;
    private String ssfCode;
    private Date ssfCreateTime;
    private Float ssfScale;
    private BigDecimal ssfMoney;
    private String ssfRemark;
    private Integer ssfSort;
    private String ssfCard;
    private String ssfAccount;
    private String ssfName;
    private String ssfSubject;
    private String ssfDepartment;
    private String ssfHead;

    public String getSsfId() {
        return ssfId;
    }

    public void setSsfId(String ssfId) {
        this.ssfId = ssfId;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public String getSsfCode() {
        return ssfCode;
    }

    public void setSsfCode(String ssfCode) {
        this.ssfCode = ssfCode;
    }

    public Date getSsfCreateTime() {
        return ssfCreateTime;
    }

    public void setSsfCreateTime(Date ssfCreateTime) {
        this.ssfCreateTime = ssfCreateTime;
    }

    public Float getSsfScale() {
        return ssfScale;
    }

    public void setSsfScale(Float ssfScale) {
        this.ssfScale = ssfScale;
    }

    public BigDecimal getSsfMoney() {
        return ssfMoney;
    }

    public void setSsfMoney(BigDecimal ssfMoney) {
        this.ssfMoney = ssfMoney;
    }

    public String getSsfRemark() {
        return ssfRemark;
    }

    public void setSsfRemark(String ssfRemark) {
        this.ssfRemark = ssfRemark;
    }

    public Integer getSsfSort() {
        return ssfSort;
    }

    public void setSsfSort(Integer ssfSort) {
        this.ssfSort = ssfSort;
    }

    public String getSsfCard() {
        return ssfCard;
    }

    public void setSsfCard(String ssfCard) {
        this.ssfCard = ssfCard;
    }

    public String getSsfAccount() {
        return ssfAccount;
    }

    public void setSsfAccount(String ssfAccount) {
        this.ssfAccount = ssfAccount;
    }

    public String getSsfName() {
        return ssfName;
    }

    public void setSsfName(String ssfName) {
        this.ssfName = ssfName;
    }

    public String getSsfSubject() {
        return ssfSubject;
    }

    public void setSsfSubject(String ssfSubject) {
        this.ssfSubject = ssfSubject;
    }

    public String getSsfDepartment() {
        return ssfDepartment;
    }

    public void setSsfDepartment(String ssfDepartment) {
        this.ssfDepartment = ssfDepartment;
    }

    public String getSsfHead() {
        return ssfHead;
    }

    public void setSsfHead(String ssfHead) {
        this.ssfHead = ssfHead;
    }
}
