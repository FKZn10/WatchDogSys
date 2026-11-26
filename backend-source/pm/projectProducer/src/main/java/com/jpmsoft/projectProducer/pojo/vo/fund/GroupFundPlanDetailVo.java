package com.jpmsoft.projectProducer.pojo.vo.fund;

import com.jpmsoft.common.model.requestAndResponse.fund.GroupFundPlanDetailSubjectDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class GroupFundPlanDetailVo {
    private String id;
    // Gfp_Id
    private String gfpId;
    // Gfpd_Type
    private String gfpdType;
    // Gfpd_Contract_Id 合同ID
    private String gfpdContractId;
    // Gfpd_Contract_Name 合同名称
    private String gfpdContractName;
    // 合同金额
    private BigDecimal gfpdContractAmount;
    // Gfpd_Contract_Company_Id 乙方单位ID
    private String gfpdContractCompanyId;
    // Gfpd_Contract_Company_Name 乙方单位名称
    private String gfpdContractCompanyName;
    // Gfpd_Pay_Amount 已支付金额
    private BigDecimal gfpdPayAmount;
    // Gfpd_Pay_Rate 已支付比例
    private BigDecimal gfpdPayRate;
    // Gfpd_Apply_Amount 本次申请金额
    private BigDecimal gfpdApplyAmount;
    // Gfpd_Total_Pay_Amount 本次累计支付金额
    private BigDecimal gfpdTotalPayAmount;
    // Gfpd_Total_Pay_Rate 本次累计支付比例
    private BigDecimal gfpdTotalPayRate;
    // Gfpd_Cost_Name 费用名称
    private String gfpdCostName;
    // Gfpd_Cost_Code 费用编码
    private String gfpdCostCode;
    // Gfpd_Remark 备注
    private String gfpdRemark;
    // Gfpd_Attachment 附件
    private String gfpdAttachment;
    // Gfpd_Attachment 附件FileId
    private String gfpdAttachmentFileIds;

    /**
     * 支付类型
     */
    private String payType;

    /**
     * 资金需求
     */
    private BigDecimal fundsDemand;

    /**
     * 需求描述
     */
    private String demandDesc;

    /**
     * 余额支付金额
     */
    private BigDecimal gfpdBalancePayAmount;

    /**
     * 余额支付比例
     */
    private BigDecimal gfpdBalancePayRate;

    /**
     * 预计来款支付金额
     */
    private BigDecimal gfpdExpectedPayAmount;

    /**
     * 预计来款支付比例
     */
    private BigDecimal gfpdExpectedPayRate;

    /**
     * 收款单位
     */
    private String payCompany;

    private String payCompanyName;

    /**
     * 概算科目
     */
    private List<GroupFundPlanDetailSubjectDTO> budgetSubjects;

    /**
     * 概算金额
     */
    private BigDecimal budgetAmount;

    private Date gfpDate;

    /**
     * 其他支付类型
     */
    private String otherPayType;

    private String conType;

    private BigDecimal conMoneyTotal;

    private String budgetSubjectsName;

    /**
     * 资金用途
     */
    private String fundPurpose;

    /**
     * 合同计量计价id
     */
    private String cmpId;

    /**
     * 估算概算的主表id
     */
    private String budgetSubjectsId;
}
