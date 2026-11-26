package com.jpmsoft.projectConsumer.controller.v1.constructionProcedure;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.CpFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/4/10 18:14
 */
@Slf4j
@RestController
@Api(tags = "成果多次办理", value = "成果多次办理")
@RequestMapping("/v1/ConstructionProcedure/ProjectCpRpr")
public class ProjectCpRprController {

    @Resource
    private CpFeign cpFeign;

    @ApiOperation(value = "审批并结束建设程序，同步修改进度计划状态")
    @GetMapping("/approve2SyncGanttTaskState")
    public BaseResponse approve2SyncGanttTaskState(@RequestParam String id) {
        log.info("approve2SyncGanttTaskState");
        return cpFeign.approve2SyncGanttTaskState(id);
    }

    /**
     * 进度计划变更 判断进度计划节点是否可以移除
     * 如果有办理记录则不允许移除
     *
     * @param pId
     * @param cplId
     * @return
     */
    @ApiOperation(value = "进度计划变更 判断进度计划节点是否可以移除 true可以 false不可以")
    @GetMapping("/canToDeleteGanttTaskChange")
    public BaseResponse canToDeleteGanttTaskChange(@RequestParam String pId, @RequestParam String cplId) {
        log.info("canToDeleteGanttTaskChange");
        return cpFeign.canToDeleteGanttTaskChange(pId, cplId);
    }


}
