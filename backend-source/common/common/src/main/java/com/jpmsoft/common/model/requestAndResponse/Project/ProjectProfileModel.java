package com.jpmsoft.common.model.requestAndResponse.Project;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.base.BaseFileDto;

import java.util.Date;

public class ProjectProfileModel extends BaseFileDto {

    private String id;

    // 图片类型 01 施工效果图  02 形象进度图
    private String ppType;

    // 当前阶段
    private String ppCurrent;

    // 形象进度
    private String progress;

    // 标段id
    private String secId;

    // 日期
    private Date pDate;

    // 备注
    private String remark;

    // 项目id
    @JsonProperty("pId")
    private String pId;

    @JsonProperty("pName")
    private String pName;

    // 文件状态
    private String fileStatus;

    private String createUid;

    private Date createTime;

    private String createUname;

    private Integer state;

    private Date stateTime;

    // 是否同步形象进度到驾驶舱 01 显示  02 隐藏
    private String imgShow;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getPpType() {
        return ppType;
    }

    public void setPpType(String ppType) {
        this.ppType = ppType;
    }

    public String getPpCurrent() {
        return ppCurrent;
    }

    public void setPpCurrent(String ppCurrent) {
        this.ppCurrent = ppCurrent;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    @Override
    public String getSecId() {
        return secId;
    }

    @Override
    public void setSecId(String secId) {
        this.secId = secId;
    }

    public Date getpDate() {
        return pDate;
    }

    public void setpDate(Date pDate) {
        this.pDate = pDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(String fileStatus) {
        this.fileStatus = fileStatus;
    }

    @Override
    public String getCreateUid() {
        return createUid;
    }

    @Override
    public void setCreateUid(String createUid) {
        this.createUid = createUid;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String getCreateUname() {
        return createUname;
    }

    @Override
    public void setCreateUname(String createUname) {
        this.createUname = createUname;
    }

    @Override
    public Integer getState() {
        return state;
    }

    @Override
    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public Date getStateTime() {
        return stateTime;
    }

    @Override
    public void setStateTime(Date stateTime) {
        this.stateTime = stateTime;
    }

    public String getImgShow() {
        return imgShow;
    }

    public void setImgShow(String imgShow) {
        this.imgShow = imgShow;
    }
}
