package com.jpmsoft.common.model.requestAndResponse.integratedWork;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 三联单主表
 */
@Data
public class IntegratedWorkOrderDTO {
    private String id;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private String pId;
    private Date workStartDate;
    private Date workEndDate;
    private String workConstructionUnitId;
    private String workJldwId;
    private String workSgdwId;
    private String workKcsjdwId;
    private Date weeklyStartDate;
    private Date weeklyEndDate;
    private String weeklyCompletionStatus;
    private String weeklyPersonnelStatus;
    private String weeklyEquipmentStatus;
    private String weeklyHandlingStatus;
    private String weeklySgInspectionStatus;
    private String weeklyJlInspectionStatus;
    private String weeklyJlzlInspectionStatus;
    private String weeklyFrInspectionStatus;
    private Date threeStartDate;
    private Date threeEndDate;
    private String threeJsdwId;
    private String threeJldwId;
    private String threeSgdwId;
    private String conConstructionId;
    private String conConstructionName;
    private String conSupervisionId;
    private String conSupervisionName;
    private String workConstructionUnitName;
    private String workJldwName;
    private String workSgdwName;
    private String workKcsjdwName;
    private String majorNodeSupervision;
    private String nightConstruction;
    private String inspectionPlan;
    private String resourceAllocation;
    private String coordination;
    private String industry;
    private String formalities;
    private List<IntegratedWorkOrderDtlDTO> dataList;
}