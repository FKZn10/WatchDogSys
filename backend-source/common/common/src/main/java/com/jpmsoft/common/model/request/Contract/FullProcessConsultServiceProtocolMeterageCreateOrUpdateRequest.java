package com.jpmsoft.common.model.request.Contract;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class FullProcessConsultServiceProtocolMeterageCreateOrUpdateRequest extends BaseFileRequest {

    private String id;

    // 项目id
    private String projectId;

    // 合同id
    private String contractId;

    // 合同金额
    private BigDecimal contractAmount;

    // 本期计划付款
    private BigDecimal thisStagePlanPayAmount;

    // 本期应付款
    private BigDecimal shouldPayAmountTotal;

    // 累计合同支付比例
    private BigDecimal totalPayRate;

    // 流程状态
    private Integer state;

    // 审批时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date stateTime;

    // 创建人id
    private String createUid;

    // 创建人名称
    private String createUname;

    // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    // 填报日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date fillDate;

    // 明细列表
    private List<FullProcessConsultServiceProtocolMeterageApplyItemParam> itemList;

}
