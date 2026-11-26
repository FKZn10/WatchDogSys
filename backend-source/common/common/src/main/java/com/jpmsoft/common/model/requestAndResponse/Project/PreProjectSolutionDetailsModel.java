package com.jpmsoft.common.model.requestAndResponse.Project;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class PreProjectSolutionDetailsModel {
    private String id;
    private String projectId;
    private String solutionId;
    private String infoType;
    private String categoryFirst;
    private String categorySecond;
    private String name;
    private String description;
    private PreProjectSolutionDetailApplicationModel applicationObject;
    private String applications;
    private Date startDate;
    private Date endDate;
    private BigDecimal areaUsed;
    private BigDecimal areaAvailable;
    private BigDecimal score;
    private BigDecimal selfScore;
    private BigDecimal scoreReal;
    private String appStage;
    private String responsibleParty;
    private Integer delFlag;
    private Integer status;
}
