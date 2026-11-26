package com.jpmsoft.projectProducer.controller.v1.ganttPlan;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ganttPlan.GanttMilestoneStatisticsRequest;
import com.jpmsoft.projectProducer.pojo.dto.ganttPlan.MonthlyReportDTO;
import com.jpmsoft.projectProducer.service.ganttPlan.GanttMilestoneStatisticsService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * @author tianhelin
 * @date 2025/5/23
 */
@Slf4j
@RestController
@RequestMapping("/v1/ganttMilestoneStatistics")
public class GanttMilestoneStatisticsController {
    @Resource
    private GanttMilestoneStatisticsService ganttMilestoneStatisticsService;
    @Resource
    private RedisUtils redisUtils;

    /**
     * 获取计划管理列表
     *
     * @param request
     * @param token
     * @return
     */
    @PostMapping("/getGanttMilestoneList")
    public BaseResponse getGanttMilestoneList(@RequestBody GanttMilestoneStatisticsRequest request, @RequestHeader String token) {
        log.info("getGanttMilestoneList");
        return ganttMilestoneStatisticsService.getGanttMilestoneList(request, redisUtils.getUid(token));
    }

    /**
     * 查询计划管理信息
     *
     * @param request
     * @param token
     * @return
     */
    @PostMapping("/getGanttMilestoneClassInfo")
    public BaseResponse getGanttMilestoneClassInfo(@RequestBody GanttMilestoneStatisticsRequest request, @RequestHeader String token) {
        log.info("getGanttMilestoneClassInfo");
        return ganttMilestoneStatisticsService.getGanttMilestoneClassInfo(request, redisUtils.getUid(token));
    }

    /**
     * 导出年度收尾计划列表
     *
     * @param request
     * @param token
     * @return
     */
    @PostMapping("/ganttMilestoneListExportExcel")
    public void ganttMilestoneListExportExcel(HttpServletResponse response, @RequestBody GanttMilestoneStatisticsRequest request, @RequestHeader String token) {
        log.info("ganttMilestoneListExportExcel");
        try {
            Workbook workbook = ganttMilestoneStatisticsService.ganttMilestoneListExportExcel(request, redisUtils.getUid(token));
            String fileName = URLEncoder.encode("年度收尾计划报表.xls", "UTF-8");
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
            log.error("年度收尾计划报表导出失败{}", e.getMessage(), e);
        }
    }

    /**
     * 查询计划管理月报信息
     *
     * @param request
     * @param token
     * @return
     */
    @PostMapping("/getMonthlyReport")
    public BaseResponse getMonthlyReport(@RequestBody MonthlyReportDTO request, @RequestHeader String token) {
        log.info("getMonthlyReport");
        return ganttMilestoneStatisticsService.getMonthlyReport(request, redisUtils.getUid(token));
    }
}
