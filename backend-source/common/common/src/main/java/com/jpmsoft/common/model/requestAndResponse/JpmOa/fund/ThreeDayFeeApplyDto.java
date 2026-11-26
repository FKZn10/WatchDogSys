package com.jpmsoft.common.model.requestAndResponse.JpmOa.fund;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class ThreeDayFeeApplyDto extends BaseFileDto {
    private static final long serialVersionUID = 4554378873781569370L;

    /**
     * 申请人id
     */
    private String applyUserId;

    /**
     * 申请人姓名
     */
    private String applyUserName;

    /**
     * 申请人部门id
     */
    private String applyDepartmentId;

    /**
     * 申请人部门名称
     */
    private String applyDepartmentName;

    /**
     * 申请时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date applyTime;
    private String applyName;
    /**
     * 申请理由
     */
    private String applyReason;

    /**
     * 申请金额
     */
    private BigDecimal applyAmount;
    private BigDecimal applyAmountUsed;
    /**
     * 差值
     */
    private BigDecimal applyAmountDifference;
}
