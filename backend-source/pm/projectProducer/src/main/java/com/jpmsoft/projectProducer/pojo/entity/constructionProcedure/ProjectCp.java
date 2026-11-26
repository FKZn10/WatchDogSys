package com.jpmsoft.projectProducer.pojo.entity.constructionProcedure;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * 项目建设程序
 */
@TableName("Project_Cp")
public class ProjectCp {

    @TableId(type = IdType.INPUT)
    private String cpId;
    private String pId;
    private Date cpCreateTime;
    private String cpCreateUid;
    private String cpCreateUname;
    private String cpRemark;
    private String cpltId;
    private Integer state;
    private Date stateTime;
    private String teId;

    public String getCpId() {
        return cpId;
    }

    public void setCpId(String cpId) {
        this.cpId = cpId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public Date getCpCreateTime() {
        return cpCreateTime;
    }

    public void setCpCreateTime(Date cpCreateTime) {
        this.cpCreateTime = cpCreateTime;
    }

    public String getCpCreateUid() {
        return cpCreateUid;
    }

    public void setCpCreateUid(String cpCreateUid) {
        this.cpCreateUid = cpCreateUid;
    }

    public String getCpCreateUname() {
        return cpCreateUname;
    }

    public void setCpCreateUname(String cpCreateUname) {
        this.cpCreateUname = cpCreateUname;
    }

    public String getCpRemark() {
        return cpRemark;
    }

    public void setCpRemark(String cpRemark) {
        this.cpRemark = cpRemark;
    }

    public String getCpltId() {
        return cpltId;
    }

    public void setCpltId(String cpltId) {
        this.cpltId = cpltId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getStateTime() {
        return stateTime;
    }

    public void setStateTime(Date stateTime) {
        this.stateTime = stateTime;
    }

    public String getTeId() {
        return teId;
    }

    public void setTeId(String teId) {
        this.teId = teId;
    }
}
