package com.jpmsoft.projectConsumer.controller.v1.enghonorpunish;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.enghonorpunish.EngPunishDto;
import com.jpmsoft.projectConsumer.feign.EngHonorPunishFeign;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/v1/engPunish")
public class EngPunishController {

    @Resource
    private EngHonorPunishFeign engHonorPunishFeign;

    @PostMapping("/getEngPunishList")
    public BaseResponse getEngPunishList(@RequestBody EngPunishDto engPunishDto)
    {
        return engHonorPunishFeign.getEngPunishList(engPunishDto);
    }


}

