package com.jpmsoft.projectConsumer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.ProjectFundExecutionFeign;
import com.jpmsoft.projectConsumer.pojo.dto.project.ProjectFundExecutionDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 基建项目资金执行明细对外接口。
 */
@RestController
@Api(tags = "基建项目资金执行明细")
@RequestMapping("/v1/projectFundExecution")
public class ProjectFundExecutionController {

    @Resource
    private ProjectFundExecutionFeign projectFundExecutionFeign;

    /**
     * 按项目查询基础信息、固定三类概算科目树及合同和自筹、国拨支付汇总。
     *
     * @param pId 项目 ID，必填
     * @return 概算科目明细、自筹实际年度子表和小计
     */
    @ApiOperation("查询基建项目资金执行明细（含自筹、国拨年度汇总）")
    @GetMapping("/getProjectFundExecutionByPId")
    public BaseResponse getProjectFundExecutionByPId(@RequestParam("pId") String pId) {
        return projectFundExecutionFeign.getProjectFundExecutionByPId(pId);
    }

    /**
     * 分页查询全部未删除且审批结束的项目资金执行统计。
     *
     * @param request 分页与项目筛选 DTO，支持 pId 和 projectIds，不传项目条件时查询全部已结束项目
     * @return 当前页完整项目统计数组及项目总数
     */
    @ApiOperation("分页查询已结束项目资金执行统计")
    @PostMapping("/getProjectFundExecutionList")
    public BaseResponse getProjectFundExecutionList(@RequestBody ProjectFundExecutionDTO request) {
        return projectFundExecutionFeign.getProjectFundExecutionList(request);
    }
}
