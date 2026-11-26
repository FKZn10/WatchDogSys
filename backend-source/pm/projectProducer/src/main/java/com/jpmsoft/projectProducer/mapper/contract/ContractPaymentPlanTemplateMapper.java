package com.jpmsoft.projectProducer.mapper.contract;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.projectProducer.pojo.entity.contract.ContractPaymentPlanTemplate;
import com.jpmsoft.projectProducer.pojo.vo.contract.ConPayDisbursementVo;
import com.jpmsoft.projectProducer.pojo.vo.contract.ContractBrowseVo;
import com.jpmsoft.projectProducer.pojo.vo.contract.ContractManageVo;
import com.jpmsoft.projectProducer.pojo.vo.contract.ProgressDeclarationVo;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface ContractPaymentPlanTemplateMapper extends BaseMapper<ContractPaymentPlanTemplate> {

    IPage<ContractManageVo> getPayableContrDataList(IPage<ContractManageVo> page, @Param("contractManageVo") ContractManageVo contractManageVo);

    int updatePaymentState(@Param("contractManageVo") ContractManageVo contractManageVo);

    Map<String, Object> getPaymentStateContr(@Param("cId") String cId);

    IPage<ContractManageVo> getCompanyPaySum(IPage<ContractManageVo> page, @Param("contractManageVo") ContractManageVo contractManageVo);

    IPage<ContractManageVo> getCapitalSituation(IPage<ContractManageVo> page, @Param("contractManageVo") ContractManageVo contractManageVo);

    List<ContractManageVo> getCapitalSituationTwo(@Param("contractManageVo") ContractManageVo contractManageVo);


    List<Map<String, Object>> getPaymentfundsAmt(@Param("conIds") List<String> conIds, @Param("year") String year);

    List<Map<String, Object>> getDisbursementAmt(@Param("conIds") List<String> conIds, @Param("year") String year);

    IPage<ProgressDeclarationVo> getProgressPlan(IPage<ProgressDeclarationVo> page, @Param("progressDeclarationVo") ProgressDeclarationVo progressDeclarationVo);

    IPage<ContractBrowseVo> contrBrowse(Page<ContractBrowseVo> page, @Param("vo") ContractBrowseVo vo);

    List<ConPayDisbursementVo> moneyDisbursement(@Param("vo") ConPayDisbursementVo vo);
}
