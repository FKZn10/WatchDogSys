package com.jpmsoft.common.model.requestAndResponse.zdyxy;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class MaintenanceRecordModel extends BaseFileDto {
    private static final long serialVersionUID = -6642936133850760871L;
    private String mrId;
    @JsonProperty(value = "pId")
    private String pId;
    private String secId;
    private String conId;
    private String mrCId;
    private String mrCName;
    private String mrType;
    private String mrPosition;
    private String mrPicBefore;
    private String mrPicAfter;
    private BigDecimal mrCost;
    private String mrCreateUid;
    private String mrCreateUname;
    private Date mrCreateTime;
    private Integer mrState;
    private Date mrStateTime;
    private String mrReportUid;
    private String mrReportUname;
    private String mrReportTel;
    private String mrAreaNumber;
    private String mrAddress;
    private Date mrReportTime;
    private String mrProblem;
    private String mrDispatchUid;
    private String mrDispatchUname;
    private Date mrDispatchTime;
    private String mrRepairUid;
    private String mrRepairUname;
    private String mrRepairFeedback;
    /**
     * 修缮项目申报审批 01、
     * 修缮项目申报完成 02、
     * 施工完成确认审批 03、
     * 施工完成确认完成 04、
     * 结算送审审批 05、
     * 结算送审完成 06、
     * 款项支付审批 07、
     * 款项支付完成 08。
     */
    private String mrRepairState;
    private String metId;
    private String teId;

    @JsonProperty(value = "pName")
    private String pName;
    private String mrSerialNumber;
    private String mrCampus;
    private String mrContent;
    private String mrUnit;
    private String mrIssuingUnit;
    private String mrRepairUnit;
    private String mrAttach;

    private String mrConstructionName;//施工单位名称
    private String mrConstructionId;//施工单位id

    /**
     * 报修部门Id
     */
    private String mrReportDepartmentId;
    private String mrReportDepartmentName;

    /**
     * 报修金额（预）
     */
    private BigDecimal mrReportAmountPre;

    /**
     * 报修是否包含施工图 0-否 1-是
     */
    private Integer mrReportIsContainsConstructionDrawing;

    /**
     * 报修施工图Id
     */
    private String mrReportConstructionDrawingId;
    private String mrReportConstructionDrawingCode;
    /**
     * 报修内容描述
     */
    private String mrReportContext;

    /**
     * 报修附件上传
     */
    private String mrReportFile;
    private String mrReportFileIds;

    /**
     * 预算金额
     */
    private BigDecimal mrBudgetAmount;
    /**
     * 施工图编号
     */
    private String mrConstructionDrawingCode;

    private String mrConstructionDrawingName;

    /**
     *
     * 是否选择 0：否 1：是
     */
    private Integer isSelect;
    private List<Date> createTimeRange;

    private Integer awState;

    /**
     * 是否排除已经选过的设计变更
     * 0：否 1：是
     */
    private Integer excludeSelectedRenovatioChanges;

    /**
     * 是否排除已经选过签证变更
     * 0：否 1：是
     */
    private Integer excludeSelectedRenovatioVisa;

    private String mrCategory;
}