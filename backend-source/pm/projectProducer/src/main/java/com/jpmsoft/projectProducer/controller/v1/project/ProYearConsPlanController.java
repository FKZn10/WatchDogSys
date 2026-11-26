package com.jpmsoft.projectProducer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.project.ProYearConsPlanDto;
import com.jpmsoft.projectProducer.service.project.IProYearConsPlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: 李文涛 aaron
 * @date: 2022/11/9 15:42
 */
@Slf4j
@RestController
@RequestMapping("/v1/proYearConsPlan")
public class ProYearConsPlanController {

    @Resource
    private IProYearConsPlanService proYearConsPlanService;

    @TenantId
    @PostMapping("/createOrUpdateProYearConsPlan")
    public BaseResponse createOrUpdateProYearConsPlan(@RequestBody ProYearConsPlanDto request) {
        log.info("createOrUpdateProYearConsPlan");
        return proYearConsPlanService.createOrUpdateProYearConsPlan(request);
    }

    @TenantId
    @PostMapping("/getProYearConsPlanList")
    public BaseResponse getProYearConsPlanList(@RequestBody ProYearConsPlanDto request) {
        log.info("getProYearConsPlanList");
        return proYearConsPlanService.getProYearConsPlanList(request);
    }

    @GetMapping("/getProYearConsPlanById")
    public BaseResponse getProYearConsPlanById(@RequestParam String id) {
        log.info("getProYearConsPlanById");
        return proYearConsPlanService.getProYearConsPlanById(id);
    }
}
