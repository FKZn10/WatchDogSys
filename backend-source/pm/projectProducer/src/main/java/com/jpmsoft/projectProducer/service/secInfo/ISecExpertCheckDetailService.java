package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecExpertCheckDetailModel;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertCheckDetail;

public interface ISecExpertCheckDetailService extends IService<SecExpertCheckDetail> {
    BaseResponse createOrupdateSecExpertCheckDetailAndDtl(SecExpertCheckDetailModel request, String uid);

    BaseResponse getSecExpertCheckDetailAndDtlById(String scdId, String uid);

    BaseResponse getSecExpertCheckDetailAndDtlSelect(String srdId, String uid);

    BaseResponse deleteSecExpertCheckDetailBySrId(String srId, String uid);

}
