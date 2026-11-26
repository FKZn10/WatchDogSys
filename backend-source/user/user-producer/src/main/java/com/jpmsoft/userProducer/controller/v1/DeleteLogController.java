package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.DeleteLogDto;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.userProducer.service.DeleteLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author yb.Sun
 * @date 2024/10/23 16:20
 */
@Slf4j
@RestController
@RequestMapping("/v1/DeleteLog")
public class DeleteLogController {
    @Resource
    private DeleteLogService deleteLogService;
    @PostMapping("/insertDeleteLog")
    public BaseResponse insertDeleteLog(@RequestBody DeleteLogDto request) {
        log.info("insertDeleteLog");
        log.info("request:{}", JacksonUtils.toJSONString(request));
        return deleteLogService.insertDeleteLog(request);
    }

    @PostMapping("/getDeleteLogList")
    public BaseResponse getDeleteLogList(@RequestBody DeleteLogDto request) {
        log.info("getDeleteLogList");
        log.info("request:{}", JacksonUtils.toJSONString(request));
        return deleteLogService.getDeleteLogList(request);
    }


    @GetMapping("/getDeleteLogById")
    public BaseResponse getDeleteLogById(@RequestParam String id) {
        log.info("getDeleteLogById");
        return deleteLogService.getDeleteLogById(id);
    }

    @PostMapping("/approveData")
    public BaseResponse approveData(@RequestBody DeleteLogDto request) {
        log.info("approveData");
        log.info("request:{}", JacksonUtils.toJSONString(request));
        return deleteLogService.approveData(request);
    }
}
