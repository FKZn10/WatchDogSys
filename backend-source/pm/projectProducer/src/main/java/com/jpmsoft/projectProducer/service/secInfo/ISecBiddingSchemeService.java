package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecBiddingSchemeModel;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecBiddingScheme;

public interface ISecBiddingSchemeService extends IService<SecBiddingScheme> {


    BaseResponse createOrUpdateSecBiddingScheme(SecBiddingSchemeModel request);

    BaseResponse getSecBiddingSchemeList(SecBiddingSchemeModel request);

    BaseResponse getSecBiddingSchemeById(String bsId);

    BaseResponse updateSecBiddingScheme(SecBiddingSchemeModel request);
}
