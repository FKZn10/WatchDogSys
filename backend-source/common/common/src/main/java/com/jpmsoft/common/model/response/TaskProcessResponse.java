package com.jpmsoft.common.model.response;

import java.util.Date;
import java.util.List;
import java.util.Map;


public class TaskProcessResponse {

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
    private Integer actOrder;
    private String tpCountersigntitle;
    private Date tpStarttime;
    private Date tpEndtime;
    private String tpTaskid;
    private String tpTaskrb;
    private String tpFrom;
    private String tpFromName;
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
    private String  tpClient;

    /******************************/
    private String tpFlowName;
    private String tpFileTitle;
    //发送人 对应主表tpFrom字段
    private List<String> tpSendName;
    private List<String> tpSendIds;
    private String pName;
    private String secName;
    private String conName;
    private String tpUserName;
    private String tpUserSignature;
    private String tpUserType;
    private String tCode;
    private String tName;
    private String flowRecordRule;
    private String fileCreateUser;
    private String fileCreateUserName;
    private String tpSource;
    private Integer actNum;
    private List<Map<String, Object>> taskUsetAndActList;
    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
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

    public String getTpFlowName() {
        return tpFlowName;
    }

    public void setTpFlowName(String tpFlowName) {
        this.tpFlowName = tpFlowName;
    }

    public String getTpFileTitle() {
        return tpFileTitle;
    }

    public void setTpFileTitle(String tpFileTitle) {
        this.tpFileTitle = tpFileTitle;
    }

    public List<String> getTpSendName() {
        return tpSendName;
    }

    public void setTpSendName(List<String> tpSendName) {
        this.tpSendName = tpSendName;
    }

    public String getTpUserName() {
        return tpUserName;
    }

    public void setTpUserName(String tpUserName) {
        this.tpUserName = tpUserName;
    }

    public String gettCode() {
        return tCode;
    }

    public void settCode(String tCode) {
        this.tCode = tCode;
    }

    public String getTpUserSignature() {
        return tpUserSignature;
    }

    public void setTpUserSignature(String tpUserSignature) {
        this.tpUserSignature = tpUserSignature;
    }

    public String getTpUserType() {
        return tpUserType;
    }

    public void setTpUserType(String tpUserType) {
        this.tpUserType = tpUserType;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public String getFlowRecordRule() {
        return flowRecordRule;
    }

    public void setFlowRecordRule(String flowRecordRule) {
        this.flowRecordRule = flowRecordRule;
    }


    public String getFileCreateUser() {
        return fileCreateUser;
    }

    public void setFileCreateUser(String fileCreateUser) {
        this.fileCreateUser = fileCreateUser;
    }


    public String getFileCreateUserName() {
        return fileCreateUserName;
    }

    public void setFileCreateUserName(String fileCreateUserName) {
        this.fileCreateUserName = fileCreateUserName;
    }

    public String getTpClient() {
        return tpClient;
    }

    public void setTpClient(String tpClient) {
        this.tpClient = tpClient;
    }

    public List<Map<String, Object>> getTaskUsetAndActList() {
        return taskUsetAndActList;
    }

    public void setTaskUsetAndActList(List<Map<String, Object>> taskUsetAndActList) {
        this.taskUsetAndActList = taskUsetAndActList;
    }

    public Integer getActOrder() {
        return actOrder;
    }

    public void setActOrder(Integer actOrder) {
        this.actOrder = actOrder;
    }

    public String getTpSource() {
        return tpSource;
    }

    public void setTpSource(String tpSource) {
        this.tpSource = tpSource;
    }

    public String getTpFromName() {
        return tpFromName;
    }

    public void setTpFromName(String tpFromName) {
        this.tpFromName = tpFromName;
    }

    public List<String> getTpSendIds() {
        return tpSendIds;
    }

    public void setTpSendIds(List<String> tpSendIds) {
        this.tpSendIds = tpSendIds;
    }

    public Integer getActNum() {
        return actNum;
    }

    public void setActNum(Integer actNum) {
        this.actNum = actNum;
    }
}
