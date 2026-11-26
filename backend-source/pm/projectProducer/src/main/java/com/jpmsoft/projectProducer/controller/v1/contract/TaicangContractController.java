package com.jpmsoft.projectProducer.controller.v1.contract;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.vo.contract.*;
import com.jpmsoft.projectProducer.service.contract.ContractPaymentPlanTemplateDlService;
import com.jpmsoft.projectProducer.service.contract.ContractPaymentPlanTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/taicang/plan")
public class TaicangContractController {

    @Autowired
    public ContractPaymentPlanTemplateService contractPaymentPlanTemplateService;

    @Autowired
    public ContractPaymentPlanTemplateDlService contractPaymentPlanTemplateDlService;

    //查询合同计划(含节点)
    @PostMapping("/getContractTemplate")
    public BaseResponse getContractTemplate(@RequestBody ContractResultVo contractResultVo) {
        return contractPaymentPlanTemplateService.getContractTemplate(contractResultVo);
    }

    //添加合同计划
    @PostMapping("/saveContractPaymentPlan")
    public BaseResponse saveContractPaymentPlan(@RequestBody ContractResultVo contractResultVo) {
        return contractPaymentPlanTemplateService.saveContractPaymentPlan(contractResultVo);
    }

    //删除合同计划
    @GetMapping("/delContractTemplate")
    public BaseResponse delContractTemplate(@RequestParam String id) {
        return contractPaymentPlanTemplateService.delContractTemplate(id);
    }

    //修改合同计划
    @PostMapping("/updateContractTemplate")
    public BaseResponse updateContractTemplate(@RequestBody ContractResultVo contractResultVo) {
        return contractPaymentPlanTemplateService.updateContractTemplate(contractResultVo);
    }


    //修改合同节点
    @PostMapping("/updateContractTemplateDl")
    public BaseResponse updateContractTemplateDl(@RequestBody ContractResultVo contractResultVo) {
        return contractPaymentPlanTemplateDlService.updateContractTemplateDl(contractResultVo);
    }

    //删除合同节点
    @GetMapping("/delContractTemplateDl")
    public BaseResponse delContractTemplateDl(@RequestParam String id) {
        return contractPaymentPlanTemplateDlService.delContractTemplateDlId(id);
    }

    //添加合同节点
    @PostMapping("/saveContractPaymentPlanDlNode")
    public BaseResponse saveContractPaymentPlanDlNode(@RequestBody ContractResultVo contractResultVo) {
        return contractPaymentPlanTemplateDlService.saveContractPaymentPlanDlNode(contractResultVo);

    }

    //查询合同类型
    @GetMapping("/getContrcatDataList")
    public BaseResponse getContrcatDataList() {
        return contractPaymentPlanTemplateService.getContrcatDataList();

    }

    //支付条件类型
    @GetMapping("/getPaymentConditionType")
    public BaseResponse getPaymentConditionType() {
        return contractPaymentPlanTemplateService.getPaymentConditionType();
    }

    //支付方式
    @GetMapping("/getPaymentMethod")
    public BaseResponse getPaymentMethod() {
        return contractPaymentPlanTemplateService.getPaymentMethod();
    }

    //支付基础
    @GetMapping("/getPaymentBasis")
    public BaseResponse getPaymentBasis() {
        return contractPaymentPlanTemplateService.getPaymentBasis();
    }

    //根据查询  合同类型 金额上下限都查
    //查询合同类型金额上下限
    @PostMapping("/getContrActDataList")
    public BaseResponse getContrActDataList(@RequestBody ContractResultVo contractResultVo) {
        return contractPaymentPlanTemplateService.getContrActDataList(contractResultVo);
    }

    //批量新建合同
    @PostMapping("/saveContractPaymentPlanBatch")
    public BaseResponse saveContractPaymentPlanBatch(@RequestBody List<ContractResultVo> list) {
        return contractPaymentPlanTemplateService.saveContractPaymentPlanBatch(list);
    }

    //合同待支付管理
    @PostMapping("/getPayableConDataList")
    public BaseResponse getPayableConDataList(@RequestBody ContractManageVo contractManageVo) {
        return contractPaymentPlanTemplateService.getPayableConDataList(contractManageVo);
    }

    //合同支付状态管理修改
    @PostMapping("/updatePaymentState")
    public BaseResponse updatePaymentState(@RequestBody ContractManageVo contractManageVo) {
        return contractPaymentPlanTemplateService.updatePaymentState(contractManageVo);

    }

    @GetMapping("/getPaymentStateContr")
    public BaseResponse getPaymentState(@RequestParam("cId") String cId) {
        return contractPaymentPlanTemplateService.getPaymentStateContr(cId);
    }

    //查询甲方单位支付金额汇总
    @PostMapping("/getCompanyPaySum")
    public BaseResponse getCompanyPaySum(@RequestBody ContractManageVo contractManageVo) {
        return contractPaymentPlanTemplateService.getCompanyPaySum(contractManageVo);
    }

    //资金年度情况统计
    @PostMapping("/getCapitalSituation")
    public BaseResponse getCapitalSituation(@RequestBody ContractManageVo contractManageVo) {
        return contractPaymentPlanTemplateService.getCapitalSituation(contractManageVo);
    }

    //进度计划台账
    @PostMapping("/getProgressPlan")
    public BaseResponse getProgressPlan(@RequestBody ProgressDeclarationVo progressDeclarationVo) {
        return contractPaymentPlanTemplateService.getProgressPlan(progressDeclarationVo);
    }

    //合同浏览
    @PostMapping("/contrBrowse")
    public BaseResponse contrBrowse(@RequestBody ContractBrowseVo contractBrowseVo) {
        return contractPaymentPlanTemplateService.contrBrowse(contractBrowseVo);
    }

    //资金拨付(分配)
    @PostMapping("/moneyDisbursement")
    public BaseResponse moneyDisbursement(@RequestBody ConPayDisbursementVo vo) {
        return contractPaymentPlanTemplateService.moneyDisbursement(vo);
    }


    //资金拨付(保存)
    @PostMapping("/saveMoneyDisbursement")
    public BaseResponse saveMoneyDisbursement(@RequestBody ConPayDisbursementVo vo) {
        return contractPaymentPlanTemplateService.saveMoneyDisbursement(vo);
    }

}
