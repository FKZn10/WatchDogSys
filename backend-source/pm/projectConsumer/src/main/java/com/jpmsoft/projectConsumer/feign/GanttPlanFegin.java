package com.jpmsoft.projectConsumer.feign;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.request.ganttplan.PreProjectPage;
import com.jpmsoft.projectConsumer.request.ganttplan.TasksInfoRequest;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "project-producer", contextId = "ganttPlan")
public interface GanttPlanFegin {
    /***********************获取项目临期或逾期*************************/
    @PostMapping("/v1/ganttPlan/getPreprojectInfo")
    BaseResponse getPreProjectList(@RequestBody PreProjectPage page, @RequestHeader String token);

    @PostMapping("/v1/ganttPlan/getPreprojectClassInfo")
    BaseResponse getPreprojectClassInfo(@RequestBody PreProjectPage page, @RequestHeader String token);

    /***********************资金计划表*************************/
    @PostMapping("/v1/ganttPlan/getTaskImportance")
    BaseResponse getTaskImportance(@RequestBody PreProjectPage page, @RequestHeader String token);

    @PostMapping("/v1/ganttPlan/getTasksOverdueDueInfo")
    BaseResponse getTasksOverdueDueInfo(@RequestBody TasksInfoRequest request, @RequestHeader String token);

    @PostMapping("/v1/ganttPlan/getTasksOverdueDueClassInfo")
    BaseResponse getTasksOverdueDueClassInfo(@RequestBody TasksInfoRequest request, @RequestHeader String token);

    @PostMapping("/v1/ganttPlan/exportTasksOverdueDueInfoExcel")
    Response exportTasksOverdueDueInfoExcel(@RequestBody TasksInfoRequest request, @RequestHeader String token);

    @PostMapping("/v1/ganttPlan/exportPreprojectInfoExcel")
    Response exportPreprojectInfoExcel(@RequestBody PreProjectPage request, @RequestHeader String token);
}

