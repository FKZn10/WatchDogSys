package com.jpmsoft.projectConsumer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InvestmentControl.InvAdjustmentTemplateRequest;
import com.jpmsoft.common.model.request.InvestmentControl.InvBudgetDeclareRequest;
import com.jpmsoft.projectConsumer.feign.InvFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "调整事项模板", value = "调整事项模板")
@RequestMapping("/inv/v1/invAdjustmentTemplate")
public class InvAdjustmentTemplateController {
    @Resource
    private InvFeign invFeign;

    @ApiOperation(value = "新增或修改调整事项模板")
    @PostMapping("/createOrUpdateInvAdjustmentTemplate")
    public BaseResponse createOrUpdateInvAdjustmentTemplate(@RequestBody InvAdjustmentTemplateRequest request) {
        log.info("createOrUpdateInvAdjustmentTemplate");
        return invFeign.createOrUpdateInvAdjustmentTemplate(request);
    }

    @ApiOperation(value = "分页查询调整事项模板")
    @PostMapping("/getInvAdjustmentTemplateList")
    public BaseResponse getInvAdjustmentTemplateList(@RequestBody InvAdjustmentTemplateRequest request, @RequestHeader String token) {
        log.info("getInvAdjustmentTemplateList");
        return invFeign.getInvAdjustmentTemplateList(request, token);
    }

    @ApiOperation(value = "ID查询调整事项模板")
    @GetMapping("/getInvAdjustmentTemplateByAtId")
    public BaseResponse getInvAdjustmentTemplateByAtId(@RequestParam String atId) {
        log.info("getInvAdjustmentTemplateByAtId");
        return invFeign.getInvAdjustmentTemplateByAtId(atId);
    }

    @ApiOperation(value = "ID删除调整事项模板")
    @GetMapping("/deleteInvAdjustmentTemplateByAtId")
    public BaseResponse deleteInvAdjustmentTemplateByAtId(@RequestParam String atId) {
        log.info("deleteInvAdjustmentTemplateByAtId");
        return invFeign.deleteInvAdjustmentTemplateByAtId(atId);
    }
}
