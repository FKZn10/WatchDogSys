package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecChallengeComplaintsRequest;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecChallengeComplaints;

public interface ISecChallengeComplaintsService extends IService<SecChallengeComplaints> {


    BaseResponse createOrUpdateSecChallengeComplaints(SecChallengeComplaintsRequest request);

    BaseResponse getSecChallengeComplaintsList(SecChallengeComplaintsRequest request);

    BaseResponse getSecChallengeComplaintsById(String sccId);

    BaseResponse getSecChallengeComplaintsListOne2Many(SecChallengeComplaintsRequest request);
}
