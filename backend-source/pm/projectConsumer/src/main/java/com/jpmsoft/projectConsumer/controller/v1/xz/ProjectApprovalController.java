package com.jpmsoft.projectConsumer.controller.v1.xz;

import com.jpmsoft.common.model.request.xz.ProjectApplicationCreateRequest;
import com.jpmsoft.common.model.request.xz.ProjectApplicationQueryRequest;
import com.jpmsoft.common.model.request.xz.ProjectApprovalQueryRequest;
import com.jpmsoft.common.model.response.xz.ProjectApplicationVO;
import com.jpmsoft.common.model.response.xz.ProjectApprovalVO;
import com.jpmsoft.projectConsumer.config.CommonResponse;
import com.jpmsoft.projectConsumer.config.PageResult;
import com.jpmsoft.projectConsumer.feign.ProjectApplicationFeign;
import com.jpmsoft.projectConsumer.feign.ProjectApprovalFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2024/8/29 14:13
 * @Description:
 * @Version: 1.0
 */
@RestController
@RequestMapping("/v1/xz/projectApproval")
public class ProjectApprovalController {

    @Autowired
    private ProjectApprovalFeign projectApprovalFeign;

    @PostMapping("/page")
    public CommonResponse<PageResult<ProjectApprovalVO>> page(@RequestBody ProjectApprovalQueryRequest request){
        return projectApprovalFeign.page(request);
    }
}
