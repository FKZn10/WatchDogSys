package com.jpmsoft.jpbpm.pojo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("Sys_Task_Processin")
public class TaskProcessin {

    @TableId(type = IdType.INPUT)
    private String tpId;
    private String tpFlowid;
    private String tpActid;
    private String tpRuleid;
    private String tpEntityid;
    private Date tpDotime;
    private String tpUser;
    private String tpResult;
    private String tpExplain;
    private String tpVote;
    private String tpProject;
    private String tpName;
    private String tpType;
    private Integer tpSkip;
    private String tpSkipuser;
    private Date tpSigntime;
    private String actname;
    private String tpCountersigntitle;
    private Date tpStarttime;
    private Date tpEndtime;
    private String tpTaskid;
    private String tpTaskrb;
    private String tpFrom;
    private String tpSend;
    private String tpFileid;
    private String tpTemplatecode;
    private String tpTemplatecodeo;
    private String tpPerson;
    private String tpActname;
    private String tpActtype;
    private Float tpActhour;
    private String tpSection;
    private String tpContract;
    private String tpTaskgetflagstr;
    private String tpTaskReturnType;

    private String tpActivityType;
    private String teId;
    private String  tpClient;

    public String getTpActivityType() {
        return tpActivityType;
    }

    public void setTpActivityType(String tpActivityType) {
        this.tpActivityType = tpActivityType;
    }

    public String getTpTaskReturnType() {
        return tpTaskReturnType;
    }

    public void setTpTaskReturnType(String tpTaskReturnType) {
        this.tpTaskReturnType = tpTaskReturnType;
    }

    public String getTpTaskgetflagstr() {
        return tpTaskgetflagstr;
    }

    public void setTpTaskgetflagstr(String tpTaskgetflagstr) {
        this.tpTaskgetflagstr = tpTaskgetflagstr;
    }

    public String getTpId() {
        return tpId;
    }

    public void setTpId(String tpId) {
        this.tpId = tpId;
    }

    public String getTpFlowid() {
        return tpFlowid;
    }

    public void setTpFlowid(String tpFlowid) {
        this.tpFlowid = tpFlowid;
    }

    public String getTpActid() {
        return tpActid;
    }

    public void setTpActid(String tpActid) {
        this.tpActid = tpActid;
    }

    public String getTpRuleid() {
        return tpRuleid;
    }

    public void setTpRuleid(String tpRuleid) {
        this.tpRuleid = tpRuleid;
    }

    public String getTpEntityid() {
        return tpEntityid;
    }

    public void setTpEntityid(String tpEntityid) {
        this.tpEntityid = tpEntityid;
    }

    public Date getTpDotime() {
        return tpDotime;
    }

    public void setTpDotime(Date tpDotime) {
        this.tpDotime = tpDotime;
    }

    public String getTpUser() {
        return tpUser;
    }

    public void setTpUser(String tpUser) {
        this.tpUser = tpUser;
    }

    public String getTpResult() {
        return tpResult;
    }

    public void setTpResult(String tpResult) {
        this.tpResult = tpResult;
    }

    public String getTpExplain() {
        return tpExplain;
    }

    public void setTpExplain(String tpExplain) {
        this.tpExplain = tpExplain;
    }

    public String getTpVote() {
        return tpVote;
    }

    public void setTpVote(String tpVote) {
        this.tpVote = tpVote;
    }

    public String getTpProject() {
        return tpProject;
    }

    public void setTpProject(String tpProject) {
        this.tpProject = tpProject;
    }

    public String getTpName() {
        return tpName;
    }

    public void setTpName(String tpName) {
        this.tpName = tpName;
    }

    public String getTpType() {
        return tpType;
    }

    public void setTpType(String tpType) {
        this.tpType = tpType;
    }

    public Integer getTpSkip() {
        return tpSkip;
    }

    public void setTpSkip(Integer tpSkip) {
        this.tpSkip = tpSkip;
    }

    public String getTpSkipuser() {
        return tpSkipuser;
    }

    public void setTpSkipuser(String tpSkipuser) {
        this.tpSkipuser = tpSkipuser;
    }

    public Date getTpSigntime() {
        return tpSigntime;
    }

    public void setTpSigntime(Date tpSigntime) {
        this.tpSigntime = tpSigntime;
    }

    public String getActname() {
        return actname;
    }

    public void setActname(String actname) {
        this.actname = actname;
    }

    public String getTpCountersigntitle() {
        return tpCountersigntitle;
    }

    public void setTpCountersigntitle(String tpCountersigntitle) {
        this.tpCountersigntitle = tpCountersigntitle;
    }

    public Date getTpStarttime() {
        return tpStarttime;
    }

    public void setTpStarttime(Date tpStarttime) {
        this.tpStarttime = tpStarttime;
    }

    public Date getTpEndtime() {
        return tpEndtime;
    }

    public void setTpEndtime(Date tpEndtime) {
        this.tpEndtime = tpEndtime;
    }

    public String getTpTaskid() {
        return tpTaskid;
    }

    public void setTpTaskid(String tpTaskid) {
        this.tpTaskid = tpTaskid;
    }

    public String getTpTaskrb() {
        return tpTaskrb;
    }

    public void setTpTaskrb(String tpTaskrb) {
        this.tpTaskrb = tpTaskrb;
    }

    public String getTpFrom() {
        return tpFrom;
    }

    public void setTpFrom(String tpFrom) {
        this.tpFrom = tpFrom;
    }

    public String getTpSend() {
        return tpSend;
    }

    public void setTpSend(String tpSend) {
        this.tpSend = tpSend;
    }

    public String getTpFileid() {
        return tpFileid;
    }

    public void setTpFileid(String tpFileid) {
        this.tpFileid = tpFileid;
    }

    public String getTpTemplatecode() {
        return tpTemplatecode;
    }

    public void setTpTemplatecode(String tpTemplatecode) {
        this.tpTemplatecode = tpTemplatecode;
    }

    public String getTpTemplatecodeo() {
        return tpTemplatecodeo;
    }

    public void setTpTemplatecodeo(String tpTemplatecodeo) {
        this.tpTemplatecodeo = tpTemplatecodeo;
    }

    public String getTpPerson() {
        return tpPerson;
    }

    public void setTpPerson(String tpPerson) {
        this.tpPerson = tpPerson;
    }

    public String getTpActname() {
        return tpActname;
    }

    public void setTpActname(String tpActname) {
        this.tpActname = tpActname;
    }

    public String getTpActtype() {
        return tpActtype;
    }

    public void setTpActtype(String tpActtype) {
        this.tpActtype = tpActtype;
    }

    public Float getTpActhour() {
        return tpActhour;
    }

    public void setTpActhour(Float tpActhour) {
        this.tpActhour = tpActhour;
    }

    public String getTpSection() {
        return tpSection;
    }

    public void setTpSection(String tpSection) {
        this.tpSection = tpSection;
    }

    public String getTpContract() {
        return tpContract;
    }

    public void setTpContract(String tpContract) {
        this.tpContract = tpContract;
    }

    public String getTeId() {
        return teId;
    }

    public void setTeId(String teId) {
        this.teId = teId;
    }

    public String getTpClient() {
        return tpClient;
    }

    public void setTpClient(String tpClient) {
        this.tpClient = tpClient;
    }
}
