package com.jpmsoft.projectConsumer.feign;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.enghonorpunish.EngAwardForecastDto;
import com.jpmsoft.common.model.requestAndResponse.enghonorpunish.EngHonorDto;
import com.jpmsoft.common.model.requestAndResponse.enghonorpunish.EngPunishDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 工程荣誉与处罚
 */
@FeignClient(name = "project-producer", contextId = "engHonorPunish")
public interface EngHonorPunishFeign {

    @PostMapping("/v1/engAwardForecast/getEngAwardForecastList")
    BaseResponse getEngAwardForecastList(@RequestBody EngAwardForecastDto engAwardForecastDto);

    @PostMapping("/v1/engHonor/getEngHonorList")
    BaseResponse getEngHonorList(@RequestBody EngHonorDto engHonorDto);

    @PostMapping("/v1/engPunish/getEngPunishList")
    BaseResponse getEngPunishList(@RequestBody EngPunishDto engPunishDto);

}
