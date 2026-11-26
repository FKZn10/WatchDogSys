package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectApprovalModel;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectApproval;

public interface IPreProjectApprovalService extends IService<PreProjectApproval> {
    BaseResponse createOrUpdatePreProjectApproval(PreProjectApprovalModel request);

    BaseResponse getPreProjectApprovalList(PreProjectApprovalModel request);

    BaseResponse getPreProjectApprovalById(PreProjectApprovalModel request);

    BaseResponse approvalData(PreProjectApprovalModel request);
}
