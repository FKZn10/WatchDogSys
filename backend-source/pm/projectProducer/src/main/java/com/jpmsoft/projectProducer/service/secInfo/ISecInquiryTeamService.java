package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecInquiryTeamModel;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInquiryTeam;

public interface ISecInquiryTeamService extends IService<SecInquiryTeam> {

    BaseResponse createOrUpdateSecInquiryTeam(SecInquiryTeamModel request);

    BaseResponse deleteSecInquiryTeamBySitId(String sitId);

    BaseResponse getSecInquiryTeamBySitId(String sitId);
}
