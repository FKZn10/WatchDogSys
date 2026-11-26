package com.jpmsoft.projectProducer.pojo.dto.contract;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class FullProcessConsultServiceProtocolMeterageApplyExportDTO {

    @Excel(name = "项目名称", width = 20)
    private String projectName;

    @Excel(name = "合同名称", width = 20)
    private String contractName;

    // 合同金额
    @Excel(name = "合同金额（元）", width = 20, type = 10)
    private BigDecimal contractAmount;

    // 本期计划付款
    @Excel(name = "本期计划付款（元）", width = 20, type = 10)
    private BigDecimal thisStagePlanPayAmount;

    // 本期应付款
    @Excel(name = "本期应付款（元）", width = 20, type = 10)
    private BigDecimal shouldPayAmountTotal;

    // 累计合同支付比例
    @Excel(name = "累计合同支付比例(%)", width = 25, type = 10)
    private BigDecimal totalPayRate;

    @Excel(name = "流程状态", replace = {"已保存_0", "审批中_1", "流程退回_2", "已作废_3", "审批中_4", "已结束_7"})
    private Integer state;

}
