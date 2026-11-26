package com.jpmsoft.projectProducer.controller.v1.actualMeasurement;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ActualMeasurement.TaskDistributionRequest;
import com.jpmsoft.common.model.request.Qs.QsRecordRequest;
import com.jpmsoft.common.model.response.ActualMeasurement.TaskDistributionCheckModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.actualMeasurement.ITaskDistributionCheckService;
import com.jpmsoft.projectProducer.service.actualMeasurement.ITaskDistributionService;
import com.jpmsoft.projectProducer.service.ganttPlan.IGantSectionTaskChangeService;
import com.jpmsoft.projectProducer.utils.ExpressionUtils;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author jjz
 * data 2025/09/02
 * 实测实量 - 任务管理
 */
@Slf4j
@RestController
@RequestMapping("/v1/taskDistribution")
public class TaskDistributionController {

    @Resource
    private ITaskDistributionService taskDistributionService;

    @Resource
    private ITaskDistributionCheckService taskDistributionCheckService;

    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdateTaskDistributionAndCheck")
    public BaseResponse createOrUpdateTaskDistributionAndCheck(@RequestBody TaskDistributionRequest request) {
        log.info("createOrUpdateTaskDistributionAndCheck");
        return taskDistributionService.createOrUpdateTaskDistributionAndCheck(request);
    }

    // 批量生成“图纸授权载体任务”：只生成任务主表并写入可操作用户集合，不预生成检查点。
    // 原保存接口会整体重建检查明细，不能用于空任务批量初始化，否则可能误删已有点位。
    @TenantId
    @PostMapping("/batchCreateTaskDistributionByDmIds")
    public BaseResponse batchCreateTaskDistributionByDmIds(@RequestBody TaskDistributionRequest request) {
        log.info("batchCreateTaskDistributionByDmIds");
        return taskDistributionService.batchCreateTaskDistributionByDmIds(request);
    }


    @PostMapping("/createOrUpdateTaskDistributionCheckNew")
    public BaseResponse createOrUpdateTaskDistributionCheckNew(@RequestBody List<TaskDistributionCheckModel> request) {
        log.info("createOrUpdateTaskDistributionCheckNew");
        return taskDistributionService.createOrUpdateTaskDistributionCheckNew(request);
    }

    @PostMapping("/createOrUpdateTaskDistributionCheckSingle")
    public BaseResponse createOrUpdateTaskDistributionCheckSingle(@RequestBody TaskDistributionCheckModel request) {
        log.info("createOrUpdateTaskDistributionCheckSingle");
        return taskDistributionCheckService.createOrUpdateTaskDistributionCheckSingle(request);
    }

    @GetMapping("/getTaskDistributionByTdId")
    public BaseResponse getTaskDistributionByTdId(@RequestParam String tdId) {
        log.info("getTaskDistributionByTdId");
        return taskDistributionService.getTaskDistributionByTdId(tdId);
    }

    // todo 此接口已用不了   改为 getTaskDistributionByDmId
    @GetMapping("/getTaskDistributionByDiId")
    public BaseResponse getTaskDistributionByDiId(@RequestParam String diId) {
        log.info("getTaskDistributionByDiId");
        return taskDistributionService.getTaskDistributionByDiId(diId);
    }

    @GetMapping("/getTaskDistributionByDmId")
    public BaseResponse getTaskDistributionByDmId(@RequestParam String dmId) {
        log.info("getTaskDistributionByDmId");
        return taskDistributionService.getTaskDistributionByDmId(dmId);
    }

    @TenantId
    @PostMapping("/getTaskDistributionList")
    public BaseResponse getTaskDistributionList(@RequestBody TaskDistributionRequest request,@RequestHeader String token) {
        log.info("getTaskDistributionList");
        return taskDistributionService.getTaskDistributionList(request,redisUtils.getUid(token));
    }

    @GetMapping("/deleteTaskDistributionByTdId")
    public BaseResponse deleteTaskDistributionByTdId(@RequestParam String tdId) {
        log.info("deleteTaskDistributionByTdId");
        return taskDistributionService.deleteTaskDistributionByTdId(tdId);
    }

    @GetMapping("/deleteTaskDistributionCheckByTdId")
    public BaseResponse deleteTaskDistributionCheckByTdId(@RequestParam String tdId) {
        log.info("deleteTaskDistributionCheckByTdId");
        return taskDistributionService.deleteTaskDistributionCheckByTdId(tdId);
    }


    @PostMapping("/backWriteTaskDistributionCheck")
    public BaseResponse backWriteTaskDistributionCheck(@RequestBody TaskDistributionCheckModel request) {
        log.info("backWriteTaskDistributionCheck");
        return taskDistributionCheckService.backWriteTaskDistributionCheck(request);
    }


    @GetMapping("/getTaskDistributionCheckByCiId")
    public BaseResponse getTaskDistributionCheckByCiId(@RequestParam String ciId) {
        log.info("getTaskDistributionCheckByCiId");
        return BaseResponse.ok(taskDistributionCheckService.getTaskDistributionCheckByCiId(ciId));
    }

    @GetMapping("/getTaskDistributionCheckByDiId")
    public BaseResponse getTaskDistributionCheckByDiId(@RequestParam String diId) {
        log.info("getTaskDistributionCheckByDiId");
        return BaseResponse.ok(taskDistributionService.getTaskDistributionCheckByDiId(diId));
    }

    @TenantId
    @PostMapping("/getTaskDistributionCheckList")
    public BaseResponse getTaskDistributionCheckList(@RequestBody TaskDistributionCheckModel request,@RequestHeader String token) {
        log.info("getTaskDistributionCheckList");
        return taskDistributionCheckService.getTaskDistributionCheckList(request,redisUtils.getUid(token));
    }

    @PostMapping("/getTaskDistributionCheckByDmId")
    public BaseResponse getTaskDistributionCheckByDmId(@RequestBody TaskDistributionCheckModel request) {
        log.info("getTaskDistributionCheckByDmId");
        return taskDistributionCheckService.getTaskDistributionCheckByDmId(request);
    }
    // 默认第一个任务
    @TenantId
    @PostMapping("/getTaskDistributionCheckByCreateTimeNew")
    public BaseResponse getTaskDistributionCheckByCreateTimeNew(@RequestBody TaskDistributionCheckModel request) {
        log.info("getTaskDistributionCheckByCreateTimeNew");
        return taskDistributionCheckService.getTaskDistributionCheckByCreateTimeNew(request);
    }

    // 生成质量检查报告
    @TenantId
    @PostMapping("/generateQualityCheckReport")
    public BaseResponse generateQualityCheckReport(@RequestBody QsRecordRequest request,@RequestHeader String token) {
        log.info("generateQualityCheckReport");
        return taskDistributionCheckService.generateQualityCheckReport(request,token);
    }

}
