package com.jpmsoft.projectProducer.service.xz;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.xz.ProjectApprovalQueryRequest;
import com.jpmsoft.common.model.response.xz.ProjectApprovalVO;
import com.jpmsoft.projectProducer.config.PageResult;
import com.jpmsoft.projectProducer.pojo.entity.xz.ProjectApproval;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2024/9/12 10:48
 * @Description:
 * @Version: 1.0
 */
public interface ProjectApprovalService extends IService<ProjectApproval> {

    PageResult<ProjectApprovalVO> page(ProjectApprovalQueryRequest request);
}
