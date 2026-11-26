package com.jpmsoft.common.model.requestAndResponse.integratedWork;

import lombok.Data;

import java.util.Date;

/**
 * 三联单明细表
 */
@Data
public class IntegratedWorkOrderDtlDTO {
    private String id;
    private String iwoId;
    private String engineeringLocation;
    private String constructionContent;
    private String zlcontrolNodes;
    private String aqcontrolNodes;
    private String threeSgdwConstructionId;
    private String threeSgdwConstructionName;
    private String threeSgdwConstructionTel;
    private String threeSgdwQualityId;
    private String threeSgdwQualityName;
    private String threeSgdwQualityTel;
    private String threeSgdwSafetyId;
    private String threeSgdwSafetyName;
    private String threeSgdwSafetyTel;
    private String threeJlId;
    private String threeJlName;
    private String threeJlTel;
    private String threeLegalpersonId;
    private String threeLegalpersonName;
    private String threeLegalpersonTel;
    private String threeMajorContent;
    private String iwodType;
    private Date createTime;
    private Date threeDate;

    private String threeResponsibleId;
    private String threeResponsibleName;
    private String threeResponsibleTel;
}