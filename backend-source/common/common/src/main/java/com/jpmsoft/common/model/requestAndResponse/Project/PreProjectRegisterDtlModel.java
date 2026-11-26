package com.jpmsoft.common.model.requestAndResponse.Project;

import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvBudgetDeclareDtlModel;

import java.math.BigDecimal;
import java.util.List;

/**
 * 项目注册清单-匡算清单子表
 */
public class PreProjectRegisterDtlModel {

    private String prdId;
    private String prdFid;
    private String prdNumber;
    private String prdParenetId;
    private String prdCode;
    private String prdName;
    private String prdUnit;
    private Float prdCount;
    private BigDecimal prdPrice;
    private BigDecimal prdTotal;
    private String prdRemark;
    private Integer prdOrder;
    private Integer prdLevel;
    private Integer prdType;
    private Integer prdState;
    private String prdPath;
    private Integer prdLeafNode;

    private List<PreProjectRegisterDtlModel> childNodes;

    public String getPrdId() {
        return prdId;
    }

    public void setPrdId(String prdId) {
        this.prdId = prdId;
    }

    public String getPrdFid() {
        return prdFid;
    }

    public void setPrdFid(String prdFid) {
        this.prdFid = prdFid;
    }

    public String getPrdNumber() {
        return prdNumber;
    }

    public void setPrdNumber(String prdNumber) {
        this.prdNumber = prdNumber;
    }

    public String getPrdParenetId() {
        return prdParenetId;
    }

    public void setPrdParenetId(String prdParenetId) {
        this.prdParenetId = prdParenetId;
    }

    public String getPrdCode() {
        return prdCode;
    }

    public void setPrdCode(String prdCode) {
        this.prdCode = prdCode;
    }

    public String getPrdName() {
        return prdName;
    }

    public void setPrdName(String prdName) {
        this.prdName = prdName;
    }

    public String getPrdUnit() {
        return prdUnit;
    }

    public void setPrdUnit(String prdUnit) {
        this.prdUnit = prdUnit;
    }

    public Float getPrdCount() {
        return prdCount;
    }

    public void setPrdCount(Float prdCount) {
        this.prdCount = prdCount;
    }

    public String getPrdRemark() {
        return prdRemark;
    }

    public void setPrdRemark(String prdRemark) {
        this.prdRemark = prdRemark;
    }

    public BigDecimal getPrdTotal() {
        return prdTotal;
    }

    public void setPrdTotal(BigDecimal prdTotal) {
        this.prdTotal = prdTotal;
    }

    public Integer getPrdOrder() {
        return prdOrder;
    }

    public void setPrdOrder(Integer prdOrder) {
        this.prdOrder = prdOrder;
    }

    public Integer getPrdLevel() {
        return prdLevel;
    }

    public void setPrdLevel(Integer prdLevel) {
        this.prdLevel = prdLevel;
    }

    public Integer getPrdType() {
        return prdType;
    }

    public void setPrdType(Integer prdType) {
        this.prdType = prdType;
    }

    public Integer getPrdState() {
        return prdState;
    }

    public void setPrdState(Integer prdState) {
        this.prdState = prdState;
    }

    public String getPrdPath() {
        return prdPath;
    }

    public void setPrdPath(String prdPath) {
        this.prdPath = prdPath;
    }

    public BigDecimal getPrdPrice() {
        return prdPrice;
    }

    public void setPrdPrice(BigDecimal prdPrice) {
        this.prdPrice = prdPrice;
    }

    public Integer getPrdLeafNode() {
        return prdLeafNode;
    }

    public void setPrdLeafNode(Integer prdLeafNode) {
        this.prdLeafNode = prdLeafNode;
    }
    public List<PreProjectRegisterDtlModel> getChildNodes() {
        return this.childNodes;
    }

    public void setChildNodes(List<PreProjectRegisterDtlModel> childNodes) {
        this.childNodes = childNodes;
    }
}
