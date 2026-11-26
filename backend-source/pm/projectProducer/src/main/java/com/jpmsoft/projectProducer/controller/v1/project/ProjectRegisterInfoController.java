package com.jpmsoft.projectProducer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.ProjectRegisterInfoModel;
import com.jpmsoft.projectProducer.service.project.IProjectRegisterInfoSerice;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/projectRegisterInfo")
public class ProjectRegisterInfoController {
    @Autowired
    private IProjectRegisterInfoSerice projectRegisterInfoSerice;
    @Autowired
    private RedisUtils redisUtils;

    /**
     * 校验选定登记项目能否发起申报或论证。
     *
     * @param request 登记ID、业务类型、论证目的及编辑时的单据ID
     * @return 允许发起或具体阻止原因
     */
    @PostMapping("/checkProjectRegisterInfo")
    public BaseResponse checkProjectRegisterInfo(@RequestBody ProjectRegisterInfoModel request) {
        return projectRegisterInfoSerice.checkProjectRegisterInfo(request);
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
        return projectRegisterInfoSerice.createOrUpdatePreProjectRegisterInfo(request);
    }
    @PostMapping("/deletePreProjectRegisterInfo")
    public BaseResponse deletePreProjectRegisterInfo(@RequestBody ProjectRegisterInfoModel request) {
        log.info("deletePreProjectRegisterInfo");
        return projectRegisterInfoSerice.deletePreProjectRegisterInfo(request);
    }
    @PostMapping("/getPreProjectRegisterInfoList")
    public BaseResponse getPreProjectRegisterInfoList(@RequestBody ProjectRegisterInfoModel request, @RequestHeader String token) {
        log.info("getPreProjectRegisterInfoList");
        return projectRegisterInfoSerice.getPreProjectRegisterInfoList(request, redisUtils.getUid(token));
    }
    @GetMapping("/getPreProjectRegisterInfoById")
    public BaseResponse getPreProjectRegisterInfoById(@RequestParam("id") String id) {
        log.info("getPreProjectRegisterInfoById");
        return projectRegisterInfoSerice.getPreProjectRegisterInfoById(id);
    }

    @PostMapping("/modifyPreProjectRegisterInfo")
    public BaseResponse modifyPreProjectRegisterInfo(@RequestBody ProjectRegisterInfoModel request) {
        log.info("modifyPreProjectRegisterInfo");
        return projectRegisterInfoSerice.modifyPreProjectRegisterInfo(request);
    }

    @PostMapping("/getPreProjectRegisterInfoModifyInfo")
    public BaseResponse getPreProjectRegisterInfoModifyInfo(@RequestBody ProjectRegisterInfoModel request, @RequestHeader String token) {
        log.info("getPreProjectRegisterInfoModifyInfo");
        return projectRegisterInfoSerice.getPreProjectRegisterInfoModifyInfo(request, redisUtils.getUid(token));
    }

    /**
     * 项目申报流程结束后同步到项目表 Pre_Project（前端调用，入参传申报 id）
     */
    @GetMapping("/syncPreProject")
    public BaseResponse syncPreProject(@RequestParam("id") String id) {
        log.info("syncPreProject");
        return projectRegisterInfoSerice.syncPreProject(id);
    }

    /**
     * 项目申报同步项目表补偿（定时任务管理配置 RestHttpGetTask 定时调用 不对外开放调用）
     */
    @GetMapping("/syncPreProjectCompensate")
    public BaseResponse syncPreProjectCompensate() {
        log.info("syncPreProjectCompensate");
        return projectRegisterInfoSerice.syncPreProjectCompensate();
    }

    /**
     * 按类型统计当前用户可见的最新申报或论证，同一登记已有申报时不再计入论证。
     *
     * @param registerType 必填，01项目申报、02项目论证
     * @return 总数、审批中和流程结束数量
     */
    @GetMapping("/getDeclarationStatistics")
    public BaseResponse getDeclarationStatistics(@RequestParam("registerType") String registerType) {
        return projectRegisterInfoSerice.getDeclarationStatistics(registerType);
    }
}
