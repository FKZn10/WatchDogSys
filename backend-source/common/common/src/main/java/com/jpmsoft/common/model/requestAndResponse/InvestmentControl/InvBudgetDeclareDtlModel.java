package com.jpmsoft.common.model.requestAndResponse.InvestmentControl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.util.List;

/**
 * 概算申报清单
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InvBudgetDeclareDtlModel extends TreeNode {

    private String bddId;
    private String bdId;
    private String bddNumber;
    private String bddParentId;
    private String bddCode;
    private String bddName;
    private String bddUnit;
    private Float bddCount;
    private BigDecimal bddPrice;
    private BigDecimal bddTotal;
    private String bddRemark;
    private Integer bddOrder;
    private Integer bddLevel;
    private Integer bddType;
    private Integer bddState;
    private String bddPath;
    private Integer bddLeafNode;
    private BigDecimal bddRate;
    private String bddOldId;
    private String pId;

    //是有存在关联
    private String sfAssociation;
    //所关联的标段
    private List<String> associationSectionIds;

    private List<InvBudgetDeclareDtlModel> childNodes;


    private BigDecimal bddBuildFee;
    private BigDecimal bddInstallFee;
    private BigDecimal bddDeviceFee;
    private BigDecimal bddOtherFee;
    private BigDecimal reserveCost;      // 基本预备费（万元）

    public BigDecimal getBddBuildFee() {
        return bddBuildFee;
    }

    public void setBddBuildFee(BigDecimal bddBuildFee) {
        this.bddBuildFee = bddBuildFee;
    }

    public BigDecimal getBddInstallFee() {
        return bddInstallFee;
    }

    public void setBddInstallFee(BigDecimal bddInstallFee) {
        this.bddInstallFee = bddInstallFee;
    }

    public BigDecimal getBddDeviceFee() {
        return bddDeviceFee;
    }

    public void setBddDeviceFee(BigDecimal bddDeviceFee) {
        this.bddDeviceFee = bddDeviceFee;
    }

    public BigDecimal getBddOtherFee() {
        return bddOtherFee;
    }

    public void setBddOtherFee(BigDecimal bddOtherFee) {
        this.bddOtherFee = bddOtherFee;
    }

    public String getSfAssociation() {
        return sfAssociation;
    }

    public void setSfAssociation(String sfAssociation) {
        this.sfAssociation = sfAssociation;
    }

    public List<String> getAssociationSectionIds() {
        return associationSectionIds;
    }

    public void setAssociationSectionIds(List<String> associationSectionIds) {
        this.associationSectionIds = associationSectionIds;
    }

    public String getBddOldId() {
        return bddOldId;
    }

    public void setBddOldId(String bddOldId) {
        this.bddOldId = bddOldId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getBddId() {
        return bddId;
    }

    public void setBddId(String bddId) {
        this.bddId = bddId;
    }

    public String getBdId() {
        return bdId;
    }

    public void setBdId(String bdId) {
        this.bdId = bdId;
    }

    public String getBddNumber() {
        return bddNumber;
    }

    public void setBddNumber(String bddNumber) {
        this.bddNumber = bddNumber;
    }

    public String getBddParentId() {
        return bddParentId;
    }

    public void setBddParentId(String bddParentId) {
        this.bddParentId = bddParentId;
    }

    public String getBddCode() {
        return bddCode;
    }

    public void setBddCode(String bddCode) {
        this.bddCode = bddCode;
    }

    public String getBddName() {
        return bddName;
    }

    public void setBddName(String bddName) {
        this.bddName = bddName;
    }

    public String getBddUnit() {
        return bddUnit;
    }

    public void setBddUnit(String bddUnit) {
        this.bddUnit = bddUnit;
    }

    public Float getBddCount() {
        return bddCount;
    }

    public void setBddCount(Float bddCount) {
        this.bddCount = bddCount;
    }

    public BigDecimal getBddPrice() {
        return bddPrice;
    }

    public void setBddPrice(BigDecimal bddPrice) {
        this.bddPrice = bddPrice;
    }

    public BigDecimal getBddTotal() {
        return bddTotal;
    }

    public void setBddTotal(BigDecimal bddTotal) {
        this.bddTotal = bddTotal;
    }

    public String getBddRemark() {
        return bddRemark;
    }

    public void setBddRemark(String bddRemark) {
        this.bddRemark = bddRemark;
    }

    public Integer getBddOrder() {
        return bddOrder;
    }

    public void setBddOrder(Integer bddOrder) {
        this.bddOrder = bddOrder;
    }

    public Integer getBddLevel() {
        return bddLevel;
    }

    public void setBddLevel(Integer bddLevel) {
        this.bddLevel = bddLevel;
    }

    public Integer getBddType() {
        return bddType;
    }

    public void setBddType(Integer bddType) {
        this.bddType = bddType;
    }

    public Integer getBddState() {
        return bddState;
    }

    public void setBddState(Integer bddState) {
        this.bddState = bddState;
    }

    public String getBddPath() {
        return bddPath;
    }

    public void setBddPath(String bddPath) {
        this.bddPath = bddPath;
    }

    public Integer getBddLeafNode() {
        return bddLeafNode;
    }

    public void setBddLeafNode(Integer bddLeafNode) {
        this.bddLeafNode = bddLeafNode;
    }

    public List<InvBudgetDeclareDtlModel> getChildNodes() {
        return childNodes;
    }

    public void setChildNodes(List<InvBudgetDeclareDtlModel> childNodes) {
        this.childNodes = childNodes;
    }
}
