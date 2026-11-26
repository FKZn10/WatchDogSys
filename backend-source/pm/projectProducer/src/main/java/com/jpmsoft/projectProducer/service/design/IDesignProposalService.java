package com.jpmsoft.projectProducer.service.design;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignProposalModel;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignProposal;


public interface IDesignProposalService extends IService<DesignProposal> {


    BaseResponse createOrUpdateDesignProposal(DesignProposalModel request);

    BaseResponse getDesignProposalList(DesignProposalModel request, String uId);

    BaseResponse getDesignProposalListNoAuth(DesignProposalModel request);

    BaseResponse getDesignProposalById(String dpId);
}
