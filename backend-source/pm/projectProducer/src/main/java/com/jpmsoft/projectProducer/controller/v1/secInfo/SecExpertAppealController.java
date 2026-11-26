package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecExpertAppealDTO;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecExpertAppealDtlDTO;
import com.jpmsoft.projectProducer.service.secInfo.ISecExpertAppealDtlService;
import com.jpmsoft.projectProducer.service.secInfo.ISecExpertAppealService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/8/2 14:08
 */
@Slf4j
@RestController
@RequestMapping("/v1/expert/appeal")
public class SecExpertAppealController {

    @Resource
    private RedisUtils redisUtils;
    @Resource
    private ISecExpertAppealService secExpertAppealService;
    @Resource
    private ISecExpertAppealDtlService secExpertAppealDtlService;

    @TenantId
    @PostMapping("/createOrUpdateSecExpertAppeal")
    public BaseResponse createOrUpdateSecExpertAppeal(@RequestBody SecExpertAppealDTO request) {
        log.info("createOrUpdateSecExpertAppeal");
        return secExpertAppealService.createOrUpdateSecExpertAppeal(request);
    }

    @TenantId
    @PostMapping("/getSecExpertAppealList")
    public BaseResponse getSecExpertAppealList(@RequestBody SecExpertAppealDTO request, @RequestHeader String token) {
        log.info("getSecExpertAppealList");
        return secExpertAppealService.getSecExpertAppealList(request, redisUtils.getUserInfoByToken(token));
    }

    @GetMapping("/getSecExpertAppealById")
    public BaseResponse getSecExpertAppealById(@RequestParam String id) {
        log.info("getSecExpertAppealById");
        return secExpertAppealService.getSecExpertAppealById(id);
    }

    @GetMapping("/approveSecExpertAppealById")
    public BaseResponse approveSecExpertAppealById(@RequestParam String id) {
        log.info("approveSecExpertAppealById");
        return secExpertAppealService.approveSecExpertAppealById(id);
    }


    @PostMapping("/updateSecExpertAppealDtl")
    public BaseResponse updateSecExpertAppealDtl(@RequestBody SecExpertAppealDtlDTO request) {
        log.info("updateSecExpertAppealDtl");
        return secExpertAppealDtlService.updateSecExpertAppealDtl(request);
    }

    @PostMapping("/updateSecExpertAppealDtlBath")
    public BaseResponse updateSecExpertAppealDtlBath(@RequestBody List<SecExpertAppealDtlDTO> request) {
        log.info("updateSecExpertAppealDtlBath");
        return secExpertAppealDtlService.updateSecExpertAppealDtlBath(request);
    }

}
