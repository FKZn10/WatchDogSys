package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecCalibrationSchemeModel;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecCalibrationScheme;

public interface ISecCalibrationSchemeService extends IService<SecCalibrationScheme> {

    BaseResponse createOrUpdateSecCalibrationScheme(SecCalibrationSchemeModel request);

    BaseResponse getSecCalibrationSchemeList(SecCalibrationSchemeModel request);

    BaseResponse getSecCalibrationSchemeById(String scsId);

}
