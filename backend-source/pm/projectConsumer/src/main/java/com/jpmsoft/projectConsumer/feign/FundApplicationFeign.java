package com.jpmsoft.projectConsumer.feign;

import com.jpmsoft.common.model.request.xz.FundApplicationCreateRequest;
import com.jpmsoft.common.model.request.xz.FundApplicationQueryRequest;
import com.jpmsoft.common.model.response.xz.FundApplicationVO;
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
@FeignClient(name = "project-producer", contextId = "fundApplication")
public interface FundApplicationFeign {

    @PostMapping("/v1/xz/fundApplication/page")
    CommonResponse<PageResult<FundApplicationVO>> page(@RequestBody FundApplicationQueryRequest request);

    @PostMapping("/v1/xz/fundApplication/create")
    CommonResponse<String> create(@RequestBody FundApplicationCreateRequest request);

    @PostMapping("/v1/xz/fundApplication/update")
    CommonResponse<String> update(@RequestBody FundApplicationCreateRequest request);

    @PostMapping("/v1/xz/fundApplication/detail")
    CommonResponse<FundApplicationVO> detail(@RequestBody FundApplicationCreateRequest request);

    @PostMapping("/v1/xz/fundApplication/syncApproveState")
    CommonResponse<Void> syncApproveState(@RequestBody FundApplicationCreateRequest request);

    @PostMapping("/v1/xz/fundApplication/remove")
    CommonResponse<Void> remove(@RequestBody FundApplicationCreateRequest request);
}
