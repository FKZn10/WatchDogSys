package com.jpmsoft.projectProducer.service.visaChange;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.DesignChange.ConSvcrRequest;
import com.jpmsoft.projectProducer.pojo.entity.visaChange.ConSvcr;

public interface IConSvcrService extends IService<ConSvcr> {

    BaseResponse createOrUpdateConSvcr(ConSvcrRequest request);

    BaseResponse getConSvcrList(ConSvcrRequest request, String uId);

    BaseResponse getConSvcrById(String svcrId);
}
