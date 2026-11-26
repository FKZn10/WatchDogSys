package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecIntentionDTO;
import com.jpmsoft.projectProducer.service.secInfo.ISecIntentionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 招标意向
 *
 * @Author: 李文涛 aaron
 * @date: 2024/3/20 16:21
 */
@Slf4j
@RestController
@RequestMapping("/v1/intention")
public class SecIntentionController {

    @Resource
    private ISecIntentionService secIntentionService;


    @PostMapping("/createOrUpdateSecIntention")
    public BaseResponse createOrUpdateSecIntention(@RequestBody SecIntentionDTO request) {
        log.info("createOrUpdateSecIntention");
        return secIntentionService.createOrUpdate(request);
    }

    @GetMapping("/getSecIntentionById")
    public BaseResponse getSecIntentionById(@RequestParam String id) {
        log.info("getSecIntentionById");
        return secIntentionService.getSecIntentionById(id);
    }
}
