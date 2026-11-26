package com.jpmsoft.projectProducer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InvestmentControl.InvAdjustmentTemplateRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.investment.IInvAdjustmentTemplateService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/invAdjustmentTemplate")
public class InvAdjustmentTemplateController {

    @Resource
    private IInvAdjustmentTemplateService invAdjustmentTemplateService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateInvAdjustmentTemplate")
    public BaseResponse createOrUpdateInvAdjustmentTemplate(@RequestBody InvAdjustmentTemplateRequest request) {
        log.info("createOrUpdateInvAdjustmentTemplate");
        return invAdjustmentTemplateService.createOrUpdateInvAdjustmentTemplate(request);
    }

    @TenantId
    @PostMapping("/getInvAdjustmentTemplateList")
    public BaseResponse getInvAdjustmentTemplateList(@RequestBody InvAdjustmentTemplateRequest request, @RequestHeader String token) {
        log.info("getInvAdjustmentTemplateList");
        return invAdjustmentTemplateService.getInvAdjustmentTemplateList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getInvAdjustmentTemplateByAtId")
    public BaseResponse getInvAdjustmentTemplateByAtId(@RequestParam String atId) {
        log.info("getInvAdjustmentTemplateByAtId");
        return invAdjustmentTemplateService.getInvAdjustmentTemplateByAtId(atId);
    }

    @GetMapping("/deleteInvAdjustmentTemplateByAtId")
    public BaseResponse deleteInvAdjustmentTemplateByAtId(@RequestParam String atId) {
        log.info("deleteInvAdjustmentTemplateByAtId");
        return invAdjustmentTemplateService.deleteInvAdjustmentTemplateByAtId(atId);
    }
    
}
