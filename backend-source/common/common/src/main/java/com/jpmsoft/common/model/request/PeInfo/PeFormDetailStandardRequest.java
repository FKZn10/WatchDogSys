package com.jpmsoft.common.model.request.PeInfo;


import com.jpmsoft.common.model.BaseModeRequest;

/**
 * 评价标准
 */
public class PeFormDetailStandardRequest extends BaseModeRequest {

    private String sId;
    private String fdId;
    private String sContent;
    private Float sScore;
    private Integer sOrder;
    private String sState;
    private String sRemark;

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public String getFdId() {
        return fdId;
    }

    public void setFdId(String fdId) {
        this.fdId = fdId;
    }

    public String getsContent() {
        return sContent;
    }

    public void setsContent(String sContent) {
        this.sContent = sContent;
    }

    public Float getsScore() {
        return sScore;
    }

    public void setsScore(Float sScore) {
        this.sScore = sScore;
    }

    public Integer getsOrder() {
        return sOrder;
    }

    public void setsOrder(Integer sOrder) {
        this.sOrder = sOrder;
    }

    public String getsState() {
        return sState;
    }

    public void setsState(String sState) {
        this.sState = sState;
    }

    public String getsRemark() {
        return sRemark;
    }

    public void setsRemark(String sRemark) {
        this.sRemark = sRemark;
    }
}
