package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecOpenResultRequest;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecOpenResult;

public interface ISecOpenResultService extends IService<SecOpenResult> {


    BaseResponse createOrUpdateSecOpenResult(SecOpenResultRequest request);

    BaseResponse getSecOpenResultList(SecOpenResultRequest request);

    BaseResponse getSecOpenResultById(String orId);

    BaseResponse getSecOpenResultListOne2Many(SecOpenResultRequest request);
}
