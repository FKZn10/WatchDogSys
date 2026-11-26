package com.jpmsoft.common.model.request.SecInfo;

import java.math.BigDecimal;

public class SecInfoBudgetDeclareDtlDto {
    private String sibdId;

    //标段id
    private String secId;

    //项目id
    private String pId;

    //项目名称
    private String pName;

    //招采计划id
    private String ppId;

    //招采计划名称
    private String ppName;

    private String bdId;

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


    public String getSibdId() {
        return sibdId;
    }

    public void setSibdId(String sibdId) {
        this.sibdId = sibdId;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getPpId() {
        return ppId;
    }

    public void setPpId(String ppId) {
        this.ppId = ppId;
    }

    public String getPpName() {
        return ppName;
    }

    public void setPpName(String ppName) {
        this.ppName = ppName;
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
