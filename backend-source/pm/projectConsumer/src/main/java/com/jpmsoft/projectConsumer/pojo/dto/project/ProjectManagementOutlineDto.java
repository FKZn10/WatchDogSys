package com.jpmsoft.projectConsumer.pojo.dto.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.base.FileFields;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ProjectManagementOutlineDto extends BaseFileRequest {
    @ProcessFields(type = ProcessFieldEnum.id)
    private String id;
    private String projectId;
    private String title;
    private String urgencyLevel;
    private String preparedBy;
    private String preparedName;
    private String projectScale;
    @FileFields(index = 1, type = "fileRelationId")
    private String supportingDocument;
    @FileFields(index = 2, type = "fileRelationId")
    private String reviewOpinionForm;
    @FileFields(index = 3, type = "supportingDocumentFileIds")
    private String supportingDocumentFileIds;
    @FileFields(index = 4, type = "reviewOpinionFormFileIds")
    private String reviewOpinionFormFileIds;
    private String projectUndertake;
    private String projectUndertakeName;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新人
     */
    private String updateBy;
    /**
     * 删除状态：1正常 2删除
     */
    private Integer delFlag;
    /**
     * 状态：1 有效，2 禁用
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;
    @ProcessFields(type = ProcessFieldEnum.state)
    private Integer state;
    @ProcessFields(type = ProcessFieldEnum.stateTime)
    private Date stateTime;
    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String createUid;
    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String createUname;
    /**
     * 建设模式
     */
    private String pBuildModel;

    @JsonProperty("pDepartmentNameList")
    private List<String> pDepartmentNameList;
    @JsonProperty("pCompetentDepartmentNameList")
    private List<String> pCompetentDepartmentNameList;
    @JsonProperty("pBuildModelList")
    private List<String> pBuildModelList;
    @JsonProperty("pNameList")
    private List<String> pNameList;
    private List<String> stateList;
    private List<String> createUnameList;
    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date createTime;

    @JsonProperty("pName")
    private String pName;
}
