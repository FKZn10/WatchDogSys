package com.jpmsoft.projectProducer.controller.v1.ganttPlan;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.request.ganttplan.PreProjectPage;
import com.jpmsoft.projectProducer.pojo.request.ganttplan.TasksInfoRequest;
import com.jpmsoft.projectProducer.service.ganttPlan.IGanttPlanService;
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
@RequestMapping("/v1/ganttPlan")
public class GanttPlanListController {
    @Resource
    private IGanttPlanService ganttPlanService;

    @Resource
    private RedisUtils redisUtils;

    @PostMapping("/getPreprojectInfo")
    public BaseResponse getPreProjectList(@RequestBody PreProjectPage page,@RequestHeader String token) {
        return ganttPlanService.getPreprojectInfo(redisUtils.getUid(token),page);
    }

    @PostMapping("/getPreprojectClassInfo")
    public BaseResponse getPreprojectClassInfo(@RequestBody PreProjectPage page,@RequestHeader String token) {
        return ganttPlanService.getPreprojectClassInfo(redisUtils.getUid(token),page);
    }

    @PostMapping("/getTaskImportance")
    public BaseResponse getTaskImportance(@RequestBody PreProjectPage page,@RequestHeader String token) {
        log.info("method getTaskImportance token" + token);
        return ganttPlanService.getTaskImportance(redisUtils.getUid(token),page);
    }
    @PostMapping("/getTasksOverdueDueInfo")
    public BaseResponse getTasksOverdueDueInfo(@RequestBody TasksInfoRequest request, @RequestHeader String token){
        log.info("method getTasksInfo TasksInfoRequest request" + request);
        return ganttPlanService.getTasksInfo(redisUtils.getUid(token),request);
    }

    @PostMapping("/getTasksOverdueDueClassInfo")
    public BaseResponse getTasksClassInfo(@RequestBody TasksInfoRequest request, @RequestHeader String token) {
        log.info("getTasksOverdueDueClassInfo");
        return ganttPlanService.getTasksClassInfo(redisUtils.getUid(token), request);
    }

    @PostMapping("/exportTasksOverdueDueInfoExcel")
    public void exportTasksOverdueDueInfoExcel(HttpServletResponse response, @RequestBody TasksInfoRequest request, @RequestHeader String token) {
        log.info("exportTasksOverdueDueInfoExcel");
        try {
            Workbook workbook = ganttPlanService.exportTasksOverdueDueInfoExcel(redisUtils.getUid(token), request);
            String fileName = URLEncoder.encode("逾期临期任务表.xls", "UTF-8");
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
            log.error("逾期临期任务列表导出失败{}", e.getMessage(), e);
        }
    }

    @PostMapping("/exportPreprojectInfoExcel")
    public void exportPreprojectInfoExcel(HttpServletResponse response, @RequestBody PreProjectPage request, @RequestHeader String token) {
        log.info("exportPreprojectInfoExcel");
        try {
            Workbook workbook = ganttPlanService.exportPreprojectInfoExcel(redisUtils.getUid(token), request);
            String fileName = URLEncoder.encode("进度计划表.xls", "UTF-8");
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
            log.error("进度计划列表导出失败{}", e.getMessage(), e);
        }
    }
}
