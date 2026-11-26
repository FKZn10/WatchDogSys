package com.jpmsoft.common.model.requestAndResponse.RiskWarn;


/**
 * 预警抽取规则
 */
public class RiskEtlRulesModel {

    private String reId;
    private String rwiId;
    private String reInvolveTable;
    private String reMapping;
    private String reFromKeyword;
    private String reToKeyword;
    private String reIndexKeyword;
    private String reListHeader;
    private String reListColumn;

    public String getReId() {
        return reId;
    }

    public void setReId(String reId) {
        this.reId = reId;
    }

    public String getRwiId() {
        return rwiId;
    }

    public void setRwiId(String rwiId) {
        this.rwiId = rwiId;
    }

    public String getReInvolveTable() {
        return reInvolveTable;
    }

    public void setReInvolveTable(String reInvolveTable) {
        this.reInvolveTable = reInvolveTable;
    }

    public String getReMapping() {
        return reMapping;
    }

    public void setReMapping(String reMapping) {
        this.reMapping = reMapping;
    }

    public String getReFromKeyword() {
        return reFromKeyword;
    }

    public void setReFromKeyword(String reFromKeyword) {
        this.reFromKeyword = reFromKeyword;
    }

    public String getReToKeyword() {
        return reToKeyword;
    }

    public void setReToKeyword(String reToKeyword) {
        this.reToKeyword = reToKeyword;
    }

    public String getReIndexKeyword() {
        return reIndexKeyword;
    }

    public void setReIndexKeyword(String reIndexKeyword) {
        this.reIndexKeyword = reIndexKeyword;
    }

    public String getReListHeader() {
        return reListHeader;
    }

    public void setReListHeader(String reListHeader) {
        this.reListHeader = reListHeader;
    }

    public String getReListColumn() {
        return reListColumn;
    }

    public void setReListColumn(String reListColumn) {
        this.reListColumn = reListColumn;
    }
}
