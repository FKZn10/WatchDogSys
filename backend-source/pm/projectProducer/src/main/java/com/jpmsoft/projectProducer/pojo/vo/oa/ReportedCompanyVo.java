package com.jpmsoft.projectProducer.pojo.vo.oa;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.projectProducer.pojo.dto.oa.ReportedCompanyDto;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: 李文涛 aaron
 * @date: 2022/9/21 14:40
 */
@Data
public class ReportedCompanyVo extends ReportedCompanyDto {

    private static final long serialVersionUID = 5397065550792639923L;

    @JsonProperty("pConsName")
    private String pConsName;

    @JsonProperty("pName")
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
    @JsonProperty("pInvTotal")
    private BigDecimal pInvTotal;
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
    @JsonProperty("pAgentConstructionUnit")
    private String pAgentConstructionUnit;

    @JsonProperty("pAgentUnitName")
    private String pAgentUnitName;
}
