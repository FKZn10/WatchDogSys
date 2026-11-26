package com.jpmsoft.common.model.requestAndResponse.JpmOa.hr;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
@Data
public class OaDepartmentContractChangeDetailModel implements Serializable {
    private static final long serialVersionUID = 3292242484902314989L;
    /**
     *
     */
    private String id;

    /**
     * 部门合同任务变更id
     */
    private String oaDepartmentContractChangeId;

    /**
     * 变更类型
     */
    private String changeType;

    /**
     * 合同任务id
     */
    private String contractTaskId;

    private String contractTaskName;

    private String contractName;
    private BigDecimal contractAmountTotal;
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
