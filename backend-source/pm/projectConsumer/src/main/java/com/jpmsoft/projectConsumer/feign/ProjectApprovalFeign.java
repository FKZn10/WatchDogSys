package com.jpmsoft.projectConsumer.feign;

import com.jpmsoft.common.model.request.xz.ProjectApprovalQueryRequest;
import com.jpmsoft.common.model.response.xz.ProjectApprovalVO;
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
@FeignClient(name = "project-producer", contextId = "projectApproval")
public interface ProjectApprovalFeign {

    @PostMapping("/v1/xz/projectApproval/page")
    CommonResponse<PageResult<ProjectApprovalVO>> page(@RequestBody ProjectApprovalQueryRequest request);

}
