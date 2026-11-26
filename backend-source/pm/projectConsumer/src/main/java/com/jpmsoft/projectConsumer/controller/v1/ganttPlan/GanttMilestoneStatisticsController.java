package com.jpmsoft.projectConsumer.controller.v1.ganttPlan;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ganttPlan.GanttMilestoneStatisticsRequest;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import feign.Response;
import com.jpmsoft.projectConsumer.pojo.dto.ganttPlan.MonthlyReportDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author tianhelin
 * @date 2025/5/26
 */
@Slf4j
@RestController
@Api(tags = "里程碑计划汇总", value = "里程碑计划汇总")
@RequestMapping("/v1/ganttMilestoneStatistics")
public class GanttMilestoneStatisticsController {
    @Resource
    private BaseFeign baseFeign;

    /**
     * 获取计划管理列表
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "计划管理列表分页查询")
    @PostMapping("/getGanttMilestoneList")
    public BaseResponse getGanttMilestoneList(@RequestBody GanttMilestoneStatisticsRequest request, @RequestHeader String token) {
        log.info("getGanttMilestoneList");
        return baseFeign.getGanttMilestoneList(request, token);
    }

    /**
     * 查询计划管理信息
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "计划管理条件信息查询")
    @PostMapping("/getGanttMilestoneClassInfo")
    public BaseResponse getGanttMilestoneClassInfo(@RequestBody GanttMilestoneStatisticsRequest request, @RequestHeader String token) {
        log.info("getGanttMilestoneClassInfo");
        return baseFeign.getGanttMilestoneClassInfo(request, token);
    }

    /**
     * 导出年度收尾计划列表
     *
     * @param request
     * @param token
     * @return
     */
    @ApiOperation(value = "导出年度收尾计划列表")
    @PostMapping("/ganttMilestoneListExportExcel")
    public void ganttMilestoneListExportExcel(HttpServletResponse response, @RequestBody GanttMilestoneStatisticsRequest request, @RequestHeader String token) {
        log.info("ganttMilestoneListExportExcel");
        Response feignResponse = baseFeign.ganttMilestoneListExportExcel(request, token);
        Response.Body body = feignResponse.body();
        try (InputStream inputStream = body.asInputStream();
             OutputStream outputStream = response.getOutputStream()) {
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, feignResponse.headers().get(HttpHeaders.CONTENT_DISPOSITION).stream().findFirst().get());
            response.setContentType(feignResponse.headers().get(HttpHeaders.CONTENT_TYPE).stream().findFirst().get());
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            log.error("ganttMilestoneListExportExcel error");
        }
    }

    /**
     * 查询计划管理信息
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "查询计划管理月报信息")
    @PostMapping("/getMonthlyReport")
    public BaseResponse getMonthlyReport(@RequestBody MonthlyReportDTO request, @RequestHeader String token) {
        log.info("getMonthlyReport");
        return baseFeign.getMonthlyReport(request, token);
    }
}
