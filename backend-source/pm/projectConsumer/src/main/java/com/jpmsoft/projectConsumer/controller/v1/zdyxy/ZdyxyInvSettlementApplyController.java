package com.jpmsoft.projectConsumer.controller.v1.zdyxy;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.zdyxy.InvSettlementApplyModel;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectConsumer.feign.ZdyxyInvSettlementApplyFeign;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "结算申请", value = "结算申请")
@RequestMapping("/v1/zdyxy/invSettlementApply")
public class ZdyxyInvSettlementApplyController {
    @Autowired
    private ZdyxyInvSettlementApplyFeign zdyxyInvFeign;

    @PostMapping("/saveOrUpdateData")
    public BaseResponse saveOrUpdateData(@RequestBody InvSettlementApplyModel request) {
        log.info("saveOrUpdateData:{}", JacksonUtils.toJSONString(request));
        return zdyxyInvFeign.saveOrUpdateData(request);
    }
    @PostMapping("/approvalData")
    public BaseResponse approvalData(@RequestBody InvSettlementApplyModel request) {
        log.info("approvalData:{}", JacksonUtils.toJSONString(request));
        return zdyxyInvFeign.approvalData(request);
    }
    @PostMapping("/getList")
    public BaseResponse getList(@RequestBody InvSettlementApplyModel request, @RequestHeader String token) {
        log.info("getList:{}", JacksonUtils.toJSONString(request));
        return zdyxyInvFeign.getList(request, token);
    }

    @GetMapping("/getDetail")
    public BaseResponse getDetail(@RequestParam("saId") String saId) {
        log.info("getDetail");
        return zdyxyInvFeign.getDetail(saId);
    }
}