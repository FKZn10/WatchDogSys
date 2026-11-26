package com.jpmsoft.projectProducer.pojo.entity.peInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 履约评价详细
 */
@TableName("Pe_Info_Detail")
public class PeInfoDetail {

    @TableId(type = IdType.INPUT)
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
