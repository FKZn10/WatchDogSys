package com.jpmsoft.projectConsumer.controller.v1.constructionProcedure;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.proCp.ProjectCpWorkCommentDTO;
import com.jpmsoft.projectConsumer.feign.CpFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/1/9 12:19
 */
@Slf4j
@RestController
@Api(tags = "工作办理记录评论", value = "工作办理记录评论")
@RequestMapping("/v1/ConstructionProcedure/proCpWorkComment")
public class ProjectCpWorkCommentController {
    @Resource
    private CpFeign cpFeign;

    @ApiOperation(value = "创建或修改工作办理记录评论")
    @PostMapping("/createOrUpdateWorkComment")
    public BaseResponse createOrUpdateWorkComment(@RequestBody ProjectCpWorkCommentDTO request) {
        log.info("createOrUpdateWorkComment");
        return cpFeign.createOrUpdateWorkComment(request);
    }

    @ApiOperation(value = "分页工作办理记录评论")
    @PostMapping("/getWorkCommentList")
    public BaseResponse getWorkCommentList(@RequestBody ProjectCpWorkCommentDTO request) {
        log.info("getWorkCommentList");
        return cpFeign.getWorkCommentList(request);
    }

    @ApiOperation(value = "ID删除")
    @GetMapping("/deleteById")
    public BaseResponse deleteById(@RequestParam String id) {
        log.info("deleteById");
        return cpFeign.deleteById(id);
    }
}
