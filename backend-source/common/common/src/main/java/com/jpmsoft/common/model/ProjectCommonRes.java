package com.jpmsoft.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.annotations.ClassInfo;
import com.jpmsoft.common.model.response.FileResponse;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 项目信息链表统一返回信息
 *
 * @Author: 李文涛 aaron
 * @date: 2022/11/29 10:20
 */
@Data
public class ProjectCommonRes extends BaseFileRequest implements Serializable {

    private static final long serialVersionUID = 4398987753048002435L;
    //项目主管单位主管部门
//    @JsonProperty("pConstructionUnitName")
//    private String pConstructionUnitName;
//    @JsonProperty("pConsAbbName")
//    private String pConsAbbName;
//    @JsonProperty("pCompetentDepartmentName")
//    private String pCompetentDepartmentName;
    @JsonProperty("pConsName")
    private String pConsName;
    @JsonProperty("pCompDepName")
    private String pCompDepName;

    @JsonProperty("pName")
    @ClassInfo(title = "项目名称",type="string",value = "pname")
    private String pName;
    @JsonProperty("pNameHistory")
    private String pNameHistory;
    @JsonProperty("pCode")
    private String pCode;
    @JsonProperty("pOverview")
    private String pOverview;
    @JsonProperty("pProjectStage")
    private String pProjectStage;
    @JsonProperty("pRegion")
    private String pRegion;
    @JsonProperty("pEachDistrict")
    private String pEachDistrict;
    @JsonProperty("pBuildModel")
    private String pBuildModel;
    @JsonProperty("pApprovalUnit")
    private String pApprovalUnit;
    @JsonProperty("pConstructionUnit")
    private String pConstructionUnit;
    @JsonProperty("pCompetentDepartment")
    private String pCompetentDepartment;
    @JsonProperty("pImpCompany")
    private String pImpCompany;
    @JsonProperty("pUseUnit")
    private String pUseUnit;
    @JsonProperty("pType")
    private String pType;
    @JsonProperty("pMajorType")
    private String pMajorType;
    @JsonProperty("pAgentConstructionUnit")
    private String pAgentConstructionUnit;
    @JsonProperty("pProjectCode")
    private String pProjectCode;
    @JsonProperty("pInvTotal")
    private BigDecimal pInvTotal;
    @JsonProperty("pCalculateRoughlyAmount")
    private BigDecimal pCalculateRoughlyAmount;
    @JsonProperty("pAdvanceFund")
    private Integer pAdvanceFund;
    @JsonProperty("pCorporateRisk")
    private Integer pCorporateRisk;

    @JsonProperty("pAgentUnitName")
    private String pAgentUnitName;
    @JsonProperty("pPlanStartDate")
    private Date pPlanStartDate;
    @JsonProperty("pPlanCompleteDate")
    private Date  pPlanCompleteDate;
    private List<FileResponse> files;
    //工程进展状态
    @JsonProperty("pStatus")
    private String pStatus;

    @JsonProperty("pBuildingScale")
    private Float pBuildingScale;
}
