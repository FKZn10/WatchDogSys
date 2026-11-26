package com.jpmsoft.projectProducer.pojo.dto.qualitySafety;

import com.jpmsoft.common.base.FileFields;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;

import java.util.Date;


public class QsManageSystemDto extends BaseFileRequest {
    @ProcessFields(type = ProcessFieldEnum.id)
    private String id;
    @ProcessFields(type = ProcessFieldEnum.state)
    private Integer state;
    @ProcessFields(type = ProcessFieldEnum.stateTime)
    private Date stateTime;
    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String createUid;
    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String createUname;
    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date createTime;

    private String updateUid;

    private Date updateTime;

    private String teId;

    //项目id
    private String pId;
    private String pName;
    //项目管理单位
    private String pCompetentDepartment;
    //项目建设模式
    private String pBuildModel;

    //质量管理体系
    @FileFields(index = 1)
    private String manageSystemDoc;


    //质量管理体系
    @FileFields(index = 1 , type = "manageSystemDocFileIds")
    private String manageSystemDocFileIds;

    //质量管理方案
    @FileFields(index = 2)
    private String manageProgrammeDoc;

    //质量管理方案
    @FileFields(index = 2 ,type = "manageProgrammeDocFileIds")
    private String manageProgrammeDocFileIds;

    //质量管理计划
    @FileFields(index = 3)
    private String managePlanDoc;

    //质量管理计划
    @FileFields(index = 3,type = "managePlanDocFileIds")
    private String managePlanDocFileIds;

    //备注
    private String remark;

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpCompetentDepartment() {
        return pCompetentDepartment;
    }

    public void setpCompetentDepartment(String pCompetentDepartment) {
        this.pCompetentDepartment = pCompetentDepartment;
    }

    public String getpBuildModel() {
        return pBuildModel;
    }

    public void setpBuildModel(String pBuildModel) {
        this.pBuildModel = pBuildModel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUid() {
        return updateUid;
    }

    public void setUpdateUid(String updateUid) {
        this.updateUid = updateUid;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String getTeId() {
        return teId;
    }

    @Override
    public void setTeId(String teId) {
        this.teId = teId;
    }

    @Override
    public String getpId() {
        return pId;
    }

    @Override
    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getManageSystemDoc() {
        return manageSystemDoc;
    }

    public void setManageSystemDoc(String manageSystemDoc) {
        this.manageSystemDoc = manageSystemDoc;
    }

    public String getManageSystemDocFileIds() {
        return manageSystemDocFileIds;
    }

    public void setManageSystemDocFileIds(String manageSystemDocFileIds) {
        this.manageSystemDocFileIds = manageSystemDocFileIds;
    }

    public String getManageProgrammeDoc() {
        return manageProgrammeDoc;
    }

    public void setManageProgrammeDoc(String manageProgrammeDoc) {
        this.manageProgrammeDoc = manageProgrammeDoc;
    }

    public String getManageProgrammeDocFileIds() {
        return manageProgrammeDocFileIds;
    }

    public void setManageProgrammeDocFileIds(String manageProgrammeDocFileIds) {
        this.manageProgrammeDocFileIds = manageProgrammeDocFileIds;
    }

    public String getManagePlanDoc() {
        return managePlanDoc;
    }

    public void setManagePlanDoc(String managePlanDoc) {
        this.managePlanDoc = managePlanDoc;
    }

    public String getManagePlanDocFileIds() {
        return managePlanDocFileIds;
    }

    public void setManagePlanDocFileIds(String managePlanDocFileIds) {
        this.managePlanDocFileIds = managePlanDocFileIds;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
