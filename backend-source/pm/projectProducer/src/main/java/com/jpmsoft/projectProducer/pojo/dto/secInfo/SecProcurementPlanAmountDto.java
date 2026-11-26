package com.jpmsoft.projectProducer.pojo.dto.secInfo;

import lombok.Data;

import java.math.BigDecimal;


public class SecProcurementPlanAmountDto {

    private String ppaId;

    //招采计划id
    private String ppId;

    //概算申报主表id
    private String bdId;

    //概算申报清单id
    private String bddId;

    //科目名称
    private String bddName;

    //序号
    private String bddNumber;

    //造价金额
    private BigDecimal bddAmount;

    //选择金额
    private BigDecimal selectAmount;

    //选择比例
    private Float selectProportion;


    public String getPpaId() {
        return ppaId;
    }

    public void setPpaId(String ppaId) {
        this.ppaId = ppaId;
    }

    public String getPpId() {
        return ppId;
    }

    public void setPpId(String ppId) {
        this.ppId = ppId;
    }

    public String getBdId() {
        return bdId;
    }

    public void setBdId(String bdId) {
        this.bdId = bdId;
    }

    public String getBddId() {
        return bddId;
    }

    public void setBddId(String bddId) {
        this.bddId = bddId;
    }

    public String getBddName() {
        return bddName;
    }

    public void setBddName(String bddName) {
        this.bddName = bddName;
    }

    public String getBddNumber() {
        return bddNumber;
    }

    public void setBddNumber(String bddNumber) {
        this.bddNumber = bddNumber;
    }

    public BigDecimal getBddAmount() {
        return bddAmount;
    }

    public void setBddAmount(BigDecimal bddAmount) {
        this.bddAmount = bddAmount;
    }

    public BigDecimal getSelectAmount() {
        return selectAmount;
    }

    public void setSelectAmount(BigDecimal selectAmount) {
        this.selectAmount = selectAmount;
    }

    public Float getSelectProportion() {
        return selectProportion;
    }

    public void setSelectProportion(Float selectProportion) {
        this.selectProportion = selectProportion;
    }
}
