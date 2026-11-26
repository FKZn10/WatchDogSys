package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.EngineeringTrainingModel;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.EngineeringTraining;

public interface EngineeringTrainingService extends IService<EngineeringTraining> {
    BaseResponse saveOrUpdateEngineeringTraining(EngineeringTrainingModel request);

    BaseResponse getEngineeringTrainingList(EngineeringTrainingModel request, String token);

    BaseResponse getEngineeringTrainingDetail(String id);

    BaseResponse approvalData(EngineeringTrainingModel request);
}
