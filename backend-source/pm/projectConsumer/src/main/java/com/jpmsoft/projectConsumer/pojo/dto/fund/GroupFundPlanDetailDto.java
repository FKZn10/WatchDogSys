package com.jpmsoft.projectConsumer.pojo.dto.fund;

import com.jpmsoft.common.model.requestAndResponse.fund.GroupFundPlanDetailSubjectDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class GroupFundPlanDetailDto {
    private String id;
    //Gfp_Id
    private String gfpId;
    //Gfpd_Type
    private String gfpdType;
    //Gfpd_Contract_Id 合同ID
    private String gfpdContractId;
    private String gfpdContractName;
    //Gfpd_Pay_Amount 已支付金额
    private Double gfpdPayAmount;
    //Gfpd_Pay_Rate 已支付比例
    private Double gfpdPayRate;
    //Gfpd_Apply_Amount 本次申请金额
    private Double gfpdApplyAmount;
    //Gfpd_Total_Pay_Amount 本次累计支付金额
    private Double gfpdTotalPayAmount;
    //Gfpd_Total_Pay_Rate 本次累计支付比例
    private Double gfpdTotalPayRate;
    //Gfpd_Cost_Name 费用名称
    private String gfpdCostName;
    //Gfpd_Cost_Code 费用编码
    private String gfpdCostCode;
    //Gfpd_Remark 备注
    private String gfpdRemark;
    //Gfpd_Attachment 附件
    private String gfpdAttachment;
    //Gfpd_Attachment 附件FileId
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

    /**
     * 概算科目
     */
    private List<GroupFundPlanDetailSubjectDTO> budgetSubjects;

    /**
     * 概算金额
     */
    private BigDecimal budgetAmount;

    /**
     * 其他支付类型
     */
    private String otherPayType;

    /**
     * 资金用途
     */
    private String fundPurpose;

    /**
     * 合同计量计价id
     */
    private String cmpId;
}
