package com.jpmsoft.projectProducer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.project.PreProjectRegisterRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.project.IPreProjectRegisterService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Slf4j
@RestController
@RequestMapping("/v1/projectRegister")
public class PreProjectRegisterController {
    @Resource
    private IPreProjectRegisterService preProjectRegisterService;
    @Resource
    private RedisUtils redisUtils;

    /**
     * 创建修改
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/CreateOrUpdatePreProjectRegister")
    public BaseResponse CreateOrUpdatePreProjectRegister(@RequestBody PreProjectRegisterRequest request) {
        log.info("CreateOrUpdatePreProjectRegister");
        return preProjectRegisterService.CreateOrUpdatePreProjectRegister(request);
    }

    /**
     * 删除
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/deletePreProjectRegister")
    public BaseResponse deletePreProjectRegister(@RequestBody PreProjectRegisterRequest request) {
        log.info("deletePreProjectRegister");
        return preProjectRegisterService.deletePreProjectRegister(request);
    }

    /**
     * 审批
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/approvalPreProjectRegister")
    public BaseResponse approvalPreProjectRegister(@RequestBody PreProjectRegisterRequest request) {
        log.info("approvalPreProjectRegister");
        return preProjectRegisterService.approvalPreProjectRegister(request);
    }


    /**
     * 分页查询项目注册
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/getPreProjectRegisterList")
    public BaseResponse getPreProjectRegisterList(@RequestBody PreProjectRegisterRequest request) {
        log.info("getPreProjectRegisterList");
        return preProjectRegisterService.getPreProjectRegisterList(request);
    }

    @TenantId
    @PostMapping("/getPreProjectRegisterStatisticsList")
    public BaseResponse getPreProjectRegisterStatisticsList(@RequestBody PreProjectRegisterRequest request) {
        log.info("getPreProjectRegisterStatisticsList");
        return preProjectRegisterService.getPreProjectRegisterStatisticsList(request);
    }

    /**
     * 组织机构部门单位鉴权列表查询
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/getPreProjectRegisterByDepCompany")
    public BaseResponse getPreProjectRegisterByDepCompany(@RequestBody PreProjectRegisterRequest request, @RequestHeader String token) {
        log.info("getPreProjectRegisterByDepCompany");
        return preProjectRegisterService.getPreProjectRegisterByDepCompany(request, redisUtils.getUserInfoByToken(token));
    }

    /**
     * ID查询单条项目注册
     * 项目资金来源单位（元）
     *
     * @param request
     * @return
     */
    @PostMapping("/getPreProjectRegisterById")
    public BaseResponse getPreProjectRegisterById(@RequestBody PreProjectRegisterRequest request) {
        log.info("getPreProjectRegisterById");
        return preProjectRegisterService.getPreProjectRegisterById(request);
    }

    /**
     * ID查询单条项目注册
     * 项目资金来源单位（万元）
     *
     * @param request
     * @return
     */
    @PostMapping("/getPreProjectRegisterWYById")
    public BaseResponse getPreProjectRegisterWYById(@RequestBody PreProjectRegisterRequest request) {
        log.info("getPreProjectRegisterWYById");
        return preProjectRegisterService.getPreProjectRegisterWYById(request);
    }
}
