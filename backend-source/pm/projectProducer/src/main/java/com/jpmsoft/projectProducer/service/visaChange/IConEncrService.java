package com.jpmsoft.projectProducer.service.visaChange;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.DesignChange.ConEncrRequest;
import com.jpmsoft.projectProducer.pojo.entity.visaChange.ConEncr;

public interface IConEncrService extends IService<ConEncr> {


    BaseResponse createOrUpdateConEncr(ConEncrRequest request);

    BaseResponse getConEncrList(ConEncrRequest request, String uId);

    BaseResponse getConEncrListNoAuth(ConEncrRequest request);

    BaseResponse getConEncrById(String encrId);
}
