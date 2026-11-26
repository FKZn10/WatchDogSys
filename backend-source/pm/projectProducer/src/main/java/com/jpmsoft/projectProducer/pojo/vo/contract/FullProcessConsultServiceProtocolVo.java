package com.jpmsoft.projectProducer.pojo.vo.contract;

import lombok.Data;

import java.util.List;

@Data
public class FullProcessConsultServiceProtocolVo {

    // 合同id
    private String contractId;

    // 合同名称
    private String contractName;

    //是否含有合同金额明细；没金额明细说明没走通用合同；
    private boolean conMoneyDtlFlag;

    // 计量支付信息
    private List<MeteragePayVo> meteragePayVoList;

    // 是否有投资概算分解
    private Boolean hasInvestBudgetBreakDown;


}
