package com.jpmsoft.projectProducer.pojo.entity.peInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 履约评价表详细
 */
@TableName("Pe_Form_Detail")
public class PeFormDetail {

    @TableId(type = IdType.INPUT)
    private String fdId;
    private String fId;
    private String fdType;
    private String fdContent;
    private Float fdScore;
    private Integer fdOrder;
    private String fdCode;
    private String fdState;
    private String fdCalculationRule;
    private String fdStandard;
    private String fdRemark;
    private String teId;

    public String getFdId() {
        return fdId;
    }

    public void setFdId(String fdId) {
        this.fdId = fdId;
    }

    public String getfId() {
        return fId;
    }

    public void setfId(String fId) {
        this.fId = fId;
    }

    public String getFdType() {
        return fdType;
    }

    public void setFdType(String fdType) {
        this.fdType = fdType;
    }

    public String getFdContent() {
        return fdContent;
    }

    public void setFdContent(String fdContent) {
        this.fdContent = fdContent;
    }

    public Float getFdScore() {
        return fdScore;
    }

    public void setFdScore(Float fdScore) {
        this.fdScore = fdScore;
    }

    public Integer getFdOrder() {
        return fdOrder;
    }

    public void setFdOrder(Integer fdOrder) {
        this.fdOrder = fdOrder;
    }

    public String getFdCode() {
        return fdCode;
    }

    public void setFdCode(String fdCode) {
        this.fdCode = fdCode;
    }

    public String getFdState() {
        return fdState;
    }

    public void setFdState(String fdState) {
        this.fdState = fdState;
    }

    public String getFdCalculationRule() {
        return fdCalculationRule;
    }

    public void setFdCalculationRule(String fdCalculationRule) {
        this.fdCalculationRule = fdCalculationRule;
    }

    public String getFdStandard() {
        return fdStandard;
    }

    public void setFdStandard(String fdStandard) {
        this.fdStandard = fdStandard;
    }

    public String getFdRemark() {
        return fdRemark;
    }

    public void setFdRemark(String fdRemark) {
        this.fdRemark = fdRemark;
    }

    public String getTeId() {
        return teId;
    }

    public void setTeId(String teId) {
        this.teId = teId;
    }
}
