package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecSelectionRecordDtlModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecExpertCheckDTO;
import com.jpmsoft.projectProducer.service.secInfo.ISecExpertCheckDtlService;
import com.jpmsoft.projectProducer.service.secInfo.ISecExpertCheckService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/8/2 14:08
 */
@Slf4j
@RestController
@RequestMapping("/v1/expert/check")
public class SecExpertCheckController {

    @Resource
    private RedisUtils redisUtils;
    @Resource
    private ISecExpertCheckService secExpertCheckService;
    @Resource
    private ISecExpertCheckDtlService secExpertCheckDtlService;

    @TenantId
    @PostMapping("/createOrUpdateSecExpertCheck")
    public BaseResponse createOrUpdateSecExpertCheck(@RequestBody SecExpertCheckDTO request) {
        log.info("createOrUpdateSecExpertCheck");
        return secExpertCheckService.createOrUpdateSecExpertCheck(request);
    }

    @TenantId
    @PostMapping("/getSecExpertCheckList")
    public BaseResponse getSecExpertCheckList(@RequestBody SecExpertCheckDTO request, @RequestHeader String token) {
        log.info("getSecExpertCheckList");
        return secExpertCheckService.getSecExpertCheckList(request, redisUtils.getUserInfoByToken(token));
    }

    @GetMapping("/getSecExpertCheckById")
    public BaseResponse getSecExpertCheckById(@RequestParam String id) {
        log.info("getSecExpertCheckById");
        return secExpertCheckService.getSecExpertCheckById(id);
    }


    @GetMapping("/getExpertCheckDtlList")
    public BaseResponse getExpertCheckDtlList(@RequestParam String secId,@RequestHeader String token) {
        return secExpertCheckDtlService.getExpertCheckDtlList(secId, redisUtils.getUid(token));
    }

    @GetMapping("getSecExpertCheckBySrId")
    public BaseResponse getSecExpertCheckBySrId(@RequestParam String srId,@RequestHeader String token) {


        return secExpertCheckService.getSecExpertCheckBySrId(srId, redisUtils.getUid(token));
    }
    @PostMapping("/getSecExpertCheckDtlRecord")
    public BaseResponse getSecExpertCheckDtlRecord(@RequestBody SecSelectionRecordDtlModel request) {
        log.info("getSecExpertCheckDtlRecord");
        return secExpertCheckDtlService.getSecExpertCheckDtlRecord(request);
    }

    @GetMapping("getSecEXpertCheckSendToDo")
    public BaseResponse getSecEXpertCheckSendToDo() {
        log.info("getSecEXpertCheckSendToDo");
        return secExpertCheckService.getSecEXpertCheckSendToDo();
    }

    // 获取超时未处理的待办
    @PostMapping("/getSecExpertCheckTaskTimeout")
    public BaseResponse getSecExpertCheckTimeout(@RequestBody SecExpertCheckDTO request) {
        log.info("getSecExpertCheckTaskTimeout");
        return secExpertCheckService.getSecExpertCheckTaskTimeout(request);
    }

    @GetMapping("/syncSecExpertPause")
    public BaseResponse syncSecExpertPause(@RequestParam String id) {
        log.info("syncSecExpertPause");
        return secExpertCheckService.syncSecExpertPause(id);
    }
}
