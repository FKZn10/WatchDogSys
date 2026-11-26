package com.jpmsoft.projectProducer.controller.v1.fundPlan;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.fundPlan.ProgressDeclarationDTO;
import com.jpmsoft.projectProducer.pojo.dto.fundPlan.ProgressDeclarationMatchDTO;
import com.jpmsoft.projectProducer.service.fundPlan.IProgressDeclarationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/9/24 20:20
 */
@Slf4j
@RestController
@RequestMapping("/v1/progressDeclaration")
public class ProgressDeclarationController {


    @Resource
    private IProgressDeclarationService progressDeclarationService;


    @PostMapping("/createOrUpdateProgressDeclaration")
    public BaseResponse createOrUpdateProgressDeclaration(@RequestBody ProgressDeclarationDTO request) {
        log.info("createOrUpdateProgressDeclaration");
        return progressDeclarationService.createOrUpdateProgressDeclaration(request);
    }

    @GetMapping("/getProgressDeclarationById")
    public  BaseResponse getProgressDeclarationById(@RequestParam String id) {
        log.info("getProgressDeclarationById");
        return progressDeclarationService.getProgressDeclarationById(id);
    }


    @PostMapping("/getProgressDeclarationList")
    public BaseResponse getProgressDeclarationList(@RequestBody ProgressDeclarationDTO request) {
        log.info("getProgressDeclarationList");
        return progressDeclarationService.getProgressDeclarationList(request);
    }



    @GetMapping("/getPlanNodes")
    public BaseResponse getPlanNodes(@RequestParam String conId){
        return progressDeclarationService.getPlanNodes(conId);
    }


    @PostMapping("/progressDeclarationMatch")
    public BaseResponse  progressDeclarationMatch(@RequestBody ProgressDeclarationMatchDTO dto){
        return progressDeclarationService.progressDeclarationMatch(dto);
    }

    @PostMapping("/getContractDeclarationList")
    public BaseResponse  getContractDeclarationList(@RequestBody ProgressDeclarationMatchDTO dto){
        return progressDeclarationService.getContractDeclarationList(dto);
    }

    @PostMapping("/progerGeneratePayment")
    public BaseResponse  progerGeneratePayment(@RequestBody ProgressDeclarationMatchDTO dto){
        log.info("progerGeneratePayment");
        return progressDeclarationService.progerGeneratePayment(dto);
    }


    @PostMapping("/updateContrCompletionData")
    public BaseResponse  updateContrCompletionData(@RequestBody ProgressDeclarationMatchDTO dto){
        log.info("updateContrCompletionData");
        return progressDeclarationService.updateContrCompletionData(dto);
    }

    //删除尾款申请
    @GetMapping("/delBalancePaymentById")
    public BaseResponse delBalancePaymentById(@RequestParam String id,@RequestParam String conId){
        return progressDeclarationService.delBalancePaymentById(id,conId);
    }

    //删除竣工决算
    @GetMapping("/delInvFinalAccountsApplyById")
    public BaseResponse delInvFinalAccountsApplyById(@RequestParam String id,@RequestParam String conId){
        return progressDeclarationService.delInvFinalAccountsApplyById(id,conId);
    }

    //删除初步审计
    @GetMapping("/delPreliminaryAuditById")
    public BaseResponse delPreliminaryAuditById(@RequestParam String id,@RequestParam String conId){
        return progressDeclarationService.delPreliminaryAuditById(id,conId);
    }

    //删除结算审计
    @GetMapping("/delAuditSettlementById")
    public BaseResponse delAuditSettlementById(@RequestParam String id,@RequestParam String conId){
        return progressDeclarationService.delAuditSettlementById(id,conId);
    }

    @PostMapping("/getAccordingThePlan")
    public BaseResponse getAccordingThePlan(@RequestBody ProgressDeclarationMatchDTO dto){
        return progressDeclarationService.getAccordingThePlan(dto);
    }





}
