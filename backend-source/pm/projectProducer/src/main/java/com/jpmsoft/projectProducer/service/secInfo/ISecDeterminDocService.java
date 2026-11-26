package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecDeterminDocRequest;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecDeterminDoc;

public interface ISecDeterminDocService extends IService<SecDeterminDoc> {


    BaseResponse createOrUpdateDeterminDoc(SecDeterminDocRequest request);

    BaseResponse cancelSecDeterminDocByBdId(String bdId);

    BaseResponse getSecDeterminDocByBdId(String bdId);
}
