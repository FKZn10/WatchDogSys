package com.jpmsoft.common.model.requestAndResponse.Design;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;

import java.io.Serializable;
import java.util.Date;

public class DesignQrCodeModel  extends BaseFileRequest implements Serializable {

    private static final long serialVersionUID = 4395489846554884435L;

    @ProcessFields(type = ProcessFieldEnum.id)
    private String id; // 主键ID

    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date createTime; // 创建时间

    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String createUid; // 创建人ID

    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String createUname; // 创建人姓名

    @ProcessFields(type = ProcessFieldEnum.state)
    private Integer state; // 状态

    @ProcessFields(type = ProcessFieldEnum.stateTime)
    private Date stateTime; // 状态更新时间

    @JsonProperty("pId")
    private String pId; // 项目ID

    @JsonProperty("pName")
    private String pName;

    private Date dqcApplyTime;

    private String dqcUsed;

    private String dqcStatus;

    private String driId;

    private String dqCode;

    private String dqPurpose;

    private String dqcApplyPerson;

    private String driBussnisType;

    private Integer hasRelatedDri;


    private String driName; // 成果名称


    private String driType; // 成果类型

    private Integer driPaperNum; // 纸质成果份数

    private String driCategory; // 专业类别

    private Date driTerm; // 失效日期  null 为长期有效

    private String driIssuedDescribe; // 下发详情

    private String driRemark; // 备注

    private String driFormType; // 成果收发类型

    private String designIsIssued; // 是否下发 01 下发 02 不下发 03 补充下发

    public String getDriName() {
        return driName;
    }

    public void setDriName(String driName) {
        this.driName = driName;
    }

    public String getDriType() {
        return driType;
    }

    public void setDriType(String driType) {
        this.driType = driType;
    }

    public Integer getDriPaperNum() {
        return driPaperNum;
    }

    public void setDriPaperNum(Integer driPaperNum) {
        this.driPaperNum = driPaperNum;
    }

    public String getDriCategory() {
        return driCategory;
    }

    public void setDriCategory(String driCategory) {
        this.driCategory = driCategory;
    }

    public Date getDriTerm() {
        return driTerm;
    }

    public void setDriTerm(Date driTerm) {
        this.driTerm = driTerm;
    }

    public String getDriIssuedDescribe() {
        return driIssuedDescribe;
    }

    public void setDriIssuedDescribe(String driIssuedDescribe) {
        this.driIssuedDescribe = driIssuedDescribe;
    }

    public String getDriRemark() {
        return driRemark;
    }

    public void setDriRemark(String driRemark) {
        this.driRemark = driRemark;
    }

    public String getDriFormType() {
        return driFormType;
    }

    public void setDriFormType(String driFormType) {
        this.driFormType = driFormType;
    }

    public String getDesignIsIssued() {
        return designIsIssued;
    }

    public void setDesignIsIssued(String designIsIssued) {
        this.designIsIssued = designIsIssued;
    }

    public Integer getHasRelatedDri() {
        return hasRelatedDri;
    }

    public void setHasRelatedDri(Integer hasRelatedDri) {
        this.hasRelatedDri = hasRelatedDri;
    }

    public String getDriBussnisType() {
        return driBussnisType;
    }

    public void setDriBussnisType(String driBussnisType) {
        this.driBussnisType = driBussnisType;
    }

    public String getDqcApplyPerson() {
        return dqcApplyPerson;
    }

    public void setDqcApplyPerson(String dqcApplyPerson) {
        this.dqcApplyPerson = dqcApplyPerson;
    }

    public String getDqCode() {
        return dqCode;
    }

    public void setDqCode(String dqCode) {
        this.dqCode = dqCode;
    }

    public String getDqPurpose() {
        return dqPurpose;
    }

    public void setDqPurpose(String dqPurpose) {
        this.dqPurpose = dqPurpose;
    }

    public String getDriId() {
        return driId;
    }

    public void setDriId(String driId) {
        this.driId = driId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUid() {
        return createUid;
    }

    public void setCreateUid(String createUid) {
        this.createUid = createUid;
    }

    public String getCreateUname() {
        return createUname;
    }

    public void setCreateUname(String createUname) {
        this.createUname = createUname;
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

    @Override
    public String getpId() {
        return pId;
    }

    @Override
    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public Date getDqcApplyTime() {
        return dqcApplyTime;
    }

    public void setDqcApplyTime(Date dqcApplyTime) {
        this.dqcApplyTime = dqcApplyTime;
    }

    public String getDqcUsed() {
        return dqcUsed;
    }

    public void setDqcUsed(String dqcUsed) {
        this.dqcUsed = dqcUsed;
    }

    public String getDqcStatus() {
        return dqcStatus;
    }

    public void setDqcStatus(String dqcStatus) {
        this.dqcStatus = dqcStatus;
    }
}
