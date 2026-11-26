package com.jpmsoft.projectProducer.pojo.vo.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ProjectManagementOutlineVo {
    private String id;
    private String projectId;
    @JsonProperty("pDepartmentName")
    private String pDepartmentName;
    @JsonProperty("pCompetentDepartmentName")
    private String pCompetentDepartmentName;
    @JsonProperty("pBuildModel")
    private String pBuildModel;
    @JsonProperty("pName")
    private String pName;
    private String title;
    private String urgencyLevel;
    private String preparedBy;
    private String preparedName;
    private String projectScale;
    private String supportingDocument;
    private String reviewOpinionForm;
    private String projectUndertake;
    private String projectUndertakeName;
    /**
     * 创建时间
     */
    private Date createTime;
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
    private Integer state;
    private Date stateTime;
    private String createUid;
    private String createUname;

}
