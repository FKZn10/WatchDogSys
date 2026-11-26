package com.jpmsoft.common.model.response.SecInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 定标情况报告
 */
@Data
public class SecCalibrationReportResponse {
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
    private Date scrDate;
    @JsonProperty("scRMethod")
    private String scRMethod;
    private Double bidPrice;
    private BigDecimal tenderPrice;
    @JsonProperty("bidWInner")
    private String bidWInner;
    private String bidWinnerName;
    private Integer totalVotes;
    private Integer ownVotes;
    private String scrRemarks;

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
