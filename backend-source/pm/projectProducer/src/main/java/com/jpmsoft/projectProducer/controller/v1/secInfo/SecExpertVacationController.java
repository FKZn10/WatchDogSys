package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecExpertVacationDTO;
import com.jpmsoft.projectProducer.service.secInfo.ISecExpertVacationService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/8/1 10:25
 */
@Slf4j
@RestController
@RequestMapping("/v1/expert/vacation")
public class SecExpertVacationController {

    @Resource
    private RedisUtils redisUtils;
    @Resource
    private ISecExpertVacationService secExpertVacationService;


    @TenantId
    @PostMapping("/createOrUpdateSecExpertVacation")
    public BaseResponse createOrUpdateSecExpertVacation(@RequestBody SecExpertVacationDTO request) {
        log.info("createOrUpdateSecExpertVacation");
        return secExpertVacationService.createOrUpdateSecExpertVacation(request);
    }

    @TenantId
    @PostMapping("/getSecExpertVacationList")
    public BaseResponse getSecExpertVacationList(@RequestBody SecExpertVacationDTO request, @RequestHeader String token) {
        log.info("getSecExpertVacationList");
        return secExpertVacationService.getSecExpertVacationList(request, redisUtils.getUserInfoByToken(token));
    }

    @GetMapping("/getSecExpertVacationById")
    public BaseResponse getSecExpertVacationById(@RequestParam String id) {
        log.info("getSecExpertVacationById");
        return secExpertVacationService.getSecExpertVacationById(id);
    }

    @GetMapping("/approveSecExpertVacationById")
    public BaseResponse approveSecExpertVacationById(@RequestParam String id, @RequestHeader String token) {
        log.info("approveSecExpertVacationById");
        return secExpertVacationService.approveSecExpertVacationById(id, token);
    }

}
