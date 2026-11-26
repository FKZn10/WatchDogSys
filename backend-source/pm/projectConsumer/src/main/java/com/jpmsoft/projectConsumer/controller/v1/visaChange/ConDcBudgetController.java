package com.jpmsoft.projectConsumer.controller.v1.visaChange;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.DesignChange.ConDcBudgetRequest;
import com.jpmsoft.projectConsumer.feign.VisaFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "变更签证-施工变更预算", value = "变更签证-施工变更预算")
@RequestMapping("/v1/conDcBudget")
public class ConDcBudgetController {
    @Autowired
    private VisaFeign visaFeign;

    @ApiOperation(value = "新增或修改施工变更预算")
    @PostMapping("/createOrUpdateConDcBudget")
    public BaseResponse createOrUpdateConDcBudget(@RequestBody ConDcBudgetRequest request) {
        log.info("createOrUpdateConDcBudget");
        return visaFeign.createOrUpdateConDcBudget(request);
    }

    @ApiOperation(value = "分页查询施工变更预算")
    @PostMapping("/getConDcBudgetList")
    public BaseResponse getConDcBudgetList(@RequestBody ConDcBudgetRequest request, @RequestHeader String token) {
        log.info("getConDcBudgetList");
        return visaFeign.getConDcBudgetList(request, token);
    }

    @ApiOperation(value = "id查询施工变更预算")
    @GetMapping("/getConDcBudgetByDcbId")
    public BaseResponse getConDcBudgetByDcbId(@RequestParam String dcbId) {
        log.info("getConDcBudgetByDcbId");
        return visaFeign.getConDcBudgetByDcbId(dcbId);
    }

    @ApiOperation(value = "查询合同累计变更金额（不包含本次）")
    @GetMapping("/getDcbNotContainAmtByConId")
    public BaseResponse getDcbNotContainAmtByConId(@RequestParam String conId) {
        log.info("getDcbNotContainAmtByConId");
        return visaFeign.getDcbNotContainAmtByConId(conId);
    }
}
