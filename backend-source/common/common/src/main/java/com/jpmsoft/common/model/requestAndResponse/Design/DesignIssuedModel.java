package com.jpmsoft.common.model.requestAndResponse.Design;

import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;

import java.util.Date;
import java.util.List;

public class DesignIssuedModel  extends BaseFileRequest {

    // 主键id
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

    private String diEntityId;

    private String diModule;

    private String diIsIssued;  // 01 下发 03 补充下发

    List<DesignIssuedDetailModel> designIssuedDetails;

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

    public String getDiEntityId() {
        return diEntityId;
    }

    public void setDiEntityId(String diEntityId) {
        this.diEntityId = diEntityId;
    }

    public String getDiModule() {
        return diModule;
    }

    public void setDiModule(String diModule) {
        this.diModule = diModule;
    }

    public String getDiIsIssued() {
        return diIsIssued;
    }

    public void setDiIsIssued(String diIsIssued) {
        this.diIsIssued = diIsIssued;
    }

    public List<DesignIssuedDetailModel> getDesignIssuedDetails() {
        return designIssuedDetails;
    }

    public void setDesignIssuedDetails(List<DesignIssuedDetailModel> designIssuedDetails) {
        this.designIssuedDetails = designIssuedDetails;
    }
}
