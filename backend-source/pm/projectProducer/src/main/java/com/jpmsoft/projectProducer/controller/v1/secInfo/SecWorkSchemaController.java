package com.jpmsoft.projectProducer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecWorkSchemaDTO;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.secInfo.ISecWorkSchemaService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/2/23 10:05
 */
@Slf4j
@RestController
@RequestMapping("/v1/workSchema")
public class SecWorkSchemaController {

    @Resource
    private ISecWorkSchemaService secWorkSchemaService;

    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdateSecWorkSchema")
    public BaseResponse createOrUpdateSecWorkSchema(@RequestBody SecWorkSchemaDTO request) {
        log.info("createOrUpdateSecWorkSchema");
        return secWorkSchemaService.createOrUpdateSecWorkSchema(request);
    }

    @TenantId
    @PostMapping("/getSecWorkSchemaListClassInfo")
    public BaseResponse getSecWorkSchemaListClassInfo(@RequestBody SecWorkSchemaDTO request, @RequestHeader String token) {
        log.info("getSecWorkSchemaListClassInfo");
        return secWorkSchemaService.getSecWorkSchemaListClassInfo(request, redisUtils.getUserInfoByToken(token));
    }

    @TenantId
    @PostMapping("/getSecWorkSchemaListClass")
    public BaseResponse getSecWorkSchemaListClass(@RequestBody SecWorkSchemaDTO request, @RequestHeader String token) {
        log.info("getSecWorkSchemaListClass");
        return secWorkSchemaService.getSecWorkSchemaListClass(request, redisUtils.getUserInfoByToken(token));
    }

    @TenantId
    @PostMapping("/getSecWorkSchemaList")
    public BaseResponse getSecWorkSchemaList(@RequestBody SecWorkSchemaDTO request) {
        log.info("getSecWorkSchemaList");
        return secWorkSchemaService.getSecWorkSchemaList(request);
    }

    @GetMapping("/getSecWorkSchemaByWsId")
    public BaseResponse getSecWorkSchemaByWsId(@RequestParam String wsId) {
        log.info("getSecWorkSchemaByWsId");
        return secWorkSchemaService.getSecWorkSchemaByWsId(wsId);
    }

    @GetMapping("/getSecWorkSchemaBySecId")
    public BaseResponse getSecWorkSchemaBySecId(@RequestParam String secId) {
        log.info("getSecWorkSchemaBySecId");
        return secWorkSchemaService.getSecWorkSchemaBySecId(secId);
    }
}
