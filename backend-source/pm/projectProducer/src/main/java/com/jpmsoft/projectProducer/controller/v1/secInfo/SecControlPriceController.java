package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecControlPriceRequest;
import com.jpmsoft.common.model.response.SecInfo.SecControlPriceResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.secInfo.ISecControlPriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/controlPrice")
public class SecControlPriceController {

    @Resource
    private ISecControlPriceService secControlPriceService;

    @TenantId
    @PostMapping("/createOrUpdateControlPrice")
    public BaseResponse createOrUpdateControlPrice(@RequestBody SecControlPriceRequest request) {
        log.info("createOrUpdateControlPrice");
        return secControlPriceService.createOrUpdateControlPrice(request);
    }

    @GetMapping("/deleteControlPriceBycpId")
    public BaseResponse deleteControlPriceBycpId(@RequestParam String cpId) {
        log.info("deleteControlPriceBycpId");
        return secControlPriceService.deleteControlPriceBycpId(cpId);
    }

    @GetMapping("/getControlPriceBycpId")
    public BaseResponse getControlPriceBycpId(@RequestParam String cpId) {
        log.info("getControlPriceBycpId");
        return secControlPriceService.getControlPriceBycpId(cpId);
    }

    @TenantId
    @PostMapping("/getControlPriceList")
    public BaseResponse getControlPriceList(@RequestBody SecControlPriceRequest request) {
        log.info("getControlPriceList");
        return secControlPriceService.getControlPriceList(request);
    }

    /**
     * 根据标段ID获取最新的招标控制价
     */
    @GetMapping("/getControlPriceBysecId")
    public BaseResponse getControlPriceBysecId(@RequestParam String secId) {
        log.info("getControlPriceBysecId");
        return BaseResponse.ok(secControlPriceService.getControlPriceBysecId(secId));
    }

}
