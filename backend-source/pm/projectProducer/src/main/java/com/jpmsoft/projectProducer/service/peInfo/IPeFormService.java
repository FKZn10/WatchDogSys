package com.jpmsoft.projectProducer.service.peInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.PeInfo.PeFormRequest;
import com.jpmsoft.projectProducer.pojo.entity.peInfo.PeForm;

public interface IPeFormService extends IService<PeForm> {

    BaseResponse createOrUpdatePeForm(PeFormRequest request);

    BaseResponse getPeFormList(PeFormRequest request, String uId);

    BaseResponse deletePeFormByFId(String fId);

    BaseResponse getPeFormCascadeInfo(String fId);
}
