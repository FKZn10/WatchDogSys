package com.jpmsoft.projectProducer.service.oa;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Oa.OaSupervisionFeedbackRequest;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaSupervisionFeedback;

public interface IOaSupervisionFeedbackService extends IService<OaSupervisionFeedback> {

    BaseResponse createOrUpdateOaSupervisionFeedback(OaSupervisionFeedbackRequest request);

    BaseResponse getOaSupervisionFeedbackList(OaSupervisionFeedbackRequest request);

    BaseResponse getOaSupervisionFeedbackBySfId(String sfId);
}
