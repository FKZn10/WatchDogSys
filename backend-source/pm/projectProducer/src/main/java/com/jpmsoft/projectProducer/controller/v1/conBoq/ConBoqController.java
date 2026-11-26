package com.jpmsoft.projectProducer.controller.v1.conBoq;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.RequestBean;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConBoqDto;
import com.jpmsoft.projectProducer.service.conBoq.IConBoqDtlService;
import com.jpmsoft.projectProducer.service.conBoq.IConBoqService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/conbop")
public class ConBoqController {

    @Resource
    private IConBoqService conBoqService;

    @Resource
    private IConBoqDtlService conBoqDtlService;
    @Resource
    private RedisUtils redisUtils;

    /**
     * 首页查询
     */
    @PostMapping("/getBoqList")
    public BaseResponse getBoqList(@RequestBody Page page, @RequestHeader String token) {
        log.info("getBoqList");
        return conBoqService.getBoqList(page, redisUtils.getTeId(token));
    }

    /**
     * 新建
     */
    @PostMapping("/createBoq")
    public BaseResponse createBoq(@RequestBody RequestBean requestBean, @RequestHeader String token) {
        log.info("createBoq");
        return conBoqService.createBoq(requestBean, redisUtils.getTeId(token));
    }

    /**
     * 工程清单查看
     */
    @PostMapping("/getBoqById")
    public BaseResponse getBoqById(@RequestBody RequestBean requestBean) {
        log.info("getBoqById");
        return conBoqService.getBoqById(requestBean);
    }

    /**
     * 工程清单修改
     */
    @PostMapping("/updateBop")
    public BaseResponse updateBop(@RequestBody RequestBean requestBean) {
        log.info("updateBop");
        return conBoqService.updateBop(requestBean);
    }

    /**
     * 工程清审核
     */
    @PostMapping("/updateBoqState")
    public BaseResponse updateBoqState(@RequestBody RequestBean requestBean) {
        log.info("updateBop");
        return conBoqService.updateBoqState(requestBean);
    }

    /**
     * 清空临时表
     */
    @PostMapping("/deleteConBoqDtlTemp")
    public BaseResponse deleteConBoqDtlTemp(@RequestBody RequestBean requestBean) {
        log.info("updateBop");
        return conBoqDtlService.deleteConBoqDtlTemp(requestBean);
    }

    @TenantId
    @PostMapping("/createOrUpdateConBoq")
    public BaseResponse createOrUpdateConBoq(@RequestBody ConBoqDto request) {
        log.info("createOrUpdateConBoq");
        return conBoqService.createOrUpdateConBoq(request);
    }

    @TenantId
    @PostMapping("/getConBoqList")
    public BaseResponse getConBoqList(@RequestBody ConBoqDto request, @RequestHeader String token) {
        log.info("getConBoqList");
        return conBoqService.getConBoqList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getConBoqById")
    public BaseResponse getConBoqById(@RequestParam String boqId,@RequestParam(required = false) Boolean queryDtl) {
        log.info("getConBoqById");
        return conBoqService.getConBoqById(boqId,queryDtl);
    }
}
