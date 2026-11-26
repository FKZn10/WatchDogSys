package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecFormDetailDTO;
import com.jpmsoft.projectProducer.service.secInfo.ISecFormDetailService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/8/7 17:53
 */
@Slf4j
@RestController
@RequestMapping("/v1/expert/secFormDetail")
public class SecFormDetailController {

    @Resource
    private ISecFormDetailService secFormDetailService;
    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdateSecFormDetail")
    public BaseResponse createOrUpdateSecFormDetail(@RequestBody SecFormDetailDTO request) {
        log.info("createOrUpdateSecFormDetail");
        return secFormDetailService.createOrUpdateSecFormDetail(request);
    }

    @TenantId
    @PostMapping("/getSecFormDetailList")
    public BaseResponse getSecFormDetailList(@RequestBody SecFormDetailDTO request) {
        log.info("getSecFormDetailList");
        return secFormDetailService.getSecFormDetailList(request);
    }

    @GetMapping("/deleteSecFormDetailById")
    public BaseResponse deleteSecFormDetailById(@RequestParam String id) {
        log.info("deleteSecFormDetailById");
        return secFormDetailService.deleteSecFormDetailById(id);
    }


    @GetMapping("/getSecFormDetailLatestList")
    public BaseResponse getSecFormDetailLatestList(@RequestHeader String token)
    {
        log.info("getSecFormDetailLatestList");
        return secFormDetailService.getSecFormDetailLatestList(redisUtils.getTeId(token));
    }
}
