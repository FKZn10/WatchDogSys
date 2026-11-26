package com.jpmsoft.jpbpm.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * 文档创建时，用于指定该文档流转时的所有活动的实例信息，
 * 如活动计划完成时间、活动操作者、活动是否需要办理等
 *（用于管控流程的实例）
 */
@TableName("SYS_FileFlowAct")
public class FileFlowAct {

    @TableId(type = IdType.INPUT)
    public String ffauId;
    public String ffauActid;
    public String ffauMId;
    public Date ffauPctime;
    public Date ffauCtime;
    public Integer ffauRequire;
    public String ffauMrbl;
    public String ffauJsfs;
    //1：未办理
    //2：办理中
    //3：办理完成
    public Integer ffauStatus;
    public String ffauTemplatecode;
    public String ffauTemplatecodeo;
    public String ffauZrrid;
    public Integer ffauXh;
    private String teId;

    public String getFfauId() {
        return ffauId;
    }

    public void setFfauId(String ffauId) {
        this.ffauId = ffauId;
    }

    public String getFfauActid() {
        return ffauActid;
    }

    public void setFfauActid(String ffauActid) {
        this.ffauActid = ffauActid;
    }

    public String getFfauMId() {
        return ffauMId;
    }

    public void setFfauMId(String ffauMId) {
        this.ffauMId = ffauMId;
    }

    public Date getFfauPctime() {
        return ffauPctime;
    }

    public void setFfauPctime(Date ffauPctime) {
        this.ffauPctime = ffauPctime;
    }

    public Date getFfauCtime() {
        return ffauCtime;
    }

    public void setFfauCtime(Date ffauCtime) {
        this.ffauCtime = ffauCtime;
    }

    public Integer getFfauRequire() {
        return ffauRequire;
    }

    public void setFfauRequire(Integer ffauRequire) {
        this.ffauRequire = ffauRequire;
    }

    public String getFfauMrbl() {
        return ffauMrbl;
    }

    public void setFfauMrbl(String ffauMrbl) {
        this.ffauMrbl = ffauMrbl;
    }

    public String getFfauJsfs() {
        return ffauJsfs;
    }

    public void setFfauJsfs(String ffauJsfs) {
        this.ffauJsfs = ffauJsfs;
    }

    public Integer getFfauStatus() {
        return ffauStatus;
    }

    public void setFfauStatus(Integer ffauStatus) {
        this.ffauStatus = ffauStatus;
    }

    public String getFfauTemplatecode() {
        return ffauTemplatecode;
    }

    public void setFfauTemplatecode(String ffauTemplatecode) {
        this.ffauTemplatecode = ffauTemplatecode;
    }

    public String getFfauTemplatecodeo() {
        return ffauTemplatecodeo;
    }

    public void setFfauTemplatecodeo(String ffauTemplatecodeo) {
        this.ffauTemplatecodeo = ffauTemplatecodeo;
    }

    public String getFfauZrrid() {
        return ffauZrrid;
    }

    public void setFfauZrrid(String ffauZrrid) {
        this.ffauZrrid = ffauZrrid;
    }

    public Integer getFfauXh() {
        return ffauXh;
    }

    public void setFfauXh(Integer ffauXh) {
        this.ffauXh = ffauXh;
    }

    public String getTeId() {
        return teId;
    }

    public void setTeId(String teId) {
        this.teId = teId;
    }
}
