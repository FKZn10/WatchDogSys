package com.jpmsoft.common.model.request.Completion;

import com.jpmsoft.common.base.FileFields;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;

import java.util.Date;

/**
 * 项目移交
 */
public class CompletionHandoverRequest extends BaseFileRequest {

    @ProcessFields(type = ProcessFieldEnum.id)
    private String chId;
    private String pId;
    private String caId;
    private String chSupervisorId;
    private String chUserUnitId;
    private String chContractorId;
    private String chRemark;
    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String chCreateUid;
    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String chCreateUname;
    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date chCreateTime;
    @ProcessFields(type = ProcessFieldEnum.state)
    private Integer chState;
    @ProcessFields(type = ProcessFieldEnum.stateTime)
    private Date chStateTime;
    //文件上传
    @FileFields(index = 1)
    private String chDataList;
    @FileFields(index = 2)
    private String chEntityList;
    @FileFields(index = 3)
    private String chProductDesc;
    @FileFields(index = 4)
    private String chQualityDesc;
    @FileFields(index = 5)
    private String chWarrantyDesc;
    @FileFields(index = 6)
    private String chContactTel;
    @FileFields(index = 7)
    private String chBlueprint;
    @FileFields(index = 8)
    private String chDevice;

    private String pName;
    @FileFields(index = 1, type = "chDataListIds")
    private String chDataListIds;
    @FileFields(index = 2, type = "chEntityListIds")
    private String chEntityListIds;
    @FileFields(index = 3, type = "chProductDescIds")
    private String chProductDescIds;
    @FileFields(index = 4, type = "chQualityDescIds")
    private String chQualityDescIds;
    @FileFields(index = 5, type = "chWarrantyDescIds")
    private String chWarrantyDescIds;
    @FileFields(index = 6, type = "chContactTelIds")
    private String chContactTelIds;
    @FileFields(index = 7, type = "chBlueprintIds")
    private String chBlueprintIds;
    @FileFields(index = 8, type = "chDeviceIds")
    private String chDeviceIds;

    public String getChId() {
        return chId;
    }

    public void setChId(String chId) {
        this.chId = chId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getCaId() {
        return caId;
    }

    public void setCaId(String caId) {
        this.caId = caId;
    }

    public String getChSupervisorId() {
        return chSupervisorId;
    }

    public void setChSupervisorId(String chSupervisorId) {
        this.chSupervisorId = chSupervisorId;
    }

    public String getChUserUnitId() {
        return chUserUnitId;
    }

    public void setChUserUnitId(String chUserUnitId) {
        this.chUserUnitId = chUserUnitId;
    }

    public String getChContractorId() {
        return chContractorId;
    }

    public void setChContractorId(String chContractorId) {
        this.chContractorId = chContractorId;
    }

    public String getChRemark() {
        return chRemark;
    }

    public void setChRemark(String chRemark) {
        this.chRemark = chRemark;
    }

    public String getChCreateUid() {
        return chCreateUid;
    }

    public void setChCreateUid(String chCreateUid) {
        this.chCreateUid = chCreateUid;
    }

    public String getChCreateUname() {
        return chCreateUname;
    }

    public void setChCreateUname(String chCreateUname) {
        this.chCreateUname = chCreateUname;
    }

    public Date getChCreateTime() {
        return chCreateTime;
    }

    public void setChCreateTime(Date chCreateTime) {
        this.chCreateTime = chCreateTime;
    }

    public Integer getChState() {
        return chState;
    }

    public void setChState(Integer chState) {
        this.chState = chState;
    }

    public Date getChStateTime() {
        return chStateTime;
    }

    public void setChStateTime(Date chStateTime) {
        this.chStateTime = chStateTime;
    }

    public String getChDataList() {
        return chDataList;
    }

    public void setChDataList(String chDataList) {
        this.chDataList = chDataList;
    }

    public String getChEntityList() {
        return chEntityList;
    }

    public void setChEntityList(String chEntityList) {
        this.chEntityList = chEntityList;
    }

    public String getChProductDesc() {
        return chProductDesc;
    }

    public void setChProductDesc(String chProductDesc) {
        this.chProductDesc = chProductDesc;
    }

    public String getChQualityDesc() {
        return chQualityDesc;
    }

    public void setChQualityDesc(String chQualityDesc) {
        this.chQualityDesc = chQualityDesc;
    }

    public String getChWarrantyDesc() {
        return chWarrantyDesc;
    }

    public void setChWarrantyDesc(String chWarrantyDesc) {
        this.chWarrantyDesc = chWarrantyDesc;
    }

    public String getChContactTel() {
        return chContactTel;
    }

    public void setChContactTel(String chContactTel) {
        this.chContactTel = chContactTel;
    }

    public String getChBlueprint() {
        return chBlueprint;
    }

    public void setChBlueprint(String chBlueprint) {
        this.chBlueprint = chBlueprint;
    }

    public String getChDevice() {
        return chDevice;
    }

    public void setChDevice(String chDevice) {
        this.chDevice = chDevice;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getChDataListIds() {
        return chDataListIds;
    }

    public void setChDataListIds(String chDataListIds) {
        this.chDataListIds = chDataListIds;
    }

    public String getChEntityListIds() {
        return chEntityListIds;
    }

    public void setChEntityListIds(String chEntityListIds) {
        this.chEntityListIds = chEntityListIds;
    }

    public String getChProductDescIds() {
        return chProductDescIds;
    }

    public void setChProductDescIds(String chProductDescIds) {
        this.chProductDescIds = chProductDescIds;
    }

    public String getChQualityDescIds() {
        return chQualityDescIds;
    }

    public void setChQualityDescIds(String chQualityDescIds) {
        this.chQualityDescIds = chQualityDescIds;
    }

    public String getChWarrantyDescIds() {
        return chWarrantyDescIds;
    }

    public void setChWarrantyDescIds(String chWarrantyDescIds) {
        this.chWarrantyDescIds = chWarrantyDescIds;
    }

    public String getChContactTelIds() {
        return chContactTelIds;
    }

    public void setChContactTelIds(String chContactTelIds) {
        this.chContactTelIds = chContactTelIds;
    }

    public String getChBlueprintIds() {
        return chBlueprintIds;
    }

    public void setChBlueprintIds(String chBlueprintIds) {
        this.chBlueprintIds = chBlueprintIds;
    }

    public String getChDeviceIds() {
        return chDeviceIds;
    }

    public void setChDeviceIds(String chDeviceIds) {
        this.chDeviceIds = chDeviceIds;
    }
}
