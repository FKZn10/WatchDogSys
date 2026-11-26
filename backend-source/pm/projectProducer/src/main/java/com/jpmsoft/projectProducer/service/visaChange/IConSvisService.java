package com.jpmsoft.projectProducer.service.visaChange;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.DesignChange.ConSvisRequest;
import com.jpmsoft.projectProducer.pojo.entity.visaChange.ConSvis;

public interface IConSvisService extends IService<ConSvis> {

    BaseResponse createOrUpdateConSvis(ConSvisRequest request);

    BaseResponse getConSvisList(ConSvisRequest request, String uId);

    BaseResponse getConSvisById(String svisId);
}
