package com.jpmsoft.projectProducer.controller.v1.constructionProcedure;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.service.constructionProcedure.IProjectCpRprService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/4/10 18:14
 */
@Slf4j
@RestController
@RequestMapping("/v1/ConstructionProcedure/ProjectCpRpr")
public class ProjectCpRprController {

    @Resource
    private IProjectCpRprService projectCpRprService;

    @GetMapping("/approve2SyncGanttTaskState")
    public BaseResponse approve2SyncGanttTaskState(@RequestParam String id) {
        log.info("approve2SyncGanttTaskState");
        return projectCpRprService.approve2SyncGanttTaskState(id);
    }

    /**
     * 进度计划变更 判断进度计划节点是否可以移除
     * 如果有办理记录则不允许移除
     *
     * @param pId
     * @param cplId
     * @return
     */
    @GetMapping("/canToDeleteGanttTaskChange")
    public BaseResponse canToDeleteGanttTaskChange(@RequestParam String pId, @RequestParam String cplId) {
        log.info("canToDeleteGanttTaskChange");
        return projectCpRprService.canToDeleteGanttTaskChange(pId, cplId);
    }


}
