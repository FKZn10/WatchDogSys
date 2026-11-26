package com.jpmsoft.projectProducer.pojo.entity.maintenance;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 维保记录
 */
@Data
@TableName("Maintenance_Record")
public class MaintenanceRecord implements Serializable {
    private static final long serialVersionUID = 738840249346545602L;
    @TableId(type = IdType.INPUT)
    @ProcessFields(type = ProcessFieldEnum.id)
    private String mrId;
    @JsonProperty(value = "pId")
    private String pId;
    private String secId;
    private String conId;
    private String mrCId;
    private String mrType;
    private String mrPosition;
    private String mrPicBefore;
    private String mrPicAfter;
    private BigDecimal mrCost;
    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String mrCreateUid;
    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String mrCreateUname;
    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date mrCreateTime;
    @ProcessFields(type = ProcessFieldEnum.state)
    private Integer mrState;
    @ProcessFields(type = ProcessFieldEnum.stateTime)
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

    /**
     * 报修内容描述
     */
    private String mrReportContext;

    /**
     * 报修附件上传
     */
    private String mrReportFile;

    /**
     * 预算金额
     */
    private BigDecimal mrBudgetAmount;

    private String mrCategory;
}
