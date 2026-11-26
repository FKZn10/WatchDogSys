package com.jpmsoft.projectProducer.controller.v1.construction;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.construction.NightConstructionFilingDTO;
import com.jpmsoft.projectProducer.service.construction.INightConstructionFilingService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/nightConstructionFiling")
public class NightConstructionFilingController {

    @Resource
    private INightConstructionFilingService nightConstructionFilingService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateNightConstructionFiling")
    public BaseResponse createOrUpdateNightConstructionFiling(@RequestBody NightConstructionFilingDTO request) {
        log.info("createOrUpdateNightConstructionFiling");
        return nightConstructionFilingService.createOrUpdateNightConstructionFiling(request);
    }

    @TenantId
    @PostMapping("/getNightConstructionFilingList")
    public BaseResponse getNightConstructionFilingList(@RequestBody NightConstructionFilingDTO request, @RequestHeader String token) {
        log.info("getNightConstructionFilingList");
        return nightConstructionFilingService.getNightConstructionFilingList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getNightConstructionFilingById")
    public BaseResponse getNightConstructionFilingById(@RequestParam String id) {
        log.info("getNightConstructionFilingById");
        return nightConstructionFilingService.getNightConstructionFilingById(id);
    }
}
