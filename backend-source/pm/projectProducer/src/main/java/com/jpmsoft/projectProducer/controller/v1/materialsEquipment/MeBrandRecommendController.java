package com.jpmsoft.projectProducer.controller.v1.materialsEquipment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.materialsEquipment.MeBrandRecommendDto;
import com.jpmsoft.projectProducer.service.materialsEquipment.IMeBrandRecommendService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/1/3 10:07
 */
@Slf4j
@RestController
@RequestMapping("/v1/meBrandRecommend")
public class MeBrandRecommendController {

    @Resource
    private IMeBrandRecommendService meBrandRecommendService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateMeBrandRecommend")
    public BaseResponse createOrUpdateMeBrandRecommend(@RequestBody MeBrandRecommendDto request) {
        log.info("createOrUpdateMeBrandRecommend");
        return meBrandRecommendService.createOrUpdateMeBrandRecommend(request);
    }

    @TenantId
    @PostMapping("/getMeBrandRecommendList")
    public BaseResponse getMeBrandRecommendList(@RequestBody MeBrandRecommendDto request, @RequestHeader String token) {
        log.info("getMeBrandRecommendList");
        return meBrandRecommendService.getMeBrandRecommendList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getMeBrandRecommendById")
    public BaseResponse getMeBrandRecommendById(@RequestParam String id) {
        log.info("getMeBrandRecommendById");
        return meBrandRecommendService.getMeBrandRecommendById(id);
    }
}
