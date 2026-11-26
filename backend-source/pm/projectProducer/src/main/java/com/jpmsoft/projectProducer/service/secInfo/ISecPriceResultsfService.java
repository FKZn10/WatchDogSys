package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecPriceResultsfModel;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecPriceResultsf;

public interface ISecPriceResultsfService extends IService<SecPriceResultsf> {

    BaseResponse createOrUpdateSecPriceResultsf(SecPriceResultsfModel request);

    BaseResponse deleteSecPriceResultsfBySprId(String sprId);

    BaseResponse getSecPriceResultsfBySprId(String sprId);

    BaseResponse getSecPriceResultsfList(SecPriceResultsfModel request);
}
