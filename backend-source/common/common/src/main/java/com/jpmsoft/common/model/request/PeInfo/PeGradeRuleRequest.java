package com.jpmsoft.common.model.request.PeInfo;

import com.jpmsoft.common.model.BaseModeRequest;

import java.util.Date;

/**
 * 评价等级规则
 */
public class PeGradeRuleRequest extends BaseModeRequest {

    private String rId;
    private Float rStartScore;
    private Float rEndScore;
    private String rGrade;
    private String rRemark;
    private String rCreateUid;
    private String rCreateUname;
    private Date rCreateTime;

    public String getrId() {
        return rId;
    }

    public void setrId(String rId) {
        this.rId = rId;
    }

    public Float getrStartScore() {
        return rStartScore;
    }

    public void setrStartScore(Float rStartScore) {
        this.rStartScore = rStartScore;
    }

    public Float getrEndScore() {
        return rEndScore;
    }

    public void setrEndScore(Float rEndScore) {
        this.rEndScore = rEndScore;
    }

    public String getrGrade() {
        return rGrade;
    }

    public void setrGrade(String rGrade) {
        this.rGrade = rGrade;
    }

    public String getrRemark() {
        return rRemark;
    }

    public void setrRemark(String rRemark) {
        this.rRemark = rRemark;
    }

    public String getrCreateUid() {
        return rCreateUid;
    }

    public void setrCreateUid(String rCreateUid) {
        this.rCreateUid = rCreateUid;
    }

    public String getrCreateUname() {
        return rCreateUname;
    }

    public void setrCreateUname(String rCreateUname) {
        this.rCreateUname = rCreateUname;
    }

    public Date getrCreateTime() {
        return rCreateTime;
    }

    public void setrCreateTime(Date rCreateTime) {
        this.rCreateTime = rCreateTime;
    }
}
