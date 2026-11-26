package com.jpmsoft.common.model.requestAndResponse.JpmOa.fund;


import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author yb.Sun
 * @date 2025/1/17 14:18
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ReimburseApprovalDto extends BaseFileDto {
    private static final long serialVersionUID = -3532570170518550664L;
    private String reimburseCode;
    private String reimburseUserId;
    private String reimburseUserName;
    private String reimburseDeptId;
    private String reimburseDeptName;
    private String reimburseType;
    private String reimburseProId;
    private String reimburseConId;
    private String reimburseProName;
    private String reimburseConName;
    private String reimburseConOutsourcingId;
    private String reimburseIsPurchaseContract;
    private String reimbursePurchaseContractId;
    private String purchaseContractName;
    private BigDecimal reimbursePurchaseContractAmountApply;
    private String reimbursePurchaseContractAmount;
    private String reimburseCompanyId;
    private String reimburseIsPublicTransfer;
    private String reimbursePaymentMethod;
    private String reimbursePaymentObject;
    private String reimburseBank;
    private String reimburseBankAccount;
    private String reimburseFinancialSubject;
    private String conInvalidId;
    private String conInvalidChargesName;
    private BigDecimal reimburseAmount;
    private Date reimburseTime;
    private String reimburseDescribe;
    private String reimburseFile;
    private String reimburseFileIds;
    private String reimburseTravelApplyId;
    private String isTravelApply;
    private String travelApplyNumber;
    private String reimbursePrintStatus;
    //报销事由
    private String reimburseReason;
    private String isRepaymentApply;
    private String borrowId;
    private String borrowApplyNumber;
    private List<InvoiceInfoDto> invoiceInfoDtos;
    private List<ReimburseApprovalInvoiceDetailDto> invoiceDetailDtos;

}
