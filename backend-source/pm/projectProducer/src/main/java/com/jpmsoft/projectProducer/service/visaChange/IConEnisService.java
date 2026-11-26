package com.jpmsoft.projectProducer.service.visaChange;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.DesignChange.ConEnisRequest;
import com.jpmsoft.projectProducer.pojo.entity.visaChange.ConEnis;

public interface IConEnisService extends IService<ConEnis> {


    BaseResponse createOrUpdateConEnis(ConEnisRequest request);

    BaseResponse getConEnisList(ConEnisRequest request, String uId);

    BaseResponse getConEnisListGm(ConEnisRequest request, String uId);

    BaseResponse getConEnisListNoAuth(ConEnisRequest request);

    BaseResponse getConEnisById(String enisId);
}
