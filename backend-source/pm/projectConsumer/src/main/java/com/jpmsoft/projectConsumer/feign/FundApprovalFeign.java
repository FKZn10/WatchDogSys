package com.jpmsoft.projectConsumer.feign;

import com.jpmsoft.common.model.request.xz.FundApprovalCreateRequest;
import com.jpmsoft.common.model.request.xz.FundApprovalQueryRequest;
import com.jpmsoft.common.model.response.xz.FundApprovalVO;
import com.jpmsoft.projectConsumer.config.CommonResponse;
import com.jpmsoft.projectConsumer.config.PageResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2024/8/29 14:26
 * @Description:
 * @Version: 1.0
 */
@FeignClient(name = "project-producer", contextId = "fundApproval")
public interface FundApprovalFeign {

    @PostMapping("/v1/xz/fundApproval/page")
    CommonResponse<PageResult<FundApprovalVO>> page(@RequestBody FundApprovalQueryRequest request);

    @PostMapping("/v1/xz/fundApproval/create")
    CommonResponse<String> create(@RequestBody FundApprovalCreateRequest request);

    @PostMapping("/v1/xz/fundApproval/update")
    CommonResponse<String> update(@RequestBody FundApprovalCreateRequest request);

    @PostMapping("/v1/xz/fundApproval/detail")
    CommonResponse<FundApprovalVO> detail(@RequestBody FundApprovalCreateRequest request);

    @PostMapping("/v1/xz/fundApproval/syncApproveState")
    CommonResponse<Void> syncApproveState(@RequestBody FundApprovalCreateRequest request);

    @PostMapping("/v1/xz/fundApproval/remove")
    CommonResponse<Void> remove(@RequestBody FundApprovalCreateRequest request);
}
