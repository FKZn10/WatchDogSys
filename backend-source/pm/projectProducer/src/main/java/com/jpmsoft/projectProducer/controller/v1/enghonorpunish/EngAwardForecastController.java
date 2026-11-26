package com.jpmsoft.projectProducer.controller.v1.enghonorpunish;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.enghonorpunish.EngAwardForecastDto;
import com.jpmsoft.projectProducer.service.enghonorpunish.IEngAwardForecastService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 评奖预报
 *
 * @since 2024-12-03 10:40:05
 */
@RestController
@RequestMapping("/v1/engAwardForecast")
public class EngAwardForecastController {

    @Resource
    private IEngAwardForecastService engAwardForecastService;

    @PostMapping("/getEngAwardForecastList")
    public BaseResponse getEngAwardForecastList(@RequestBody EngAwardForecastDto engAwardForecastDto)
    {
        return engAwardForecastService.getEngAwardForecastList(engAwardForecastDto);
    }

}

