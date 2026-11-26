package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecBidderEliminationModel;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecBidderElimination;

public interface ISecBidderEliminationService extends IService<SecBidderElimination> {


    BaseResponse createOrUpdateSecBidderElimination(SecBidderEliminationModel request);

    BaseResponse getSecBidderEliminationList(SecBidderEliminationModel request);

    BaseResponse getSecBidderEliminationById(String sbeId);
}
