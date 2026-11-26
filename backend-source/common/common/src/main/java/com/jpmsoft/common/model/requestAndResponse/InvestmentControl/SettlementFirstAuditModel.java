package com.jpmsoft.common.model.requestAndResponse.InvestmentControl;

import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class SettlementFirstAuditModel extends BaseFileDto {
    private String id;
    private String projectId;
    private String projectName;
    private String contractId ;
    private String contractName;
    private BigDecimal contractTotalMoney;
    //累计变更签证金额
    private BigDecimal contractTotalMoneyChange;
    //承包单位
    private String contractCompanyId;
    private String contractCompanyName;
    //招标控制价
    private BigDecimal contractControlPrice;
    private BigDecimal sfaContractPrice;
    private String sfaSupervisionUnit;
    private String sfaSupervisionUnitName;
    private BigDecimal sfaSupervisionUnitPrice;
    private String sfaCostConsultantUnit;
    private String sfaCostConsultantUnitName;
    private BigDecimal sfaCostConsultantUnitPrice;
    private BigDecimal sfaSettlementFirstPrice;
    private BigDecimal sfaCorePrice;
    private String sfaSettlementFirstDecisionDoc;
    private String sfaSettlementFirstDecisionDocs;
    private String sfaSettlementDocUrl;
    private String sfaSettlementDocUrls;
    private Integer delFlag;
    private Integer status;
    private List<SettlementFirstAuditChangeLogModel> changeLogModels;
}
