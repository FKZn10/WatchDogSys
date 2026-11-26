package com.jpmsoft.projectProducer.service.construction;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Construction.ConCompleteModel;
import com.jpmsoft.projectProducer.pojo.entity.construction.ConComplete;

public interface IConCompleteService extends IService<ConComplete> {

    BaseResponse createOrUpdateConComplete(ConCompleteModel request);

    BaseResponse getConCompleteList(ConCompleteModel request, String uId);

    BaseResponse getConCompleteById(String coId);
}
