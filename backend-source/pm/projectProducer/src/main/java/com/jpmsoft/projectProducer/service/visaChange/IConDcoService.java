package com.jpmsoft.projectProducer.service.visaChange;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.DesignChange.ConDcoRequest;
import com.jpmsoft.projectProducer.pojo.entity.visaChange.ConDco;

public interface IConDcoService extends IService<ConDco> {

    BaseResponse createOrUpdateConDco(ConDcoRequest request);

    BaseResponse getConDcoList(ConDcoRequest request, String uId);

    BaseResponse getConDcoListGm(ConDcoRequest request, String uId);

    BaseResponse getConDcoListNoAuth(ConDcoRequest request);

    BaseResponse getConDcoByDcoId(String dcoId);
}
