package com.jpmsoft.projectProducer.controller.v1.zdyxy;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.zdyxy.InvSettlementApplyModel;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectProducer.service.zdyxy.ZdyxyInvSettlementApplyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/zdyxy/invSettlementApply")
public class ZdyxyInvSettlementApplyController {
    @Autowired
    private ZdyxyInvSettlementApplyService invSettlementApplyService;

    @PostMapping("/saveOrUpdateData")
    public BaseResponse saveOrUpdateData(@RequestBody InvSettlementApplyModel request) {
        log.info("saveOrUpdateData:{}", JacksonUtils.toJSONString(request));
        return invSettlementApplyService.saveOrUpdateData(request);
    }
    @PostMapping("/approvalData")
    public BaseResponse approvalData(@RequestBody InvSettlementApplyModel request) {
        log.info("approvalData:{}", JacksonUtils.toJSONString(request));
        return invSettlementApplyService.approvalData(request);
    }
    @PostMapping("/getList")
    public BaseResponse getList(@RequestBody InvSettlementApplyModel request, @RequestHeader String token) {
        log.info("getList:{}", JacksonUtils.toJSONString(request));
        return invSettlementApplyService.getList(request, token);
    }

    @GetMapping("/getDetail")
    public BaseResponse getDetail(@RequestParam("saId") String saId) {
        log.info("getDetail{}",saId);
        return invSettlementApplyService.getDetail(saId);
    }
}