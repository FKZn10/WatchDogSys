package com.jpmsoft.projectConsumer.feign;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.ProjectRegisterInfoModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "project-producer", contextId = "projectRegister")
public interface PreRegisterFeign {
    /**
     * 调用生产端的申报、论证发起预校验。
     *
     * @param request 登记ID、业务类型、论证目的及可选的当前单据ID
     * @return 校验结果
     */
    @PostMapping("/v1/projectRegisterInfo/checkProjectRegisterInfo")
    BaseResponse checkProjectRegisterInfo(@RequestBody ProjectRegisterInfoModel request);

    @PostMapping("/v1/projectRegisterInfo/createOrUpdatePreProjectRegisterInfo")
    BaseResponse createOrUpdatePreProjectRegisterInfo(@RequestBody ProjectRegisterInfoModel request);

    @PostMapping("/v1/projectRegisterInfo/deletePreProjectRegisterInfo")
    BaseResponse deletePreProjectRegisterInfo(@RequestBody ProjectRegisterInfoModel request);

    @PostMapping("/v1/projectRegisterInfo/approvalPreProjectRegisterInfo")
    BaseResponse approvalPreProjectRegisterInfo(@RequestBody ProjectRegisterInfoModel request);

    @PostMapping("/v1/projectRegisterInfo/getPreProjectRegisterInfoList")
    BaseResponse getPreProjectRegisterInfoList(@RequestBody ProjectRegisterInfoModel request, @RequestHeader String token);

    @GetMapping("/v1/projectRegisterInfo/getPreProjectRegisterInfoById")
    BaseResponse getPreProjectRegisterInfoById(@RequestParam("id") String id);
    @PostMapping("/v1/projectRegisterInfo/modifyPreProjectRegisterInfo")
    BaseResponse modifyPreProjectRegisterInfo(@RequestBody ProjectRegisterInfoModel request);
    @PostMapping("/v1/projectRegisterInfo/getPreProjectRegisterInfoModifyInfo")
    BaseResponse getPreProjectRegisterInfoModifyInfo(@RequestBody ProjectRegisterInfoModel request, @RequestHeader String token);

    @GetMapping("/v1/projectRegisterInfo/syncPreProject")
    BaseResponse syncPreProject(@RequestParam("id") String id);

    /**
     * 按类型统计当前用户可见的最新申报或论证，同一登记已有申报时不再计入论证。
     *
     * @param registerType 必填，01项目申报、02项目论证
     * @return 总数、审批中和流程结束数量
     */
    @GetMapping("/v1/projectRegisterInfo/getDeclarationStatistics")
    BaseResponse getDeclarationStatistics(@RequestParam("registerType") String registerType);
}
