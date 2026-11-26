package com.jpmsoft.projectProducer.controller.v1.constructionProcedure;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.proCp.ProjectCpRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.constructionProcedure.IProjectCpService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/ConstructionProcedure/proCp")
public class ProjectCpController {
    @Resource
    private IProjectCpService projectCpService;
    @Resource
    private RedisUtils redisUtils;

    /**
     * 项目审批通过生成项目建设程序
     *
     * @param pid
     * @param token
     * @return
     */
    @GetMapping("/projectApprove2generateCp")
    public BaseResponse projectApprove2generateCp(@RequestParam String pid, @RequestHeader String token) {
        log.info("projectApprove2generateCp");
        return projectCpService.projectApprove2generateCp(pid, redisUtils.getUserInfoByToken(token));
    }

    /**
     * 给没有项目建设程序的项目生成全量项目建设程序库的项目建设程序
     *
     * @return
     */
    @GetMapping("/generateProjectCpWithUnCpProject")
    public BaseResponse generateProjectCpWithUnCpProject(@RequestHeader String token) {
        log.info("generateProjectCpWithUnCpProject");
        return projectCpService.generateProjectCpWithUnCpProject(redisUtils.getUserInfoByToken(token));
    }


    @TenantId
    @PostMapping("/createOrUpdateProjectCp")
    public BaseResponse createOrUpdateProjectCp(@RequestBody ProjectCpRequest request) {
        log.info("createOrUpdateProjectCp");
        return projectCpService.createOrUpdateProjectCp(request);
    }

    @TenantId
    @PostMapping("/getProCpList")
    public BaseResponse getProCpList(@RequestBody ProjectCpRequest request, @RequestHeader String token) {
        log.info("getProCpList");
        return projectCpService.getProCpList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getProjectCpByCpId")
    public BaseResponse getProjectCpByCpId(@RequestParam String cpId) {
        log.info("getProjectCpByCpId");
        return projectCpService.getProjectCpByCpId(cpId);
    }

    @TenantId
    @PostMapping("/statisticalProCp")
    public BaseResponse statisticalProCp(@RequestBody ProjectCpRequest request) {
        log.info("statisticalProCp");
        return projectCpService.statisticalProCp(request);
    }

    /**
     * 统计用户建设项目
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/statisticalUserProCp")
    public BaseResponse statisticalUserProCp(@RequestBody ProjectCpRequest request) {
        log.info("statisticalUserProCp");
        return projectCpService.statisticalUserProCp(request);
    }

    /**
     * ID删除记录
     *
     * @param cpId
     * @return
     */
    @GetMapping("/deleteProCpByCpId")
    public BaseResponse deleteProCpByCpId(@RequestParam String cpId) {
        log.info("deleteProCpByCpId");
        return projectCpService.deleteProCpByCpId(cpId);
    }

    @GetMapping("/getCpIdByPid")
    public BaseResponse getCpIdByPid(@RequestParam String pId) {
        log.info("getCpIdByPid");
        return projectCpService.getCpIdByPid(pId);
    }


    @TenantId
    @PostMapping("/getProCpAndDtlList")
    public BaseResponse getProCpAndDtlList(@RequestBody ProjectCpRequest request, @RequestHeader String token) {
        log.info("getProCpAndDtlList");
        return projectCpService.getProCpAndDtlList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getTaskIsManyByCplIdPlanId")
    public BaseResponse getTaskIsManyByCplIdPlanId(@RequestParam("cpdCplId") String cpdCplId, @RequestParam("pId") String pId) {
        log.info("getTaskIsManyByCplIdPlanId");
        return projectCpService.getTaskIsManyByCplIdPlanId(cpdCplId, pId);
    }

    @PostMapping("/getProCpLedgerNJCJ")
    public BaseResponse getProCpLedgerNJCJ(@RequestBody ProjectCpRequest request) {
        log.info("getProCpLedgerNJCJ");
        return projectCpService.getProCpLedgerNJCJ(request);
    }

    /**
     * 根据项目id和项目建设配置生成项目配置
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/generateProjectCpWithPid")
    public BaseResponse generateProjectCpWithPid(@RequestBody ProjectCpRequest request) {
        log.info("generateProjectCpWithPid");
        return projectCpService.generateProjectCpWithPid(request);
    }
}
