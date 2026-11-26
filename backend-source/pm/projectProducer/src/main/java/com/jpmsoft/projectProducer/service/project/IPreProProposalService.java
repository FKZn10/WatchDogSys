package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProProposalModel;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProProposal;

public interface IPreProProposalService extends IService<PreProProposal> {

    BaseResponse createOrUpdatePreProProposal(PreProProposalModel request);

    BaseResponse getPreProProposalList(PreProProposalModel request, String uId);

    BaseResponse getPreProProposalStatisticsList(PreProProposalModel request, String uId);

    BaseResponse getPreProProposalById(String ppId);

    BaseResponse approvePreProProposal(String ppId);
}
