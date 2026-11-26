package com.jpmsoft.projectConsumer.controller.v1.ganttPlan;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.GanttPlanFegin;
import com.jpmsoft.projectConsumer.request.ganttplan.PreProjectPage;
import com.jpmsoft.projectConsumer.request.ganttplan.TasksInfoRequest;
import feign.Response;
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


@Slf4j
@RestController
@Api(tags = "临期逾期", value = "临期逾期")
@RequestMapping("/plan/v1/ganttPlanInfo")
public class GanttPlanListController {

    @Resource
    private GanttPlanFegin ganttPlanFegin;

    @ApiOperation(value = "查询项目信息，返回临期逾期数")
    @PostMapping("/getPreprojectInfo")
    public BaseResponse createOrUpdateGanttPlanTemplate(@RequestBody PreProjectPage page, @RequestHeader String token) {
        log.info("getPreProjectList");
        return ganttPlanFegin.getPreProjectList(page, token);
    }

    @ApiOperation(value = "查询项目信息多条件筛选")
    @PostMapping("/getPreprojectClassInfo")
    public BaseResponse getPreprojectClassInfo(@RequestBody PreProjectPage page,@RequestHeader String token) {
        return ganttPlanFegin.getPreprojectClassInfo(page, token);
    }

    @ApiOperation(value = "查询临期逾期总数，类型")
    @PostMapping("/getTaskImportance")
    public BaseResponse getTaskImportance(@RequestBody PreProjectPage page, @RequestHeader String token) {
        log.info("getTaskImportance");
        return ganttPlanFegin.getTaskImportance(page, token);
    }

    @ApiOperation(value = "查询逾期临期任务列表分页")
    @PostMapping("/getTasksOverdueDueInfo")
    public BaseResponse getTasksOverdueDueInfo(@RequestBody TasksInfoRequest request, @RequestHeader String token) {
        log.info("getTasksOverdueDueInfo");
        return ganttPlanFegin.getTasksOverdueDueInfo(request, token);
    }

    @ApiOperation(value = "查询逾期临期任务列表多条件筛选")
    @PostMapping("/getTasksOverdueDueClassInfo")
    public BaseResponse getTasksClassInfo(@RequestBody TasksInfoRequest request, @RequestHeader String token) {
        log.info("getTasksOverdueDueClassInfo");
        return ganttPlanFegin.getTasksOverdueDueClassInfo(request, token);
    }

    @ApiOperation("导出逾期临期任务列表Excel")
    @PostMapping("/exportTasksOverdueDueInfoExcel")
    public void exportTasksOverdueDueInfoExcel(HttpServletResponse response, @RequestBody TasksInfoRequest request, @RequestHeader String token) {
        log.info("exportTasksOverdueDueInfoExcel");
        Response feignResponse = ganttPlanFegin.exportTasksOverdueDueInfoExcel(request, token);
        Response.Body body = feignResponse.body();
        try (InputStream inputStream = body.asInputStream();
             OutputStream outputStream = response.getOutputStream()) {
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, feignResponse.headers().get(HttpHeaders.CONTENT_DISPOSITION).stream().findFirst().get());
            response.setContentType(feignResponse.headers().get(HttpHeaders.CONTENT_TYPE).stream().findFirst().get());
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            log.error("exportTasksOverdueDueInfoExcel error");
        } finally {
            try {
                body.close();
            } catch (IOException e) {
                log.error("exportTasksOverdueDueInfoExcel close error");
            }
        }
    }

    @ApiOperation("导出计划看板列表Excel")
    @PostMapping("/exportPreprojectInfoExcel")
    public void exportPreprojectInfoExcel(HttpServletResponse response, @RequestBody PreProjectPage request, @RequestHeader String token) {
        log.info("exportPreprojectInfoExcel");
        Response feignResponse = ganttPlanFegin.exportPreprojectInfoExcel(request, token);
        Response.Body body = feignResponse.body();
        try (InputStream inputStream = body.asInputStream();
             OutputStream outputStream = response.getOutputStream()) {
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, feignResponse.headers().get(HttpHeaders.CONTENT_DISPOSITION).stream().findFirst().get());
            response.setContentType(feignResponse.headers().get(HttpHeaders.CONTENT_TYPE).stream().findFirst().get());
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            log.error("exportPreprojectInfoExcel error");
        } finally {
            try {
                body.close();
            } catch (IOException e) {
                log.error("exportPreprojectInfoExcel close error");
            }
        }
    }
}
