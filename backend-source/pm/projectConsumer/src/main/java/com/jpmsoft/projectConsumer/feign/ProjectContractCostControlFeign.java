package com.jpmsoft.projectConsumer.feign;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.project.ProjectContractCostControlDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 施工合同造价控制统计服务调用。
 */
@FeignClient(name = "project-producer", contextId = "projectContractCostControl")
public interface ProjectContractCostControlFeign {
    /**
     * 查询一个项目的已审批合同造价控制统计。
     *
     * @param pId 项目 ID
     * @return 合同明细和合计
     */
    @GetMapping("/v1/projectContractCostControl/getProjectContractCostControlByPId")
    BaseResponse getProjectContractCostControlByPId(@RequestParam("pId") String pId);

    /**
     * 查询合同已审批变更汇总表。
     *
     * @param conId 合同 ID
     * @return 变更明细及报审、初审金额合计
     */
    @GetMapping("/v1/projectContractCostControl/getApprovedConEcSummaryByConId")
    BaseResponse getApprovedConEcSummaryByConId(@RequestParam("conId") String conId);

    /**
     * 查询合同未完成审批变更汇总表。
     *
     * @param conId 合同 ID
     * @return 变更明细及报审、初审金额合计
     */
    @GetMapping("/v1/projectContractCostControl/getPendingConEcSummaryByConId")
    BaseResponse getPendingConEcSummaryByConId(@RequestParam("conId") String conId);

    /**
     * 分页查询未删除、审批结束项目的合同造价控制统计。
     *
     * @param request 项目DTO，支持分页、pId、projectIds，不传项目条件时查询全部
     * @return 当前页完整项目统计数组，外层total为筛选后的项目总数
     */
    @PostMapping("/v1/projectContractCostControl/getProjectContractCostControlList")
    BaseResponse getProjectContractCostControlList(@RequestBody ProjectContractCostControlDTO request);
}
