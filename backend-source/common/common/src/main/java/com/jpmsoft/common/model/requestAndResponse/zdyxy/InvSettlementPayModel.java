package com.jpmsoft.common.model.requestAndResponse.zdyxy;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class InvSettlementPayModel extends BaseFileDto {

    private static final long serialVersionUID = 718126319744709837L;
    /**
     * 结算申请ID
     */
    private String invSettlementApplyId;
    private String invSettlementSerialNumber;

    /**
     * 确认完成ID
     */
    private String confirmCompleteId;
    private String confirmSerialNumber;

    /**
     * 维修ID
     */
    private String mrId;
    private String mrSerialNumber;
    private BigDecimal mrReportAmountPre;
    /**
     * 支付金额
     */
    private BigDecimal payAmount;

    /**
     * 质保金支付比例
     */
    private BigDecimal zbjPayRate;

    /**
     * 质保金支付金额
     */
    private BigDecimal zbjPayAmount;

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
    /**
     *
     * 是否选择 0：否 1：是
     */
    private Integer isSelect;
    private List<Date> createTimeRange;

    /**
     * 发票附件
     */
    private String invoiceAttachmentFile;

    private String invoiceAttachmentFileIds;

    /**
     * 请款单附件
     */
    private String payAttachmentFile;

    private String payAttachmentFileIds;

    /**
     * 其他附件
     */
    private String otherAttachmentFile;

    private String otherAttachmentFileIds;


    private String remark;

    @JsonProperty("pName")
    private String pName;

    private String saTitle;
}
