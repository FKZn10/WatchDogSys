package com.jpmsoft.projectProducer.pojo.request.investment;

import com.jpmsoft.common.annotations.ClassInfo;
import com.jpmsoft.common.model.BasePageEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SettlementFirstAuditRequest extends BasePageEntity {
    @ClassInfo(title = "项目名称",type="project",value = "projectId",dbValue = "project_id")
    private List<String> projectId;
    @ClassInfo(title = "合同名称",type="contract",value = "contractId",dbValue = "contract_id")
    private List<String> contractId;
    @ClassInfo(title = "承包单位报送价",type="number",value = "sfaContractPrice",dbValue = "sfa_contract_price")
    private List<BigDecimal> sfaContractPrice;
    @ClassInfo(title = "监理单位",type="company",value = "sfaSupervisionUnit",dbValue = "sfa_supervision_unit")
    private List<String> sfaSupervisionUnit;
    @ClassInfo(title = "监理单位审核价",type="number",value = "sfaSupervisionUnitPrice",dbValue = "sfa_supervision_unit_price")
    private List<BigDecimal> sfaSupervisionUnitPrice;
    @ClassInfo(title = "造价咨询单位",type="company",value = "sfaCostConsultantUnit",dbValue = "sfa_cost_consultant_unit")
    private List<String> sfaCostConsultantUnit;
    @ClassInfo(title = "造价咨询单位报价",type="number",value = "sfaCostConsultantUnitPrice",dbValue = "sfa_cost_consultant_unit_price")
    private List<BigDecimal> sfaCostConsultantUnitPrice;
    @ClassInfo(title = "结算初审金额",type="number",value = "sfaSettlementFirstPrice",dbValue = "sfa_settlement_first_price")
    private List<BigDecimal> sfaSettlementFirstPrice;
    @ClassInfo(title = "核减金额",type="number",value = "sfaCorePrice",dbValue = "sfa_core_price")
    private List<BigDecimal> sfaCorePrice;
}
