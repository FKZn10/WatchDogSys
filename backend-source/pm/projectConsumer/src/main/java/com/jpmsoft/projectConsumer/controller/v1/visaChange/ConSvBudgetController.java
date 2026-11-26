package com.jpmsoft.projectConsumer.controller.v1.visaChange;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.visaChange.ConSvBudgetDTO;
import com.jpmsoft.projectConsumer.feign.VisaFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Slf4j
@RestController
@Api(tags = "变更签证-现场签证内部核准单", value = "变更签证-现场签证内部核准单")
@RequestMapping("/v1/conSvBudget")
public class ConSvBudgetController {
    @Resource
    private VisaFeign visaFeign;

    @ApiOperation(value = "新增或修改现场签证内部核准单")
    @PostMapping("/createOrUpdateConSvBudget")
    public BaseResponse createOrUpdateConSvBudget(@RequestBody ConSvBudgetDTO request) {
        log.info("createOrUpdateConSvBudget");
        return visaFeign.createOrUpdateConSvBudget(request);
    }

    @ApiOperation(value = "分页查现场签证内部核准单")
    @PostMapping("/getConSvBudgetList")
    public BaseResponse getConSvBudgetList(@RequestBody ConSvBudgetDTO request, @RequestHeader String token) {
        log.info("getConSvBudgetList");
        return visaFeign.getConSvBudgetList(request, token);
    }

    @ApiOperation(value = "ID查现场签证内部核准单")
    @GetMapping("/getConSvBudgetById")
    public BaseResponse getConSvBudgetById(@RequestParam String id) {
        log.info("getConSvBudgetById");
        return visaFeign.getConSvBudgetById(id);
    }

    @ApiOperation(value = "查询合同累计变更金额（不包含本次）")
    @GetMapping("/getCsbNotContainAmtByConId")
    public BaseResponse getCsbNotContainAmtByConId(@RequestParam String conId) {
        log.info("getCsbNotContainAmtByConId");
        return visaFeign.getCsbNotContainAmtByConId(conId);
    }

}

