package com.jpmsoft.projectProducer.controller.v1.xz;

import com.jpmsoft.common.model.request.xz.ProjectApprovalQueryRequest;
import com.jpmsoft.common.model.response.xz.ProjectApprovalVO;
import com.jpmsoft.projectProducer.config.CommonResponse;
import com.jpmsoft.projectProducer.config.PageResult;
import com.jpmsoft.projectProducer.service.xz.ProjectApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2024/9/12 10:50
 * @Description:
 * @Version: 1.0
 */
@RestController
@RequestMapping("/v1/xz/projectApproval")
public class ProjectApprovalController {

    @Autowired
    private ProjectApprovalService projectApprovalService;

    @PostMapping("/page")
    public CommonResponse<PageResult<ProjectApprovalVO>> page(@RequestBody ProjectApprovalQueryRequest request){
        return CommonResponse.ok(projectApprovalService.page(request));
    }
}
