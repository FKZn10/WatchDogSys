package com.jpmsoft.projectConsumer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.contract.ConContractPayPlanDto;
import com.jpmsoft.projectConsumer.pojo.dto.fundPlan.ProgressDeclarationDtlDTO;
import com.jpmsoft.projectConsumer.feign.ConFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: 李文涛 aaron
 * @date: 2022/9/8 10:27
 */
@Slf4j
@RestController
@Api(tags = "合同支付条款", value = "合同支付条款")
@RequestMapping("/v1/conPayPlan")
public class ConContractPayPlanController {

    @Autowired
    private ConFeign conFeign;

    @ApiOperation(value = "合同ID查询合同支付条款")
    @GetMapping("/getConContractPayPlanByConId")
    public BaseResponse getConContractPayPlanByConId(@RequestParam String conId) {
        return conFeign.getConContractPayPlanByConId(conId);
    }

    @ApiOperation(value = "进度申报 计算本次申报金额 金额为0 则表示不满足支付计划条件")
    @PostMapping("/ProgressDeclarationCompute")
    public BaseResponse ProgressDeclarationCompute(@RequestBody ProgressDeclarationDtlDTO request) {
        return conFeign.ProgressDeclarationCompute(request);
    }


    // 更新合同支付条款（新增3字段）
    @ApiOperation(value = "更新合同支付条款")
    @PostMapping("/updateConPayPlan")
    public BaseResponse updateConPayPlan(@RequestBody ConContractPayPlanDto request){
        return conFeign.updateConPayPlan(request);
    }


}
