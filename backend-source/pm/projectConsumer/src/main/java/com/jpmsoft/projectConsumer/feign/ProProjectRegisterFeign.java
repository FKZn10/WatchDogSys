package com.jpmsoft.projectConsumer.feign;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.project.ProProjectRegisterRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 项目登记生产者接口调用。
 */
@FeignClient(name = "project-producer", contextId = "proProjectRegister")
public interface ProProjectRegisterFeign {

    /**
     * 调用生产者保存项目登记及参建单位。
     *
     * @param request 整表单保存参数
     * @param token   登录令牌
     * @return 保存后的登记ID
     */
    @PostMapping("/v1/proProjectRegister/createOrUpdateProProjectRegister")
    BaseResponse createOrUpdateProProjectRegister(@RequestBody ProProjectRegisterRequest request,
                                                  @RequestHeader("token") String token);

    /**
     * 调用生产者分页查询项目登记。
     *
     * @param request 筛选及分页参数
     * @return 当前页记录及总条数
     */
    @PostMapping("/v1/proProjectRegister/getProProjectRegisterList")
    BaseResponse getProProjectRegisterList(@RequestBody ProProjectRegisterRequest request);

    /**
     * 调用生产者查询登记详情。
     *
     * @param id 登记ID
     * @return 登记主表、参建单位和附件
     */
    @GetMapping("/v1/proProjectRegister/getProProjectRegisterById")
    BaseResponse getProProjectRegisterById(@RequestParam("id") String id);

    /**
     * 调用生产者删除项目登记及关联明细。
     *
     * @param id 登记ID
     * @return 删除结果
     */
    @PostMapping("/v1/proProjectRegister/deleteProProjectRegister")
    BaseResponse deleteProProjectRegister(@RequestParam("id") String id);

    /**
     * 统计当前用户可见的全部年度项目登记。
     *
     * @return 总数、审核中和已审核数量
     */
    @PostMapping("/v1/proProjectRegister/getRegistrationStatistics")
    BaseResponse getRegistrationStatistics();
}
