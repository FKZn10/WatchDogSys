package com.jpmsoft.projectConsumer.controller.v1.materialsEquipment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.materialsEquipment.MeQualityPriceDto;
import com.jpmsoft.projectConsumer.feign.MaterialFeign;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/1/3 14:10
 */
@Slf4j
@RestController
@Api(tags = "认质认价", value = "认质认价")
@RequestMapping("/v1/meQualityPrice")
public class MeQualityPriceController {

    @Resource
    private MaterialFeign materialFeign;


    @PostMapping("/createOrUpdateMeQualityPrice")
    public BaseResponse createOrUpdateMeQualityPrice(@RequestBody MeQualityPriceDto request) {
        log.info("createOrUpdateMeQualityPrice");
        return materialFeign.createOrUpdateMeQualityPrice(request);
    }

    @PostMapping("/getMeQualityPriceList")
    public BaseResponse getMeQualityPriceList(@RequestBody MeQualityPriceDto request, @RequestHeader String token) {
        log.info("getMeQualityPriceList");
        return materialFeign.getMeQualityPriceList(request, token);
    }

    @GetMapping("/getMeQualityPriceById")
    public BaseResponse getMeQualityPriceById(@RequestParam String id) {
        log.info("getMeQualityPriceById");
        return materialFeign.getMeQualityPriceById(id);
    }
}
