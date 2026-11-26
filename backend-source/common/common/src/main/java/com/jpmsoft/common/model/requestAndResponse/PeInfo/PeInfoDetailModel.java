package com.jpmsoft.common.model.requestAndResponse.PeInfo;

import java.util.List;

/**
 * 履约评价详细
 */
public class PeInfoDetailModel {

    private String pedId;
    private String peId;
    private String pedType;
    private String pedContent;
    private Float pedShouldScore;
    private Float pedActualScore;
    private String pedCalculationRule;
    private Integer pedOrder;
    private String pedStandard;
    private String pedRemark;
    private List<PeInfoDetailStandardModel> peInfoDetailStandards;

    public String getPedId() {
        return pedId;
    }

    public void setPedId(String pedId) {
        this.pedId = pedId;
    }

    public String getPeId() {
        return peId;
    }

    public void setPeId(String peId) {
        this.peId = peId;
    }

    public String getPedType() {
        return pedType;
    }

    public void setPedType(String pedType) {
        this.pedType = pedType;
    }

    public String getPedContent() {
        return pedContent;
    }

    public void setPedContent(String pedContent) {
        this.pedContent = pedContent;
    }

    public Float getPedShouldScore() {
        return pedShouldScore;
    }

    public void setPedShouldScore(Float pedShouldScore) {
        this.pedShouldScore = pedShouldScore;
    }

    public Float getPedActualScore() {
        return pedActualScore;
    }

    public void setPedActualScore(Float pedActualScore) {
        this.pedActualScore = pedActualScore;
    }

    public String getPedCalculationRule() {
        return pedCalculationRule;
    }

    public void setPedCalculationRule(String pedCalculationRule) {
        this.pedCalculationRule = pedCalculationRule;
    }

    public Integer getPedOrder() {
        return pedOrder;
    }

    public void setPedOrder(Integer pedOrder) {
        this.pedOrder = pedOrder;
    }

    public List<PeInfoDetailStandardModel> getPeInfoDetailStandards() {
        return peInfoDetailStandards;
    }

    public void setPeInfoDetailStandards(List<PeInfoDetailStandardModel> peInfoDetailStandards) {
        this.peInfoDetailStandards = peInfoDetailStandards;
    }

    public String getPedStandard() {
        return pedStandard;
    }

    public void setPedStandard(String pedStandard) {
        this.pedStandard = pedStandard;
    }

    public String getPedRemark() {
        return pedRemark;
    }

    public void setPedRemark(String pedRemark) {
        this.pedRemark = pedRemark;
    }
}
