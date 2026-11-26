package com.jpmsoft.projectProducer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConInvoiceSignDTO;
import com.jpmsoft.projectProducer.service.contract.IConInvoiceSignService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 发票签收
 *
 * @Author: 李文涛 aaron
 * @date: 2024/3/28 15:36
 */
@Slf4j
@RestController
@RequestMapping("/v1/conInvoiceSign")
public class ConInvoiceSignController {

    @Resource
    private IConInvoiceSignService conInvoiceSignService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateConInvoiceSign")
    public BaseResponse createOrUpdateConInvoiceSign(@RequestBody ConInvoiceSignDTO request) {
        log.info("createOrUpdateConInvoiceSign");
        return conInvoiceSignService.createOrUpdateConInvoiceSign(request);
    }

    @TenantId
    @PostMapping("/getConInvoiceSignList")
    public BaseResponse getConInvoiceSignList(@RequestBody ConInvoiceSignDTO request, @RequestHeader String token) {
        log.info("getConInvoiceSignList");
        return conInvoiceSignService.getConInvoiceSignList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getConInvoiceSignById")
    public BaseResponse getConInvoiceSignById(@RequestParam String id) {
        log.info("getConInvoiceSignById");
        return conInvoiceSignService.getConInvoiceSignById(id);
    }
}
