package com.jpmsoft.projectProducer.controller.v1.materialsEquipment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.materialsEquipment.MeBrandLibraryApplyDto;
import com.jpmsoft.projectProducer.service.materialsEquipment.IMeBrandLibraryApplyService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/1/3 9:29
 */
@Slf4j
@RestController
@RequestMapping("/v1/meBrandLibraryApply")
public class MeBrandLibraryApplyController {

    @Resource
    private IMeBrandLibraryApplyService meBrandLibraryApplyService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateMeBrandLibraryApply")
    public BaseResponse createOrUpdateMeBrandLibraryApply(@RequestBody MeBrandLibraryApplyDto request) {
        log.info("createOrUpdateMeBrandLibraryApply");
        return meBrandLibraryApplyService.createOrUpdateMeBrandLibraryApply(request);
    }

    @TenantId
    @PostMapping("/getMeBrandLibraryApplyList")
    public BaseResponse getMeBrandLibraryApplyList(@RequestBody MeBrandLibraryApplyDto request, @RequestHeader String token) {
        log.info("getMeBrandLibraryApplyList");
        return meBrandLibraryApplyService.getMeBrandLibraryApplyList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getMeBrandLibraryApplyById")
    public BaseResponse getMeBrandLibraryApplyById(@RequestParam String id) {
        log.info("getMeBrandLibraryApplyById");
        return meBrandLibraryApplyService.getMeBrandLibraryApplyById(id);
    }
}
