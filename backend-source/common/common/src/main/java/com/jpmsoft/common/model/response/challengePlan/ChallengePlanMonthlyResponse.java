package com.jpmsoft.common.model.response.challengePlan;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author tianhelin
 * @date 2025/5/30
 */
@Data
public class ChallengePlanMonthlyResponse {
    private String id;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private String cpaId;
    @JsonProperty("pId")
    private String pId;
    @JsonProperty("pName")
    private String pName;
    @JsonProperty("pCompetentDepartmentName")
    private String pCompetentDepartmentName;
    @JsonProperty("pBuildYear")
    private String pBuildYear;
    private String orderNumber;
    private String problems;
    private String planProgress;
    private String declareYear;
    private String declareMonth;
    private String executionStatus;
    private String specificProgress;
    private String fileRelationId;
    private String fileIds;
}