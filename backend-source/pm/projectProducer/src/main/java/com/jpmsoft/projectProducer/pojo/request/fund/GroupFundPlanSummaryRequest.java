package com.jpmsoft.projectProducer.pojo.request.fund;

import com.jpmsoft.common.model.BasePageEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class GroupFundPlanSummaryRequest extends BasePageEntity {
    private String id;
    //return_Type 类型 0、需要审批 1、无需审批
    private Integer returnType;
    private Integer gfpsType;
    private Integer gfpsMonth;
    private Integer gfpsYear;
    private String gfpsProjectCompetentDepartmentId;
    private List<String> gfpDetailIds;
    private List<String> departmentIds;
    private List<GroupFundPlanSummaryDetailRequest> gfpDetailDtos;

    private String groupType;

    /**
     * 本期计划向集团申请资金
     */
    private BigDecimal totalFundApplyAmount;

    /**
     * 本期计划向下游支付资金
     */
    private BigDecimal totalNextMonthFunds;

    private String groupId;
    private String groupMainType;
    private Integer delFlag;

    private String gfpsTitle;
}
