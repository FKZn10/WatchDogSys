package com.jpmsoft.projectProducer.controller.v1.materialsEquipment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.materialsEquipment.MePriceListDto;
import com.jpmsoft.projectProducer.service.materialsEquipment.IMePriceListService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 核价单
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/4 15:05
 */
@Slf4j
@RestController
@RequestMapping("/v1/mePriceList")
public class MePriceListController {

    @Resource
    private IMePriceListService mePriceListService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateMePriceList")
    public BaseResponse createOrUpdateMePriceList(@RequestBody MePriceListDto request) {
        log.info("createOrUpdateMePriceList");
        return mePriceListService.createOrUpdateMePriceList(request);
    }

    @TenantId
    @PostMapping("/getMePriceListList")
    public BaseResponse getMePriceListList(@RequestBody MePriceListDto request, @RequestHeader String token) {
        log.info("getMePriceListList");
        return mePriceListService.getMePriceListList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getMePriceListById")
    public BaseResponse getMePriceListById(@RequestParam String id) {
        log.info("getMePriceListById");
        return mePriceListService.getMePriceListById(id);
    }
}
