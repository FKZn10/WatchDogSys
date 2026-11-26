package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.ExtractExpertDto;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecExpertSelectDto;
import com.jpmsoft.projectProducer.service.secInfo.ISecExpertSelectService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 专家选取申请
 *
 * @author: 李文涛 aaron
 * @date: 2022/10/9 10:17
 */
@Slf4j
@RestController
@RequestMapping("/v1/secExpertSelect")
public class SecExpertSelectController {

    @Resource
    private ISecExpertSelectService secExpertSelectService;
    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdateSecExpertSelect")
    public BaseResponse createOrUpdateSecExpertSelect(@RequestBody SecExpertSelectDto request) {
        log.info("createOrUpdateSecExpertSelect");
        return secExpertSelectService.createOrUpdateSecExpertSelect(request);
    }

    @TenantId
    @PostMapping("/getSecExpertSelectList")
    public BaseResponse getSecExpertSelectList(@RequestBody SecExpertSelectDto request, @RequestHeader String token) {
        log.info("getSecExpertSelectList");
        return secExpertSelectService.getSecExpertSelectList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getSecExpertSelectById")
    public BaseResponse getSecExpertSelectById(@RequestParam String id) {
        log.info("getSecExpertSelectById");
        return secExpertSelectService.getSecExpertSelectById(id);
    }

    @TenantId
    @PostMapping("/checkExPeople")
    public BaseResponse checkExPeople(@RequestBody ExtractExpertDto request, @RequestHeader String token) {
        log.info("checkExPeople");
        return secExpertSelectService.checkExPeople(request, redisUtils.getTeId(token));
    }

}
