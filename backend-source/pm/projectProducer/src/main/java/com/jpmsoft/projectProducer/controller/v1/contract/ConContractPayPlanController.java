package com.jpmsoft.projectProducer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConContractPayPlanDto;
import com.jpmsoft.projectProducer.pojo.dto.fundPlan.ProgressDeclarationDtlDTO;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContractPayPlan;
import com.jpmsoft.projectProducer.service.contract.IConContractPayPlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: 李文涛 aaron
 * @date: 2022/9/8 10:27
 */
@Slf4j
@RestController
@RequestMapping("/v1/conPayPlan")
public class ConContractPayPlanController {

    @Resource
    private IConContractPayPlanService conContractPayPlanService;


    // 查询合同支付计划信息
    @GetMapping("/getConContractPayPlanByConId")
    public BaseResponse getConContractPayPlanByConId(@RequestParam String conId) {
        return BaseResponse.ok(conContractPayPlanService.getConContractPayPlanByConId(conId));
    }



    @PostMapping("/ProgressDeclarationCompute")
    public BaseResponse ProgressDeclarationCompute(@RequestBody ProgressDeclarationDtlDTO request){
        return conContractPayPlanService.ProgressDeclarationCompute(request);
    }

    // ==================== 新增：保存合同支付计划 ====================
    @PostMapping("/updateConPayPlan")
    public BaseResponse updateConPayPlan(@RequestBody ConContractPayPlanDto request){

        return conContractPayPlanService.updateConPayPlan(request);
    }



}
