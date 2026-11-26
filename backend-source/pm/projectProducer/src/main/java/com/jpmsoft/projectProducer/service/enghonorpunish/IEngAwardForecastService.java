package com.jpmsoft.projectProducer.service.enghonorpunish;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.enghonorpunish.EngAwardForecastDto;
import com.jpmsoft.projectProducer.pojo.entity.enghonorpunish.EngAwardForecast;

public interface IEngAwardForecastService extends IService<EngAwardForecast> {


    BaseResponse getEngAwardForecastList(EngAwardForecastDto engAwardForecastDto);

}
