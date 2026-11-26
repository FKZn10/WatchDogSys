package com.jpmsoft.common.model.requestAndResponse.JpmOa.hr;

import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OaDepartmentCostChangeModel extends BaseFileDto {

    private static final long serialVersionUID = -4501658721974704414L;
    /**
     *
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 变更前的部门id
     */
    private String oldDepartmentId;
    private String oldDepartmentName;

    /**
     * 变更后的部门id
     */
    private String newDepartmentId;
    private String newDepartmentName;
    /**
     * 变更比例
     */
    private BigDecimal changeRateSalaryGrantInfo;

    /**
     * 变更金额
     */
    private BigDecimal changeAmountSalaryGrantInfo;
    /**
     * 变更比例
     */
    private BigDecimal changeRateReimburseApproval;

    /**
     * 变更金额
     */
    private BigDecimal changeAmountReimburseApproval;
    /**
     * 备注
     */
    private String remark;
    private List<OaDepartmentCostChangeDetailModel> salaryGrantInfoDetails;
    private List<OaDepartmentCostChangeDetailModel> reimburseApprovalDetails;
}
