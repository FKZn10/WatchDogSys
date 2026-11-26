package com.jpmsoft.projectConsumer.request.investment;

import com.jpmsoft.common.annotations.ClassInfo;
import com.jpmsoft.common.model.BasePageEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class SettlementFirstAuditRequest extends BasePageEntity {
    @ClassInfo(title = "项目名称",type="string",value = "projectName",dbValue = "P_Name")
    private List<String> projectName;
    @ClassInfo(title = "合同名称",type="string",value = "contractName",dbValue = "Con_Name")
    private List<String> contractName;
    @ClassInfo(title = "承包单位报送价",type="number",value = "sfaContractPrice",dbValue = "sfa_contract_price")
    private List<BigDecimal> sfaContractPrice;
    @ClassInfo(title = "监理单位",type="string",value = "sfaSupervisionUnitName",dbValue = "sfa_supervision_unit_name")
    private List<String> sfaSupervisionUnitName;
    @ClassInfo(title = "监理单位审核价",type="number",value = "sfaSupervisionUnitPrice",dbValue = "sfa_supervision_unit_price")
    private List<BigDecimal> sfaSupervisionUnitPrice;
    @ClassInfo(title = "造价咨询单位",type="string",value = "sfaCostConsultantUnitName",dbValue = "sfa_cost_consultant_unit_name")
    private List<String> sfaCostConsultantUnitName;
    @ClassInfo(title = "造价咨询单位报价",type="number",value = "sfaCostConsultantUnitPrice",dbValue = "sfa_cost_consultant_unit_price")
    private List<BigDecimal> sfaCostConsultantUnitPrice;
    @ClassInfo(title = "结算初审金额",type="number",value = "sfaSettlementFirstPrice",dbValue = "sfa_settlement_first_price")
    private List<BigDecimal> sfaSettlementFirstPrice;
    @ClassInfo(title = "核减金额",type="number",value = "sfaCorePrice",dbValue = "sfa_core_price")
    private List<BigDecimal> sfaCorePrice;
}
