package com.jpmsoft.projectConsumer.feign;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.project.ProjectFundExecutionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 基建项目资金执行明细服务调用。
 */
@FeignClient(name = "project-producer", contextId = "projectFundExecution")
public interface ProjectFundExecutionFeign {

    /**
     * 调用生产者，查询指定项目按概算科目汇总的资金执行明细。
     *
     * @param pId 项目 ID
     * @return 项目基础信息、概算科目树、合同金额和自筹、国拨年度子表及小计
     */
    @GetMapping("/v1/projectFundExecution/getProjectFundExecutionByPId")
    BaseResponse getProjectFundExecutionByPId(@RequestParam("pId") String pId);

    /**
     * 调用生产者，分页查询全部未删除且审批结束的项目资金执行统计。
     *
     * @param request 分页与项目筛选 DTO，支持 pId 和 projectIds，不传项目条件时查询全部已结束项目
     * @return 当前页完整项目统计数组及项目总数
     */
    @PostMapping("/v1/projectFundExecution/getProjectFundExecutionList")
    BaseResponse getProjectFundExecutionList(@RequestBody ProjectFundExecutionDTO request);
}
