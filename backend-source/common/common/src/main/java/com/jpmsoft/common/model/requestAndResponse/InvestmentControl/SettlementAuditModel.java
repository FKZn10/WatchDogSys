package com.jpmsoft.common.model.requestAndResponse.InvestmentControl;

import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SettlementAuditModel extends BaseFileDto {
    private String id;
    private String projectId;
    private String projectName;
    private Integer auditContractNum;
    private BigDecimal auditContractMoney;
    private Integer delFlag;
    private Integer status;
    private List<SettlementAuditDetailModel> detailModels;
    private List<SettlementAuditDetailChangeLogModel> detailChangeLogModels;
}
