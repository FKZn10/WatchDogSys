package com.jpmsoft.projectConsumer.controller.v1.funPlan;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.FundPlanFeign;
import com.jpmsoft.projectConsumer.pojo.dto.fundPlan.ProgressDeclarationDTO;
import com.jpmsoft.projectConsumer.pojo.dto.fundPlan.ProgressDeclarationMatchDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/9/24 20:20
 */
@Slf4j
@RestController
@Api(tags = "太仓进度申报", value = "太仓进度申报")
@RequestMapping("/v1/progressDeclaration")
public class ProgressDeclarationController {


    @Resource
    private FundPlanFeign fundPlanFeign;

    @ApiOperation(value = "新增修改进度申报")
    @PostMapping("/createOrUpdateProgressDeclaration")
    public BaseResponse createOrUpdateProgressDeclaration(@RequestBody ProgressDeclarationDTO request) {
        log.info("createOrUpdateProgressDeclaration");
        return fundPlanFeign.createOrUpdateProgressDeclaration(request);
    }

    @ApiOperation(value = "ID查询进度申报")
    @GetMapping("/getProgressDeclarationById")
    public BaseResponse getProgressDeclarationById(@RequestParam String id) {
        log.info("getProgressDeclarationById");
        return fundPlanFeign.getProgressDeclarationById(id);
    }

    @ApiOperation(value = "分页查询进度申报")
    @PostMapping("/getProgressDeclarationList")
    public BaseResponse getProgressDeclarationList(@RequestBody ProgressDeclarationDTO request) {
        log.info("getProgressDeclarationList");
        return fundPlanFeign.getProgressDeclarationList(request);
    }

    @ApiOperation(value = "根据合同id查询计划节点")
    @GetMapping("/getPlanNodes")
    public BaseResponse getPlanNodes(@RequestParam String conId){
        log.info("getPlanNodes");
        return fundPlanFeign.getPlanNodes(conId);
    }

    @ApiOperation(value = "进度申报自动计算资金")
    @PostMapping("/progressDeclarationMatch")
    public BaseResponse  progressDeclarationMatch(@RequestBody ProgressDeclarationMatchDTO dto){
        log.info("progressDeclarationMatch");
        return fundPlanFeign.progressDeclarationMatch(dto);
    }

    @ApiOperation(value = "待申报进度合同列表查询")
    @PostMapping("/getContractDeclarationList")
    public BaseResponse  getContractDeclarationList(@RequestBody ProgressDeclarationMatchDTO dto){
        log.info("getContractDeclarationList");
        return fundPlanFeign.getContractDeclarationList(dto);
    }

    @ApiOperation(value = "竣工决算-初步审计-结算审计-尾款结束后自动生成支付")
    @PostMapping("/progerGeneratePayment")
    public BaseResponse  progerGeneratePayment(@RequestBody ProgressDeclarationMatchDTO dto){
        log.info("progerGeneratePayment");
        return fundPlanFeign.progerGeneratePayment(dto);
    }

    @ApiOperation(value = "进度申报填写完竣工日期回写到合同")
    @PostMapping("/updateContrCompletionData")
    public BaseResponse  updateContrCompletionData(@RequestBody ProgressDeclarationMatchDTO dto){
        log.info("updateContrCompletionData");
        return fundPlanFeign.updateContrCompletionData(dto);
    }

    @ApiOperation(value = "删除尾款申请")
    @GetMapping("/delBalancePaymentById")
    public BaseResponse delBalancePaymentById(@RequestParam String id,@RequestParam String conId){
        return fundPlanFeign.delBalancePaymentById(id,conId);
    }

    //删除竣工决算
    @ApiOperation(value = "删除竣工决算")
    @GetMapping("/delInvFinalAccountsApplyById")
    public BaseResponse delInvFinalAccountsApplyById(@RequestParam String id,@RequestParam String conId){
        return fundPlanFeign.delInvFinalAccountsApplyById(id,conId);
    }

    //删除初步审计
    @ApiOperation(value = "删除初步审计")
    @GetMapping("/delPreliminaryAuditById")
    public BaseResponse delPreliminaryAuditById(@RequestParam String id,@RequestParam String conId){
        return fundPlanFeign.delPreliminaryAuditById(id,conId);
    }

    //删除结算审计
    @ApiOperation(value = "删除结算审计")
    @GetMapping("/delAuditSettlementById")
    public BaseResponse delAuditSettlementById(@RequestParam String id,@RequestParam String conId){
        return fundPlanFeign.delAuditSettlementById(id,conId);
    }

    @ApiOperation(value = "计划申报节点分配资金")
    @PostMapping("/getAccordingThePlan")
    public BaseResponse getAccordingThePlan(@RequestBody ProgressDeclarationMatchDTO dto){
        return fundPlanFeign.getAccordingThePlan(dto);
    }



}
