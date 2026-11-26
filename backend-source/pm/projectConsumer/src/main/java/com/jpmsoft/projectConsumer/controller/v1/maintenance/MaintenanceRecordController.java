package com.jpmsoft.projectConsumer.controller.v1.maintenance;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Maintenance.MaintenanceRecordRequest;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import feign.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
@RestController
@Api(tags = "维保管理-维保记录", value = "维保管理-维保记录")
@RequestMapping("/v1/maintenanceRecord")
public class MaintenanceRecordController {
    @Autowired
    private BaseFeign baseFeign;

    @ApiOperation(value = "新增或修改维保记录")
    @PostMapping("/createOrUpdateMaintenanceRecord")
    public BaseResponse createOrUpdateMaintenanceRecord(@RequestBody MaintenanceRecordRequest request) {
        log.info("createOrUpdateMaintenanceRecord");
        return baseFeign.createOrUpdateMaintenanceRecord(request);
    }

    @ApiOperation(value = "分页查询维保记录")
    @PostMapping("/getMaintenanceRecordList")
    public BaseResponse getMaintenanceRecordList(@RequestBody MaintenanceRecordRequest request, @RequestHeader String token) {
        log.info("getMaintenanceRecordList");
        return baseFeign.getMaintenanceRecordList(request, token);
    }

    @ApiOperation(value = "广工项目定制权限接口，内部不鉴权，外部用户仅审批人与施工单位审批节点同单位人员可见")
    @PostMapping("/getMaintenanceRecordGgList")
    public BaseResponse getMaintenanceRecordGgList(@RequestBody MaintenanceRecordRequest request, @RequestHeader String token) {
        log.info("getMaintenanceRecordGgList");
        return baseFeign.getMaintenanceRecordGgList(request, token);
    }

    @ApiOperation(value = "分页查询维保记录-不鉴权")
    @PostMapping("/getMaintenanceRecordListNoAuth")
    public BaseResponse getMaintenanceRecordListNoAuth(@RequestBody MaintenanceRecordRequest request) {
        log.info("getMaintenanceRecordListNoAuth");
        return baseFeign.getMaintenanceRecordListNoAuth(request);
    }


    @ApiOperation(value = "ID查询维保记录")
    @GetMapping("/getMaintenanceRecordById")
    public BaseResponse getMaintenanceRecordById(@RequestParam String mrId) {
        log.info("getMaintenanceRecordById");
        return baseFeign.getMaintenanceRecordById(mrId);
    }

    @ApiOperation(value = "零星维修申报导出（广工）")
    @PostMapping("/exportMaintenanceRecordGgExcel")
    public void exportMaintenanceRecordGgExcel(HttpServletResponse response, @RequestBody MaintenanceRecordRequest request, @RequestHeader String token) {
        log.info("exportMaintenanceRecordGgExcel");
        Response feignResponse = baseFeign.exportMaintenanceRecordGgExcel(request, token);
        Response.Body body = feignResponse.body();
        try (InputStream inputStream = body.asInputStream();
             OutputStream outputStream = response.getOutputStream()) {
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, feignResponse.headers().get(HttpHeaders.CONTENT_DISPOSITION).stream().findFirst().get());
            response.setContentType(feignResponse.headers().get(HttpHeaders.CONTENT_TYPE).stream().findFirst().get());
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            log.error("exportMaintenanceRecordGgExcel error");
        } finally {
            try {
                body.close();
            } catch (IOException e) {
                log.error("exportMaintenanceRecordGgExcel close error");
            }
        }
    }

    @ApiOperation("根据流程刷新零星维修维修状态（广工）")
    @GetMapping("/refreshRepairStateGg")
    public BaseResponse refreshRepairStateGg(@RequestParam("mrId") String mrId) {
        log.info("refreshRepairStateGg");
        return baseFeign.refreshRepairStateGg(mrId);
    }

    /**
     * 根据流程节点全量刷新广工零星维修状态。
     *
     * @return 全量刷新结果
     */
    @ApiOperation("根据流程全量刷新零星维修维修状态（广工）")
    @GetMapping("/refreshRepairStateGgAll")
    public BaseResponse refreshRepairStateGgAll() {
        log.info("refreshRepairStateGgAll");
        return baseFeign.refreshRepairStateGgAll();
    }

    @ApiOperation("根据维修记录id查责任人（广工）")
    @GetMapping("/getRepairPersonByMrId")
    public BaseResponse getRepairPersonByMrId(@RequestParam("mrId") String mrId) {
        log.info("getRepairPersonByMrId");
        return baseFeign.getRepairPersonByMrId(mrId);
    }
}
