package com.jpmsoft.projectProducer.controller.v1.constructionProcedure;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.proCp.ProjectCpChangeDTO;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.constructionProcedure.IProjectCpChangeService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
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
@RequestMapping("/v1/ConstructionProcedure/proCpChange")
public class ProjectCpChangeController {

    @Resource
    private IProjectCpChangeService projectCpChangeService;
    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdateProjectCpChange")
    public BaseResponse createOrUpdateProjectCpChange(@RequestBody ProjectCpChangeDTO request) {
        log.info("createOrUpdateProjectCpChange");
        return projectCpChangeService.createOrUpdateProjectCpChange(request);
    }

    @TenantId
    @PostMapping("/getProCpChangeList")
    public BaseResponse getProCpChangeList(@RequestBody ProjectCpChangeDTO request, @RequestHeader String token) {
        log.info("getProCpChangeList");
        return projectCpChangeService.getProCpChangeList(request, redisUtils.getUid(token));
    }

    /**
     * ID删除记录
     *
     * @param id
     * @return
     */
    @GetMapping("/deleteProCpChangeById")
    public BaseResponse deleteProCpChangeById(@RequestParam String id) {
        log.info("deleteProCpChangeById");
        return projectCpChangeService.deleteProCpChangeById(id);
    }


    @GetMapping("/getProjectCpChangeById")
    public BaseResponse getProjectCpChangeById(@RequestParam String id) {
        log.info("getProjectCpChangeById");
        return projectCpChangeService.getProjectCpChangeById(id);
    }

    @GetMapping("/getProjectCpChangeCplIdList")
    public BaseResponse getProjectCpChangeCplIdList(@RequestParam String id) {
        return projectCpChangeService.getProjectCpChangeCplIdList(id);
    }

    @GetMapping("/approve2SyncProjectCpDtl")
    public BaseResponse approve2SyncProjectCpDtl(@RequestParam String id) {
        return projectCpChangeService.approve2SyncProjectCpDtl(id);
    }

    @GetMapping("/getProCpChangeDtlTreeById")
    public BaseResponse getProCpChangeDtlTreeById(@RequestParam String id, @RequestHeader String token) {
        return projectCpChangeService.getProCpChangeDtlTreeById(id, redisUtils.getTeId(token));
    }

}
