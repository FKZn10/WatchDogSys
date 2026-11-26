package com.jpmsoft.common.model.request.PeInfo;

import com.jpmsoft.common.model.BaseModeRequest;

import java.util.List;

/**
 * 履约评价表详细
 */
public class PeFormDetailRequest extends BaseModeRequest {

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
    private List<PeFormDetailStandardRequest> peFormStandards;

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

    public List<PeFormDetailStandardRequest> getPeFormStandards() {
        return peFormStandards;
    }

    public void setPeFormStandards(List<PeFormDetailStandardRequest> peFormStandards) {
        this.peFormStandards = peFormStandards;
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
}
