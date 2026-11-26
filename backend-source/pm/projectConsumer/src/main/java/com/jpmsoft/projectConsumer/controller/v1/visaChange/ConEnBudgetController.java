package com.jpmsoft.projectConsumer.controller.v1.visaChange;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.DesignChange.ConEnBudgetRequest;
import com.jpmsoft.projectConsumer.feign.VisaFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@Api(tags = "变更签证-工程洽商施工方案及预算", value = "变更签证-工程洽商施工方案及预算")
@RequestMapping("/v1/conEnBudget")
public class ConEnBudgetController {
    @Autowired
    private VisaFeign visaFeign;


    @ApiOperation(value = "新增或修改工程洽商施工方案及预算")
    @PostMapping("/createOrUpdateConEnBudget")
    public BaseResponse createOrUpdateConEnBudget(@RequestBody ConEnBudgetRequest request) {
        log.info("createOrUpdateConEnBudget");
        return visaFeign.createOrUpdateConEnBudget(request);
    }

    @ApiOperation(value = "分页查工程洽商施工方案及预算")
    @PostMapping("/getConEnBudgetList")
    public BaseResponse getConEnBudgetList(@RequestBody ConEnBudgetRequest request, @RequestHeader String token) {
        log.info("getConEnBudgetList");
        return visaFeign.getConEnBudgetList(request, token);
    }

    @ApiOperation(value = "ID查工程洽商施工方案及预算")
    @GetMapping("/getConEnBudgetById")
    public BaseResponse getConEnBudgetById(@RequestParam String enbId) {
        log.info("getConEnBudgetById");
        return visaFeign.getConEnBudgetById(enbId);
    }

    @ApiOperation(value = "查询合同累计变更金额（不包含本次）")
    @GetMapping("/getEnbNotContainAmtByConId")
    public BaseResponse getEnbNotContainAmtByConId(@RequestParam String conId) {
        log.info("getEnbNotContainAmtByConId");
        return visaFeign.getEnbNotContainAmtByConId(conId);
    }
}
