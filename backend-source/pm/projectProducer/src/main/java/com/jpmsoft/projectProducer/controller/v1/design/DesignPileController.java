package com.jpmsoft.projectProducer.controller.v1.design;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.design.DesignPileDto;
import com.jpmsoft.projectProducer.service.design.IDesignPileService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 桩基检测
 * Created By:李文涛 aaron
 * Created Time:2022/6/22 14:18
 */
@Slf4j
@RestController
@RequestMapping("/v1/designPile")
public class DesignPileController {

    @Resource
    private IDesignPileService designPileService;

    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdateDesignPile")
    public BaseResponse createOrUpdateDesignPile(@RequestBody DesignPileDto request) {
        log.info("createOrUpdateDesignPile");
        return designPileService.createOrUpdateDesignPile(request);
    }

    @TenantId
    @PostMapping("/getDesignPileList")
    public BaseResponse getDesignPileList(@RequestBody DesignPileDto request, @RequestHeader String token) {
        log.info("getDesignPileList");
        return designPileService.getDesignPileList(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getDesignPileListNoAuth")
    public BaseResponse getDesignPileListNoAuth(@RequestBody DesignPileDto request) {
        log.info("getDesignPileListNoAuth");
        return designPileService.getDesignPileListNoAuth(request);
    }

    @GetMapping("/getDesignPileById")
    public BaseResponse getDesignPileById(@RequestParam String dpId) {
        log.info("getDesignPileById");
        return designPileService.getDesignPileById(dpId);
    }
}
