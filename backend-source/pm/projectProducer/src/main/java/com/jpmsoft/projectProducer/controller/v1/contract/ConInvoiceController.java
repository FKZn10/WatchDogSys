package com.jpmsoft.projectProducer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConInvoiceDTO;
import com.jpmsoft.projectProducer.service.contract.IConInvoiceService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/11/14 10:43
 */
@Slf4j
@RestController
@RequestMapping("/v1/conInvoice")
public class ConInvoiceController {

    @Resource
    private IConInvoiceService conInvoiceService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateInvoice")
    public BaseResponse createOrUpdateInvoice(@RequestBody ConInvoiceDTO request) {
        log.info("createOrUpdateInvoice");
        return conInvoiceService.createOrUpdateInvoice(request);
    }

    @TenantId
    @PostMapping("/getInvoiceList")
    public BaseResponse getInvoiceList(@RequestBody ConInvoiceDTO request, @RequestHeader String token) {
        log.info("getInvoiceList");
        return conInvoiceService.getInvoiceList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getInvoiceById")
    public BaseResponse getInvoiceById(@RequestParam String id) {
        log.info("getInvoiceById");
        return conInvoiceService.getInvoiceById(id);
    }
}
