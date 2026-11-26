package com.jpmsoft.projectProducer.controller.v1.constructionProcedure;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.proCp.ProjectCpWorkCommentDTO;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.constructionProcedure.IProjectCpWorkCommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/1/9 12:19
 */
@Slf4j
@RestController
@RequestMapping("/v1/ConstructionProcedure/proCpWorkComment")
public class ProjectCpWorkCommentController {
    @Resource
    private IProjectCpWorkCommentService projectCpWorkCommentService;


    @TenantId
    @PostMapping("/createOrUpdateWorkComment")
    public BaseResponse createOrUpdateWorkComment(@RequestBody ProjectCpWorkCommentDTO request) {
        log.info("createOrUpdateWorkComment");
        return projectCpWorkCommentService.createOrUpdateWorkComment(request);
    }

    @TenantId
    @PostMapping("/getWorkCommentList")
    public BaseResponse getWorkCommentList(@RequestBody ProjectCpWorkCommentDTO request) {
        log.info("getWorkCommentList");
        return projectCpWorkCommentService.getWorkCommentList(request);
    }

    @GetMapping("/deleteById")
    public BaseResponse deleteById(@RequestParam String id) {
        log.info("deleteById");
        return projectCpWorkCommentService.deleteById(id);
    }
}
