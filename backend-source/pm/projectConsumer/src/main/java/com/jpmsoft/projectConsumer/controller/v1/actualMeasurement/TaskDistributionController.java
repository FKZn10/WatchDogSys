package com.jpmsoft.projectConsumer.controller.v1.actualMeasurement;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ActualMeasurement.TaskDistributionRequest;
import com.jpmsoft.common.model.request.Qs.QsRecordRequest;
import com.jpmsoft.common.model.response.ActualMeasurement.TaskDistributionCheckModel;
import com.jpmsoft.projectConsumer.feign.ActualMeasurementFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "任务管理", value = "任务管理")
@RequestMapping("/v1/taskDistribution")
public class TaskDistributionController {

    @Resource
    private ActualMeasurementFeign actualMeasurementFeign;


    @ApiOperation(value = "创建或更新任务分配和检查")
    @PostMapping("/createOrUpdateTaskDistributionAndCheck")
    public BaseResponse createOrUpdateTaskDistributionAndCheck(@RequestBody TaskDistributionRequest request) {
        log.info("createOrUpdateTaskDistributionAndCheck");
        return actualMeasurementFeign.createOrUpdateTaskDistributionAndCheck(request);
    }

    // 新授权模型的空任务初始化接口：批量创建图纸任务主表并写入可操作用户集合。
    // 不生成检查点，避免沿用旧接口时触发检查明细整体重建，保证旧点位数据不受影响。
    @PostMapping("/batchCreateTaskDistributionByDmIds")
    public BaseResponse batchCreateTaskDistributionByDmIds(@RequestBody TaskDistributionRequest request) {
        log.info("batchCreateTaskDistributionByDmIds");
        return actualMeasurementFeign.batchCreateTaskDistributionByDmIds(request);
    }

    @PostMapping("/createOrUpdateTaskDistributionCheckNew")
    public BaseResponse createOrUpdateTaskDistributionCheckNew(@RequestBody List<TaskDistributionCheckModel> request) {
        log.info("createOrUpdateTaskDistributionCheckNew");
        return actualMeasurementFeign.createOrUpdateTaskDistributionCheckNew(request);
    }

    @PostMapping("/createOrUpdateTaskDistributionCheckSingle")
    public BaseResponse createOrUpdateTaskDistributionCheckSingle(@RequestBody TaskDistributionCheckModel request) {
        log.info("createOrUpdateTaskDistributionCheckSingle");
        return actualMeasurementFeign.createOrUpdateTaskDistributionCheckSingle(request);
    }

    @ApiOperation(value = "通过任务分配ID查询任务分配")
    @GetMapping("/getTaskDistributionByTdId")
    public BaseResponse getTaskDistributionByTdId(@RequestParam String tdId) {
        log.info("getTaskDistributionByTdId");
        return actualMeasurementFeign.getTaskDistributionByTdId(tdId);
    }


    @ApiOperation(value = "查询任务分配列表")
    @PostMapping("/getTaskDistributionList")
    public BaseResponse getTaskDistributionList(@RequestBody TaskDistributionRequest request,@RequestHeader String token) {
        log.info("getTaskDistributionList");
        return actualMeasurementFeign.getTaskDistributionList(request,token);
    }

    @ApiOperation(value = "删除任务分配")
    @GetMapping("/deleteTaskDistributionByTdId")
    public BaseResponse deleteTaskDistributionByTdId(@RequestParam String tdId) {
        log.info("deleteTaskDistributionByTdId");
        return actualMeasurementFeign.deleteTaskDistributionByTdId(tdId);
    }

    @ApiOperation(value = "删除任务分配检查项")
    @GetMapping("/deleteTaskDistributionCheckByTdId")
    public BaseResponse deleteTaskDistributionCheckByTdId(@RequestParam String tdId) {
        log.info("deleteTaskDistributionCheckByTdId");
        return actualMeasurementFeign.deleteTaskDistributionCheckByTdId(tdId);
    }


    @PostMapping("/backWriteTaskDistributionCheck")
    public BaseResponse backWriteTaskDistributionCheck(@RequestBody TaskDistributionCheckModel request) {
        log.info("backWriteTaskDistributionCheck");
        return actualMeasurementFeign.backWriteTaskDistributionCheck(request);
    }

    @GetMapping("/getTaskDistributionCheckByCiId")
    public BaseResponse getTaskDistributionCheckByCiId(@RequestParam String ciId) {
        log.info("getTaskDistributionCheckByCiId");
        return actualMeasurementFeign.getTaskDistributionCheckByCiId(ciId);
    }

    @GetMapping("/getTaskDistributionCheckByDiId")
    public BaseResponse getTaskDistributionCheckByDiId(@RequestParam String diId) {
        log.info("getTaskDistributionCheckByDiId");
        return actualMeasurementFeign.getTaskDistributionCheckByDiId(diId);
    }

    @PostMapping("/getTaskDistributionCheckList")
    public BaseResponse getTaskDistributionCheckList(@RequestBody TaskDistributionCheckModel request,@RequestHeader String token) {
        log.info("getTaskDistributionCheckList");
        return actualMeasurementFeign.getTaskDistributionCheckList(request,token);
    }

    @PostMapping("/getTaskDistributionCheckByDmId")
    public BaseResponse getTaskDistributionCheckByDmId(@RequestBody TaskDistributionCheckModel request) {
        log.info("getTaskDistributionCheckByDmId");
        return actualMeasurementFeign.getTaskDistributionCheckByDmId(request);
    }

    @PostMapping("/getTaskDistributionCheckByCreateTimeNew")
    public BaseResponse getTaskDistributionCheckByCreateTimeNew(@RequestBody TaskDistributionCheckModel request) {
        log.info("getTaskDistributionCheckByCreateTimeNew");
        return actualMeasurementFeign.getTaskDistributionCheckByCreateTimeNew(request);
    }

    @GetMapping("/getTaskDistributionByDiId")
    public BaseResponse getTaskDistributionByDiId(@RequestParam String diId) {
        log.info("getTaskDistributionByDiId");
        return actualMeasurementFeign.getTaskDistributionByDiId(diId);
    }

    @GetMapping("/getTaskDistributionByDmId")
    public BaseResponse getTaskDistributionByDmId(@RequestParam String dmId) {
        log.info("getTaskDistributionByDmId");
        return actualMeasurementFeign.getTaskDistributionByDmId(dmId);
    }

    @PostMapping("/generateQualityCheckReport")
    public BaseResponse generateQualityCheckReport(@RequestBody QsRecordRequest request,@RequestHeader String token) {
        log.info("generateQualityCheckReport");
        return actualMeasurementFeign.generateQualityCheckReport(request,token);
    }
}
