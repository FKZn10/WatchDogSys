package com.jpmsoft.projectConsumer.controller.v1.contract;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.ConFeign;
import com.jpmsoft.projectConsumer.pojo.vo.contract.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "太仓", value = "太仓")
@RestController
@RequestMapping("/taicang/plan")
public class TaicangContractController {

    @Resource
    private ConFeign conFeign;


    //查询合同计划(含节点)
    @ApiOperation(value = "查询合同计划(含节点)")
    @PostMapping("/getContractTemplate")
    public BaseResponse getContractTemplate(@RequestBody ContractResultVo contractResultVo) {
        return conFeign.getContractTemplate(contractResultVo);
    }

    //添加合同计划
    @ApiOperation(value = "添加合同计划")
    @PostMapping("/saveContractPaymentPlan")
    public BaseResponse saveContractPaymentPlan(@RequestBody ContractResultVo contractResultVo) {
        return conFeign.saveContractPaymentPlan(contractResultVo);
    }

    //删除合同计划
    @ApiOperation(value = "删除合同计划")
    @GetMapping("/delContractTemplate")
    public BaseResponse delContractTemplate(@RequestParam String id) {
        return conFeign.delContractTemplate(id);
    }

    //修改合同计划
    @ApiOperation(value = "修改合同计划")
    @PostMapping("/updateContractTemplate")
    public BaseResponse updateContractTemplate(@RequestBody ContractResultVo contractResultVo) {
        return conFeign.updateContractTemplate(contractResultVo);
    }


    //修改合同节点
    @ApiOperation(value = "修改合同节点")
    @PostMapping("/updateContractTemplateDl")
    public BaseResponse updateContractTemplateDl(@RequestBody ContractResultVo contractResultVo) {
        return conFeign.updateContractTemplateDl(contractResultVo);
    }

    //删除合同节点
    @ApiOperation(value = "删除合同节点")
    @GetMapping("/delContractTemplateDl")
    public BaseResponse delContractTemplateDl(@RequestParam String id) {
        return conFeign.delContractTemplateDl(id);
    }

    //添加合同节点
    @ApiOperation(value = "添加合同节点")
    @PostMapping("/saveContractPaymentPlanDlNode")
    public BaseResponse saveContractPaymentPlanDlNode(@RequestBody ContractResultVo contractResultVo) {
        return conFeign.saveContractPaymentPlanDlNode(contractResultVo);

    }

    //查询合同类型
    @ApiOperation(value = "查询合同类型")
    @GetMapping("/getContrcatDataList")
    public BaseResponse getContrcatDataList() {
        return conFeign.getContrcatDataList();

    }

    //支付条件类型
    @ApiOperation(value = "支付条件类型")
    @GetMapping("/getPaymentConditionType")
    public BaseResponse getPaymentConditionType() {
        return conFeign.getPaymentConditionType();
    }

    //支付方式
    @ApiOperation(value = "支付方式")
    @GetMapping("/getPaymentMethod")
    public BaseResponse getPaymentMethod() {
        return conFeign.getPaymentMethod();
    }

    //支付基础
    @ApiOperation(value = "支付基础")
    @GetMapping("/getPaymentBasis")
    public BaseResponse getPaymentBasis() {
        return conFeign.getPaymentBasis();
    }

    //根据查询  合同类型 金额上下限都查
    @ApiOperation(value = "查询合同类型金额上下限")
    @PostMapping("/getContrActDataList")
    public BaseResponse getContrActDataList(@RequestBody ContractResultVo contractResultVo) {
        return conFeign.getContrActDataList(contractResultVo);
    }

    //批量新建合同
    @ApiOperation(value = "批量新建合同")
    @PostMapping("/saveContractPaymentPlanBatch")
    public BaseResponse saveContractPaymentPlanBatch(@RequestBody List<ContractResultVo> list) {
        return conFeign.saveContractPaymentPlanBatch(list);
    }

    //合同待支付管理

    @ApiOperation(value = "合同待支付管理")
    @PostMapping("/getPayableConDataList")
    public BaseResponse getPayableConDataList(@RequestBody ContractManageVo contractManageVo) {

        return conFeign.getPayableConDataList(contractManageVo);
    }

    @ApiOperation(value = "合同支付状态管理修改")
    @PostMapping("/updatePaymentState")
    public BaseResponse updatePaymentState(@RequestBody ContractManageVo contractManageVo) {
        return conFeign.updatePaymentState(contractManageVo);

    }

    @GetMapping("/getPaymentStateContr")
    public BaseResponse getPaymentStateContr(@RequestParam("cId") String cId) {
        return conFeign.getPaymentStateContr(cId);
    }

    @ApiOperation(value = "查询甲方单位支付金额汇总")
    @PostMapping("/getCompanyPaySum")
    public BaseResponse getCompanyPaySum(@RequestBody ContractManageVo contractManageVo) {
        return conFeign.getCompanyPaySum(contractManageVo);
    }

    @ApiOperation(value = "资金年度情况统计")
    @PostMapping("/getCapitalSituation")
    public BaseResponse getCapitalSituation(@RequestBody ContractManageVo contractManageVo) {
        return conFeign.getCapitalSituation(contractManageVo);
    }

    @ApiOperation(value = "进度计划台账")
    @PostMapping("/getProgressPlan")
    public BaseResponse getProgressPlan(@RequestBody ProgressDeclarationVo progressDeclarationVo) {
        return conFeign.getProgressPlan(progressDeclarationVo);
    }

    @ApiOperation(value = "合同浏览")
    @PostMapping("/contrBrowse")
    public BaseResponse contrBrowse(@RequestBody ContractBrowseVo contractBrowseVo) {
        return conFeign.contrBrowse(contractBrowseVo);
    }

    @ApiOperation(value = "资金拨付(分配)")
    @PostMapping("/moneyDisbursement")
    public BaseResponse moneyDisbursement(@RequestBody ConPayDisbursementVo vo) {
        return conFeign.moneyDisbursement(vo);
    }


    @ApiOperation(value = "资金拨付(保存)")
    @PostMapping("/saveMoneyDisbursement")
    public BaseResponse saveMoneyDisbursement(@RequestBody ConPayDisbursementVo vo) {
        return conFeign.saveMoneyDisbursement(vo);
    }

}
