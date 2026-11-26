package com.jpmsoft.common.model.requestAndResponse.zdyxy;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class InvSettlementApplyModel extends BaseFileDto {
    private static final long serialVersionUID = -6104187620459652043L;
    private String saId;
    private String saSerialNumber;
    private String mrId;
    private BigDecimal mrReportAmountPre;
    /**
     * 施工完成确认单
     */
    private String completionConfirmId;
    private String confirmSerialNumber;
    /**
     * 施工单位
     */
    private String constructionContractor;
    private String constructionContractorName;
    /**
     * 审计单位
     */
    private String auditUnit;
    private String auditUnitName;
    /**
     * 工程形象照片
     */
    private String constructionImage;
    /**
     * 竣工图纸
     */
    private String constructionDrawing;
    /**
     * 结算总价
     */
    private BigDecimal saAmountTotal;
    /**
     * 结算报价附件
     */
    private String saReportFile;
    private String saReportFileIds;
    /**
     * 审定金额
     */
    private BigDecimal saApprovedAmt;
    /**
     * 核增核减金额
     */
    private BigDecimal saIncreaseDecrease;
    /**
     * 审定报告
     */
    private String saAuditReport;
    private String saAuditReportIds;
    /**
     * 创建人
     */
    private String saCreateUid;
    private String saCreateUname;
    private Date saCreateTime;
    private Integer saState;
    private Date saStateTime;
    private String saRemark;
    private List<Date> createTimeRange;

    /**
     * 审计单位备注
     */
    private String saAuditRemark;

    @JsonProperty("pName")
    private String pName;

    private Integer isSelect;

    private String saTitle;
}