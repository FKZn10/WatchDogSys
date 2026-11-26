package com.jpmsoft.common.model.requestAndResponse.InvestmentControl;

import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class SettlementFirstAuditChangeLogModel  extends BaseFileDto {
    private String id;
    private String sfaId;
    private Date changeTime;
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
    private BigDecimal sfaContractPriceOld;
    private String sfaSupervisionUnitOld;
    private String sfaSupervisionUnitNameOld;
    private BigDecimal sfaSupervisionUnitPriceOld;
    private String sfaCostConsultantUnitOld;
    private String sfaCostConsultantUnitNameOld;
    private BigDecimal sfaCostConsultantUnitPriceOld;
    private BigDecimal sfaSettlementFirstPriceOld;
    private BigDecimal sfaCorePriceOld;
    private String sfaSettlementFirstDecisionDoc;
    private String sfaSettlementDocUrl;
    private String sfaSettlementFirstDecisionDocs;
    private String sfaSettlementDocUrls;
}
