package com.jpmsoft.projectProducer.pojo.entity.contract;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 全过程资讯服务协议计量报审
 */
@Data
@TableName("full_process_consult_service_protocol_meterage_apply")
public class FullProcessConsultServiceProtocolMeterageApply {

    @TableId
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
    private Date stateTime;

    // 创建人id
    private String createUid;

    // 创建人名称
    private String createUname;

    // 创建时间
    private Date createTime;

    // 填报日期
    private Date fillDate;

    //是否含有合同金额明细；没金额明细说明没走通用合同；
    private boolean conMoneyDtlFlag;

}
