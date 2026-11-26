package com.jpmsoft.common.model.requestAndResponse.Design;

import com.jpmsoft.common.base.FileFields;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.ProjectCommonRes;

import java.util.Date;
import java.util.List;

/**
 * 图纸管理
 */

public class DesignDrawingModel extends ProjectCommonRes {

    @ProcessFields(type = ProcessFieldEnum.id)
    private String ddId;
    //项目ID
    private String pId;
    //设计图名称
    private String ddName;
    //设计图类型（字典编码）
    private String ddType;
    //版本号
    private String ddVersionNumber;
    //单体 字典 JPMsoft_XCDGJZ
    private String ddMonomer;
    //图纸专业
    private String ddProfessional;
    //图纸文件格式 字典 JPMsoft_TZGS
    private String ddFileFormat;
    //图纸文件上传
    @FileFields(index = 1)
    private String ddFileRelationId;
    //图纸文件上传（fileids）
    @FileFields(index = 1,type = "ddFileIds")
    private String ddFileIds;
    //备注
    private String ddRemark;
    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String ddCreateUid;
    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String ddCreateUname;
    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date ddCreateTime;
    @ProcessFields(type = ProcessFieldEnum.state)
    private Integer ddState;
    @ProcessFields(type = ProcessFieldEnum.stateTime)
    private Date ddStateTime;

    private List<String> ddCreateTimes;

    private List<DesignDrawingModel> childDrawing;

    private String ddCategory;

    public String getDdCategory() {
        return ddCategory;
    }

    public void setDdCategory(String ddCategory) {
        this.ddCategory = ddCategory;
    }

    public String getDdId() {
        return ddId;
    }

    public void setDdId(String ddId) {
        this.ddId = ddId;
    }

    @Override
    public String getpId() {
        return pId;
    }

    @Override
    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getDdName() {
        return ddName;
    }

    public void setDdName(String ddName) {
        this.ddName = ddName;
    }

    public String getDdType() {
        return ddType;
    }

    public void setDdType(String ddType) {
        this.ddType = ddType;
    }

    public String getDdVersionNumber() {
        return ddVersionNumber;
    }

    public void setDdVersionNumber(String ddVersionNumber) {
        this.ddVersionNumber = ddVersionNumber;
    }

    public String getDdMonomer() {
        return ddMonomer;
    }

    public void setDdMonomer(String ddMonomer) {
        this.ddMonomer = ddMonomer;
    }

    public String getDdProfessional() {
        return ddProfessional;
    }

    public void setDdProfessional(String ddProfessional) {
        this.ddProfessional = ddProfessional;
    }

    public String getDdFileFormat() {
        return ddFileFormat;
    }

    public void setDdFileFormat(String ddFileFormat) {
        this.ddFileFormat = ddFileFormat;
    }

    public String getDdFileRelationId() {
        return ddFileRelationId;
    }

    public void setDdFileRelationId(String ddFileRelationId) {
        this.ddFileRelationId = ddFileRelationId;
    }

    public String getDdFileIds() {
        return ddFileIds;
    }

    public void setDdFileIds(String ddFileIds) {
        this.ddFileIds = ddFileIds;
    }

    public String getDdRemark() {
        return ddRemark;
    }

    public void setDdRemark(String ddRemark) {
        this.ddRemark = ddRemark;
    }

    public String getDdCreateUid() {
        return ddCreateUid;
    }

    public void setDdCreateUid(String ddCreateUid) {
        this.ddCreateUid = ddCreateUid;
    }

    public String getDdCreateUname() {
        return ddCreateUname;
    }

    public void setDdCreateUname(String ddCreateUname) {
        this.ddCreateUname = ddCreateUname;
    }

    public Date getDdCreateTime() {
        return ddCreateTime;
    }

    public void setDdCreateTime(Date ddCreateTime) {
        this.ddCreateTime = ddCreateTime;
    }

    public Integer getDdState() {
        return ddState;
    }

    public void setDdState(Integer ddState) {
        this.ddState = ddState;
    }

    public Date getDdStateTime() {
        return ddStateTime;
    }

    public void setDdStateTime(Date ddStateTime) {
        this.ddStateTime = ddStateTime;
    }


    public List<String> getDdCreateTimes() {
        return ddCreateTimes;
    }

    public void setDdCreateTimes(List<String> ddCreateTimes) {
        this.ddCreateTimes = ddCreateTimes;
    }

    public List<DesignDrawingModel> getChildDrawing() {
        return childDrawing;
    }

    public void setChildDrawing(List<DesignDrawingModel> childDrawing) {
        this.childDrawing = childDrawing;
    }
}
