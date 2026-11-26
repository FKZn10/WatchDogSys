package com.jpmsoft.projectProducer.controller.v1.maintenance;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Maintenance.MaintenanceRecordRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.maintenance.IMaintenanceRecordService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Slf4j
@RestController
@RequestMapping("/v1/maintenanceRecord")
public class MaintenanceRecordController {

    @Resource
    private IMaintenanceRecordService maintenanceRecordService;
    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdateMaintenanceRecord")
    public BaseResponse createOrUpdateMaintenanceRecord(@RequestBody MaintenanceRecordRequest request) {
        log.info("createOrUpdateMaintenanceRecord");
        return maintenanceRecordService.createOrUpdateMaintenanceRecord(request);
    }

    @TenantId
    @PostMapping("/getMaintenanceRecordList")
    public BaseResponse getMaintenanceRecordList(@RequestBody MaintenanceRecordRequest request, @RequestHeader String token) {
        log.info("getMaintenanceRecordList");
        return maintenanceRecordService.getMaintenanceRecordList(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getMaintenanceRecordListNoAuth")
    public BaseResponse getMaintenanceRecordListNoAuth(@RequestBody MaintenanceRecordRequest request) {
        log.info("getMaintenanceRecordListNoAuth");
        return maintenanceRecordService.getMaintenanceRecordListNoAuth(request);
    }

    /**
     * 广工定制权限，内部不鉴权 外部审批人与施工单位节点同单位人员可见
     *
     * @param request
     * @param token
     * @return
     */
    @TenantId
    @PostMapping("/getMaintenanceRecordGgList")
    public BaseResponse getMaintenanceRecordGgList(@RequestBody MaintenanceRecordRequest request, @RequestHeader String token) {
        log.info("getMaintenanceRecordGgList");
        return maintenanceRecordService.getMaintenanceRecordGgList(request, redisUtils.getUserInfoByToken(token));
    }


    @GetMapping("/getMaintenanceRecordById")
    public BaseResponse getMaintenanceRecordById(@RequestParam String mrId) {
        log.info("getMaintenanceRecordById");
        return maintenanceRecordService.getMaintenanceRecordById(mrId);
    }

    @TenantId
    @PostMapping("/exportMaintenanceRecordGgExcel")
    public void exportMaintenanceRecordGgExcel(HttpServletResponse response, @RequestBody MaintenanceRecordRequest request, @RequestHeader String token) {
        log.info("exportMaintenanceRecordGgExcel");
        try {
            Workbook workbook = maintenanceRecordService.exportMaintenanceRecordGgExcel(request, redisUtils.getUserInfoByToken(token));
            String fileName = URLEncoder.encode("零星维修申报表.xls", "UTF-8");
            // 告诉浏览器用什么软件可以打开此文件
            response.setHeader("content-Type", "application/vnd.ms-excel");
            // 下载文件的默认名称
            response.setHeader("Content-Disposition", fileName);
            //编码
            response.setCharacterEncoding("UTF-8");
            //多sheet
            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (IOException e) {
            log.error("零星维修申报表导出失败{}", e.getMessage(), e);
        }
    }

    @GetMapping("/refreshRepairStateGg")
    public BaseResponse refreshRepairStateGg(@RequestParam("mrId") String mrId) {
        log.info("refreshRepairStateGg");
        return maintenanceRecordService.refreshRepairStateGg(mrId);
    }

    /**
     * 全量刷新广工零星维修记录的维修状态。
     *
     * @return 全量刷新结果
     */
    @GetMapping("/refreshRepairStateGgAll")
    public BaseResponse refreshRepairStateGgAll() {
        log.info("refreshRepairStateGgAll");
        return maintenanceRecordService.refreshRepairStateGgAll();
    }


    // 根据零星维修id去查责任人
    @GetMapping("/getRepairPersonByMrId")
    public BaseResponse getRepairPersonByMrId(@RequestParam("mrId") String mrId) {
        log.info("getRepairPersonByMrId");
        return maintenanceRecordService.getRepairPersonByMrId(mrId);
    }
}
