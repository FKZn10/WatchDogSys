package com.jpmsoft.common.model.request.ActualMeasurement;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseFileRequest;
import com.jpmsoft.common.model.response.ActualMeasurement.TaskDistributionCheckModel;

import java.util.Date;
import java.util.List;

/**
 * @author jjz
 */
public class TaskDistributionRequest extends BaseFileRequest {

    private String tdId;

    // 项目id
    @JsonProperty("pId")
    private String pId;

    private String pName;

    // 任务名称
    private String taskName;

    // 任务编码
    private String taskCode;

    // 对应图纸表的id
    private String diId;

    private String dmId;

    // 批量生成任务时传入多个图纸目录ID；只生成任务主表和可操作用户集合，不预生成检查点。
    // 这样兼容旧逻辑：老数据仍靠检查点负责人可见，新数据可先授权用户看到图纸再动态加点。
    private List<String> dmIds;

    // 图纸操作授权用户集合，多个用户用英文逗号分隔。
    // 该字段只表示用户能看到/操作此图纸任务，不代表该用户已经有检查点。
    private String taskUserIds;

    // 图纸操作授权用户名称集合，由前端按展示格式拼好后传入，后端原样保存并返回。
    private String taskUserNames;

    private String createUid;

    private String createUname;

    private Date createTime;

    private Integer state;

    private Date stateTime;

    private List<TaskDistributionCheckModel> taskDistributionCheckModels;

    public List<TaskDistributionCheckModel> getTaskDistributionCheckModels() {
        return taskDistributionCheckModels;
    }

    public void setTaskDistributionCheckModels(List<TaskDistributionCheckModel> taskDistributionCheckModels) {
        this.taskDistributionCheckModels = taskDistributionCheckModels;
    }

    public String getDmId() {
        return dmId;
    }

    public void setDmId(String dmId) {
        this.dmId = dmId;
    }

    public List<String> getDmIds() {
        return dmIds;
    }

    public void setDmIds(List<String> dmIds) {
        this.dmIds = dmIds;
    }

    public String getTaskUserIds() {
        return taskUserIds;
    }

    public void setTaskUserIds(String taskUserIds) {
        this.taskUserIds = taskUserIds;
    }

    public String getTaskUserNames() {
        return taskUserNames;
    }

    public void setTaskUserNames(String taskUserNames) {
        this.taskUserNames = taskUserNames;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getTdId() {
        return tdId;
    }

    public void setTdId(String tdId) {
        this.tdId = tdId;
    }

    @Override
    public String getpId() {
        return pId;
    }

    @Override
    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getDiId() {
        return diId;
    }

    public void setDiId(String diId) {
        this.diId = diId;
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
}
