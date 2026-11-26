package com.jpmsoft.projectProducer.pojo.vo.fund;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class GroupFundPlanSummaryVo extends BaseFileDto {
    private String id;
    private Integer gfpsMonth;
    private Integer gfpsYear;
    private String gfpsTitle;
    // Gfps_Type 类型 1、成员 2、集团
    private Integer gfpsType;
    // return_Type 类型 0、需要审批 1、无需审批
    private Integer returnType;
    private String gfpsRemark;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date stateTime;
    private String gfpsProjectCompetentDepartmentId;
    private String gfpsProjectCompetentDepartmentName;
    private List<GroupFundPlanSummaryDetailVo> detailVos;
    private List<GroupFundPlanSummaryDetailTotalVo> detailTotalVos;

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

    /**
     * 是否可修改
     */
    private Boolean canChange;
}
