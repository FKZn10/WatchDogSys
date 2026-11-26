package com.jpmsoft.projectProducer.service.visaChange;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.DesignChange.ConSvVerifyRequest;
import com.jpmsoft.projectProducer.pojo.entity.visaChange.ConSvVerify;

public interface IConSvVerifyService extends IService<ConSvVerify> {

    BaseResponse createOrUpdateConSvVerify(ConSvVerifyRequest request);

    BaseResponse getConSvVerifyList(ConSvVerifyRequest request, String uId);

    BaseResponse getConSvVerifyById(String svvId);
}
