package com.jpmsoft.projectProducer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.ConMeasurePriceRequest;
import com.jpmsoft.projectProducer.service.contract.IConMeasurePriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 合同计量计价 控制器
 *
 * @author tianhelin
 * @date 2025/10/21
 */
@Slf4j
@RestController
@RequestMapping("/v1/conMeasurePrice")
public class ConMeasurePriceController {
    @Resource
    private IConMeasurePriceService conMeasurePriceService;

    /**
     * 创建或更新合同计量计价
     *
     * @param request
     * @return
     */
    @PostMapping("/createOrUpdateConMeasurePrice")
    public BaseResponse createOrUpdateConMeasurePrice(@RequestBody ConMeasurePriceRequest request) {
        log.info("createOrUpdateConMeasurePrice");
        return conMeasurePriceService.createOrUpdateConMeasurePrice(request);
    }

    /**
     * 获取合同计量计价列表
     *
     * @param request
     * @param token
     * @return
     */
    @PostMapping("/getConMeasurePriceList")
    public BaseResponse getConMeasurePriceList(@RequestBody ConMeasurePriceRequest request, @RequestHeader String token) {
        log.info("getConMeasurePriceList");
        return conMeasurePriceService.getConMeasurePriceList(request, token);
    }

    /**
     * 获取合同计量计价列表（不鉴权）
     *
     * @param request
     * @return
     */
    @PostMapping("/getConMeasurePriceListNoAuth")
    public BaseResponse getConMeasurePriceListNoAuth(@RequestBody ConMeasurePriceRequest request) {
        log.info("getConMeasurePriceListNoAuth");
        return conMeasurePriceService.getConMeasurePriceListNoAuth(request);
    }

    /**
     * 获取合同计量计价详情
     *
     * @param id
     * @return
     */
    @GetMapping("/getConMeasurePriceById")
    public BaseResponse getConMeasurePriceById(@RequestParam String id) {
        log.info("getConMeasurePriceById");
        return conMeasurePriceService.getConMeasurePriceById(id);
    }

    /**
     * 通过合同ID获取合同计量计价信息
     *
     * @param conId
     * @param id
     * @return
     */
    @GetMapping("/getConMeasurePriceInfoByConId")
    public BaseResponse getConMeasurePriceInfoByConId(@RequestParam("conId") String conId, @RequestParam(value = "id", required = false) String id) {
        log.info("getConMeasurePriceInfoByConId");
        return conMeasurePriceService.getConMeasurePriceInfoByConId(conId, id);
    }
}
