package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecBrightMoonModel;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecCalibrationMoon;

public interface ISecCalibrationMoonService extends IService<SecCalibrationMoon> {


    BaseResponse createOrUpdateSecCalibrationMoon(SecBrightMoonModel request);

    BaseResponse getSecCalibrationMoonList(SecBrightMoonModel request);

    BaseResponse getSecCalibrationMoonById(String sbmId);
}
