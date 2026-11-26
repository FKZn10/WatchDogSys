package com.jpmsoft.projectProducer.pojo.vo.contract;

import com.jpmsoft.projectProducer.pojo.entity.contract.FullProcessConsultServiceProtocolMeterageApplyItem;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class FullProcessConsultServiceProtocolMeterageDetailVo {

    private String id;

    // 项目id
    private String projectId;
    private String projectName;

    // 合同id
    private String contractId;
    private String contractName;

    // 合同金额
    private BigDecimal contractAmount;

    // 本期计划付款
    private BigDecimal thisStagePlanPayAmount;

    // 本期应付款
    private BigDecimal shouldPayAmountTotal;

    // 累计合同支付比例
    private BigDecimal totalPayRate;

    //是否含有合同金额明细；没金额明细说明没走通用合同；
    private boolean conMoneyDtlFlag;

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

    // 明细列表
    private List<FullProcessConsultServiceProtocolMeterageApplyItem> itemList;

    private String fileRelationId;

    private Boolean hasInvestBudgetBreakDown;

}
