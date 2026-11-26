package com.jpmsoft.projectProducer.pojo.dto.contract;

import java.math.BigDecimal;

public class ConPayCountDto {

    public  String conId;//合同id
    public  Integer typeStr;//支付类型
    public String payId;
    public  String pId;
    public  String year;
    public BigDecimal payApplyNew;

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public Integer getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(Integer typeStr) {
        this.typeStr = typeStr;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public BigDecimal getPayApplyNew() {
        return payApplyNew;
    }

    public void setPayApplyNew(BigDecimal payApplyNew) {
        this.payApplyNew = payApplyNew;
    }
}
