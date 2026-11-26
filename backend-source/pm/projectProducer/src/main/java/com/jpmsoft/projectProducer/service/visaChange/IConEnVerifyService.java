package com.jpmsoft.projectProducer.service.visaChange;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.DesignChange.ConEnVerifyRequest;
import com.jpmsoft.projectProducer.pojo.entity.visaChange.ConEnVerify;

public interface IConEnVerifyService extends IService<ConEnVerify> {


    BaseResponse createOrUpdateConEnVerify(ConEnVerifyRequest request);

    BaseResponse getConEnVerifyList(ConEnVerifyRequest request, String uId);

    BaseResponse getConEnVerifyById(String envId);
}
