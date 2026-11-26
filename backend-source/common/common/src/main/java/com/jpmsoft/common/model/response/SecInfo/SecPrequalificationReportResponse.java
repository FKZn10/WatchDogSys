package com.jpmsoft.common.model.response.SecInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * 资格预审情况报告
 */
@Data
public class SecPrequalificationReportResponse {
    private String id;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    @JsonProperty("pId")
    private String pId;
    @JsonProperty("pName")
    private String pName;
    private String secId;
    private String secName;
    private Date sprJudgeDate;
    private String sprStyle;
    private String sprRemark;

    @JsonProperty("pCategory")
    private String pCategory;
    @JsonProperty("pBuildModel")
    private String pBuildModel;
    @JsonProperty("pBusinessUnit")
    private String pBusinessUnit;
    private String swsId;
    @JsonProperty("pCompetentDepartmentName")
    private String pCompetentDepartmentName;
}
