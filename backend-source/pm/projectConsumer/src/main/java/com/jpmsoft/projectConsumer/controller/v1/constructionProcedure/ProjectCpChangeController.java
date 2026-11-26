package com.jpmsoft.projectConsumer.controller.v1.constructionProcedure;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.proCp.ProjectCpChangeDTO;
import com.jpmsoft.projectConsumer.feign.CpFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 项目建设程序变更
 *
 * @Author: 李文涛 aaron
 * @date: 2023/9/5 11:08
 */
@Slf4j
@RestController
@Api(tags = "项目建设程序变更", value = "项目建设程序变更")
@RequestMapping("/v1/ConstructionProcedure/proCpChange")
public class ProjectCpChangeController {

    @Resource
    private CpFeign cpFeign;

    @ApiOperation(value = "创建或修改项目建设程序变更（附带项目建设程序变更详情）")
    @PostMapping("/createOrUpdateProjectCpChange")
    public BaseResponse createOrUpdateProjectCpChange(@RequestBody ProjectCpChangeDTO request) {
        log.info("createOrUpdateProjectCpChange");
        return cpFeign.createOrUpdateProjectCpChange(request);
    }


    @ApiOperation(value = "获取项目建设程序变更列表")
    @PostMapping("/getProCpChangeList")
    public BaseResponse getProCpChangeList(@RequestBody ProjectCpChangeDTO request, @RequestHeader String token) {
        log.info("getProCpChangeList");
        return cpFeign.getProCpChangeList(request, token);
    }

    /**
     * ID删除记录
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "ID删除项目建设程序变更")
    @GetMapping("/deleteProCpChangeById")
    public BaseResponse deleteProCpChangeById(@RequestParam String id) {
        log.info("deleteProCpChangeById");
        return cpFeign.deleteProCpChangeById(id);
    }

    @ApiOperation(value = "ID查询项目建设程序变更")
    @GetMapping("/getProjectCpChangeById")
    public BaseResponse getProjectCpChangeById(@RequestParam String id) {
        log.info("getProjectCpChangeById");
        return cpFeign.getProjectCpChangeById(id);
    }

    @ApiOperation(value = "查询选中的程序库ID集合(新增时id=建设程序Id,修改时id=建设程序变更id)")
    @GetMapping("/getProjectCpChangeCplIdList")
    public BaseResponse getProjectCpChangeCplIdList(@RequestParam String id) {
        log.info("getProjectCpChangeCplIdList");
        return cpFeign.getProjectCpChangeCplIdList(id);
    }


    @ApiOperation(value = "审批通过同步新增取消数据，同时重设上级状态（办理中，已完成）")
    @GetMapping("/approve2SyncProjectCpDtl")
    public BaseResponse approve2SyncProjectCpDtl(@RequestParam String id) {
        log.info("approve2SyncProjectCpDtl");
        return cpFeign.approve2SyncProjectCpDtl(id);
    }

    @ApiOperation(value = "ID查询变更详情树")
    @GetMapping("/getProCpChangeDtlTreeById")
    public BaseResponse getProCpChangeDtlTreeById(@RequestParam String id) {
        log.info("getProCpChangeDtlTreeById");
        return cpFeign.getProCpChangeDtlTreeById(id);
    }
}
