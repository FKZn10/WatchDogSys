package com.jpmsoft.projectConsumer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.ProjectContractCostControlFeign;
import com.jpmsoft.projectConsumer.pojo.dto.project.ProjectContractCostControlDTO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 施工合同造价控制统计及合同变更明细接口。
 */
@RestController
@RequestMapping("/v1/projectContractCostControl")
public class ProjectContractCostControlController {
    @Resource
    private ProjectContractCostControlFeign feign;

    /**
     * 查询一个项目全部已审批合同的造价控制统计。
     *
     * @param pId 项目 ID
     * @return 合同造价控制明细和合计
     */
    @GetMapping("/getProjectContractCostControlByPId")
    public BaseResponse getProjectContractCostControlByPId(@RequestParam("pId") String pId) {
        return feign.getProjectContractCostControlByPId(pId);
    }

    /**
     * 查询合同已审批变更汇总表。
     *
     * @param conId 合同 ID
     * @return 变更明细及报审、初审金额合计
     */
    @GetMapping("/getApprovedConEcSummaryByConId")
    public BaseResponse getApprovedConEcSummaryByConId(@RequestParam("conId") String conId) {
        return feign.getApprovedConEcSummaryByConId(conId);
    }

    /**
     * 查询合同未完成审批变更汇总表。
     *
     * @param conId 合同 ID
     * @return 变更明细及报审、初审金额合计
     */
    @GetMapping("/getPendingConEcSummaryByConId")
    public BaseResponse getPendingConEcSummaryByConId(@RequestParam("conId") String conId) {
        return feign.getPendingConEcSummaryByConId(conId);
    }

    /**
     * 分页查询未删除、审批结束项目的合同造价控制统计。
     *
     * @param request 项目DTO，支持分页、pId、projectIds，不传项目条件时查询全部
     * @return 当前页完整项目统计数组，外层total为筛选后的项目总数
     */
    @PostMapping("/getProjectContractCostControlList")
    public BaseResponse getProjectContractCostControlList(@RequestBody ProjectContractCostControlDTO request) {
        return feign.getProjectContractCostControlList(request);
    }
}
