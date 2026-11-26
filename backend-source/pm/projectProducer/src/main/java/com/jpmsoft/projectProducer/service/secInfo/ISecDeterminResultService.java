package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecDeterminResultRequest;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecDeterminResult;

public interface ISecDeterminResultService extends IService<SecDeterminResult> {

    BaseResponse createOrUpdateSecDeterminResult(SecDeterminResultRequest request);

    BaseResponse getSecDeterminResultList(SecDeterminResultRequest request);

    BaseResponse getSecDeterminResultById(String drId);

    /**
     * 根据标段id查询审批通过的最新的一条定标报告信息
     *
     * @param secId
     * @return
     */
    BaseResponse getFirstDeterminResultBySecId(String secId);
}
