package com.jpmsoft.projectConsumer.controller.v1.funPlan;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.fundPlan.FundPlanDtlCheckDto;
import com.jpmsoft.projectConsumer.feign.FundPlanFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 资金计划表审核记录
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/16 15:51
 */
@Slf4j
@RestController
@Api(tags = "资金计划表审核记录", value = "资金计划表审核记录")
@RequestMapping("/v1/fundPlanDtlCheck")
public class FundPlanDtlCheckController {

    @Autowired
    private FundPlanFeign fundPlanFeign;

    @ApiOperation(value = "新增或修改资金计划表审核记录")
    @PostMapping("/createOrUpdateFundPlanDtlCheck")
    public BaseResponse createOrUpdateFundPlanDtlCheck(@RequestBody FundPlanDtlCheckDto request) {
        log.info("createOrUpdateFundPlanDtlCheck");
        return fundPlanFeign.createOrUpdateFundPlanDtlCheck(request);
    }

    @ApiOperation(value = "分页查询资金计划表审核记录")
    @PostMapping("/getFundPlanDtlCheckList")
    public BaseResponse getFundPlanDtlCheckList(@RequestBody FundPlanDtlCheckDto request) {
        log.info("getFundPlanDtlCheckList");
        return fundPlanFeign.getFundPlanDtlCheckList(request);
    }

    @ApiOperation(value = "ID查询资金计划表审核记录")
    @GetMapping("/getFundPlanDtlCheckById")
    public BaseResponse getFundPlanDtlCheckById(@RequestParam String id) {
        log.info("getFundPlanDtlCheckById");
        return fundPlanFeign.getFundPlanDtlCheckById(id);
    }
}
