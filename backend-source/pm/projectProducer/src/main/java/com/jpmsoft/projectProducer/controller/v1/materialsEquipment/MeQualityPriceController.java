package com.jpmsoft.projectProducer.controller.v1.materialsEquipment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.materialsEquipment.MeQualityPriceDto;
import com.jpmsoft.projectProducer.service.materialsEquipment.IMeQualityPriceService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/1/3 14:10
 */
@Slf4j
@RestController
@RequestMapping("/v1/meQualityPrice")
public class MeQualityPriceController {

    @Resource
    private IMeQualityPriceService meQualityPriceService;

    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdateMeQualityPrice")
    public BaseResponse createOrUpdateMeQualityPrice(@RequestBody MeQualityPriceDto request) {
        log.info("createOrUpdateMeQualityPrice");
        return meQualityPriceService.createOrUpdateMeQualityPrice(request);
    }

    @TenantId
    @PostMapping("/getMeQualityPriceList")
    public BaseResponse getMeQualityPriceList(@RequestBody MeQualityPriceDto request, @RequestHeader String token) {
        log.info("getMeQualityPriceList");
        return meQualityPriceService.getMeQualityPriceList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getMeQualityPriceById")
    public BaseResponse getMeQualityPriceById(@RequestParam String id) {
        log.info("getMeQualityPriceById");
        return meQualityPriceService.getMeQualityPriceById(id);
    }
}
