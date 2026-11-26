package com.jpmsoft.common.model.requestAndResponse.JpmOa.fund;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yb.Sun
 * &#064;date  2025/1/17 14:27
 */
@Data
public class ReimburseApprovalInvoiceDetailDto {
    private String id;
    private String reimburseApprovalId;
    private String invoiceInfoId;
    private String reimburseInvoiceNumber;
    private String reimburseInvoiceCode;
    private Date reimburseInvoiceTime;
    private BigDecimal reimburseInvoiceAmount;
    private BigDecimal reimburseInvoiceNoTaxAmount;
    private String reimburseInvoiceCheckCode;
    private String reimburseInvoiceType;
    private String invoiceFile;
    private String invoiceFileIds;
    /**
     * 证明文件（批次id）
     */
    private String supportingDocument;
    //证明文件ids
    private String supportingDocumentFileIds;
    //校验类型
    private String invoiceCheckType;
    //校验状态
    private String invoiceCheckResult;
    private String invoiceApprovalResult;
    //校验说明
    private String invoiceCheckRemark;
    private String reimburseDetailType;
    private BigDecimal reimburseDetailAmount;
    private Date reimburseDetailTime;
    private String reimburseStartPlace;
    private String reimburseEndPlace;
    private String reimburseVehicle;
    private String reimburseDescribe;
    private Integer delFlag;
    private Integer status;
    private String remark;
}
