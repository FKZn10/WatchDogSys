package com.jpmsoft.projectProducer.controller.v1.materialsEquipment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.materialsEquipment.MeBrandLibraryDto;
import com.jpmsoft.projectProducer.service.materialsEquipment.IMeBrandLibraryService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 材料设备品牌库
 *
 * @Author: 李文涛 aaron
 * @date: 2022/12/26 11:36
 */
@Slf4j
@RestController
@RequestMapping("/v1/meBrandLibrary")
public class MeBrandLibraryController {
    @Resource
    private IMeBrandLibraryService meBrandLibraryService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateMeBrandLibrary")
    public BaseResponse createOrUpdateMeBrandLibrary(@RequestBody MeBrandLibraryDto request) {
        log.info("createOrUpdateMeBrandLibrary");
        return meBrandLibraryService.createOrUpdateMeBrandLibrary(request);
    }

    @TenantId
    @PostMapping("/getMeBrandLibraryList")
    public BaseResponse getMeBrandLibraryList(@RequestBody MeBrandLibraryDto request, @RequestHeader String token) {
        log.info("getMeBrandLibraryList");
        return meBrandLibraryService.getMeBrandLibraryList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getMeBrandLibraryById")
    public BaseResponse getMeBrandLibraryById(@RequestParam String id) {
        log.info("getMeBrandLibraryById");
        return meBrandLibraryService.getMeBrandLibraryById(id);
    }


    @GetMapping("/getMeBrandLibraryByMetId")
    public BaseResponse getMeBrandLibraryByMetId(@RequestParam String metId) {
        log.info("getMeBrandLibraryByMetId");
        return meBrandLibraryService.getMeBrandLibraryByMetId(metId);
    }

    @GetMapping("/syncMeBrandLibraryApplyState")
    public BaseResponse syncMeBrandLibraryApplyState(@RequestParam String blaId) {
        log.info("syncMeBrandLibraryApplyState");
        return meBrandLibraryService.syncMeBrandLibraryApplyState(blaId);
    }
}
