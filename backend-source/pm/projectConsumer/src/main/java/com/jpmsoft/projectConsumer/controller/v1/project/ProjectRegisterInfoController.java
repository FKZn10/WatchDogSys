package com.jpmsoft.projectConsumer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.ProjectRegisterInfoModel;
import com.jpmsoft.projectConsumer.feign.PreRegisterFeign;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "项目注册", value = "项目注册")
@RequestMapping("/v1/projectRegisterInfo")
public class ProjectRegisterInfoController {
    @Autowired
    private PreRegisterFeign preRegisterFeign;

    /**
     * 校验选定登记项目能否发起申报或论证，保存时后端会再次检查。
     *
     * @param request 登记ID、业务类型、论证目的及编辑时的单据ID
     * @return 允许发起或具体阻止原因
     */
    @PostMapping("/checkProjectRegisterInfo")
    public BaseResponse checkProjectRegisterInfo(@RequestBody ProjectRegisterInfoModel request) {
        return preRegisterFeign.checkProjectRegisterInfo(request);
    }

    /**
     * 创建或修改登记信息，registerType 为 01（项目申报）或 02（项目论证），为空默认 01。
     *
     * @param request 登记信息，registerType 非必填
     * @return 保存结果及登记信息主键
     */
    @PostMapping("/createOrUpdatePreProjectRegisterInfo")
    public BaseResponse createOrUpdatePreProjectRegisterInfo(@RequestBody ProjectRegisterInfoModel request) {
        log.info("createOrUpdatePreProjectRegisterInfo");
        return preRegisterFeign.createOrUpdatePreProjectRegisterInfo(request);
    }
    @PostMapping("/deletePreProjectRegisterInfo")
    public BaseResponse deletePreProjectRegisterInfo(@RequestBody ProjectRegisterInfoModel request) {
        log.info("deletePreProjectRegisterInfo");
        return preRegisterFeign.deletePreProjectRegisterInfo(request);
    }
    @PostMapping("/getPreProjectRegisterInfoList")
    public BaseResponse getPreProjectRegisterInfoList(@RequestBody ProjectRegisterInfoModel request, @RequestHeader String token) {
        log.info("getPreProjectRegisterInfoList");
        return preRegisterFeign.getPreProjectRegisterInfoList(request,token);
    }
    @GetMapping("/getPreProjectRegisterInfoById")
    public BaseResponse getPreProjectRegisterInfoById(@RequestParam("id") String id) {
        log.info("getPreProjectRegisterInfoById");
        return preRegisterFeign.getPreProjectRegisterInfoById(id);
    }
    @PostMapping("/modifyPreProjectRegisterInfo")
    public BaseResponse modifyPreProjectRegisterInfo(@RequestBody ProjectRegisterInfoModel request) {
        log.info("modifyPreProjectRegisterInfo");
        return preRegisterFeign.modifyPreProjectRegisterInfo(request);
    }
    @PostMapping("/getyPreProjectRegisterInfoModifyInfo")
    public BaseResponse getPreProjectRegisterInfoModifyInfo(@RequestBody ProjectRegisterInfoModel request, @RequestHeader String token) {
        log.info("getPreProjectRegisterInfoModifyInfo");
        return preRegisterFeign.getPreProjectRegisterInfoModifyInfo(request,token);
    }

    /**
     * 项目申报流程结束后同步到项目表 Pre_Project
     *
     * @param id 入参传申报 id
     */
    @GetMapping("/syncPreProject")
    public BaseResponse syncPreProject(@RequestParam("id") String id) {
        log.info("syncPreProject");
        return preRegisterFeign.syncPreProject(id);
    }

    /**
     * 按类型统计当前用户可见的最新申报或论证，同一登记已有申报时不再计入论证。
     *
     * @param registerType 必填，01项目申报、02项目论证
     * @return 总数、审批中和流程结束数量
     */
    @GetMapping("/getDeclarationStatistics")
    public BaseResponse getDeclarationStatistics(@RequestParam("registerType") String registerType) {
        return preRegisterFeign.getDeclarationStatistics(registerType);
    }
}
