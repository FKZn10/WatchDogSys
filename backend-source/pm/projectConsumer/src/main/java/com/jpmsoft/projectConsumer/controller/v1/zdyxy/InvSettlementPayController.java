package com.jpmsoft.projectConsumer.controller.v1.zdyxy;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.zdyxy.InvSettlementPayModel;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectConsumer.feign.InvSettlementPayFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/invSettlementPay/")
@Slf4j
public class InvSettlementPayController {
    @Autowired
    private InvSettlementPayFeign invSettlementPayFeign;
    @PostMapping("/saveOrUpdateData")
    public BaseResponse saveOrUpdateData(@RequestBody InvSettlementPayModel model) {
        log.info("saveOrUpdateData:{}", JacksonUtils.toJSONString(model));
        return invSettlementPayFeign.saveOrUpdateData(model);
    }
    @PostMapping("/approvalData")
    public BaseResponse approvalData(@RequestBody InvSettlementPayModel request) {
        log.info("approvalData:{}", JacksonUtils.toJSONString(request));
        return invSettlementPayFeign.approvalData(request);
    }


    @PostMapping("/getList")
    public BaseResponse getList(@RequestBody InvSettlementPayModel request, @RequestHeader String token) {
        log.info("查询列表:{}", JacksonUtils.toJSONString(request));
        return invSettlementPayFeign.getList(request,token);
    }


    @GetMapping("/getDetail")
    public BaseResponse getDetail(@RequestParam("id")String id) {
        log.info("查询结果:{}", id);
        return invSettlementPayFeign.getDetail(id);
    }
}