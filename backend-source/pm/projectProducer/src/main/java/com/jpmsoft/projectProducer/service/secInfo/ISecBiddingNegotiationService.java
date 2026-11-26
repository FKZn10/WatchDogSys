package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecBiddingNegotiationDto;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecBiddingNegotiation;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;

public interface ISecBiddingNegotiationService extends IService<SecBiddingNegotiation> {
    BaseResponse createOrUpdateSecBiddingNegotiation(SecBiddingNegotiationDto request);

    BaseResponse getSecBiddingNegotiationListClassInfo(SecBiddingNegotiationDto request, LogUserInfoVo user);

    BaseResponse getSecBiddingNegotiationListClass(SecBiddingNegotiationDto request, LogUserInfoVo user);

    BaseResponse getSecBiddingNegotiationByBnId(String bnId);

    BaseResponse deleteSecBiddingNegotiationByBnId(String bnId);


}
