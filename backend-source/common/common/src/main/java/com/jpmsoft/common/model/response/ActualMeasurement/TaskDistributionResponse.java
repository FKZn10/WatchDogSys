package com.jpmsoft.common.model.response.ActualMeasurement;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.BaseFileRequest;
import com.jpmsoft.common.model.BaseFileResponse;


import java.util.Date;
import java.util.List;

/**
 * @author jjz
 */
public class TaskDistributionResponse extends BaseFileResponse {


    private String tdId;

    // 项目id
    @JsonProperty("pId")
    private String pId;

    @JsonProperty("pName")
    private String pName;

    // 任务名称
    private String taskName;

    // 任务编码

    private String taskCode;

    // 对应图纸表的id
    private String diId;

    private String createUid;

    private String createUname;

    private Date createTime;

    private Integer state;

    private Date stateTime;

    private String diRegionName;

    private String diRegionCode;

    private String diRegionGroup;


    private String diDrawing;

    // 检查点X轴
    private Float checkPointX;

    // 检查点Y轴
    private Float checkPointY;

    private String dmDrawing;

    private String dmRegionName;

    private String dmCode;

    // 图纸操作授权用户集合，多个用户用英文逗号分隔。
    // 返回该字段是为了让前端能区分“用户有权限但还没有检查点”的空图纸任务。
    private String taskUserIds;

    // 图纸操作授权用户名称集合，后端不解析，只负责把任务主表里的展示值返回给前端。
    private String taskUserNames;

    private String ciId;

    private String ciParentName;



    private List<TaskDistributionCheckModel> taskDistributionCheckModels;

    /**
     * 任务分配 检查项统计
     */

    // 一共多少个检查项
    private Integer checkItemCount;

    // 已检查项
    private Integer checkItemCheckedCount;

    // 未检查项
    private Integer checkItemUnCheckedCount;

    // 整改项
    private Integer checkItemRectificationCount;

    public Integer getCheckItemCount() {
        return checkItemCount;
    }

    public void setCheckItemCount(Integer checkItemCount) {
        this.checkItemCount = checkItemCount;
    }

    public Integer getCheckItemCheckedCount() {
        return checkItemCheckedCount;
    }

    public void setCheckItemCheckedCount(Integer checkItemCheckedCount) {
        this.checkItemCheckedCount = checkItemCheckedCount;
    }

    public Integer getCheckItemUnCheckedCount() {
        return checkItemUnCheckedCount;
    }

    public void setCheckItemUnCheckedCount(Integer checkItemUnCheckedCount) {
        this.checkItemUnCheckedCount = checkItemUnCheckedCount;
    }

    public Integer getCheckItemRectificationCount() {
        return checkItemRectificationCount;
    }

    public void setCheckItemRectificationCount(Integer checkItemRectificationCount) {
        this.checkItemRectificationCount = checkItemRectificationCount;
    }

    public String getCiParentName() {
        return ciParentName;
    }

    public void setCiParentName(String ciParentName) {
        this.ciParentName = ciParentName;
    }

    public String getCiId() {
        return ciId;
    }

    public void setCiId(String ciId) {
        this.ciId = ciId;
    }

    public String getDmRegionName() {
        return dmRegionName;
    }

    public void setDmRegionName(String dmRegionName) {
        this.dmRegionName = dmRegionName;
    }

    public String getDmDrawing() {
        return dmDrawing;
    }

    public void setDmDrawing(String dmDrawing) {
        this.dmDrawing = dmDrawing;
    }

    public String getDmCode() {
        return dmCode;
    }

    public void setDmCode(String dmCode) {
        this.dmCode = dmCode;
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

    public Float getCheckPointX() {
        return checkPointX;
    }

    public void setCheckPointX(Float checkPointX) {
        this.checkPointX = checkPointX;
    }

    public Float getCheckPointY() {
        return checkPointY;
    }

    public void setCheckPointY(Float checkPointY) {
        this.checkPointY = checkPointY;
    }



    public String getDiDrawing() {
        return diDrawing;
    }

    public void setDiDrawing(String diDrawing) {
        this.diDrawing = diDrawing;
    }

    public String getDiRegionName() {
        return diRegionName;
    }

    public void setDiRegionName(String diRegionName) {
        this.diRegionName = diRegionName;
    }

    public String getDiRegionCode() {
        return diRegionCode;
    }

    public void setDiRegionCode(String diRegionCode) {
        this.diRegionCode = diRegionCode;
    }

    public String getDiRegionGroup() {
        return diRegionGroup;
    }

    public void setDiRegionGroup(String diRegionGroup) {
        this.diRegionGroup = diRegionGroup;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
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

    public List<TaskDistributionCheckModel> getTaskDistributionCheckModels() {
        return taskDistributionCheckModels;
    }

    public void setTaskDistributionCheckModels(List<TaskDistributionCheckModel> taskDistributionCheckModels) {
        this.taskDistributionCheckModels = taskDistributionCheckModels;
    }


}
