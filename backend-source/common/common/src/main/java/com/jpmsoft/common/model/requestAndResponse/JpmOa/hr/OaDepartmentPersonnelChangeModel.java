package com.jpmsoft.common.model.requestAndResponse.JpmOa.hr;

import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;

import java.util.List;

@Data
public class OaDepartmentPersonnelChangeModel extends BaseFileDto {
    private static final long serialVersionUID = 6546156499505902305L;
    /**
     *
     */
    private String id;

    private String userId;
    private String userName;

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
     * 是否包含合同费用变更
     */
    private Integer isContractChange;

    /**
     * 合同费用变更id
     */
    private String contractChangeId;
    private OaDepartmentContractChangeModel contractChange;
    private List<OaDepartmentContractChangeDetailModel> details;
    /**
     * 是否包含部门费用变更
     */
    private Integer isCostChange;

    /**
     * 部门费用变更id
     */
    private String costChangeId;
    private OaDepartmentCostChangeModel costChange;
    private List<OaDepartmentCostChangeDetailModel> salaryGrantInfoDetails;
    private List<OaDepartmentCostChangeDetailModel> reimburseApprovalDetails;

    /**
     * 备注
     */
    private String remark;
}
