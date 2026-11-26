package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecPublicityResultsRequest;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecPublicityResults;

public interface ISecPublicityResultsService extends IService<SecPublicityResults> {


    BaseResponse createOrUpdateSecPublicityResults(SecPublicityResultsRequest request);

    BaseResponse getSecPublicityResultsList(SecPublicityResultsRequest request);

    BaseResponse getSecPublicityResultsById(String prId);

    BaseResponse getSecPublicityResultsListOne2Many(SecPublicityResultsRequest request);
}
