package com.jpmsoft.projectProducer.service.supervision;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Supervisor.ConSupDisclosureRequest;
import com.jpmsoft.projectProducer.pojo.entity.supervision.ConSupDisclosure;

public interface IConSupDisclosureService extends IService<ConSupDisclosure> {

    BaseResponse createOrUpdateConSupDisclosure(ConSupDisclosureRequest request);

    BaseResponse getConSupDisclosureList(ConSupDisclosureRequest request, String uId);

    BaseResponse getConSupDisclosureById(String disId);

}
