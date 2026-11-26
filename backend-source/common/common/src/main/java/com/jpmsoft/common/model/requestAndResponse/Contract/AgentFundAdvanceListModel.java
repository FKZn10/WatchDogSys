package com.jpmsoft.common.model.requestAndResponse.Contract;

import java.math.BigDecimal;

/**
 * 代建资金预拨
 */
public class AgentFundAdvanceListModel {
    private String afalId;
    private String afaId;
    private String afalConId;
    private BigDecimal afalLastTotalMoney;
    private BigDecimal afalThisMoney;
    private BigDecimal afalSurplusMoney;
    private String afalFile;

    private String scTitle;
    private String scName;
    private String scCode;
    private BigDecimal scMoney;
    private String afalFileIds;

    public String getAfalId() {
        return afalId;
    }

    public void setAfalId(String afalId) {
        this.afalId = afalId;
    }

    public String getAfaId() {
        return afaId;
    }

    public void setAfaId(String afaId) {
        this.afaId = afaId;
    }

    public String getAfalConId() {
        return afalConId;
    }

    public void setAfalConId(String afalConId) {
        this.afalConId = afalConId;
    }

    public BigDecimal getAfalLastTotalMoney() {
        return afalLastTotalMoney;
    }

    public void setAfalLastTotalMoney(BigDecimal afalLastTotalMoney) {
        this.afalLastTotalMoney = afalLastTotalMoney;
    }

    public BigDecimal getAfalThisMoney() {
        return afalThisMoney;
    }

    public void setAfalThisMoney(BigDecimal afalThisMoney) {
        this.afalThisMoney = afalThisMoney;
    }

    public BigDecimal getAfalSurplusMoney() {
        return afalSurplusMoney;
    }

    public void setAfalSurplusMoney(BigDecimal afalSurplusMoney) {
        this.afalSurplusMoney = afalSurplusMoney;
    }

    public String getAfalFile() {
        return afalFile;
    }

    public void setAfalFile(String afalFile) {
        this.afalFile = afalFile;
    }

    public String getScTitle() {
        return scTitle;
    }

    public void setScTitle(String scTitle) {
        this.scTitle = scTitle;
    }

    public String getScName() {
        return scName;
    }

    public void setScName(String scName) {
        this.scName = scName;
    }

    public String getScCode() {
        return scCode;
    }

    public void setScCode(String scCode) {
        this.scCode = scCode;
    }

    public String getAfalFileIds() {
        return afalFileIds;
    }

    public void setAfalFileIds(String afalFileIds) {
        this.afalFileIds = afalFileIds;
    }

    public BigDecimal getScMoney() {
        return scMoney;
    }

    public void setScMoney(BigDecimal scMoney) {
        this.scMoney = scMoney;
    }
}
