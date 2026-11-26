package com.jpmsoft.common.model.requestAndResponse.InvestmentControl;

import java.math.BigDecimal;

/**
 * 资金计划申请明细
 */
public class FundSchemeApplyDtlModel {

    private String fsadId;
    private String fsaId;
    private String conId;
    private BigDecimal fsadHaveApplied;
    private BigDecimal fsadCurrentApplied;
    private BigDecimal fsadRemaining;
    private String fsadFile;

    private String conName;
    private String conCode;
    private BigDecimal conMoneyTotal;
    private String fsadFileIds;

    public String getFsadId() {
        return fsadId;
    }

    public void setFsadId(String fsadId) {
        this.fsadId = fsadId;
    }

    public String getFsaId() {
        return fsaId;
    }

    public void setFsaId(String fsaId) {
        this.fsaId = fsaId;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public BigDecimal getFsadHaveApplied() {
        return fsadHaveApplied;
    }

    public void setFsadHaveApplied(BigDecimal fsadHaveApplied) {
        this.fsadHaveApplied = fsadHaveApplied;
    }

    public BigDecimal getFsadCurrentApplied() {
        return fsadCurrentApplied;
    }

    public void setFsadCurrentApplied(BigDecimal fsadCurrentApplied) {
        this.fsadCurrentApplied = fsadCurrentApplied;
    }

    public BigDecimal getFsadRemaining() {
        return fsadRemaining;
    }

    public void setFsadRemaining(BigDecimal fsadRemaining) {
        this.fsadRemaining = fsadRemaining;
    }

    public String getFsadFile() {
        return fsadFile;
    }

    public void setFsadFile(String fsadFile) {
        this.fsadFile = fsadFile;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getConCode() {
        return conCode;
    }

    public void setConCode(String conCode) {
        this.conCode = conCode;
    }

    public BigDecimal getConMoneyTotal() {
        return conMoneyTotal;
    }

    public void setConMoneyTotal(BigDecimal conMoneyTotal) {
        this.conMoneyTotal = conMoneyTotal;
    }

    public String getFsadFileIds() {
        return fsadFileIds;
    }

    public void setFsadFileIds(String fsadFileIds) {
        this.fsadFileIds = fsadFileIds;
    }
}
