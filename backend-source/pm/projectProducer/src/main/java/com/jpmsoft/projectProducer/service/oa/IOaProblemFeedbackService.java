package com.jpmsoft.projectProducer.service.oa;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaProblemFeedbackModel;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaProblemFeedback;

/**
 * 问题反馈
 */
public interface IOaProblemFeedbackService extends IService<OaProblemFeedback> {

    BaseResponse createOrUpdateOaProblemFeedback(OaProblemFeedbackModel request);

    BaseResponse getOaProblemFeedbackList(OaProblemFeedbackModel request);

    BaseResponse getOaProblemFeedbackById(String pmfId);

    BaseResponse getOaProblemFeedbackByPmdId(String pmdId);

    BaseResponse approve2RefreshProblemStatus(String pmfId);
}
