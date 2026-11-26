package com.jpmsoft.common.model.request.RiskWarn;

import java.util.Date;
import java.util.List;

public class RiskWarnTableRequest {
    private String rwtId;
    private String rwiId;
    private String tableId;
    private String tableCode;
    private String tableField;
    private String rwtAssociationType;
    private String rwtAssociationTable;
    private String rwtAssociationField;
    private String rwtConditionalRelation;
    private String rwtCreateUname;
    private String rwtCreateUid;
    private Date rwtCreateTime;
    private String rwtOtherSet;
    private List<RiskWarnTableFilterRequest>  rwtConlist;

    public String getRwtId() {
        return rwtId;
    }

    public void setRwtId(String rwtId) {
        this.rwtId = rwtId;
    }

    public String getRwiId() {
        return rwiId;
    }

    public void setRwiId(String rwiId) {
        this.rwiId = rwiId;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getTableCode() {
        return tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public String getTableField() {
        return tableField;
    }

    public void setTableField(String tableField) {
        this.tableField = tableField;
    }

    public String getRwtAssociationType() {
        return rwtAssociationType;
    }

    public void setRwtAssociationType(String rwtAssociationType) {
        this.rwtAssociationType = rwtAssociationType;
    }

    public String getRwtAssociationTable() {
        return rwtAssociationTable;
    }

    public void setRwtAssociationTable(String rwtAssociationTable) {
        this.rwtAssociationTable = rwtAssociationTable;
    }

    public String getRwtAssociationField() {
        return rwtAssociationField;
    }

    public void setRwtAssociationField(String rwtAssociationField) {
        this.rwtAssociationField = rwtAssociationField;
    }

    public String getRwtConditionalRelation() {
        return rwtConditionalRelation;
    }

    public void setRwtConditionalRelation(String rwtConditionalRelation) {
        this.rwtConditionalRelation = rwtConditionalRelation;
    }

    public String getRwtCreateUname() {
        return rwtCreateUname;
    }

    public void setRwtCreateUname(String rwtCreateUname) {
        this.rwtCreateUname = rwtCreateUname;
    }

    public String getRwtCreateUid() {
        return rwtCreateUid;
    }

    public void setRwtCreateUid(String rwtCreateUid) {
        this.rwtCreateUid = rwtCreateUid;
    }

    public Date getRwtCreateTime() {
        return rwtCreateTime;
    }

    public void setRwtCreateTime(Date rwtCreateTime) {
        this.rwtCreateTime = rwtCreateTime;
    }

    public List<RiskWarnTableFilterRequest> getRwtConlist() {
        return rwtConlist;
    }

    public void setRwtConlist(List<RiskWarnTableFilterRequest> rwtConlist) {
        this.rwtConlist = rwtConlist;
    }

    public String getRwtOtherSet() {
        return rwtOtherSet;
    }

    public void setRwtOtherSet(String rwtOtherSet) {
        this.rwtOtherSet = rwtOtherSet;
    }
}
