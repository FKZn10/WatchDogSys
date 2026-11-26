package com.jpmsoft.fileUpload.pojo.dto;

import lombok.Data;

import java.util.List;

/**
 * 三联单详情 打印用
 */
@Data
public class IntegratedWorkOrderDtlDTO {
    private String state;
    private String id;
    private String iwoId;
    private String engineeringLocation;
    private String constructionContent;
    private String zlcontrolNodes;
    private String aqcontrolNodes;
    private List<IntegratedWorkOrderDtlUser> threeSgdwConstruction;
    private List<IntegratedWorkOrderDtlUser> threeSgdwQuality;
    private List<IntegratedWorkOrderDtlUser> threeSgdwSafety;
    private List<IntegratedWorkOrderDtlUser> threeJl;
    private List<IntegratedWorkOrderDtlUser> threeLegalperson;
    private String threeMajorContent;
    private String threeResponsibleId;
    private String threeResponsibleName;
    private String threeResponsibleTel;
    private String iwodType;
    private String createTime;
    private String threeDate;
    private String majorNodeSupervision;
    private String nightConstruction;
    private String inspectionPlan;
    private String resourceAllocation;
}