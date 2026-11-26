package com.jpmsoft.projectConsumer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.contract.ConInvoiceDTO;
import com.jpmsoft.projectConsumer.feign.ConFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/11/14 10:43
 */
@Slf4j
@RestController
@Api(tags = "合同收款发票", value = "合同收款发票")
@RequestMapping("/v1/conInvoice")
public class ConInvoiceController {

    @Resource
    private ConFeign conFeign;

    @ApiOperation(value = "新增或修改合同收款发票")
    @PostMapping("/createOrUpdateInvoice")
    public BaseResponse createOrUpdateInvoice(@RequestBody ConInvoiceDTO request) {
        log.info("createOrUpdateInvoice");
        return conFeign.createOrUpdateInvoice(request);
    }

    @ApiOperation(value = "分页查询合同收款发票")
    @PostMapping("/getInvoiceList")
    public BaseResponse getInvoiceList(@RequestBody ConInvoiceDTO request, @RequestHeader String token) {
        log.info("getInvoiceList");
        return conFeign.getInvoiceList(request, token);
    }

    @ApiOperation(value = "id查询合同收款发票")
    @GetMapping("/getInvoiceById")
    public BaseResponse getInvoiceById(@RequestParam String id) {
        log.info("getInvoiceById");
        return conFeign.getInvoiceById(id);
    }
}
