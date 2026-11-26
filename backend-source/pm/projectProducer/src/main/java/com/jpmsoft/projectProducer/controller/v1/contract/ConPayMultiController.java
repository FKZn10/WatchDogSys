package com.jpmsoft.projectProducer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.ConPayMultiRequest;
import com.jpmsoft.projectProducer.service.contract.IConPayMultiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 多项目合同支付
 *
 * @author tianhelin
 * @date 2025/9/10
 */
@Slf4j
@RestController
@RequestMapping("/v1/conPayMulti")
public class ConPayMultiController {
    @Resource
    private IConPayMultiService conPayMultiService;

    /**
     * 同步支付信息到合同支付表
     *
     * @param request
     * @return
     */
    @PostMapping("/syncToConPay")
    public BaseResponse syncToConPay(@RequestBody ConPayMultiRequest request) {
        log.info("syncToConPay");
        return conPayMultiService.syncToConPay(request);
    }

    /**
     * 根据ID获取多项目合同支付信息
     *
     * @param id
     * @return
     */
    @GetMapping("/getConPayMultiById")
    public BaseResponse getConPayMultiById(@RequestParam("id") String id) {
        log.info("getConPayMultiById");
        return conPayMultiService.getConPayMultiById(id);
    }

    /**
     * 多项目合同支付分页查询
     *
     * @param request
     * @param token
     * @return
     */
    @PostMapping("/getConPayMultiPage")
    public BaseResponse getConPayMultiPage(@RequestBody ConPayMultiRequest request, @RequestHeader String token) {
        log.info("getConPayMultiPage");
        return conPayMultiService.getConPayMultiPage(request, token);
    }
}
