package com.jpmsoft.projectProducer.service.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.project.ProjectFundExecutionDTO;

/**
 * 基建项目资金执行明细查询。
 */
public interface ProjectFundExecutionService {

    /**
     * 按项目查询基础信息、固定三类概算科目树、合同金额及自筹、国拨支付年度明细和小计。
     *
     * @param pId 项目 ID，不能为空
     * @return 查询结果；项目无效、已结束概算不唯一或科目树异常时返回失败响应
     */
    BaseResponse getProjectFundExecutionByPId(String pId);

    /**
     * 分页查询全部未删除且审批结束的项目及其完整资金执行统计。
     *
     * @param request 分页与项目筛选 DTO，支持 pId 和 projectIds，不传项目条件时查询全部已结束项目
     * @return 当前页项目统计数组和符合条件的项目总数
     */
    BaseResponse getProjectFundExecutionList(ProjectFundExecutionDTO request);
}
