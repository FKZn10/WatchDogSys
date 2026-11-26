package com.jpmsoft.projectProducer.service.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.project.ProjectContractCostControlDTO;

/**
 * 项目施工合同造价控制报表。
 */
public interface ProjectContractCostControlService {
    /**
     * 查询项目所有已审批合同的造价控制明细及合计，仅包括报表上半部分。
     *
     * @param pId 项目 ID
     * @return 项目信息、合同明细及金额合计
     */
    BaseResponse getProjectContractCostControlByPId(String pId);

    /**
     * 查询合同已审批变更汇总表。
     *
     * @param conId 合同 ID
     * @return 变更明细及报审、初审金额合计
     */
    BaseResponse getApprovedConEcSummaryByConId(String conId);

    /**
     * 查询合同未完成审批变更汇总表。
     *
     * @param conId 合同 ID
     * @return 变更明细及报审、初审金额合计
     */
    BaseResponse getPendingConEcSummaryByConId(String conId);

    /**
     * 分页查询未删除、审批结束项目的合同造价控制统计。
     *
     * @param request 项目DTO，支持分页、pId、projectIds，不传项目条件时查询全部
     * @return 当前页完整项目统计数组，外层total为筛选后的项目总数
     */
    BaseResponse getProjectContractCostControlList(ProjectContractCostControlDTO request);
}
