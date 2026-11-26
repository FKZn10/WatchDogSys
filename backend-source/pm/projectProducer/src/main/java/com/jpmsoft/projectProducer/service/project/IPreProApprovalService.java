package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProApprovalModel;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProApproval;

import java.util.List;

public interface IPreProApprovalService extends IService<PreProApproval> {

    BaseResponse createOrUpdatePreProApproval(PreProApprovalModel request);

    BaseResponse getPreProApprovalList(PreProApprovalModel request, String uId);

    BaseResponse getPreProApprovalStatisticsList(PreProApprovalModel request, String uId);

    BaseResponse getPreProApprovalById(String paId);

    BaseResponse getPreProApprovalByPid(String pId);

    BaseResponse getPreProApprovalWYById(String paId);

    BaseResponse getPreProApprovalDtlByPIds(List<String> pIds);

}
