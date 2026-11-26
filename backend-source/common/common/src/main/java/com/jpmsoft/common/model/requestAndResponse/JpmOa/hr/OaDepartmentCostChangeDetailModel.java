package com.jpmsoft.common.model.requestAndResponse.JpmOa.hr;

import com.jpmsoft.common.model.requestAndResponse.JpmOa.fund.ReimburseApprovalDto;
import com.jpmsoft.common.model.requestAndResponse.JpmOa.fund.SalaryGrantInfoDeptDetailDto;
import com.jpmsoft.common.model.requestAndResponse.JpmOa.fund.SalaryGrantInfoDetailDto;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
@Data
public class OaDepartmentCostChangeDetailModel implements Serializable {
    private static final long serialVersionUID = 8013338338567449927L;
    /**
     *
     */
    private String id;
    private String type;

    /**
     * 部门费用变更id
     */
    private String oaDepartmentCostChangeId;

    /**
     * 变更类型
     */
    private String changeType;

    /**
     * 报销id
     */
    private String reimburseApprovalId;

    /**
     * 报销
     */
    private ReimburseApprovalDto reimburseApproval;
    private String reimburseCode;
    private BigDecimal reimburseAmount;
    /**
     * 工资明细id
     */
    private String salaryDetailId;


    private Integer year;
    private Integer month;
    private BigDecimal salaryGrantInfoActualSalary;
    /**
     * 工资
     */
    private SalaryGrantInfoDetailDto salaryGrantInfoDetailDto;

    private BigDecimal salaryGrantInfoDeptActualSalary;
    private SalaryGrantInfoDeptDetailDto salaryGrantInfoDeptDetail;
    /**
     * 变更比例
     */
    private BigDecimal changeRate;

    /**
     * 变更金额
     */
    private BigDecimal changeAmount;

    /**
     * 变更原因
     */
    private String changeRemark;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 删除标志
     */
    private Integer delFlag;
}
