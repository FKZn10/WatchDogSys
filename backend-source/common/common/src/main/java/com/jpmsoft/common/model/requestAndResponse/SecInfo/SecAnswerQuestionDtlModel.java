package com.jpmsoft.common.model.requestAndResponse.SecInfo;

import java.util.Date;

/**
 * 答疑补遗清单
 */
public class SecAnswerQuestionDtlModel {
    private String aqdId;
    private String aqId;
    private String aqdCompany;
    private String aqdContent;
    private Date aqdTime;
    private String aqdReply;
    private Date aqdReplyTime;
    private Date aqdCreateTime;

    //提疑内容
    private String aqdQuestion;
    //答疑类型
    private String aqdType;

    private Integer aqdSort;

    public String getAqdQuestion() {
        return aqdQuestion;
    }

    public void setAqdQuestion(String aqdQuestion) {
        this.aqdQuestion = aqdQuestion;
    }

    public String getAqdType() {
        return aqdType;
    }

    public void setAqdType(String aqdType) {
        this.aqdType = aqdType;
    }

    public String getAqdId() {
        return aqdId;
    }

    public void setAqdId(String aqdId) {
        this.aqdId = aqdId;
    }

    public String getAqId() {
        return aqId;
    }

    public void setAqId(String aqId) {
        this.aqId = aqId;
    }

    public String getAqdCompany() {
        return aqdCompany;
    }

    public void setAqdCompany(String aqdCompany) {
        this.aqdCompany = aqdCompany;
    }

    public String getAqdContent() {
        return aqdContent;
    }

    public void setAqdContent(String aqdContent) {
        this.aqdContent = aqdContent;
    }

    public Date getAqdTime() {
        return aqdTime;
    }

    public void setAqdTime(Date aqdTime) {
        this.aqdTime = aqdTime;
    }

    public String getAqdReply() {
        return aqdReply;
    }

    public void setAqdReply(String aqdReply) {
        this.aqdReply = aqdReply;
    }

    public Date getAqdReplyTime() {
        return aqdReplyTime;
    }

    public void setAqdReplyTime(Date aqdReplyTime) {
        this.aqdReplyTime = aqdReplyTime;
    }

    public Date getAqdCreateTime() {
        return aqdCreateTime;
    }

    public void setAqdCreateTime(Date aqdCreateTime) {
        this.aqdCreateTime = aqdCreateTime;
    }

    public Integer getAqdSort() {
        return aqdSort;
    }

    public void setAqdSort(Integer aqdSort) {
        this.aqdSort = aqdSort;
    }
}
