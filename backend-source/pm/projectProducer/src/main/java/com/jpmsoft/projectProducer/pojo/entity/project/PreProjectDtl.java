package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectDtlModel;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectRegisterDtlModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 项目注册清单-匡算清单子表
 */
@Data
@TableName("Pre_Project_Dtl")
public class PreProjectDtl {
    @TableId(type = IdType.INPUT)
    private String ppdId;
    private String ppdFid;
    private String ppdNumber;
    private String ppdParenetId;
    private String ppdCode;
    private String ppdName;
    private String ppdUnit;
    private Float ppdCount;
    private BigDecimal ppdPrice;
    private BigDecimal ppdTotal;
    private String ppdRemark;
    private Integer ppdOrder;
    private Integer ppdLevel;
    private Integer ppdType;
    private Integer ppdState;
    private String ppdPath;
    private Integer ppdLeafNode;
    @TableField(exist = false)
    private List<PreProjectDtlModel> childNodes;
    public List<PreProjectDtlModel>  getChildNodes() {
        return childNodes;
    }

    public void setChildNodes(List<PreProjectDtlModel> childNodes) {
        this.childNodes = childNodes;
    }

    public String getPpdId() {
        return ppdId;
    }

    public void setPpdId(String ppdId) {
        this.ppdId = ppdId;
    }

    public String getPpdFid() {
        return ppdFid;
    }

    public void setPpdFid(String ppdFid) {
        this.ppdFid = ppdFid;
    }

    public String getPpdNumber() {
        return ppdNumber;
    }

    public void setPpdNumber(String ppdNumber) {
        this.ppdNumber = ppdNumber;
    }

    public String getPpdParenetId() {
        return ppdParenetId;
    }

    public void setPpdParenetId(String ppdParenetId) {
        this.ppdParenetId = ppdParenetId;
    }

    public String getPpdCode() {
        return ppdCode;
    }

    public void setPpdCode(String ppdCode) {
        this.ppdCode = ppdCode;
    }

    public String getPpdName() {
        return ppdName;
    }

    public void setPpdName(String ppdName) {
        this.ppdName = ppdName;
    }

    public String getPpdUnit() {
        return ppdUnit;
    }

    public void setPpdUnit(String ppdUnit) {
        this.ppdUnit = ppdUnit;
    }

    public Float getPpdCount() {
        return ppdCount;
    }

    public void setPpdCount(Float ppdCount) {
        this.ppdCount = ppdCount;
    }

    public String getPpdRemark() {
        return ppdRemark;
    }

    public void setPpdRemark(String ppdRemark) {
        this.ppdRemark = ppdRemark;
    }

    public BigDecimal getPpdTotal() {
        return ppdTotal;
    }

    public void setPpdTotal(BigDecimal ppdTotal) {
        this.ppdTotal = ppdTotal;
    }

    public Integer getPpdOrder() {
        return ppdOrder;
    }

    public void setPpdOrder(Integer ppdOrder) {
        this.ppdOrder = ppdOrder;
    }

    public Integer getPpdLevel() {
        return ppdLevel;
    }

    public void setPpdLevel(Integer ppdLevel) {
        this.ppdLevel = ppdLevel;
    }

    public Integer getPpdType() {
        return ppdType;
    }

    public void setPpdType(Integer ppdType) {
        this.ppdType = ppdType;
    }

    public Integer getPpdState() {
        return ppdState;
    }

    public void setPpdState(Integer ppdState) {
        this.ppdState = ppdState;
    }

    public String getPpdPath() {
        return ppdPath;
    }

    public void setPpdPath(String ppdPath) {
        this.ppdPath = ppdPath;
    }

    public BigDecimal getPpdPrice() {
        return ppdPrice;
    }

    public void setPpdPrice(BigDecimal ppdPrice) {
        this.ppdPrice = ppdPrice;
    }

    public Integer getPpdLeafNode() {
        return ppdLeafNode;
    }

    public void setPpdLeafNode(Integer ppdLeafNode) {
        this.ppdLeafNode = ppdLeafNode;
    }


}
