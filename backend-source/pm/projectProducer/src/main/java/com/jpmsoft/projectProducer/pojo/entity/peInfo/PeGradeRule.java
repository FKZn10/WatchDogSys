package com.jpmsoft.projectProducer.pojo.entity.peInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * 评价等级规则
 */
@TableName("Pe_Grade_Rule")
public class PeGradeRule {

    @TableId(type = IdType.INPUT)
    private String rId;
    private Float rStartScore;
    private Float rEndScore;
    private String rGrade;
    private String rRemark;
    private String rCreateUid;
    private String rCreateUname;
    private Date rCreateTime;
    private String teId;

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

    public String getTeId() {
        return teId;
    }

    public void setTeId(String teId) {
        this.teId = teId;
    }
}
