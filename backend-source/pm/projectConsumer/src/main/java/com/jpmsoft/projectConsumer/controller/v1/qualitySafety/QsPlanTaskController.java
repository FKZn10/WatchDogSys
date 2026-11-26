package com.jpmsoft.projectConsumer.controller.v1.qualitySafety;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Qs.QsPlanTaskQsRecordTypeRequest;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import com.jpmsoft.projectConsumer.pojo.dto.qualitySafety.ProSafetyLedgerDtlDTO;
import com.jpmsoft.projectConsumer.pojo.dto.qualitySafety.QsPlanTaskDto;
import com.jpmsoft.projectConsumer.pojo.dto.qualitySafety.QsRecordDto;
import feign.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author: 李文涛 aaron
 * @date: 2022/9/20 15:13
 */
@Slf4j
@RestController
@Api(tags = "巡检任务", value = "巡检任务")
@RequestMapping("/safety/v1/qsPlanTask")
public class QsPlanTaskController {
    @Resource
    private BaseFeign baseFeign;

    /**
     * 新增修改
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "创建或修改巡检任务")
    @PostMapping("/createOrUpdateQsPlanTask")
    public BaseResponse createOrUpdateQsPlanTask(@RequestBody QsPlanTaskDto request) {
        log.info("createOrUpdateQsPlanTask");
        return baseFeign.createOrUpdateQsPlanTask(request);
    }

    /**
     * 维护部分指定信息
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "巡检任务维护部分指定信息")
    @PostMapping("/updateQsPlanTaskPartialInfo")
    public BaseResponse updateQsPlanTaskPartialInfo(@RequestBody QsPlanTaskDto request) {
        log.info("updateQsPlanTaskPartialInfo");
        return baseFeign.updateQsPlanTaskPartialInfo(request);
    }

    @ApiOperation(value = "巡检任务更新要求整改时间")
    @PostMapping("/updateQsPlanTaskRequireTime")
    public BaseResponse updateQsPlanTaskRequireTime(@RequestBody QsPlanTaskDto request) {
        log.info("updateQsPlanTaskRequireTime");
        return baseFeign.updateQsPlanTaskRequireTime(request);
    }

    @ApiOperation(value = "分页查询巡检任务")
    @PostMapping("/getQsPlanTaskList")
    public BaseResponse getQsPlanTaskList(@RequestBody QsPlanTaskDto request, @RequestHeader String token) {
        log.info("getQsPlanTaskList");
        return baseFeign.getQsPlanTaskList(request, token);
    }
    @PostMapping("/getQsPlanTaskListExport")
    public BaseResponse getQsPlanTaskListExport(@RequestBody QsPlanTaskDto request, @RequestHeader String token) {
        log.info("getQsPlanTaskListExport");
        return baseFeign.getQsPlanTaskListExport(request, token);
    }
    @ApiOperation(value = "分页查询巡检任务(不鉴权)")
    @PostMapping("/getQsPlanTaskListNoAuth")
    public BaseResponse getQsPlanTaskListNoAuth(@RequestBody QsPlanTaskDto request) {
        log.info("getQsPlanTaskListNoAuth");
        return baseFeign.getQsPlanTaskListNoAuth(request);
    }

    @ApiOperation(value = "第三方巡检")
    @PostMapping("/getQsPlanTaskPage")
    public BaseResponse getQsPlanTaskPage(@RequestBody QsPlanTaskDto request, @RequestHeader String token) {
        log.info("getQsPlanTaskPage");
        return baseFeign.getQsPlanTaskPage(request, token);
    }

    @ApiOperation(value = "巡查报告统计台账（项目为主）")
    @PostMapping("/getProSafetyLedgerPage")
    public BaseResponse getProSafetyLedgerPage(@RequestBody ProSafetyLedgerDtlDTO request, @RequestHeader String token) {
        log.info("getProSafetyLedgerPage");
        return baseFeign.getProSafetyLedgerPage(request, token);
    }

    @PostMapping("/getProSafetyLedgerPageYW")
    public BaseResponse getProSafetyLedgerPageYW(@RequestBody ProSafetyLedgerDtlDTO request, @RequestHeader String token) {
        log.info("getProSafetyLedgerPageYW");
        return baseFeign.getProSafetyLedgerPageYW(request, token);
    }

    @ApiOperation(value = "ID查询巡检任务")
    @GetMapping("/getQsPlanTaskById")
    public BaseResponse getQsPlanTaskById(@RequestParam String id) {
        log.info("getQsPlanTaskById");
        return baseFeign.getQsPlanTaskById(id);
    }

    @ApiOperation(value = "质量安全高频问题统计")
    @PostMapping("/qualitySafetyStatistics")
    public BaseResponse qualitySafetyStatistics(@RequestBody QsRecordDto request, @RequestHeader String token) {
        return baseFeign.qualitySafetyStatistics(request, token);
    }

    @ApiOperation(value = "获取高频问题列表")
    @PostMapping("/getHighQsRecordList")
    public BaseResponse getHighQsRecordList(@RequestBody QsRecordDto request, @RequestHeader String token) {
        return baseFeign.getHighQsRecordList(request, token);
    }

    @PostMapping("/export")
    public void export(@RequestBody QsPlanTaskDto request, @RequestHeader String token, HttpServletResponse httpServletResponse) throws IOException {
        Response feignResponse = baseFeign.exportQsPlanTask(request, token);
        Response.Body body = feignResponse.body();
        try (InputStream inputStream = body.asInputStream();
             OutputStream outputStream = httpServletResponse.getOutputStream()) {
            String fileName = System.currentTimeMillis() + "-export.xlsx";
            httpServletResponse.setHeader("Content-disposition", "attachment;filename=" + fileName);
            httpServletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            IOUtils.copy(inputStream, outputStream);
            feignResponse.close();
        } catch (IOException e) {
            log.error("exportContractStampDuty  error");
        }
    }
    @PostMapping("/updateQsPlanTaskQsRecordType")
    public BaseResponse updateQsPlanTaskQsRecordType(@RequestBody QsPlanTaskQsRecordTypeRequest request) {
        log.info("updateQsPlanTaskQsRecordType");
        return baseFeign.updateQsPlanTaskQsRecordType(request);
    }

    @GetMapping("/copyHighQsRecord")
    public BaseResponse copyHighQsRecord(@RequestParam String qstId,@RequestParam String qsrId,@RequestParam String ptId,@RequestHeader String token){
        log.info("copyHighQsRecord");
        return baseFeign.copyHighQsRecord(qstId,qsrId,ptId,token);
    }

    @GetMapping("/verifyqsPlanTask")
    public BaseResponse verifyqsPlanTask(@RequestParam String pId,@RequestParam String ptType,@RequestHeader String token){
        return baseFeign.verifyqsPlanTask(pId,ptType,token);

    }
    @PostMapping("/getProSafetyLedgerPageYWNew")
    public BaseResponse getProSafetyLedgerPageYWNew(@RequestBody ProSafetyLedgerDtlDTO request, @RequestHeader String token) {
        log.info("getProSafetyLedgerPageYWNew");
        return baseFeign.getProSafetyLedgerPageYWNew(request, token);
    }
    @PostMapping("/getProSafetyLedgerPageYWExportNew")
    public BaseResponse getProSafetyLedgerPageYWExportNew(@RequestBody ProSafetyLedgerDtlDTO request, @RequestHeader String token) {
        log.info("getProSafetyLedgerPageYWExportNew");
        return baseFeign.getProSafetyLedgerPageYWExportNew(request, token);
    }

    @PostMapping("/approveData")
    public BaseResponse approveData(@RequestBody QsPlanTaskDto request) {
        log.info("approveData");
        return baseFeign.approveQsPlanTaskData(request);
    }

    @PostMapping("/refreshQsPlanTask")
    public BaseResponse refreshQsPlanTask(@RequestBody QsPlanTaskDto request) {
        log.info("refreshQsPlanTask");
        return baseFeign.refreshQsPlanTask(request);
    }

    @PostMapping("/generateSubFileRecordsAndNotifyAssignees")
    public BaseResponse generateSubFileRecordsAndNotifyAssignees(@RequestBody QsPlanTaskDto request, @RequestHeader String token) {
        log.info("generateSubFileRecordsAndNotifyAssignees");
        return baseFeign.generateSubFileRecordsAndNotifyAssignees(request, token);
    }

    @GetMapping("/delegateTodoAndNotifyCcUsers")
    public BaseResponse delegateTodoAndNotifyCcUsers(@RequestParam String id, @RequestHeader String token) {
        log.info("delegateTodoAndNotifyCcUsers");
        return baseFeign.delegateTodoAndNotifyCcUsers(id, token);
    }

    @GetMapping("/syncQsRecordState")
    public BaseResponse syncQsRecordState(@RequestParam String id) {
        log.info("syncQsRecordStatus");
        return baseFeign.syncQsRecordState(id);
    }

    @ApiOperation("安全检查分页查询（上海对外经贸）")
    @PostMapping("/getQsPlanTaskListSH")
    public BaseResponse getQsPlanTaskListSH(@RequestBody QsPlanTaskDto request, @RequestHeader String token) {
        log.info("getQsPlanTaskListSH");
        return baseFeign.getQsPlanTaskListSH(request, token);
    }
}