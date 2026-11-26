package com.jpmsoft.projectProducer.service.materialsEquipment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.MeEnter.MeDetectionGuideModel;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MeDetectionGuide;

public interface IMeDetectionGuideService extends IService<MeDetectionGuide> {

    BaseResponse createOrUpdateMeDetectionGuide(MeDetectionGuideModel model);

    BaseResponse getMeDetectionGuideList(MeDetectionGuideModel model, String uId);

    BaseResponse getMeDetectionGuideByDgId(String dgId);

    BaseResponse approvalData(String dgId);

    BaseResponse setMeDetectionGuideData(String dgId);
}
