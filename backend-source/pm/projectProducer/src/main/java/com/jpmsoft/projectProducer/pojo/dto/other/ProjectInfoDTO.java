package com.jpmsoft.projectProducer.pojo.dto.other;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 项目名称、建设程序名称、项目管理单位、所在分支机构/事业部、建设模式、创建人、创建时间
 *     private String pCompetentDepartmentName;
 *     private String pDepartmentName;
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectInfoDTO {
    /**
     * 成果办理ID 多次办理
     */
    private String pcrId;
    @JsonProperty("pName")
    private String pName;
    private String cpdName;
    private String createUname;
    private Date createTime;
    private String pcrDoc;
    private String fileApprovalTime;
    private String fileName;
    @JsonProperty("pCompetentDepartmentName")
    private String  pCompetentDepartmentName; //项目管理单位
    @JsonProperty("pDepartmentName")
    private String pDepartmentName;  //分支机构
    @JsonProperty("pBuildModel")
    private String pBuildModel;
    private String fileRelationId;

    @JsonProperty("pCategory")
    private String pCategory;
    private String fileExpire;
    private Date fileExpirationDate;
}
