package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecBidSecurityModel;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecBidSecurity;

public interface ISecBidSecurityService extends IService<SecBidSecurity> {

    BaseResponse createOrUpdateSecBidSecurity(SecBidSecurityModel request);

    BaseResponse getSecBidSecurityList(SecBidSecurityModel request);

    BaseResponse getSecBidSecurityById(String sbsId);
}
