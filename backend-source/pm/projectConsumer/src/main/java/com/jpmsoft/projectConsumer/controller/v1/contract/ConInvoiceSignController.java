package com.jpmsoft.projectConsumer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.contract.ConInvoiceSignDTO;
import com.jpmsoft.projectConsumer.feign.ConFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "发票签收", value = "发票签收")
@RequestMapping("/v1/conInvoiceSign")
public class ConInvoiceSignController {

    @Resource
    private ConFeign conFeign;

    @ApiOperation(value = "新增或修改发票签收")
    @PostMapping("/createOrUpdateConInvoiceSign")
    public BaseResponse createOrUpdateConInvoiceSign(@RequestBody ConInvoiceSignDTO request) {
        log.info("createOrUpdateConInvoiceSign");
        return conFeign.createOrUpdateConInvoiceSign(request);
    }

    @ApiOperation(value = "分页查询发票签收")
    @PostMapping("/getConInvoiceSignList")
    public BaseResponse getConInvoiceSignList(@RequestBody ConInvoiceSignDTO request, @RequestHeader String token) {
        log.info("getConInvoiceSignList");
        return conFeign.getConInvoiceSignList(request, token);
    }

    @ApiOperation(value = "ID查询发票签收")
    @GetMapping("/getConInvoiceSignById")
    public BaseResponse getConInvoiceSignById(@RequestParam String id) {
        log.info("getConInvoiceSignById");
        return conFeign.getConInvoiceSignById(id);
    }
}
