package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 全过程咨询服务协议计量报审申请明细
 */
@Data
@TableName("full_process_consult_service_protocol_meterage_apply_item")
public class FullProcessConsultServiceProtocolMeterageApplyItem {

    @TableId
    private String id;

    // 计量报审id
    private String fullProcessConsultServiceProtocolMeterageApplyId;

    // 合同价（元）
    private BigDecimal contractAmount;

    // 支付条款
    private String payTerms;

    // 批复概算金额（元）
    private BigDecimal budgetApproveAmount;

    // 协议内容
    private String content;

    // 截止上期已支付金额
    private BigDecimal lastTotalPayAmount;

    // 本期计划支付金额
    private BigDecimal thisStagePlanPayAmount;

    // 累计应支付金额
    private BigDecimal totalShouldPayAmount;

    // 累计支付比例
    private BigDecimal totalPayRate;

    // 本期完成工作量
    private String thisStageFinishWork;

    //支付说明
    private String paymentDescribe;

}
