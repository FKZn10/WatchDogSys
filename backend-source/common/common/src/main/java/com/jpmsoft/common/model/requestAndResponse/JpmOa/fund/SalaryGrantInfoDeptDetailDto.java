package com.jpmsoft.common.model.requestAndResponse.JpmOa.fund;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SalaryGrantInfoDeptDetailDto {
    private String id;
    private String salaryGrantInfoId;
    private String deptId;
    private String deptName;
    private String deptParentId;
    //员工实发工资总数
    private BigDecimal salaryGrantInfoDeptActualSalary;
    //员工个税总数
    private BigDecimal salaryGrantInfoDeptIncomeTax;
    //员工社保总数-个人
    private BigDecimal salaryGrantInfoDeptSocialInsurancePersonalPayment;
    //员工社保总数-公司
    private BigDecimal salaryGrantInfoDeptSocialInsuranceCompanyPayment;
    //员工公积金总数-个人
    private BigDecimal salaryGrantInfoDeptAccumulationFundPersonalPayment;
    //员工公积金总数-公司
    private BigDecimal salaryGrantInfoDeptAccumulationFundCompanyPayment;
    //员工总工资总数
    private BigDecimal salaryGrantInfoDeptTotalSalary;
    private Integer delFlag;
    private Integer status;
    private List<SalaryGrantInfoDeptDetailDto> salaryGrantInfoDeptDetailVos;
}
