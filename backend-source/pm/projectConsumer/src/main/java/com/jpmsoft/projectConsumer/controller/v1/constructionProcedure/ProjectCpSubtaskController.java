package com.jpmsoft.projectConsumer.controller.v1.constructionProcedure;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.proCp.ProjectCpSubtaskRequest;
import com.jpmsoft.projectConsumer.feign.CpFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "建设程序子任务", value = "建设程序")
@RequestMapping("/v1/ConstructionProcedure/rojectCpSubtask")
public class ProjectCpSubtaskController {
    @Autowired
    private CpFeign cpFeign;


    @ApiOperation(value = "创建或修改工作办理记录")
    @PostMapping("/createOrUpdateProjectCpSubtask")
    public BaseResponse createOrUpdateProjectCpSubtask(@RequestBody ProjectCpSubtaskRequest request) {
        log.info("createOrUpdateProjectCpSubtask");
        return cpFeign.createOrUpdateProjectCpSubtask(request);
    }

    @ApiOperation(value = "ID删除工作办理记录")
    @GetMapping("/getProjectCpSubtaskById")
    public BaseResponse getProjectCpSubtaskById(@RequestParam String pcsId) {
        log.info("getProjectCpSubtaskById");
        return cpFeign.getProjectCpSubtaskById(pcsId);
    }

    @ApiOperation(value = "获取工作办理记录列表")
    @PostMapping("/getProjectCpSubtaskList")
    public BaseResponse getProjectCpSubtaskList(@RequestBody ProjectCpSubtaskRequest request) {
        log.info("getProjectCpSubtaskList");
        return cpFeign.getProjectCpSubtaskList(request);
    }
}
