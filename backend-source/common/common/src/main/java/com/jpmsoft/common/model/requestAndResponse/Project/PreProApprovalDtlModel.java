package com.jpmsoft.common.model.requestAndResponse.Project;

import java.math.BigDecimal;
import java.util.List;

/**
 * 项项目立项清单-估算清单子表
 */
public class PreProApprovalDtlModel {

    private String padId;
    private String padFid;
    private String padNumber;
    private String padParenetId;
    private String padCode;
    private String padName;
    private String padUnit;
    private Float padCount;
    private BigDecimal padPrice;
    private BigDecimal padTotal;
    private String padRemark;
    private Integer padOrder;
    private Integer padLevel;
    private Integer padType;
    private Integer padState;
    private String padPath;
    private Integer padLeafNode;

    private List<PreProApprovalDtlModel> childNodes;

    public String getPadId() {
        return padId;
    }

    public void setPadId(String padId) {
        this.padId = padId;
    }
    public String getPadFid() {
        return padFid;
    }

    public void setPadFid(String padFid) {
        this.padFid = padFid;
    }


    public String getPadNumber() {
        return padNumber;
    }

    public void setPadNumber(String padNumber) {
        this.padNumber = padNumber;
    }

    public String getPadParenetId() {
        return padParenetId;
    }

    public void setPadParenetId(String padParenetId) {
        this.padParenetId = padParenetId;
    }

    public String getPadCode() {
        return padCode;
    }

    public void setPadCode(String padCode) {
        this.padCode = padCode;
    }

    public String getPadName() {
        return padName;
    }

    public void setPadName(String padName) {
        this.padName = padName;
    }

    public String getPadUnit() {
        return padUnit;
    }

    public void setPadUnit(String padUnit) {
        this.padUnit = padUnit;
    }

    public Float getPadCount() {
        return padCount;
    }

    public void setPadCount(Float padCount) {
        this.padCount = padCount;
    }

    public String getPadRemark() {
        return padRemark;
    }

    public void setPadRemark(String padRemark) {
        this.padRemark = padRemark;
    }

    public BigDecimal getPadTotal() {
        return padTotal;
    }

    public void setPadTotal(BigDecimal padTotal) {
        this.padTotal = padTotal;
    }

    public Integer getPadOrder() {
        return padOrder;
    }

    public void setPadOrder(Integer padOrder) {
        this.padOrder = padOrder;
    }

    public Integer getPadLevel() {
        return padLevel;
    }

    public void setPadLevel(Integer padLevel) {
        this.padLevel = padLevel;
    }

    public Integer getPadType() {
        return padType;
    }

    public void setPadType(Integer padType) {
        this.padType = padType;
    }

    public Integer getPadState() {
        return padState;
    }

    public void setPadState(Integer padState) {
        this.padState = padState;
    }

    public String getPadPath() {
        return padPath;
    }

    public void setPadPath(String padPath) {
        this.padPath = padPath;
    }

    public BigDecimal getPadPrice() {
        return padPrice;
    }

    public void setPadPrice(BigDecimal padPrice) {
        this.padPrice = padPrice;
    }

    public Integer getPadLeafNode() {
        return padLeafNode;
    }

    public void setPadLeafNode(Integer padLeafNode) {
        this.padLeafNode = padLeafNode;
    }

    public List<PreProApprovalDtlModel> getChildNodes() {
        return this.childNodes;
    }

    public void setChildNodes(List<PreProApprovalDtlModel> childNodes) {
        this.childNodes = childNodes;
    }
}
