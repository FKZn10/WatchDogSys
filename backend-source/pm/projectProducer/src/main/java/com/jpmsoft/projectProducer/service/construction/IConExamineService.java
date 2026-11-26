package com.jpmsoft.projectProducer.service.construction;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Construction.ConExamineModel;
import com.jpmsoft.projectProducer.pojo.entity.construction.ConExamine;

public interface IConExamineService extends IService<ConExamine> {

    BaseResponse createOrUpdateConExamine(ConExamineModel request);

    BaseResponse getConExamineList(ConExamineModel request, String uId);

    BaseResponse getConExamineById(String exId);
}
