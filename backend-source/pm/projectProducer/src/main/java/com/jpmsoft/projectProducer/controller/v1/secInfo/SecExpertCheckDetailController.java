package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecExpertCheckDetailModel;
import com.jpmsoft.projectProducer.service.secInfo.ISecExpertCheckDetailService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/expert/detail")
public class SecExpertCheckDetailController {

    @Resource
    private ISecExpertCheckDetailService secExpertCheckDetailService;

    @Resource
    private RedisUtils redisUtils;

    @PostMapping("createOrupdateSecExpertCheckDetailAndDtl")
    public BaseResponse createOrupdateSecExpertCheckDetailAndDtl(@RequestBody SecExpertCheckDetailModel request, @RequestHeader String token) {
        log.info("createOrupdateSecExpertCheckDetailAndDtl");
        return secExpertCheckDetailService.createOrupdateSecExpertCheckDetailAndDtl(request,redisUtils.getUid(token));

    }

    @GetMapping("getSecExpertCheckDetailAndDtlById")
    public BaseResponse getSecExpertCheckDetailAndDtlById(@RequestParam String id, @RequestHeader String token) {
        log.info("getSecExpertCheckDetailAndDtl");
        return secExpertCheckDetailService.getSecExpertCheckDetailAndDtlById(id,redisUtils.getUid(token));
    }



    @GetMapping("getSecExpertCheckDetailAndDtlSelect")
    public BaseResponse getSecExpertCheckDetailAndDtlSelect(@RequestParam String srdId, @RequestHeader String token) {
        log.info("getSecExpertCheckDetailAndDtlSelect");
        return secExpertCheckDetailService.getSecExpertCheckDetailAndDtlSelect(srdId,redisUtils.getUid(token));
    }

    @GetMapping("deleteSecExpertCheckDetailBySrId")
    public BaseResponse deleteSecExpertCheckDetailBySrId(@RequestParam String srId,@RequestHeader String token) {
        log.info("deleteSecExpertCheckDetailBySrId");
        return secExpertCheckDetailService.deleteSecExpertCheckDetailBySrId(srId,redisUtils.getUid(token));
    }

}
