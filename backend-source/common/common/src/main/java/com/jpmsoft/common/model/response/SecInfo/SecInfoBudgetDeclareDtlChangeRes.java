package com.jpmsoft.common.model.response.SecInfo;



import java.math.BigDecimal;

/**
 * 标段概算清单变更
 */
public class SecInfoBudgetDeclareDtlChangeRes {

    private String sibdcId;

    //标段信息变更id
    private String sicId;

    //标段概算表id
    private String sibdId;

    //标段id
    private String secId;

    //项目id
    private String pId;

    //招采计划id
    private String ppId;

    //概算主表
    private String bdId;

    //概算科目表
    private String bddId;

    private String bddName;

    //序号
    private String bddNumber;

    //造价金额
    private BigDecimal bddAmount;

    //选择金额
    private BigDecimal selectAmount;

    //选择比例
    private Float selectProportion;

    //变更类型标识
    private String secChangeType;


    public String getSibdcId() {
        return sibdcId;
    }

    public void setSibdcId(String sibdcId) {
        this.sibdcId = sibdcId;
    }

    public String getSicId() {
        return sicId;
    }

    public void setSicId(String sicId) {
        this.sicId = sicId;
    }

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

    public String getSecChangeType() {
        return secChangeType;
    }

    public void setSecChangeType(String secChangeType) {
        this.secChangeType = secChangeType;
    }
}
