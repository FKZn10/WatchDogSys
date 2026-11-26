package com.jpmsoft.projectProducer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.project.ProjectFundExecutionDTO;
import com.jpmsoft.projectProducer.service.project.ProjectFundExecutionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 基建项目资金执行明细接口。
 */
@RestController
@RequestMapping("/v1/projectFundExecution")
public class ProjectFundExecutionController {

    @Resource
    private ProjectFundExecutionService projectFundExecutionService;

    /**
     * 按项目查询基础信息、三类概算科目树及合同和自筹、国拨支付汇总。
     *
     * @param pId 项目 ID
     * @return 基建项目资金执行明细
     */
    @GetMapping("/getProjectFundExecutionByPId")
    public BaseResponse getProjectFundExecutionByPId(@RequestParam("pId") String pId) {
        return projectFundExecutionService.getProjectFundExecutionByPId(pId);
    }

    /**
     * 分页查询全部未删除且审批结束的项目资金执行统计。
     *
     * @param request 分页与项目筛选 DTO，支持 pId 和 projectIds，不传项目条件时查询全部已结束项目
     * @return 当前页完整项目统计数组及项目总数
     */
    @PostMapping("/getProjectFundExecutionList")
    public BaseResponse getProjectFundExecutionList(@RequestBody ProjectFundExecutionDTO request) {
        return projectFundExecutionService.getProjectFundExecutionList(request);
    }
}
