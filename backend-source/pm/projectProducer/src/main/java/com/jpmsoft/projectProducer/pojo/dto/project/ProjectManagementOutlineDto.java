package com.jpmsoft.projectProducer.pojo.dto.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.annotations.ClassInfo;
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
    @ClassInfo(title = "项目名称",type="string",value = "pNameList",dbValue = " pro.p_name ",no = 1)
    @JsonProperty("pNameList")
    private List<String> pNameList;
    @ClassInfo(title = "项目管理单位",type="string",value = "pCompetentDepartmentNameList",dbValue = " d.D_Name ",no = 2)
    @JsonProperty("pCompetentDepartmentNameList")
    private List<String> pCompetentDepartmentNameList;
    @ClassInfo(title = "分支机构",type="string",value = "pDepartmentNameList",dbValue = " d2.D_Name ",no = 3)
    @JsonProperty("pDepartmentNameList")
    private List<String> pDepartmentNameList;
    @ClassInfo(title = "建设模式",type="dictionary",value = "pBuildModelList",dbValue = " mn.p_Build_Model ",dictionary = "JPMsoft_JSMS",no = 4)
    @JsonProperty("pBuildModelList")
    private List<String> pBuildModelList;
    @ClassInfo(title = "创建人",type="string",value = "createUnameList",dbValue = " mn.create_Uname ",no = 5)
    private List<String> createUnameList;
    @ClassInfo(title = "创建时间",type="date",value = "createTime",dbValue = " mn.create_Time",no = 6)
    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date createTime;
    @ClassInfo(title = "流程状态",type="dictionary",value = "stateList",dbValue = "mn.state",dictionary = "JPMsoft_LCZT",no = 7)
    private List<Integer> stateList;

    @JsonProperty("pName")
    private String pName;
}
