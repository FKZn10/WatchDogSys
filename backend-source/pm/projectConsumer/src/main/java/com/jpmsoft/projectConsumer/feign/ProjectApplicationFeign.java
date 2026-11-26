package com.jpmsoft.projectConsumer.feign;

import com.jpmsoft.common.model.request.InvestmentControl.InvCompleteMonthRequest;
import com.jpmsoft.common.model.request.xz.ProjectApplicationCreateRequest;
import com.jpmsoft.common.model.request.xz.ProjectApplicationQueryRequest;
import com.jpmsoft.common.model.response.xz.ProjectApplicationVO;
import com.jpmsoft.projectConsumer.config.CommonResponse;
import com.jpmsoft.projectConsumer.config.PageResult;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2024/8/29 14:26
 * @Description:
 * @Version: 1.0
 */
@FeignClient(name = "project-producer", contextId = "projectApplication")
public interface ProjectApplicationFeign {

    @PostMapping("/v1/xz/projectApplication/page")
    CommonResponse<PageResult<ProjectApplicationVO>> page(@RequestBody ProjectApplicationQueryRequest request);

    @PostMapping("/v1/xz/projectApplication/exportExcel")
    Response exportExcel(@RequestBody ProjectApplicationQueryRequest request);

    @PostMapping("/v1/xz/projectApplication/create")
    CommonResponse<String> create(@RequestBody ProjectApplicationCreateRequest request);

    @PostMapping("/v1/xz/projectApplication/update")
    CommonResponse<String> update(@RequestBody ProjectApplicationCreateRequest request);

    @PostMapping("/v1/xz/projectApplication/detail")
    CommonResponse<ProjectApplicationVO> detail(@RequestBody ProjectApplicationCreateRequest request);

    @PostMapping("/v1/xz/projectApplication/syncApproveState")
    CommonResponse<Void> syncApproveState(@RequestBody ProjectApplicationCreateRequest request);

    @PostMapping("/v1/xz/projectApplication/taskStatus")
    CommonResponse<Void> taskStatus(@RequestBody ProjectApplicationCreateRequest request);

    @PostMapping("/v1/xz/projectApplication/remove")
    CommonResponse<Void> remove(@RequestBody ProjectApplicationCreateRequest request);
}
