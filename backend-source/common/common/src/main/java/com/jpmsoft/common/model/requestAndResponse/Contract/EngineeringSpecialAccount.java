package com.jpmsoft.common.model.requestAndResponse.Contract;

import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 工程专户台账
 */
@Data
public class EngineeringSpecialAccount extends BaseFileDto implements Serializable {

    /**
     * 项目ID
     */
    private String projectId;
    /**
     * 项目名称
     */
    private String pName;
    /**
     * 项目管理单位
     */
    private String pCompetentDepartment;
    /**
     * 项目管理单位
     */
    private String pCompetentDepartmentName;
    /**
     * 建设模式
     */
    private String pBuildModel;
    /**
     * 集团账户-收入合计
     */
    private BigDecimal esaJtTotalIncome;
    /**
     * 集团账户-支出合计
     */
    private BigDecimal esaJtTotalExpenditure;
    /**
     * 集团账户-账户余额
     */
    private BigDecimal esaJtAccountBalance;
    /**
     * 集团账户-收入占比
     */
    private BigDecimal esaJtRevenueShare;
    /**
     * 集团账户-投资进度
     */
    private BigDecimal esaJtInvestmentProgress;
    /**
     * 企业账户-收入合计
     */
    private BigDecimal esaQyTotalIncome;
    /**
     * 企业账户-支出合计
     */
    private BigDecimal esaQyTotalExpenditure;
    /**
     * 企业账户-账户余额
     */
    private BigDecimal esaQyAccountBalance;
    /**
     * 企业账户-支付占比
     */
    private BigDecimal esaQyPaymentProportion;
    /**
     * 企业账户-支付比例
     */
    private BigDecimal esaQyPaymentRate;
    /**
     * 企业账户-投资进度
     */
    private BigDecimal esaQyInvestmentProgress;

    /**
     * 项目总投资额
     */
    private BigDecimal pCalculateRoughlyAmount;
    private BigDecimal payApproval;
    private BigDecimal payPayable;
    private String payCategory;
    private String payId;

    private String pProjectFormType;
}
