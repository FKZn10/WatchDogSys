package com.jpmsoft.common.model.requestAndResponse.JpmOa.hr;

import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OaDepartmentContractChangeModel extends BaseFileDto {
    private static final long serialVersionUID = 4784963002763778342L;
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
     * 合同id
     */
    private String contractId;
    private String contractName;
    private BigDecimal contractAmountTotal;
    private BigDecimal changeAmount;
    private BigDecimal changeRate;

    /**
     * 备注
     */
    private String remark;
    private List<OaDepartmentContractChangeDetailModel> details;

}
