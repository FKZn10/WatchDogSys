package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecBrightMoonModel;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecBrightMoon;

public interface ISecBrightMoonService extends IService<SecBrightMoon> {


    BaseResponse createOrUpdateSecBrightMoon(SecBrightMoonModel request);

    BaseResponse getSecBrightMoonList(SecBrightMoonModel request);

    BaseResponse getSecBrightMoonById(String sbmId);
}
