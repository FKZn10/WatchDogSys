package com.jpmsoft.projectConsumer.controller.v1.enghonorpunish;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.enghonorpunish.EngAwardForecastDto;
import com.jpmsoft.projectConsumer.feign.EngHonorPunishFeign;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private EngHonorPunishFeign engHonorPunishFeign;


    @PostMapping("/getEngAwardForecastList")
    public BaseResponse getEngAwardForecastList(@RequestBody EngAwardForecastDto engAwardForecastDto)
    {
        return engHonorPunishFeign.getEngAwardForecastList(engAwardForecastDto);
    }

}

