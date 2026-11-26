package com.jpmsoft.common.model.requestAndResponse.JpmOa.fund;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class SalaryGrantInfoDetailDto {
    private String id;
    private String salaryGrantInfoId;
    private String staffId;
    private String staffUserId;
    private String staffUserName;
    private String staffDeptId;
    private String staffDeptName;
    //基本工资
    private BigDecimal salaryGrantInfoBasicSalary;
    //岗位工资
    private BigDecimal salaryGrantInfoPostSalary;
    //特岗工资
    private BigDecimal salaryGrantInfoSpecialWorkAmount;
    //绩效工资
    private BigDecimal salaryGrantInfoBonus;

    //绩效明细ID
    private String salaryGrantInfoBonusId;
    //员工保密补贴
    private BigDecimal salaryStaffSecrecySubsidy;
    //扣除工资
    private BigDecimal salaryGrantInfoDeductionSalary;
    //有效工资
    private BigDecimal salaryGrantInfoEffectiveSalary;
    //社保个人缴纳
    private BigDecimal salaryGrantInfoSocialInsurancePersonalPayment;
    //社保单位缴纳
    private BigDecimal salaryGrantInfoSocialInsuranceCompanyPayment;
    //公积金个人缴纳
    private BigDecimal salaryGrantInfoAccumulationFundPersonalPayment;
    //公积金单位缴纳
    private BigDecimal salaryGrantInfoAccumulationFundCompanyPayment;
    //公积金单位缴纳个人扣除部分
    private BigDecimal salaryGrantInfoAccumulationFundCompanyPaymentPersonal;
    //专项扣除费用
    private BigDecimal salaryGrantInfoSpecialDeductionExpenses;
    //个人所得税
    private BigDecimal salaryGrantInfoPersonalIncomeTax;
    //实发工资
    private BigDecimal salaryGrantInfoActualSalary;
    //发放日期
    private Date salaryGrantInfoGrantDate;
    //发放状态
    private String salaryGrantInfoDetailState;
    private Integer delFlag;
    private Integer status;
    private String  remark;
}
