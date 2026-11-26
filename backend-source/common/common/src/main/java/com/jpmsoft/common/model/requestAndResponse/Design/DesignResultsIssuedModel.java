package com.jpmsoft.common.model.requestAndResponse.Design;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class DesignResultsIssuedModel extends BaseFileRequest implements Serializable {
    private static final long serialVersionUID = 43989846304872435L;

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

    private String driName; // 成果名称

    private String driBussnisType  ; // 业务类型

    private String driType; // 成果类型

    private Integer driPaperNum; // 纸质成果份数

    private String driCategory; // 专业类别

    private Date driTerm; // 生效日期

    private String driIssuedDescribe; // 下发详情

    private String driRemark; // 备注

    private String dqcId; // 二维码ID

    private String driFormType; // 成果收发类型

    private String designIsIssued;  // 01 标识 下发  02  标识  下发与补发  03 标识 补发  null 默认 02

    private String dqCode;

    private String diCompanyType;

    private String dlUid;

    private String dlTime;

    private String dlUname;

    private String dlfileName;

    private String dlfileId;

    private Integer downloadCount;

    // 下发记录表 是否下发字段
    private String diIsIssued;

    //父级成果ID
    private String driParentId;

    // 是否变更
    private String driIsChange;

    private List<DesignIssuedDetailModel> designIssuedDetails;

    public String getDriIsChange() {
        return driIsChange;
    }

    public void setDriIsChange(String driIsChange) {
        this.driIsChange = driIsChange;
    }

    public String getDriParentId() {
        return driParentId;
    }

    public void setDriParentId(String driParentId) {
        this.driParentId = driParentId;
    }

    public String getDiIsIssued() {
        return diIsIssued;
    }

    public void setDiIsIssued(String diIsIssued) {
        this.diIsIssued = diIsIssued;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    public String getDlUid() {
        return dlUid;
    }

    public void setDlUid(String dlUid) {
        this.dlUid = dlUid;
    }

    public String getDlTime() {
        return dlTime;
    }

    public void setDlTime(String dlTime) {
        this.dlTime = dlTime;
    }

    public String getDlUname() {
        return dlUname;
    }

    public void setDlUname(String dlUname) {
        this.dlUname = dlUname;
    }

    public String getDlfileName() {
        return dlfileName;
    }

    public void setDlfileName(String dlfileName) {
        this.dlfileName = dlfileName;
    }

    public String getDlfileId() {
        return dlfileId;
    }

    public void setDlfileId(String dlfileId) {
        this.dlfileId = dlfileId;
    }

    public String getDiCompanyType() {
        return diCompanyType;
    }

    public void setDiCompanyType(String diCompanyType) {
        this.diCompanyType = diCompanyType;
    }

    public String getDqCode() {
        return dqCode;
    }

    public void setDqCode(String dqCode) {
        this.dqCode = dqCode;
    }

    public String getDesignIsIssued() {
        return designIsIssued;
    }

    public void setDesignIsIssued(String designIsIssued) {
        this.designIsIssued = designIsIssued;
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

    public String getDriName() {
        return driName;
    }

    public void setDriName(String driName) {
        this.driName = driName;
    }

    public String getDriBussnisType() {
        return driBussnisType;
    }

    public void setDriBussnisType(String driBussnisType) {
        this.driBussnisType = driBussnisType;
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

    public String getDqcId() {
        return dqcId;
    }

    public void setDqcId(String dqcId) {
        this.dqcId = dqcId;
    }

    public String getDriFormType() {
        return driFormType;
    }

    public void setDriFormType(String driFormType) {
        this.driFormType = driFormType;
    }

    public List<DesignIssuedDetailModel> getDesignIssuedDetails() {
        return designIssuedDetails;
    }

    public void setDesignIssuedDetails(List<DesignIssuedDetailModel> designIssuedDetails) {
        this.designIssuedDetails = designIssuedDetails;
    }
}
